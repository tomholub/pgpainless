/*
 * Copyright 2020 Paul Schaub.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.pgpainless.algorithm;

public enum CertificationLevel {

    /**
     * Generic certification of a User ID and Public-Key packet.
     * The issuer of this certification does not make any particular
     * assertion as to how well the certifier has checked that the owner
     * of the key is in fact the person described by the User ID.
     */
    GENERIC_CERTIFICATION(0x10),

    /**
     * Persona certification of a User ID and Public-Key packet.
     * The issuer of this certification has not done any verification of
     * the claim that the owner of this key is the User ID specified.
     */
    NO_CERTIFICATION(0x11),

    /**
     * Casual certification of a User ID and Public-Key packet.
     * The issuer of this certification has done some casual
     * verification of the claim of identity.
     */
    CASUAL_CERTIFICATION(0x12),

    /**
     * Positive certification of a User ID and Public-Key packet.
     * The issuer of this certification has done substantial
     * verification of the claim of identity.
     */
    POSITIVE_CERTIFICATION(0x13),
    ;

    private final int code;

    CertificationLevel(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
