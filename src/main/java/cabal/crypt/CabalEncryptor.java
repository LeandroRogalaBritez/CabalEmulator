package cabal.crypt;

import cabal.types.UInt32;

public class CabalEncryptor {
    private final static UInt32 DECRYPT_V1 = new UInt32(0xb7e2l);
    private final static UInt32 CIPHER_KEY_AND = new UInt32(0x3FFFl);
    private final static UInt32 ENCRYPTION_XOR_KEY = new UInt32(0x7AB38CF1l);
    private final static UInt32 MASK[] = {
            new UInt32(~0xFFFFFFFFl),
            new UInt32(~0xFFFFFF00l),
            new UInt32(~0xFFFF0000l),
            new UInt32(~0xFF000000l)
    };

    private static UInt32 getDecryptionCipherKey(UInt32 data, Key clientKey){
        UInt32 cipherKeyIndex = data.bitwiseAnd(CIPHER_KEY_AND).mul(clientKey.getDecryptionMultiplier());
        return clientKey.getKeyValue(cipherKeyIndex.intValue());
    }

    private static UInt32 getEncryptionCipherKey(UInt32 data, Key clientKey){
        UInt32 cipherKeyIndex = data.bitwiseAnd(CIPHER_KEY_AND).mul(clientKey.getEncryptionMultiplier());
        return clientKey.getKeyValue(cipherKeyIndex.intValue());
    }

    public static void encrypt(byte[] packet, Key clientKey) {
        UInt32 data, key;
        int i = 4;

        data = UInt32.fromByteArrayLE(packet, 0).bitwiseXor(ENCRYPTION_XOR_KEY);
        data.copyValueLE(packet, 0);

        key = getEncryptionCipherKey(data, clientKey);

        int t = (packet.length - i) >> 2;

        while (t > 0) {
            data = UInt32.fromByteArrayLE(packet, i);
            data.bitwiseXor(key).copyValueLE(packet, i);

            key = getEncryptionCipherKey(data.bitwiseXor(key), clientKey);

            i += 4;
            t = t-1;
        }

        if (i < packet.length) {
            data = MASK[(packet.length - 8) & 3];
            data = data.bitwiseAnd(key).bitwiseXor(UInt32.fromByteArrayLE(packet, i));
            data.copyValueLE(packet, i);
        }
    }

    public static void decrypt(byte packet[], Key clientKey) {
        UInt32 data, key;
        int i = 8;

        data = UInt32.fromByteArrayLE(packet, 0);
        new UInt32(packet.length * 0x10000).add(DECRYPT_V1).copyValueLE(packet, 0);

        key = getDecryptionCipherKey(data, clientKey);

        int t = (packet.length - i) >> 2;

        while (t > 0) {
            data = UInt32.fromByteArrayLE(packet, i);
            data.bitwiseXor(key).copyValueLE(packet, i);

            key = getDecryptionCipherKey(data, clientKey);

            i += 4;
            t = t-1;
        }

        if (i < packet.length) {
            data = MASK[(packet.length - 8) & 3];
            data = data.bitwiseAnd(key).bitwiseXor(UInt32.fromByteArrayLE(packet, i));
            data.copyValueLE(packet, i);
        }

        packet[4] = packet[5] = packet[6] = packet[7] = 0;
    }

    public byte[] decryptHeader(int available) {
        return new UInt32((available * 0x10000)).add(DECRYPT_V1).getBytesLE();
    }
}
