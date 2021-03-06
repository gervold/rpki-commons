/**
 * The BSD License
 *
 * Copyright (c) 2010-2018 RIPE NCC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   - Neither the name of the RIPE NCC nor the names of its contributors may be
 *     used to endorse or promote products derived from this software without
 *     specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.ripe.rpki.commons.crypto.cms.ghostbuster;

import com.google.common.base.Charsets;
import net.ripe.rpki.commons.crypto.cms.RpkiSignedObjectBuilder;
import net.ripe.rpki.commons.crypto.x509cert.X509ResourceCertificate;
import net.ripe.rpki.commons.validation.ValidationResult;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

/**
 * Creates a RoaCms using the DER encoding specified in the ROA format standard.
 *
 * @see <a href="http://tools.ietf.org/html/draft-ietf-sidr-roa-format-03">ROA format</a>
 */
public class GhostbustersCmsBuilder extends RpkiSignedObjectBuilder {

    private X509ResourceCertificate certificate;
    private String vCardPayload;
    private String signatureProvider;


    public GhostbustersCmsBuilder withCertificate(X509ResourceCertificate certificate) {
        this.certificate = certificate;
        return this;
    }

    public GhostbustersCmsBuilder withVCardPayload(String vCardPayload) {
        this.vCardPayload = vCardPayload;
        return this;
    }

    public GhostbustersCmsBuilder withSignatureProvider(String signatureProvider) {
        this.signatureProvider = signatureProvider;
        return this;
    }

    public GhostbustersCms build(PrivateKey privateKey) {
        String location = "unknown.gbr";
        GhostbustersCmsParser parser = new GhostbustersCmsParser();
        parser.parse(ValidationResult.withLocation(location), getEncoded(privateKey));
        return parser.getGhostbustersCms();
    }

    public byte[] getEncoded(PrivateKey privateKey) {
        return generateCms(certificate.getCertificate(), privateKey, signatureProvider, GhostbustersCms.CONTENT_TYPE, vCardPayload.getBytes(StandardCharsets.UTF_8));
    }
}
