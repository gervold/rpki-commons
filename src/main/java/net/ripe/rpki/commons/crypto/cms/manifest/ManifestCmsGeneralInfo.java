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
package net.ripe.rpki.commons.crypto.cms.manifest;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.math.BigInteger;

public class ManifestCmsGeneralInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int version;
    private BigInteger number;
    private DateTime thisUpdateTime;
    private DateTime nextUpdateTime;
    private String fileHashAlgorithm;

    public ManifestCmsGeneralInfo(int version, BigInteger number, DateTime thisUpdateTime, DateTime nextUpdateTime, String fileHashAlgorithm) {
        this.version = version;
        this.number = number;
        this.thisUpdateTime = thisUpdateTime;
        this.nextUpdateTime = nextUpdateTime;
        this.fileHashAlgorithm = fileHashAlgorithm;
    }

    public int getVersion() {
        return version;
    }

    public BigInteger getNumber() {
        return number;
    }

    public DateTime getThisUpdateTime() {
        return thisUpdateTime;
    }

    public DateTime getNextUpdateTime() {
        return nextUpdateTime;
    }

    public String getFileHashAlgorithm() {
        return fileHashAlgorithm;
    }

}
