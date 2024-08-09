package cabal.crypt;

import cabal.types.UInt32;

import java.nio.ByteBuffer;

final class EP8KeyGenerator implements KeyGenerator {
    private final static UInt32 ROUND_VALUE1 = new UInt32(0x2F6B6F5l);
    private final static UInt32 ROUND_VALUE2 = new UInt32(0x14698B7l);
    private final static UInt32 ROUND_VALUE3 = new UInt32(0x27F41C3l);
    private final static UInt32 ROUND_VALUE4 = new UInt32(0x0B327BDl);
    private final static UInt32 COMMON_KEY_ROOT_KEY = new UInt32(0x8F54C37Bl);
    private final static int COMMON_KEY_LENGTH = 0x10000;
    private final static int AUTH_KEY_TOTAL_LENGTH = 0x20000;

    EP8KeyGenerator() {
        
    }
    
    @Override
    public byte[] generateCommonKey(){        
        ByteBuffer byteBuffer = ByteBuffer.allocate(COMMON_KEY_LENGTH);        
        
        generateCommonKey(byteBuffer);                
        
        return byteBuffer.array();
    }
    
    @Override
    public byte[] generateAuthKey(UInt32 rootKey){        
        ByteBuffer byteBuffer = ByteBuffer.allocate(AUTH_KEY_TOTAL_LENGTH);
        
        generateCommonKey(byteBuffer);
        generate(byteBuffer, rootKey, COMMON_KEY_LENGTH);
        
        return byteBuffer.array();
    }
    
    private void generateCommonKey(ByteBuffer byteBuffer){
        generate(byteBuffer, COMMON_KEY_ROOT_KEY, COMMON_KEY_LENGTH);
    }
    
    private void generate(ByteBuffer byteBuffer, UInt32 rootKey, int keyLength){
        KeyRound keyRound1, keyRound2 = null;                       
        
        for (int i = 0 ; i < keyLength ; i+=4) {
            keyRound1 = makeKeyRound(keyRound2 == null ? rootKey : keyRound2.keyC);
            keyRound2 = makeKeyRound(keyRound1.keyC);            
            
            byteBuffer.put(
                makeKeyFinalRound(keyRound1.keyA, keyRound2.keyA).getBytesLE()
            );            
        }                
    }            

    private UInt32 makeKeyFinalRound(UInt32 keyA, UInt32 keyB){
        keyB = keyB.leftShift(0x10);
        return keyA.or(keyB);
    }
    
    private KeyRound makeKeyRound(UInt32 b){
        UInt32 a, c;
        
        a = b.mul(ROUND_VALUE1);
        a = a.add(ROUND_VALUE2);
        c = a;
        a = a.rightShift(0x10);
        a = a.mul(ROUND_VALUE3);
        a = a.add(ROUND_VALUE4);
        a = a.rightShift(0x10);
        
        return new KeyRound(a, c);
    }
    
    private class KeyRound {
        private final UInt32 keyA, keyC;

        public KeyRound(UInt32 keyA, UInt32 keyC) {
            this.keyA = keyA;
            this.keyC = keyC;
        }        
    }
    
}
