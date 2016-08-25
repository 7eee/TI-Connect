/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.ui.dialog;

import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.converter.impl.DIConverterException;
import com.ti.et.elg.dataimport.model.IDIConfig;
import java.io.File;
import java.util.List;
import java.util.Map;

public interface IDIDialog {
    public void init(File var1) throws DIException, DIConverterException;

    public IDIConfig showAndWait(List<Map<String, String>> var1) throws DIException;

    public boolean isDialogCancelled();
}

