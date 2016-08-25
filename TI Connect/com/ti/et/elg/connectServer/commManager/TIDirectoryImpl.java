/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TIDirectoryImpl
implements TIDirectory {
    private List<TIVar> varsList = new ArrayList<TIVar>();

    @Override
    public void addVar(TIVar tIVar) {
        this.varsList.add(tIVar);
    }

    @Override
    public boolean removeVar(TIVar tIVar) {
        return this.varsList.remove(tIVar);
    }

    @Override
    public Iterator<TIVar> getIterator() {
        return this.varsList.iterator();
    }

    @Override
    public int getNumVars() {
        return this.varsList.size();
    }
}

