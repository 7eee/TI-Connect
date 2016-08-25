/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model;

public interface IDITIBcdNumber {
    public static final int BCD_NUMBER_LENGHT = 9;
    public static final int BCD_MANTISSA_LENGHT = 7;
    public static final int BCD_SIGN_POSITION = 0;
    public static final int BCD_EXP_POSITION = 1;
    public static final int BCD_MANITSSA_START_POSITION = 2;

    public String getOriginalNumberString();

    public byte[] getTIBcdNumber();

    public byte getSign();

    public byte getExponent();

    public byte[] getMantissa();
}

