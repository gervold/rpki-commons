package net.ripe.commons.provisioning.message.query;

import net.ripe.certification.client.xml.XStreamXmlSerializer;
import net.ripe.commons.provisioning.message.ProvisioningPayloadXmlSerializerBuilder;

public class ListQueryPayloadSerializerBuilder extends ProvisioningPayloadXmlSerializerBuilder<ListQueryPayloadWrapper> {

    public ListQueryPayloadSerializerBuilder() {
        super(ListQueryPayloadWrapper.class);
    }

    @Override
    public XStreamXmlSerializer<ListQueryPayloadWrapper> build() {
        getXStream().processAnnotations(ListQueryPayloadWrapper.class);
        return super.build();
    }
}
