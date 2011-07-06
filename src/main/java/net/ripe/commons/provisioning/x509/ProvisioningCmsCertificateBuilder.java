package net.ripe.commons.provisioning.x509;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.security.auth.x500.X500Principal;

import net.ripe.commons.certification.ValidityPeriod;
import net.ripe.commons.certification.x509cert.X509CertificateBuilderHelper;

import org.bouncycastle.asn1.x509.KeyUsage;
import org.joda.time.DateTime;


public class ProvisioningCmsCertificateBuilder {

    private static final int DEFAULT_VALIDITY_TIME_DAYS_FROM_NOW = 1;

    private X509CertificateBuilderHelper builderHelper;

    public ProvisioningCmsCertificateBuilder() {
        builderHelper = new X509CertificateBuilderHelper();
    }

    public ProvisioningCmsCertificateBuilder withSignatureProvider(String signatureProvider) {
        builderHelper.withSignatureProvider(signatureProvider);
        return this;
    }

    public ProvisioningCmsCertificateBuilder withSerial(BigInteger serial) {
        builderHelper.withSerial(serial);
        return this;
    }

    public ProvisioningCmsCertificateBuilder withSubjectDN(X500Principal subjectDN) {
        builderHelper.withSubjectDN(subjectDN);
        return this;
    }

    public ProvisioningCmsCertificateBuilder withIssuerDN(X500Principal issuerDN) {
        builderHelper.withIssuerDN(issuerDN);
        return this;
    }

    public ProvisioningCmsCertificateBuilder withPublicKey(PublicKey publicKey) {
        builderHelper.withPublicKey(publicKey);
        return this;
    }

    public ProvisioningCmsCertificateBuilder withSigningKeyPair(KeyPair signingKey) {
        builderHelper.withSigningKeyPair(signingKey);
        return this;
    }

    public ProvisioningCmsCertificate build() {
        setUpImplicitRequirementsForBuilderHelper();
        return new ProvisioningCmsCertificate(builderHelper.generateCertificate());
    }

    private void setUpImplicitRequirementsForBuilderHelper() {
        builderHelper.withCa(false);
        builderHelper.withKeyUsage(KeyUsage.digitalSignature);
        builderHelper.withAuthorityKeyIdentifier(true);
        builderHelper.withSubjectKeyIdentifier(true);
        builderHelper.withValidityPeriod(new ValidityPeriod(new DateTime(), new DateTime().plusDays(DEFAULT_VALIDITY_TIME_DAYS_FROM_NOW)));
    }
}