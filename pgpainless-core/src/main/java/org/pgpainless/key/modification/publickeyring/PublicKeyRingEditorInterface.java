package org.pgpainless.key.modification.publickeyring;

import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPublicKeyRing;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.pgpainless.algorithm.CertificationLevel;
import org.pgpainless.key.protection.SecretKeyRingProtector;

public interface PublicKeyRingEditorInterface {

    WithSecretKey addTrustSignature(CertificationLevel certificationLevel);

    interface WithSecretKey {
        PublicKeyRingEditorInterface withSecretKey(PGPSecretKey secretKey,
                                                   SecretKeyRingProtector protector) throws PGPException;
    }



    PGPPublicKeyRing done();
}
