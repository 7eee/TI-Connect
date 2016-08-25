/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.ioStream.mock;

import com.ti.et.elg.connectServer.exports.IOStreamInterface;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.io.InputStream;
import java.io.OutputStream;

public class IOStreamMock
implements IOStreamInterface {
    @Override
    public TIStatus openStream(String string) {
        return null;
    }

    @Override
    public TIStatus closeStream(String string) {
        return null;
    }

    @Override
    public InputStream getDataIn() {
        return null;
    }

    @Override
    public OutputStream getDataOut() {
        return null;
    }

    @Override
    public void setDataIn(OutputStream outputStream) {
    }
}

