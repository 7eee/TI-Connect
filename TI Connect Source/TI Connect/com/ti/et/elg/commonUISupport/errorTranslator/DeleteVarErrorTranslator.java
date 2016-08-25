/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class DeleteVarErrorTranslator
extends ErrorTranslator {
    DeleteVarErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = super.errorCodeToMessage(tIStatus, string, string2);
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case 7: 
                case 8: {
                    arrstring[0] = BUNDLE.getString("TITLE_CANNOT_DELETE_FILE_ON_DEVICE");
                    arrstring[1] = BUNDLE.getString("ERROR_CANNOT_DELETE_FILE_ON_DEVICE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 76: {
                    arrstring[0] = BUNDLE.getString("TITLE_DEVICE_IS_BUSY");
                    arrstring[1] = BUNDLE.getString("ERROR_DEVICE_IS_BUSY");
                    arrstring[1] = MessageFormat.format(arrstring[1], string);
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

