package cabal.types;

public class UInt16 extends Number implements Comparable<UInt16> {
    public final static UInt16 ZERO = new UInt16(0);
    public final static int MIN_VALUE = 0x0000;
    public final static int MAX_VALUE = 0xFFFF;
    final int value;

    public UInt16(short int16){
        this((int)int16);
    }
    
    public UInt16(int int32) {
        this.value = (int32 & MAX_VALUE);
    }           
    
    int rawValue(){
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof UInt16) {
            return this.value == ((UInt16)obj).value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.value;
    }
    
    public UInt16 bitwiseAnd(UInt16 value){
        return valueOf(this.value & value.value);
    }
    
    public UInt16 shiftLeft(int times){
        return valueOf(this.value << times);
    }
    
    public UInt16 shiftRight(int times){
        return valueOf(this.value >> times);
    }
    
    @Override
    public byte byteValue(){
        return Integer.valueOf(value).byteValue();
    }
            
    public boolean greaterThanOrEqual(short int16){
        return greaterThanOrEqual(valueOf(int16));
    }
    
    public boolean greaterThanOrEqual(int int32){
        return greaterThanOrEqual(valueOf(int32));
    }
    
    public boolean greaterThanOrEqual(UInt16 uint16){
        return this.value >= uint16.value;
    }
    
    public boolean lowerThan(short int16){
        return lowerThan(valueOf(int16));
    }
    
    public boolean lowerThan(int int32){
        return lowerThan(valueOf(int32));
    }
    
    public boolean lowerThan(UInt16 uint16){
        return this.value < uint16.value;
    }
    
    public byte[] getBytesLE(){
        return new byte[]{
            (byte) (value & 0x00FF),
            (byte)((value & 0xFF00) >> 8)
        };
    }
    
    @Override
    public short shortValue() {
        return Integer.valueOf(value).shortValue();
    }
    
    @Override
    public int intValue() {
        return value;
    }

    public UInt32 uint32Value(){
        return new UInt32(value);
    }
    
    @Override
    public long longValue() {
        return Integer.valueOf(value).longValue();
    }

    @Override
    public float floatValue() {
        return Integer.valueOf(value).floatValue();
    }

    @Override
    public double doubleValue() {
        return Integer.valueOf(value).doubleValue();
    }
        
    @Override
    public int compareTo(UInt16 t) {
        if (t.value == value) {
            return 0;
        } else if(t.value > value) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public static UInt16 valueOf(UInt8 uint8){
        return new UInt16(uint8.value);
    }
    
    public static UInt16 valueOf(short int16){
        return new UInt16(int16);
    }
    
    public static UInt16 valueOf(int int32){
        return new UInt16(int32);
    }    

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}