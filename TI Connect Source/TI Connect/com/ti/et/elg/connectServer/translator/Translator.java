/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.translator;

import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.exports.TranslatorInterface;
import com.ti.et.elg.connectServer.translator.ProgramTranslation;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;
import com.ti.et.utils.logger.TILogger;

public class Translator
implements TranslatorInterface {
    private static final Translator INSTANCE = new Translator();

    public static Translator getInstance() {
        return INSTANCE;
    }

    private Translator() {
        TILogger.logDetail(Translator.class.getSimpleName(), "Translator - Loading native library...");
        System.loadLibrary("NativeConnectServer");
    }

    private native int getDisplayableNamefromObjectNameN(TIVar var1, byte[] var2);

    private native int convertImageN(TIImage var1, byte[] var2, int var3, int var4);

    private native byte[] unicodeTextToCalcTextN(byte[] var1);

    private native byte[] calcTextToUnicodeTextN(byte[] var1);

    @Override
    public TIStatus getDisplayableNamefromObjectName(TIVar tIVar) {
        int n = this.getDisplayableNamefromObjectNameN(tIVar, tIVar.getObjectName());
        return new TIStatus(n);
    }

    @Override
    public TIStatus convertImage(TIImage tIImage, int n, int n2) {
        int n3 = this.convertImageN(tIImage, tIImage.getImageData(), n, n2);
        return new TIStatus(n3);
    }

    @Override
    public byte[] unicodeTextToCalcText(byte[] arrby) {
        return this.unicodeTextToCalcTextN(arrby);
    }

    @Override
    public TIProgramDataHolder textToProgram(String string, int n) {
        return ProgramTranslation.getInstance().textToProgram(string, n);
    }

    @Override
    public TIProgramDataHolder programToText(byte[] arrby) {
        return ProgramTranslation.getInstance().programToText(arrby);
    }

    @Override
    public byte[] calcTextToUnicodeText(byte[] arrby) {
        return this.calcTextToUnicodeTextN(arrby);
    }
}

