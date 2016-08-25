/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.dataimport.ui.dialog;

import com.ti.et.elg.dataimport.ui.dialog.IDIDialog;

public abstract class ADIDialog
implements IDIDialog {
    protected static final int CONVERT_TYPE_MATRIX = 1;
    protected static final int CONVERT_TYPE_LIST = 2;
    protected static final String REGULAR_EXPRESION_PATTERN_NUMBERS = "^([0-9]|[1-9][0-9])$";
    protected static final String[] DEFAULT_VALUES_LISTS = new String[]{"1", "2", "3", "4", "5", "6"};
    protected static final String DEFAULT_VALUE_MATRIX = "2";
    protected static final String DEFAULT_VALUE_LIST = "1";
    protected static final int DEFAULT_ROW_SIZE_IN_LISTS = 0;
    protected static final int CELLS_MAXIMUM_ALLOWED = 400;
    protected static final String STYLE_CLASS_ERROR_NUMBER_STATUS = "overlay-Label-Error-Number-Bold";
    protected static final String STYLE_CLASS_NORMAL_STATUS = "overlay-Label-Header-Text-Bold";
    protected static final String STYLE_CLASS_ERROR_STATUS = "overlay-Label-Error-Text-Bold";
}

