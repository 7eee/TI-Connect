/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.model.impl;

import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.IDITIBcdNumber;
import com.ti.et.elg.dataimport.util.BCDUtils;
import com.ti.et.elg.dataimport.util.CommonValidationUtils;
import com.ti.et.elg.dataimport.util.StringUtils;

public final class DITIBcdNumber
extends BCDUtils
implements IDITIBcdNumber {
    private byte sign;
    private byte exponent;
    private byte[] tiBcdNumber = new byte[0];
    private String number = null;

    public DITIBcdNumber(String string) throws DIConverterException {
        if (!StringUtils.isNotEmpty(string)) {
            throw new DIConverterException("Invalid base 10 number (null or empty)");
        }
        try {
            CommonValidationUtils.parseNumber(string);
            this.number = string;
            this.exponent = DITIBcdNumber.getByteExponent(this.number);
            this.sign = DITIBcdNumber.getMantissaSign(this.number);
            this.tiBcdNumber = DITIBcdNumber.convertStringToBCD(this.number);
        }
        catch (Exception var2_2) {
            throw new DIConverterException(var2_2);
        }
    }

    @Override
    public final String getOriginalNumberString() {
        return this.number;
    }

    @Override
    public final byte[] getTIBcdNumber() {
        return (byte[])this.tiBcdNumber.clone();
    }

    @Override
    public final byte getSign() {
        return this.sign;
    }

    @Override
    public final byte getExponent() {
        return this.exponent;
    }

    @Override
    public final byte[] getMantissa() {
        return DITIBcdNumber.getMantissaByteArray(this.number);
    }
}

