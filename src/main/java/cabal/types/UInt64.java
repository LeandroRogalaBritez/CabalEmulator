package cabal.types;

public class UInt64 extends Number implements Comparable<UInt64> {
    private final long value;

    public final static UInt64 ZERO = new UInt64(0);
    public final static long MIN_VALUE = 0x0000000000000000L;
    public final static long MAX_VALUE = 0xFFFFFFFFFFFFFFFFL;

    public UInt64(int signedValue){
        this((long)signedValue);
    }

    public UInt64(long value) {
        this.value = value & MAX_VALUE;
    }

    public void print(String msg){
        System.out.printf(">> %s: %16X (%d)\n", msg, value, value);
    }

    private void copyValue(byte src[], byte out[], int startOffset){
        if (startOffset < out.length) {
            out[startOffset] = src[0];
            if (startOffset + 1 < out.length) {
                out[startOffset + 1] = src[1];
                if (startOffset + 2 < out.length) {
                    out[startOffset + 2] = src[2];
                    if (startOffset + 3 < out.length) {
                        out[startOffset + 3] = src[3];
                        if (startOffset + 4 < out.length) {
                            out[startOffset + 4] = src[4];
                            if (startOffset + 5 < out.length) {
                                out[startOffset + 5] = src[5];
                                if (startOffset + 6 < out.length) {
                                    out[startOffset + 6] = src[6];
                                    if (startOffset + 7 < out.length) {
                                        out[startOffset + 7] = src[7];
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void copyValueLE(byte out[], int startOffset){
        copyValue(getBytesLE(), out, startOffset);
    }

    public static UInt64 fromByteArrayLE(byte data[], int startOffset){
        long value = 0L;

        if (startOffset < data.length) {
            value = value | (((long) data[startOffset]) & 0xFFL);
            if (startOffset + 1 < data.length) {
                value = value | ((((long) data[startOffset + 1]) << 8) & 0xFF00L);
                if (startOffset + 2 < data.length) {
                    value = value | ((((long) data[startOffset + 2]) << 16) & 0xFF0000L);
                    if (startOffset + 3 < data.length) {
                        value = value | ((((long) data[startOffset + 3]) << 24) & 0xFF000000L);
                        if (startOffset + 4 < data.length) {
                            value = value | ((((long) data[startOffset + 4]) << 32) & 0xFF00000000L);
                            if (startOffset + 5 < data.length) {
                                value = value | ((((long) data[startOffset + 5]) << 40) & 0xFF0000000000L);
                                if (startOffset + 6 < data.length) {
                                    value = value | ((((long) data[startOffset + 6]) << 48) & 0xFF000000000000L);
                                    if (startOffset + 7 < data.length) {
                                        value = value | ((((long) data[startOffset + 7]) << 56) & 0xFF00000000000000L);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return new UInt64(value);
    }

    public byte[] getBytesBE(){
        return new byte[]{
                (byte) ((value >> 56) & 0xFF),
                (byte) ((value >> 48) & 0xFF),
                (byte) ((value >> 40) & 0xFF),
                (byte) ((value >> 32) & 0xFF),
                (byte) ((value >> 24) & 0xFF),
                (byte) ((value >> 16) & 0xFF),
                (byte) ((value >> 8) & 0xFF),
                (byte) (value & 0xFF)
        };
    }

    public byte[] getBytesLE(){
        return new byte[]{
                (byte) (value & 0xFF),
                (byte) ((value & 0xFF) >> 8),
                (byte) ((value & 0xFF) >> 16),
                (byte) ((value & 0xFF) >> 24),
                (byte) ((value & 0xFF) >> 32),
                (byte) ((value & 0xFF) >> 40),
                (byte) ((value & 0xFF) >> 48),
                (byte) ((value & 0xFF) >> 56)
        };
    }

    public UInt64 decrement(){
        if(this.value == MIN_VALUE){
            return this;
        }
        return new UInt64(this.value - 1l);
    }

    public UInt64 increment(){
        if(this.value == MAX_VALUE){
            return this;
        }
        return new UInt64(this.value + 1l);
    }

    public UInt64 leftShift(int times){
        return new UInt64(this.value << times);
    }

    public UInt64 rightShift(int times){
        return new UInt64(this.value >> times);
    }

    public UInt64 bitwiseXor(UInt64 unsigned){
        return new UInt64(this.value ^ unsigned.value);
    }

    public UInt64 bitwiseAnd(UInt64 unsigned){
        return new UInt64(this.value & unsigned.value);
    }

    public UInt64 or(UInt64 unsigned){
        return new UInt64(this.value | unsigned.value);
    }

    public UInt64 add(int signed){
        return add(new UInt64(signed));
    }

    public UInt64 add(UInt64 unsigned){
        return new UInt64(this.value + unsigned.value);
    }

    public UInt64 mul(UInt64 unsigned){
        return new UInt64(this.value * unsigned.value);
    }

    @Override
    public int intValue() {
        return Long.valueOf(value).intValue();
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return Long.valueOf(value).floatValue();
    }

    @Override
    public double doubleValue() {
        return Long.valueOf(value).doubleValue();
    }

    public boolean biggerThan(int value){
        return this.value > value;
    }

    @Override
    public int compareTo(UInt64 t) {
        if(t.value == value){
            return 0;
        }else if(t.value > value){
            return 1;
        }else{
            return -1;
        }
    }

    public static UInt64 fromIpAddress(String address){
        String values[] = address.split("\\.");

        long shiftCount = 0;
        long ipValue = 0l;

        for (int i = 0 ; i < 4 ; i++, shiftCount += 8) {
            int cv = Integer.valueOf(values[i]);
            if (shiftCount != 0) {
                cv = cv << shiftCount;
            }

            ipValue |= cv;
        }

        return new UInt64(ipValue);
    }

    public String toIpAddressBE(){
        byte bytes[] = getBytesBE();

        return String.format(
                "%d.%d.%d.%d",
                ((int)bytes[0]) & 0xFF,
                ((int)bytes[1]) & 0xFF,
                ((int)bytes[2]) & 0xFF,
                ((int)bytes[3]) & 0xFF
        );
    }

    public String toIpAddress(){
        byte bytes[] = getBytesLE();

        return String.format(
                "%d.%d.%d.%d",
                ((int)bytes[0]) & 0xFF,
                ((int)bytes[1]) & 0xFF,
                ((int)bytes[2]) & 0xFF,
                ((int)bytes[3]) & 0xFF
        );
    }

    public String hexString(){
        return String.format("0x%016X", value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof UInt64) {
            return ((UInt64)obj).value == value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(value).hashCode();
    }

    public static UInt64 valueOf(int int32){
        return new UInt64(int32);
    }

    public static UInt64 valueOf(long int64){
        return new UInt64(int64);
    }

    @Override
    public String toString() {
        return String.format("%d", value);
    }
}
