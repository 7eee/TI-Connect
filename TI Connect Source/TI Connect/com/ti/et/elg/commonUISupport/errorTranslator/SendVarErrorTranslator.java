/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIStatus;
import java.text.MessageFormat;
import java.util.ResourceBundle;

class SendVarErrorTranslator
extends ErrorTranslator {
    SendVarErrorTranslator() {
    }

    @Override
    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String[] arrstring = super.errorCodeToMessage(tIStatus, string, string2);
        if (arrstring[0] == null || arrstring[1] == null) {
            switch (tIStatus.getStatusCode()) {
                case 7: 
                case 8: 
                case 55: 
                case 107: {
                    arrstring[0] = BUNDLE.getString("TITLE_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE");
                    arrstring[1] = BUNDLE.getString("ERROR_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2, string);
                    break;
                }
                case 31: 
                case 74: {
                    arrstring[0] = BUNDLE.getString("TITLE_INSUFFICIENT_MEMORY_ON_DEVICE");
                    arrstring[1] = BUNDLE.getString("ERROR_INSUFFICIENT_MEMORY_ON_DEVICE");
                    String string3 = BUNDLE.getString(tIStatus.getStatusCode() == 74 ? "table.location.ram.value" : "table.location.archive.value");
                    arrstring[1] = MessageFormat.format(arrstring[1], string3, string, string2);
                    break;
                }
                case 54: {
                    arrstring[0] = BUNDLE.getString("TITLE_CANNOT_LOAD_EARLIER_OS");
                    arrstring[1] = BUNDLE.getString("ERROR_CANNOT_LOAD_EARLIER_OS");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2, string);
                    break;
                }
                case 10: 
                case 33: 
                case 34: 
                case 35: 
                case 57: 
                case 65: 
                case 102: {
                    arrstring[0] = BUNDLE.getString("TITLE_VERSION_UNSUPPORTED");
                    arrstring[1] = BUNDLE.getString("ERROR_VERSION_UNSUPPORTED");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2, string);
                    break;
                }
                case -1: {
                    arrstring[0] = BUNDLE.getString("TITLE_SENT_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = BUNDLE.getString("ERROR_SENT_HOST_FILE_INACCESSIBLE");
                    arrstring[1] = MessageFormat.format(arrstring[1], string2);
                    break;
                }
                case 44: 
                case 101: 
                case 103: 
                case 108: {
                    arrstring[0] = BUNDLE.getString("TITLE_CORRUPTED_FILE");
                    arrstring[1] = BUNDLE.getString("ERROR_CORRUPTED_FILE");
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

