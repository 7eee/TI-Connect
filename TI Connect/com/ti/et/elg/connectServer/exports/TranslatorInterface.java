/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;

public interface TranslatorInterface {
    public TIStatus getDisplayableNamefromObjectName(TIVar var1);

    public TIStatus convertImage(TIImage var1, int var2, int var3);

    public byte[] unicodeTextToCalcText(byte[] var1);

    public TIProgramDataHolder textToProgram(String var1, int var2);

    public TIProgramDataHolder programToText(byte[] var1);

    public byte[] calcTextToUnicodeText(byte[] var1);
}

