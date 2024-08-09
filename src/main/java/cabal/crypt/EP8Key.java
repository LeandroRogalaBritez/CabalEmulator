package cabal.crypt;

import cabal.types.UInt32;

import java.io.FileOutputStream;

final class EP8Key implements Key {
    private final static UInt32 COMMON_KEY_MULTIPLIER = new UInt32(0x4l);
    private final static UInt32 AUTH_KEY_MULTIPLIER = new UInt32(0x8l);
    private final byte data[];
    private final UInt32 decryptionMultiplier;    
    private final UInt32 encryptionMultiplier;
    
    EP8Key(KeyGenerator keyGenerator){
        this(keyGenerator, null);
    }
    
    EP8Key(KeyGenerator keyGenerator, UInt32 rootAuthKey) {
        if (rootAuthKey == null) {
            this.data = keyGenerator.generateCommonKey();
            this.decryptionMultiplier = COMMON_KEY_MULTIPLIER;
        } else {
            this.data = keyGenerator.generateAuthKey(rootAuthKey);
            this.decryptionMultiplier = AUTH_KEY_MULTIPLIER;
        }
        this.encryptionMultiplier = COMMON_KEY_MULTIPLIER;
    }        
    
    public void write(String filename){
        FileOutputStream fos = null;
        
        try{
            fos = new FileOutputStream(filename);
            fos.write(data);            
        } catch(Throwable t) {
            t.printStackTrace();
        } finally {
            if (fos != null) {
                try{
                    fos.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public UInt32 getKeyValue(int index) {
        return UInt32.fromByteArrayLE(data, index);
    }
        
    @Override
    public UInt32 getDecryptionMultiplier(){
        return decryptionMultiplier;
    }

    @Override
    public UInt32 getEncryptionMultiplier() {
        return encryptionMultiplier;
    }        
}
