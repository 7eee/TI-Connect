/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.TIVarImpl;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.utils.fileUtils.FileUtils;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class OneOrMultipleVarsInHostfileProcessing {
    private static final String LOG_TAG = OneOrMultipleVarsInHostfileProcessing.class.getSimpleName();

    public List<TIVar> createTIVarsFromFile(String string) {
        return this.createTIVarsFromFile(string, false);
    }

    public List<TIVar> createTIVarsFromFile(String string, boolean bl) {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        if (string != null) {
            File file = new File(string);
            if (file == null || !file.exists() || !file.canRead()) {
                try {
                    file = new File(URLDecoder.decode(string, "UTF-8"));
                }
                catch (Exception var5_5) {
                    TILogger.logError(LOG_TAG, "createTIVar URLDecoder exception", var5_5);
                    return null;
                }
            }
            byte[] arrby = null;
            if (file != null && file.exists() && file.canRead()) {
                try {
                    arrby = FileUtils.readBytesFromFile(file);
                }
                catch (IOException var6_7) {
                    TILogger.logError(LOG_TAG, "createTIVar readBytesFromFile exception", var6_7);
                    return null;
                }
            } else {
                TILogger.logError(LOG_TAG, "createTIVar invalid hostFile: " + file.getAbsolutePath());
                return null;
            }
            if (arrby != null) {
                TIVarImpl tIVarImpl = new TIVarImpl(string, bl);
                arrayList.add(tIVarImpl);
                if (tIVarImpl.isMultiVarFile()) {
                    int n = 72 + tIVarImpl.getMsgBlock2Length();
                    int n2 = tIVarImpl.getMsgBlock2Length();
                    int n3 = 17;
                    int n4 = n2 + n3;
                    int n5 = arrby.length - n4;
                    TIVarImpl tIVarImpl2 = null;
                    int n6 = 1;
                    TILogger.logDetail(LOG_TAG, "[" + n6 + "] name='" + tIVarImpl.getDeviceFileName() + "' type='0x" + Integer.toHexString(tIVarImpl.getVarType()) + "' currentMarker=0x" + Integer.toHexString(n) + " MsgBlock2Length=0x" + Integer.toHexString(n2));
                    do {
                        byte[] arrby2 = new byte[n5];
                        System.arraycopy(arrby, n, arrby2, 55, n5 - 55 - 2);
                        long l = FileUtils.calculateChecksum(arrby2);
                        arrby2[arrby2.length - 2] = (byte)(l & 255);
                        arrby2[arrby2.length - 1] = (byte)(l >> 8 & 255);
                        System.arraycopy(arrby, 0, arrby2, 0, 55);
                        tIVarImpl2 = new TIVarImpl(file, arrby2);
                        arrayList.add(tIVarImpl2);
                        n2 = tIVarImpl2.getMsgBlock2Length();
                        n4 = n2 + n3;
                        TILogger.logDetail(LOG_TAG, "[" + ++n6 + "] name='" + tIVarImpl2.getDeviceFileName() + "' type='0x" + Integer.toHexString(tIVarImpl2.getVarType()) + "' currentMarker=0x" + Integer.toHexString(n += n4) + " MsgBlock2Length=0x" + Integer.toHexString(n2) + " nextFileSize=0x" + Integer.toHexString(n5 -= n4));
                    } while (tIVarImpl2.isMultiVarFile());
                }
            }
            return arrayList;
        }
        TILogger.logError(LOG_TAG, "createTIVar with invalid hostFile: " + string);
        return null;
    }
}

