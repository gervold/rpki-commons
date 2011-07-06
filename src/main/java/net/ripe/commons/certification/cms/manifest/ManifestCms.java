package net.ripe.commons.certification.cms.manifest;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import net.ripe.commons.certification.cms.RpkiSignedObject;
import net.ripe.commons.certification.cms.RpkiSignedObjectInfo;
import net.ripe.commons.certification.util.Specification;
import net.ripe.commons.certification.validation.objectvalidators.X509ResourceCertificateValidator;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.io.DigestOutputStream;
import org.joda.time.DateTime;

/**
 * A manifest of files published by a CA certificate.
 * <p/>
 * Use the {@link ManifestCmsBuilder} or {@link ManifestCmsParser} to construct this object.
 */
public class ManifestCms extends RpkiSignedObject {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_VERSION = 0;

    public static final String CONTENT_TYPE = "1.2.840.113549.1.9.16.1.26";

    public static final String FILE_HASH_ALGORITHM = CMSSignedDataGenerator.DIGEST_SHA256;

    private Map<String, byte[]> files;

    private ManifestCmsGeneralInfo manifestCmsGeneralInfo;

    ManifestCms(RpkiSignedObjectInfo cmsObjectData, ManifestCmsGeneralInfo manifestCmsGeneralInfo, Map<String, byte[]> files) {
        super(cmsObjectData);
        this.manifestCmsGeneralInfo = manifestCmsGeneralInfo;
        this.files = files;
    }

    public int getVersion() {
        return manifestCmsGeneralInfo.getVersion();
    }

    public BigInteger getNumber() {
        return manifestCmsGeneralInfo.getNumber();
    }

    public String getFileHashAlgorithm() {
        return manifestCmsGeneralInfo.getFileHashAlgorithm();
    }

    public DateTime getThisUpdateTime() {
        return manifestCmsGeneralInfo.getThisUpdateTime();
    }

    public DateTime getNextUpdateTime() {
        return manifestCmsGeneralInfo.getNextUpdateTime();
    }

    public int size() {
        return files.size();
    }

    public boolean containsFile(String fileName) {
        return files.containsKey(fileName);
    }

    public Map<String, byte[]> getFiles() {
        return files;
    }

    public Set<String> getFileNames() {
        return files.keySet();
    }

    @Override
    public URI getCrlUri() {
        return getCertificate().findFirstRsyncCrlDistributionPoint();
    }

    @Override
    public URI getParentCertificateUri() {
        return getCertificate().getParentCertificateUri();
    }

    /**
     * @deprecated use {@link #verifyFileContents(String, byte[])} or {@link #getFileContentSpecification(String)}.
     */
    @Deprecated
    public byte[] getHash(String fileName) {
        return files.get(fileName);
    }

    public boolean verifyFileContents(String fileName, byte[] contents) {
        return getFileContentSpecification(fileName).isSatisfiedBy(contents);
    }

    public FileContentSpecification getFileContentSpecification(String fileName) {
        Validate.isTrue(containsFile(fileName));
        return new FileContentSpecification(getHash(fileName));
    }

    public static ManifestCms parseDerEncoded(byte[] encoded) {
        ManifestCmsParser parser = new ManifestCmsParser();
        parser.parse("<null>", encoded);
        return parser.getManifestCms();
    }

    @Override
    public void validate(String location, X509ResourceCertificateValidator validator) {
        ManifestCmsParser parser = new ManifestCmsParser(validator.getValidationResult());
        parser.parse(location, getEncoded());
        if (parser.getValidationResult().hasFailures()) {
            return;
        }

        validator.validate(location, getCertificate());
    }

    public static byte[] hashContents(byte[] contents) {
        NullOutputStream fileOut = null;
        DigestOutputStream digestOut = null;
        try {
            Digest digest = new SHA256Digest();
            fileOut = new NullOutputStream();
            digestOut = new DigestOutputStream(fileOut, digest);

            digestOut.write(contents);
            digestOut.flush();

            byte[] digestValue = new byte[digest.getDigestSize()];
            digest.doFinal(digestValue, 0);
            return digestValue;
        } catch (IOException e) {
            throw new ManifestCmsException(e);
        } finally {
            if (digestOut != null) {
                IOUtils.closeQuietly(digestOut);
            } else if (fileOut != null) {
                IOUtils.closeQuietly(fileOut);
            }
        }
    }

    public static class FileContentSpecification implements Specification<byte[]> {
        private byte[] hash;

        public FileContentSpecification(byte[] hash) { //NOPMD - ArrayIsStoredDirectly
            this.hash = hash;
        }

        @Override
        public boolean isSatisfiedBy(byte[] contents) {
            return Arrays.equals(hash, hashContents(contents));
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + Arrays.hashCode(hash);
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final FileContentSpecification other = (FileContentSpecification) obj;
            if (!Arrays.equals(hash, other.hash)) {
                return false;
            }
            return true;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("hash", hash).toString();
        }
    }

}