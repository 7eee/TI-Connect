/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class GetVarErrorTranslator
extends ErrorTranslator {
    GetVarErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = super.errorCodeToMessage(tIStatus, string, string2);
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case 7: 
                case 8: {
                    arrstring[0] = BUNDLE.getString("TITLE_CANNOT_ACCESS_FILE_ON_DEVICE");
                    arrstring[1] = BUNDLE.getString("ERROR_CANNOT_ACCESS_FILE_ON_DEVICE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -1: {
                    arrstring[0] = BUNDLE.getString("TITLE_INVALID_PATH_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INVALID_PATH_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -9: {
                    arrstring[0] = BUNDLE.getString("TITLE_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INSUFFICIENT_PERMISSIONS_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case -10: {
                    arrstring[0] = BUNDLE.getString("TITLE_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = BUNDLE.getString("ERROR_INSUFFICIENT_SPACE_ON_HOST");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 62: {
                    break;
                }
                default: {
                    arrstring[0] = BUNDLE.getString("TITLE_INTERNAL_ERROR");
                    arrstring[1] = BUNDLE.getString("ERROR_INTERNAL_ERROR");
                    arrstring[1] = MessageFormat.format(arrstring[1], string, tIStatus.getStatusCode());
                }
            }
        }
        return arrstring;
    }
}

