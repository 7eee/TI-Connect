/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

class FlashVarDataParser {
    public static final int SIZE_OF_FLASH_HEADER = 78;
    public static final int REVISION_NUMBER_MAJOR_INDEX = 8;
    public static final int REVISION_NUMBER_MINOR_INDEX = 9;
    public static final int FILE_FORMAT_INDEX = 10;
    public static final int OBJECT_CODE_TYPE_INDEX = 11;
    public static final int REVISION_DATE_DAY_INDEX = 12;
    public static final int REVISION_DATE_MONTH_INDEX = 13;
    public static final int REVISION_DATE_YEAR_MAJOR_INDEX = 14;
    public static final int REVISION_DATE_YEAR_MINOR_INDEX = 15;
    public static final int OBJECT_NAME_LENGTH_INDEX = 16;
    public static final int OBJECT_NAME_INDEX = 17;
    public static final int OBJECT_NAME_LENTGTH = 31;
    public static final int DEVICE_DATATYPE_MAX_LENGTH = 22;
    public static final int DEVICE_CODE_INDEX = 48;
    public static final int DATA_OBJECT_TYPE_INDEX = 49;
    public static final int PRODUCT_ID_INDEX = 73;
    public static final int DATA_SIZE_INDEX = 74;
    public static final int DATA_SIZE_LENTGH = 4;
    public static final int RAW_DATA_START_INDEX = 78;

    FlashVarDataParser() {
    }

    public static int getRevisionFormatMajor(byte[] arrby) {
        return FlashVarDataParser.byteToDecimalFromNibbles((byte)(arrby[8] & 255));
    }

    public static int getRevisionFormatMinor(byte[] arrby) {
        return FlashVarDataParser.byteToDecimalFromNibbles((byte)(arrby[9] & 255));
    }

    public static int getFileFormatFlag(byte[] arrby) {
        return arrby[10] & 255;
    }

    public static int getObjectCodeType(byte[] arrby) {
        return arrby[11] & 255;
    }

    public static int getRevisionDateDay(byte[] arrby) {
        return arrby[12] & 255;
    }

    public static int getRevisionDateMonth(byte[] arrby) {
        return arrby[13] & 255;
    }

    public static int getRevisionDateYearMajor(byte[] arrby) {
        return arrby[14] & 255;
    }

    public static int getRevisionDateYearMinor(byte[] arrby) {
        return arrby[15] & 255;
    }

    public static int getObjectNameLength(byte[] arrby) {
        return arrby[16] & 255;
    }

    public static byte[] getObjectName(byte[] arrby, int n) {
        byte[] arrby2 = new byte[n + 1];
        System.arraycopy(arrby, 17, arrby2, 0, n);
        return arrby2;
    }

    public static byte[] getDeviceCodesDataTypePairs(byte[] arrby) {
        byte[] arrby2 = new byte[22];
        System.arraycopy(arrby, 48, arrby2, 0, 22);
        return arrby2;
    }

    public static int getOwnerCalculatorID(byte[] arrby) {
        return arrby[48] & 255;
    }

    public static int getVarType(byte[] arrby) {
        return arrby[49] & 255;
    }

    public static int getOwnerProductID(byte[] arrby) {
        return arrby[73] & 255;
    }

    public static byte[] getObjectDataSize(byte[] arrby) {
        byte[] arrby2 = new byte[4];
        System.arraycopy(arrby, 74, arrby2, 0, 4);
        return arrby2;
    }

    public static byte[] getRawData(byte[] arrby, int n) {
        byte[] arrby2 = new byte[n];
        if (arrby.length >= 78 + n) {
            System.arraycopy(arrby, 78, arrby2, 0, n);
        } else {
            arrby2 = null;
        }
        return arrby2;
    }

    public static int byteToDecimalFromNibbles(byte by) {
        int n = (by & 240) >> 4;
        int n2 = by & 15;
        return n * 10 + n2;
    }
}

