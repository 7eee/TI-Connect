/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.imageUtils;

import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.ArrayList;
import java.util.List;

public class TIVarMultiplexer {
    private TIVar tivar;

    public TIVarMultiplexer(TIVar tIVar) {
        this.tivar = tIVar;
    }

    public TIVar getTIVar() {
        return this.tivar;
    }

    public TIVar getTIVarForDevice(TIDevice tIDevice) {
        return this.tivar;
    }

    public static List<TIVar> convertList(List<TIVarMultiplexer> list, TIDevice tIDevice) {
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>(list.size());
        for (TIVarMultiplexer tIVarMultiplexer : list) {
            arrayList.add(tIVarMultiplexer.getTIVarForDevice(tIDevice));
        }
        return arrayList;
    }

    public static List<TIVarMultiplexer> convertList(List<TIVar> list) {
        ArrayList<TIVarMultiplexer> arrayList = new ArrayList<TIVarMultiplexer>(list.size());
        for (TIVar tIVar : list) {
            arrayList.add(new TIVarMultiplexer(tIVar));
        }
        return arrayList;
    }
}

