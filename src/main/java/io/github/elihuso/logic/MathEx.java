package io.github.elihuso.logic;

public class MathEx {
    public static long getByteLength(byte[] v) {
        long i = 0;
        for (var u : v) {
            i += 1;
        }
        return i;
    }
}
