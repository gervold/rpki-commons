package net.ripe.commons.provisioning.payload.common;

import java.net.URI;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import net.ripe.commons.certification.x509cert.X509ResourceCertificate;
import net.ripe.ipresource.IpResource;
import net.ripe.ipresource.IpResourceSet;
import net.ripe.ipresource.IpResourceType;

import org.apache.commons.lang.builder.ToStringBuilder;

@XStreamConverter(CertificateElementConverter.class)
public class CertificateElement {
    
    private List<URI> issuerCertificatePublicationLocationUris;

    private IpResourceSet allocatedAsn;

    private IpResourceSet allocatedIpv4;

    private IpResourceSet allocatedIpv6;

    private X509ResourceCertificate certificate;

    // Setters
    CertificateElement setIssuerCertificatePublicationLocation(List<URI> issuerCertificatePublicationLocation) {   // NOPMD no clone of array stored
        this.issuerCertificatePublicationLocationUris = issuerCertificatePublicationLocation;
        return this;
    }
    
    CertificateElement setCertificate(X509ResourceCertificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public CertificateElement setIpResourceSet(IpResourceSet ipResourceSet) {
        allocatedAsn = new IpResourceSet();
        allocatedIpv4 = new IpResourceSet();
        allocatedIpv6 = new IpResourceSet();
        
        Iterator<IpResource> iter = ipResourceSet.iterator();
        while(iter.hasNext()) {
            IpResource resource = iter.next();
            if (resource.getType().equals(IpResourceType.ASN)) {
                allocatedAsn.add(resource);
            } else if (resource.getType().equals(IpResourceType.IPv4)) {
                allocatedIpv4.add(resource);
            } else if (resource.getType().equals(IpResourceType.IPv6)) {
                allocatedIpv6.add(resource);
            }
        }
        
        return this;
    }

    // Getters
    public List<URI> getIssuerCertificatePublicationUris() {
        return issuerCertificatePublicationLocationUris;
    }

    public IpResourceSet getAllocatedAsn() {
        return allocatedAsn;
    }

    public IpResourceSet getAllocatedIpv4() {
        return allocatedIpv4;
    }

    public IpResourceSet getAllocatedIpv6() {
        return allocatedIpv6;
    }

    public X509ResourceCertificate getCertificate() {
        return certificate;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}