/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator.mock;

import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.exports.TranslatorInterface;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;

public class TranslatorMock
implements TranslatorInterface {
    @Override
    public TIStatus getDisplayableNamefromObjectName(TIVar tIVar) {
        return null;
    }

    @Override
    public TIStatus convertImage(TIImage tIImage, int n, int n2) {
        return null;
    }

    @Override
    public byte[] unicodeTextToCalcText(byte[] arrby) {
        return null;
    }

    @Override
    public TIProgramDataHolder textToProgram(String string, int n) {
        return null;
    }

    @Override
    public TIProgramDataHolder programToText(byte[] arrby) {
        return null;
    }

    @Override
    public byte[] calcTextToUnicodeText(byte[] arrby) {
        return null;
    }
}

