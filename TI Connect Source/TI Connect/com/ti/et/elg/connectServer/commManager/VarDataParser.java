/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.utils.fileUtils.FileUtils;
import java.util.Arrays;

class VarDataParser {
    public static final int OBJECT_NAME_INDEX = 60;
    public static final int OBJECT_NAME_LENGTH = 8;
    public static final int LENGTH_OF_MSG1_INDEX = 55;
    public static final int LENGTH_OF_MSG1 = 13;
    public static final int NUM_BYTES_MSG1_FIELD = 2;
    public static final int NUM_BYTES_MSG2_FIELD = 2;
    public static final int LENGTH_FILE_FORMAT_HEADER = 55;
    public static final int DATA_TYPE_INDEX = 59;
    public static final int DATA_TYPE_LENGTH = 1;
    public static final int VAR_VERSION_INDEX = 68;
    public static final int VAR_VERSION_LENGTH = 1;
    public static final int DATA_TYPE2_INDEX = 69;
    public static final int DATA_TYPE2_LENGTH = 1;
    public static final int RAW_DATA_INDEX = 72;
    public static final int OWNER_PRODUCT_ID_INDEX = 10;
    public static final int MSG_BLOCK2_LENGTH_LSB = 57;
    public static final int MSG_BLOCK2_LENGTH_MSB = 58;
    public static final int LENGTH_BYTES_CHECKSUM = 2;
    public static final int MSG_BLOCK2_LENGTH_LSB_2 = 70;
    public static final int MSG_BLOCK2_LENGTH_MSB_2 = 71;

    VarDataParser() {
    }

    public static byte[] buildEquationObjectName(byte[] arrby) {
        byte[] arrby2 = arrby;
        byte[] arrby3 = new byte[2];
        arrby3[0] = 94;
        if (arrby2.length > 1) {
            arrby3[1] = arrby2[1];
        }
        block0 : switch (arrby2[0]) {
            case 114: {
                switch (arrby2[1]) {
                    case -127: {
                        arrby3[1] = 64;
                        break block0;
                    }
                    case -126: {
                        arrby3[1] = 65;
                        break block0;
                    }
                    case -125: {
                        arrby3[1] = 66;
                        break block0;
                    }
                    case -124: {
                        arrby3[1] = 67;
                        break block0;
                    }
                    case -123: {
                        arrby3[1] = 68;
                        break block0;
                    }
                    case -122: {
                        arrby3[1] = 69;
                        break block0;
                    }
                }
                break;
            }
            case 85: {
                arrby3[1] = -128;
                break;
            }
            case 86: {
                arrby3[1] = -127;
                break;
            }
            case 87: {
                arrby3[1] = -126;
                break;
            }
            case 88: {
                switch (arrby2[1]) {
                    case -127: {
                        arrby3[1] = 32;
                        break block0;
                    }
                    case -126: {
                        arrby3[1] = 34;
                        break block0;
                    }
                    case -125: {
                        arrby3[1] = 36;
                        break block0;
                    }
                    case -124: {
                        arrby3[1] = 38;
                        break block0;
                    }
                    case -123: {
                        arrby3[1] = 40;
                        break block0;
                    }
                    case -122: {
                        arrby3[1] = 42;
                        break block0;
                    }
                }
                break;
            }
            case 89: {
                switch (arrby2[1]) {
                    case -127: {
                        arrby3[1] = 33;
                        break block0;
                    }
                    case -126: {
                        arrby3[1] = 35;
                        break block0;
                    }
                    case -125: {
                        arrby3[1] = 37;
                        break block0;
                    }
                    case -124: {
                        arrby3[1] = 39;
                        break block0;
                    }
                    case -123: {
                        arrby3[1] = 41;
                        break block0;
                    }
                    case -122: {
                        arrby3[1] = 43;
                        break block0;
                    }
                }
                break;
            }
        }
        return arrby3;
    }

    private static byte[] handleDifferentObjNameMacVsPConRead(int n, byte[] arrby) {
        if (n == 3) {
            return VarDataParser.buildEquationObjectName(arrby);
        }
        return arrby;
    }

    public static byte[] getObjectNameFromData(int n, byte[] arrby) {
        byte[] arrby2 = new byte[8];
        System.arraycopy(arrby, 60, arrby2, 0, 8);
        return VarDataParser.handleDifferentObjNameMacVsPConRead(n, arrby2);
    }

    public static boolean getVarFlagArchiveFromData(byte[] arrby) {
        if (arrby[55] > 11) {
            if (arrby[69] == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static int getVarTypeFromData(byte[] arrby) {
        int n = arrby[59];
        if (n == 11) {
            n = 3;
        }
        return n;
    }

    public static int getVarVersionFromData(byte[] arrby) {
        if (arrby[55] > 11) {
            return arrby[68];
        }
        return 0;
    }

    public static int getOwnerProductID(byte[] arrby) {
        int n = 4;
        byte by = arrby[10];
        if (by == 11) {
            n = 11;
        } else if (by == 27) {
            n = 27;
        } else if (by == 10) {
            n = 10;
        } else if (by == 15) {
            n = 15;
        } else if (by == 19) {
            n = 19;
        }
        return n;
    }

    public static int getOwnerCalculatorID(byte[] arrby) {
        byte[] arrby2 = new byte[4];
        System.arraycopy(arrby, 0, arrby2, 0, 4);
        if (Arrays.equals(arrby2, "**TI".getBytes())) {
            System.arraycopy(arrby, 4, arrby2, 0, 4);
            if (Arrays.equals(arrby2, "83F*".getBytes())) {
                return 115;
            }
            return -1;
        }
        return -1;
    }

    public static int getVarDescriptor(byte[] arrby) {
        int n = -9175296;
        int n2 = VarDataParser.getVarTypeFromData(arrby);
        if (n2 == 26 || n2 == 7) {
            n = -9238016;
        }
        return n |= 255 & n2;
    }

    public static int getMsgBlock2Length(byte[] arrby) {
        int n = arrby[58] & 255;
        n = n << 8 | arrby[57] & 255;
        int n2 = arrby[71] & 255;
        if (n != (n2 = n2 << 8 | arrby[70] & 255)) {
            return 65535;
        }
        return n;
    }

    public static byte[] reduceToRawData(byte[] arrby, int n) {
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 72, arrby2, 0, n);
        return arrby2;
    }

    public static byte[] getChecksum(byte[] arrby) {
        byte[] arrby2 = new byte[2];
        if (arrby.length > 78 || arrby.length > 57) {
            int n = arrby.length - 55 - 2;
            byte[] arrby3 = new byte[n];
            System.arraycopy(arrby, 55, arrby3, 0, n);
            long l = FileUtils.calculateChecksum(arrby3);
            arrby2[0] = (byte)(l & 255);
            arrby2[1] = (byte)(l >> 8 & 255);
        } else {
            arrby2 = null;
        }
        return arrby2;
    }
}

