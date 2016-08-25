/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class SavingScreenCaptureErrorTranslator
extends ErrorTranslator {
    SavingScreenCaptureErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = new String[2];
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case -10: {
                    arrstring[0] = BUNDLE.getString("TITLE_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -9: {
                    arrstring[0] = BUNDLE.getString("TITLE_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -8: {
                    arrstring[0] = BUNDLE.getString("TITLE_FILE_ALREADY_EXISTS_ON_HOST");
                    arrstring[1] = BUNDLE.getString("PROMPT_FILE_ALREADY_EXISTS_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -7: {
                    arrstring[0] = BUNDLE.getString("TITLE_INVALID_PATH_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INVALID_PATH_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -1: {
                    arrstring[0] = BUNDLE.getString("TITLE_SENT_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = BUNDLE.getString("ERROR_SENT_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
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

