package net.ripe.commons.provisioning.message.common;

import java.net.URISyntaxException;

import net.ripe.commons.provisioning.message.common.GenericClassElementBuilder;

import org.junit.Test;

public class GenericClassElementBuilderTest {
    
    // http://tools.ietf.org/html/draft-ietf-sidr-rescerts-provisioning-09#section-3.3
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBuildWithoutRsyncURI() throws URISyntaxException {
        GenericClassElementBuilder builder = new GenericClassElementBuilder();
        builder.withClassName("a classname");
        builder.withCertificateAuthorityUri("http://some/other");
        builder.buildResourceClassListResponseClassElement();
    }
}
