/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.FlashVarDataParser;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.util.Arrays;

public class FileSupport {
    private static final String LOG_TAG = FileSupport.class.getSimpleName();

    public static boolean myTI_IDFile(byte[] arrby) {
        byte[] arrby2 = new byte[4];
        System.arraycopy(arrby, 0, arrby2, 0, 4);
        if (Arrays.equals(arrby2, "**TI".getBytes())) {
            System.arraycopy(arrby, 4, arrby2, 0, 4);
            if (Arrays.equals(arrby2, "FL**".getBytes())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static void myTI_IDFlashFile(TIVar tIVar) {
        byte[] arrby;
        int n;
        byte[] arrby2;
        byte[] arrby3 = tIVar.getData();
        tIVar.setRevisionNumberMajor(FlashVarDataParser.getRevisionFormatMajor(arrby3));
        tIVar.setRevisionNumberMinor(FlashVarDataParser.getRevisionFormatMinor(arrby3));
        tIVar.setFileFormatFlags(FlashVarDataParser.getFileFormatFlag(arrby3));
        tIVar.setObjectCodeType(FlashVarDataParser.getObjectCodeType(arrby3));
        tIVar.setRevisionDateDay(FlashVarDataParser.getRevisionDateDay(arrby3));
        tIVar.setRevisionDateMonth(FlashVarDataParser.getRevisionDateMonth(arrby3));
        tIVar.setRevisionDateYearMajor(FlashVarDataParser.getRevisionDateYearMajor(arrby3));
        tIVar.setRevisionDateYearMinor(FlashVarDataParser.getRevisionDateYearMinor(arrby3));
        tIVar.setObjectNameLength(FlashVarDataParser.getObjectNameLength(arrby3));
        tIVar.setObjectName(FlashVarDataParser.getObjectName(arrby3, tIVar.getObjectNameLength()));
        tIVar.setDeviceCodeDataTypePairs(FlashVarDataParser.getDeviceCodesDataTypePairs(arrby3));
        tIVar.setOwnerCalculatorID(tIVar.getDeviceCodeDataTypePairs()[0] & 255);
        TIStatus tIStatus = ConnectServerFactory.getTranslator().getDisplayableNamefromObjectName(tIVar);
        if (tIStatus != null && tIStatus.isFailure()) {
            tIVar.setCorruptedVarState(true);
        }
        tIVar.setVarType(tIVar.getDeviceCodeDataTypePairs()[1] & 255);
        tIVar.setOwnerProductID(FlashVarDataParser.getOwnerProductID(arrby3));
        if (tIVar.getOwnerProductID() == 0) {
            switch (tIVar.getOwnerCalculatorID()) {
                case 115: {
                    tIVar.setOwnerProductID(4);
                    break;
                }
                default: {
                    TILogger.logError(LOG_TAG, "Setting as unsupported target further validations");
                    tIVar.setOwnerCalculatorID(-1);
                }
            }
        }
        if ((arrby = FlashVarDataParser.getRawData(arrby3, n = (arrby2 = FlashVarDataParser.getObjectDataSize(arrby3))[3] << 24 | (arrby2[2] & 255) << 16 | (arrby2[1] & 255) << 8 | arrby2[0] & 255)) != null) {
            tIVar.setData(arrby);
        } else {
            tIVar.setOwnerCalculatorID(-1);
            tIVar.setCorruptedVarState(true);
        }
        if (tIVar.getDataSize() != n) {
            tIVar.setRawDataSize(n);
        }
    }
}

