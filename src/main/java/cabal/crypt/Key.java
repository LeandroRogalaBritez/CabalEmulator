package cabal.crypt;

import cabal.types.UInt32;

public interface Key {
    UInt32 getKeyValue(int index);
    UInt32 getDecryptionMultiplier();
    UInt32 getEncryptionMultiplier();
}
