/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class SaveProgramErrorTranslator
extends ErrorTranslator {
    SaveProgramErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = new String[2];
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case 63: {
                    arrstring[0] = BUNDLE.getString("TITLE_PROGRAM_SAVING_TRANSLATION_FAIL");
                    arrstring[1] = BUNDLE.getString("ERROR_PROGRAM_SAVING_TRANSLATION_FAIL");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -1: {
                    arrstring[0] = BUNDLE.getString("TITLE_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = BUNDLE.getString("ERROR_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -9: {
                    arrstring[0] = BUNDLE.getString("TITLE_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -10: {
                    arrstring[0] = BUNDLE.getString("TITLE_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 57: {
                    arrstring[0] = BUNDLE.getString("TITLE_SAVING_PROGRAM_INVALID_NAME");
                    arrstring[1] = BUNDLE.getString("ERROR_SAVING_PROGRAM_INVALID_NAME");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                default: {
                    arrstring[0] = BUNDLE.getString("TITLE_INTERNAL_ERROR");
                    arrstring[1] = BUNDLE.getString("ERROR_INTERNAL_ERROR");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2, tIStatus.getStatusCode());
                }
            }
        }
        return arrstring;
    }
}

