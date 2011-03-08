package net.ripe.certification.client.xml.converters;

import net.ripe.commons.certification.x509cert.X509ResourceCertificateParser;
import net.ripe.commons.certification.x509cert.X509ResourceCertificate;

import org.apache.commons.lang.Validate;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class X509ResourceCertificateConverter implements Converter {

	@SuppressWarnings("rawtypes")
	@Override
    public boolean canConvert(Class type) {
        return X509ResourceCertificate.class.equals(type);
    }

	@Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        X509ResourceCertificate certificate = (X509ResourceCertificate) source;
        writer.startNode("encoded");
        context.convertAnother(certificate.getEncoded());
        writer.endNode();
    }

	@Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        Validate.isTrue("encoded".equals(reader.getNodeName()));
        byte[] encoded = (byte[]) context.convertAnother(null, byte[].class);
        reader.moveUp();
        X509ResourceCertificateParser parser = new X509ResourceCertificateParser();
        parser.parse("encoded", encoded);
        return parser.getCertificate();
    }
}
