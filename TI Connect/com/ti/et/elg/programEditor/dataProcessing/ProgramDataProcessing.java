/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.dataProcessing;

import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.connectServer.translator.TIProgramDataHolder;
import com.ti.et.elg.programEditor.exports.ProgramDataProcessingInterface;
import java.io.File;
import java.util.List;

public class ProgramDataProcessing
implements ProgramDataProcessingInterface {
    @Override
    public TIProgramDataHolder tokenizeProgram(String string) {
        return ConnectServerFactory.getTranslator().textToProgram(string, -9175291);
    }

    @Override
    public TIVar makeProgramVar(TIProgramDataHolder tIProgramDataHolder) {
        int n = (byte)(tIProgramDataHolder.getProgramVersion() & 255) + 1;
        int n2 = -9240571;
        byte[] arrby = new byte[]{84, 69, 77, 80, 78, 65, 77, 69};
        TIVar tIVar = ConnectServerFactory.getCommManager().createTIVar(null, "TEMPNAME", null, 0, n, n2 |= tIProgramDataHolder.getProgramVersion() << 8, 5, false, 15, 115, false);
        tIVar.setObjectName(arrby);
        tIVar.setData(tIProgramDataHolder.getProgramTokens());
        return tIVar;
    }

    @Override
    public TIVar makeProgramVarFromFile(File file, boolean bl) {
        TIVar tIVar = null;
        List<TIVar> list = ConnectServerFactory.getCommManager().createTIVar(file.getAbsolutePath(), bl);
        if (list != null && list.size() > 0 && (tIVar = list.get(0)).isMultiVarFile() && list.size() > 1) {
            tIVar.setCorruptedVarState(true);
        }
        return tIVar;
    }

    @Override
    public TIProgramDataHolder detokenizeProgram(byte[] arrby) {
        return ConnectServerFactory.getTranslator().programToText(arrby);
    }
}

