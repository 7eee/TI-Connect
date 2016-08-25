/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.nativeDnD;

import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.exports.DeviceContentsInterface;
import com.ti.et.utils.logger.TILogger;

public class DnD {
    private static DnD instance = new DnD();
    private boolean initialized = false;
    private DeviceContentsInterface deviceContents;

    public static DnD getInstance() {
        return instance;
    }

    public void initialize(DeviceContentsInterface deviceContentsInterface) {
        this.deviceContents = deviceContentsInterface;
        try {
            TILogger.logDetail(DnD.class.getSimpleName(), "before loading the NativeDnD library");
            System.loadLibrary("NativeDnD");
            TILogger.logDetail(DnD.class.getSimpleName(), "after loading the NativeDnD library");
            this.init();
            this.initialized = true;
        }
        catch (UnsatisfiedLinkError var2_2) {
            TILogger.logError(DnD.class.getSimpleName(), "Unable to load the NativeDnD library so loading from other native library for now");
            try {
                System.loadLibrary("NativeConnectServer");
                this.init();
                this.initialized = true;
            }
            catch (UnsatisfiedLinkError var3_3) {
                TILogger.logError(DnD.class.getSimpleName(), "Unable to load the NativeDnD library or the jnilib");
            }
        }
    }

    public byte[] getDataForFileName(String string) {
        return this.deviceContents.getDataForFileName(string);
    }

    public void writeDeviceFilesToURL(String string) {
        this.deviceContents.writeSelectedDeviceFilesToURL(string);
    }

    public int beginDrag(TIVar[] arrtIVar) {
        if (this.initialized) {
            return this.startDrag(arrtIVar);
        }
        TILogger.logError(DnD.class.getSimpleName(), "NativeDnD was not successfully initialized");
        return -1;
    }

    private DnD() {
    }

    private native int init();

    private native int shutDown();

    private native int startDrag(TIVar[] var1);
}

