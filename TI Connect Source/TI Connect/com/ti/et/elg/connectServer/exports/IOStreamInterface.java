/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIStatus;
import java.io.InputStream;
import java.io.OutputStream;

public interface IOStreamInterface {
    public TIStatus openStream(String var1);

    public TIStatus closeStream(String var1);

    public InputStream getDataIn();

    public OutputStream getDataOut();

    public void setDataIn(OutputStream var1);
}

