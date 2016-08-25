/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIVar;
import java.util.Iterator;

public interface TIDirectory {
    public Iterator<TIVar> getIterator();

    public int getNumVars();

    public void addVar(TIVar var1);

    public boolean removeVar(TIVar var1);
}

