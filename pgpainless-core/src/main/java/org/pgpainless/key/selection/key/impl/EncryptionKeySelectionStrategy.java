/*
 * Copyright 2018 Paul Schaub.
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
package org.pgpainless.key.selection.key.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;

import org.bouncycastle.openpgp.PGPPublicKey;
import org.pgpainless.algorithm.KeyFlag;
import org.pgpainless.algorithm.PublicKeyAlgorithm;
import org.pgpainless.key.selection.key.PublicKeySelectionStrategy;

/**
 * Key Selection Strategy that only accepts {@link PGPPublicKey}s which are capable of encryption.
 */
public class EncryptionKeySelectionStrategy extends PublicKeySelectionStrategy {

    public static final Logger LOGGER = Logger.getLogger(EncryptionKeySelectionStrategy.class.getName());

    private final HasAnyKeyFlagSelectionStrategy.PublicKey keyFlagSelector;

    public EncryptionKeySelectionStrategy(KeyFlag... flags) {
        this.keyFlagSelector = new HasAnyKeyFlagSelectionStrategy.PublicKey(flags);
    }

    @Override
    public boolean accept(@Nonnull PGPPublicKey key) {
        if (!key.isEncryptionKey()) {
            LOGGER.log(Level.FINE, "Rejecting key " + Long.toHexString(key.getKeyID()) + " as its algorithm (" +
                    PublicKeyAlgorithm.fromId(key.getAlgorithm()) + ") is not suitable of encryption.");
            return false;
        }
        if (!keyFlagSelector.accept(key)) {
            LOGGER.log(Level.FINE, "Rejecting key " + Long.toHexString(key.getKeyID()) + " as it does not the appropriate encryption key flags.");
            return false;
        }

        return true;
    }
}
