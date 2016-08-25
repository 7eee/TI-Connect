/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataEditor.dataTypes;

public class TI83PNumberData {
    private byte mantissaSign = 0;
    private byte exponentValue = 0;
    private byte[] mantissa = new byte[]{0};
    protected final int MAX_LENGTH_MANTISSA = 7;
    public static int exponentBias = 128;

    public TI83PNumberData(byte[] arrby) {
        if (arrby.length == 9) {
            this.mantissaSign = arrby[0];
            this.exponentValue = arrby[1];
            this.mantissa = new byte[7];
            for (int i = 0; i < 7; ++i) {
                this.mantissa[i] = arrby[i + 2];
            }
        } else {
            throw new IllegalArgumentException("Byte is not consistent with this format");
        }
    }

    public int getSign() {
        int n = this.mantissaSign >> 7 << 7;
        return n >= 0 ? 1 : -1;
    }

    public byte getRawExponent() {
        return this.exponentValue;
    }

    public int getInterpretedExponent() {
        return (this.exponentValue + exponentBias) % 256;
    }

    public byte[] getMantissa() {
        return this.mantissa;
    }
}

