package cabal.types;

public class UInt8 extends Number implements Comparable<UInt8> {
    public final static UInt8 ZERO = valueOf((short)0);
    public final static UInt8 ONE = valueOf((short)1);
    public final static short MIN_VALUE = 0x00;
    public final static short MAX_VALUE = 0xFF;
    final short value;

    public UInt8(byte int8){
        this((short)int8);
    }
    
    public UInt8(short uint8) {
        this.value = (short)(uint8 & MAX_VALUE);
    }           
    
    short rawValue(){
        return value;
    }
    
    @Override
    public byte byteValue(){
        return (byte)value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof UInt8) {
            return ((UInt8)obj).value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value;
    }
    
    @Override
    public short shortValue() {
        return value;
    }
    
    @Override
    public int intValue() {
        return Short.valueOf(value).intValue();
    }

    public UInt32 uint32Value(){
        return new UInt32((int)value);
    }
    
    @Override
    public long longValue() {
        return Short.valueOf(value).longValue();
    }

    @Override
    public float floatValue() {
        return Short.valueOf(value).floatValue();
    }

    @Override
    public double doubleValue() {
        return Short.valueOf(value).doubleValue();
    }

    @Override
    public int compareTo(UInt8 t) {
        if (t.value == value) {
            return 0;
        } else if(t.value > value) {
            return 1;
        } else {
            return -1;
        }
    }
    
    public static UInt8 valueOf(UInt16 uint16){
        return new UInt8(uint16.shortValue());
    }
    
    public static UInt8 valueOf(byte int8){
        return new UInt8(int8);
    }
    
    public static UInt8 valueOf(short int16){
        return new UInt8(int16);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }

    public byte[] getBytesLE() {
        return new byte[] {
                (byte) (value & 0xFF)
        };
    }
}
