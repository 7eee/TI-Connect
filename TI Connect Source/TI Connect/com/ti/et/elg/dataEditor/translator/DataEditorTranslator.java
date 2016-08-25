/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataEditor.translator;

import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.dataEditor.dataTypes.TI83PNumberData;
import com.ti.et.utils.logger.TILogger;
import java.util.ArrayList;
import java.util.List;

public class DataEditorTranslator {
    private static Double result = null;
    private static List<Double> resultList = null;
    private static final String TAG = DataEditorTranslator.class.getSimpleName();
    private static final int BYTES_IN_REAL_NUMBER = 9;

    public static TIStatus convertRealToFloat(byte[] arrby) {
        TI83PNumberData tI83PNumberData = null;
        TIStatus tIStatus = new TIStatus(0);
        boolean bl = false;
        try {
            tI83PNumberData = new TI83PNumberData(arrby);
        }
        catch (IllegalArgumentException var4_4) {
            TILogger.logError(TAG, "Error creating byte structure for data.", var4_4);
            tIStatus = new TIStatus(49);
            bl = true;
        }
        if (!bl) {
            if (tI83PNumberData.getMantissa()[0] == 0) {
                result = new Double(0.0);
            } else {
                try {
                    String string = DataEditorTranslator.getDigitsFromBytes(tI83PNumberData.getMantissa());
                    TILogger.logDetail(TAG, string);
                    result = new Double("0." + string);
                    Double d = new Double(tI83PNumberData.getInterpretedExponent());
                    TILogger.logDetail(TAG, "exp " + d.toString());
                    result = result * Math.pow(10.0, d + 1.0);
                    result = result * (double)tI83PNumberData.getSign();
                }
                catch (Exception var4_6) {
                    TILogger.logError(TAG, "Cannot Convert Data", var4_6);
                    tIStatus = new TIStatus(48);
                }
                TILogger.logDetail(TAG, "result " + result.toString());
            }
        }
        return tIStatus;
    }

    public static String getDigitsFromBytes(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            byte by = arrby[i];
            int n = (by & 240) >> 4;
            int n2 = by & 15;
            char c = (char)(n < 10 ? 48 + n : 65 + n - 10);
            char c2 = (char)(n2 < 10 ? 48 + n2 : 65 + n2 - 10);
            stringBuffer.append(c);
            stringBuffer.append(c2);
        }
        return stringBuffer.toString();
    }

    public static Double getResult() {
        return result;
    }

    public static List<Double> getResultList() {
        return resultList;
    }

    public static TIStatus convertRealListToJavaList(byte[] arrby) {
        ArrayList<Double> arrayList = new ArrayList<Double>();
        TIStatus tIStatus = new TIStatus(0);
        byte by = arrby[0];
        byte by2 = arrby[1];
        int n = by | by2;
        if (n > 0) {
            for (int i = 0; i < n; ++i) {
                byte[] arrby2 = new byte[9];
                for (int j = 0; j < 9; ++j) {
                    arrby2[j] = arrby[j + i * 9 + 2];
                }
                if (DataEditorTranslator.convertRealToFloat(arrby2).getStatusCode() != 0) continue;
                arrayList.add(DataEditorTranslator.getResult());
            }
            if (n == arrayList.size()) {
                resultList = arrayList;
            } else {
                tIStatus = new TIStatus(48);
            }
        } else {
            tIStatus = new TIStatus(49);
        }
        return tIStatus;
    }
}

