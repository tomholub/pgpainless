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
package org.pgpainless.symmetric_encryption;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.util.io.Streams;
import org.junit.jupiter.api.Test;
import org.pgpainless.PGPainless;
import org.pgpainless.decryption_verification.DecryptionStream;
import org.pgpainless.encryption_signing.EncryptionStream;
import org.pgpainless.util.Passphrase;

public class MultiPassphraseSymmetricEncryptionTest {

    @Test
    public void test() throws IOException, PGPException {
        String message = "Here we test if during decryption of a message that was encrypted with two passphrases, " +
                "the decryptor finds the session key encrypted for the right passphrase.";
        ByteArrayInputStream plaintextIn = new ByteArrayInputStream(message.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream ciphertextOut = new ByteArrayOutputStream();
        EncryptionStream encryptor = PGPainless.encryptAndOrSign()
                .onOutputStream(ciphertextOut)
                .forPassphrases(Passphrase.fromPassword("p1"), Passphrase.fromPassword("p2"))
                .usingSecureAlgorithms()
                .doNotSign()
                .noArmor();

        Streams.pipeAll(plaintextIn, encryptor);
        encryptor.close();

        byte[] ciphertext = ciphertextOut.toByteArray();

        // decrypting the p1 package with p2 first will not work. Test if it is handled correctly.
        for (Passphrase passphrase : new Passphrase[] {Passphrase.fromPassword("p2"), Passphrase.fromPassword("p1")}) {
            DecryptionStream decryptor = PGPainless.decryptAndOrVerify().onInputStream(new ByteArrayInputStream(ciphertext))
                    .decryptWith(passphrase)
                    .doNotVerify()
                    .build();

            ByteArrayOutputStream plaintextOut = new ByteArrayOutputStream();

            Streams.pipeAll(decryptor, plaintextOut);

            decryptor.close();
        }
    }
}
