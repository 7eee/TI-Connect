/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class OpenTIVarsForEditingErrorTranslator
extends ErrorTranslator {
    OpenTIVarsForEditingErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = new String[2];
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case -1: {
                    arrstring[0] = BUNDLE.getString("TITLE_PROGRAM_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = BUNDLE.getString("ERROR_PROGRAM_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 101: {
                    arrstring[0] = BUNDLE.getString("TITLE_UNSUPPORTED_PROGRAM");
                    arrstring[1] = BUNDLE.getString("ERROR_UNSUPPORTED_PROGRAM");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 111: {
                    arrstring[0] = BUNDLE.getString("TITLE_NOT_A_PROGRAM");
                    arrstring[1] = BUNDLE.getString("ERROR_NOT_A_PROGRAM");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 113: {
                    arrstring[0] = BUNDLE.getString("TITLE_CORRUPTED_VAR");
                    arrstring[1] = BUNDLE.getString("ERROR_CORRUPTED_VAR");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 63: {
                    arrstring[0] = BUNDLE.getString("TITLE_PROGRAM_TRANSLATION_FAIL");
                    arrstring[1] = BUNDLE.getString("ERROR_PROGRAM_TRANSLATION_FAIL");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 112: {
                    arrstring[0] = BUNDLE.getString("TITLE_PROGRAM_ALREADY_PRESENT");
                    arrstring[1] = BUNDLE.getString("ERROR_PROGRAM_ALREADY_PRESENT");
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

