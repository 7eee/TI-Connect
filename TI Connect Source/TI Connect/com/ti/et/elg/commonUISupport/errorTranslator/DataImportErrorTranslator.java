/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class DataImportErrorTranslator
extends ErrorTranslator {
    DataImportErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = new String[2];
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case 115: {
                    arrstring[0] = BUNDLE.getString("TITLE_INVALID_CSV_FILE_FORMAT");
                    arrstring[1] = BUNDLE.getString("PROMPT_INVALID_CSV_FILE_FORMAT");
                    break;
                }
                default: {
                    arrstring[0] = BUNDLE.getString("TITLE_INTERNAL_ERROR");
                    arrstring[1] = BUNDLE.getString("ERROR_INTERNAL_ERROR");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                }
            }
        }
        return arrstring;
    }
}

