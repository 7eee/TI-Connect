/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.ui;

import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.elg.dataimport.ui.dialog.IDIDialog;
import java.io.File;
import java.util.List;

public interface IDIController {
    public static final int NUM_OF_CSV_FILES_ALLOWED = 1;

    public List<IDITIVarData> importFiles(List<File> var1);

    public void setDialog(IDIDialog var1) throws DIException;

    public IDIDialog getDialog();
}

