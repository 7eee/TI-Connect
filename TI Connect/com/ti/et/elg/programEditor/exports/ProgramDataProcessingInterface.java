/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.exports;

import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;
import java.io.File;

public interface ProgramDataProcessingInterface {
    public TIProgramDataHolder tokenizeProgram(String var1);

    public TIVar makeProgramVar(TIProgramDataHolder var1);

    public TIVar makeProgramVarFromFile(File var1, boolean var2);

    public TIProgramDataHolder detokenizeProgram(byte[] var1);
}

