package cabal.crypt;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;

public class RSA {
    public static final int KEY_SIZE = 2048;
    private final ByteArrayOutputStream recyclableMemoryStream;
    private final KeyPair rsaProvider;
    private byte[] publicKey;

    public RSA() throws Exception {
        recyclableMemoryStream = new ByteArrayOutputStream();
        rsaProvider = generateRSAKeyPair(KEY_SIZE);

        preparePublicKey();
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, rsaProvider.getPrivate());
        return cipher.doFinal(encrypted);
    }

    private void preparePublicKey() throws Exception {
        RSAPublicKeySpec publicSpec = KeyFactory.getInstance("RSA")
                .getKeySpec(rsaProvider.getPublic(), RSAPublicKeySpec.class);

        recyclableMemoryStream.reset();
        DataOutputStream bitStringWriter = new DataOutputStream(recyclableMemoryStream);
        bitStringWriter.writeByte(0x30); // sequence

        ByteArrayOutputStream paramsStream = new ByteArrayOutputStream();
        DataOutputStream paramsWriter = new DataOutputStream(paramsStream);
        encodeIntegerBigEndian(paramsWriter, publicSpec.getModulus().toByteArray(), true); // modulus
        encodeIntegerBigEndian(paramsWriter, publicSpec.getPublicExponent().toByteArray(), true); // exponent
        int paramsLength = paramsStream.size();
        encodeLength(bitStringWriter, paramsLength);
        bitStringWriter.write(paramsStream.toByteArray(), 0, paramsLength);

        publicKey = recyclableMemoryStream.toByteArray();
    }

    private void encodeLength(DataOutputStream stream, int length) throws IOException {
        if (length < 0)
            throw new IllegalArgumentException("Length must be non-negative");

        if (length < 0x80) {
            stream.writeByte(length);
        } else {
            int temp = length;
            int bytesRequired = 0;

            while (temp > 0) {
                temp >>= 8;
                bytesRequired++;
            }

            stream.writeByte(bytesRequired | 0x80);

            for (int i = bytesRequired - 1; i >= 0; i--) {
                stream.writeByte(length >> 8 * i & 0xFF);
            }
        }
    }

    private void encodeIntegerBigEndian(DataOutputStream stream, byte[] value, boolean forceUnsigned) throws IOException {
        stream.writeByte(0x02);
        int prefixZeros = 0;

        for (int i = 0; i < value.length; i++) {
            if (value[i] != 0) break;
            prefixZeros++;
        }

        if (value.length - prefixZeros == 0) {
            encodeLength(stream, 1);
            stream.writeByte(0);
        } else {
            if (forceUnsigned && value[prefixZeros] > 0x7F) {
                encodeLength(stream, value.length - prefixZeros + 1);
                stream.writeByte(0);
            } else {
                encodeLength(stream, value.length - prefixZeros);
            }

            for (int i = prefixZeros; i < value.length; i++) {
                stream.writeByte(value[i]);
            }
        }
    }

    private KeyPair generateRSAKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        return keyGen.generateKeyPair();
    }

}
