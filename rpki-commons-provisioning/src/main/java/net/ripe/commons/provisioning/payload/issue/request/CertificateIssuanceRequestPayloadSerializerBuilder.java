package net.ripe.commons.provisioning.payload.issue.request;

import net.ripe.certification.client.xml.XStreamXmlSerializer;
import net.ripe.commons.provisioning.payload.ProvisioningPayloadXmlSerializerBuilder;

public class CertificateIssuanceRequestPayloadSerializerBuilder extends ProvisioningPayloadXmlSerializerBuilder<CertificateIssuanceRequestPayload> {

    public CertificateIssuanceRequestPayloadSerializerBuilder() {
        super(CertificateIssuanceRequestPayload.class);
    }

    @Override
    public XStreamXmlSerializer<CertificateIssuanceRequestPayload> build() {
        getXStream().processAnnotations(CertificateIssuanceRequestPayload.class);
        return super.build();
    }
}