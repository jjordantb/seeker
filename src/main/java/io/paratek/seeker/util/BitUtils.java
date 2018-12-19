package io.paratek.seeker.util;

public class BitUtils {

    public static byte setBit(byte payload, int index) {
        payload |= 1 << index;
        return payload;
    }

    public static byte clearBit(byte payload, int index) {
        payload &= ~(1 << index);
        return payload;
    }

}
