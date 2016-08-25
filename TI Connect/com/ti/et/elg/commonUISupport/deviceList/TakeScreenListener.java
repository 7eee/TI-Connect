/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.deviceList;

import com.ti.et.elg.connectServer.exports.TIDevice;
import java.util.List;

public interface TakeScreenListener {
    public void takeScreenCapture(TIDevice var1);

    public void takeScreenCapture(List<TIDevice> var1);
}

