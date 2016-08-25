/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.errorTranslator;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.errorTranslator.DataImportErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.DeleteVarErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.GetVarErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.OpenTIVarsForEditingErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.SaveProgramErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.SavingScreenCaptureErrorTranslator;
import com.ti.et.elg.commonUISupport.errorTranslator.SendVarErrorTranslator;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class ErrorTranslator {
    private static ErrorTranslator genericErrorTranslator = new ErrorTranslator();
    private static SendVarErrorTranslator sendVarErrorTranslator = new SendVarErrorTranslator();
    private static GetVarErrorTranslator getVarErrorTranslator = new GetVarErrorTranslator();
    private static DeleteVarErrorTranslator deleteVarErrorTranslator = new DeleteVarErrorTranslator();
    private static SavingScreenCaptureErrorTranslator screenCaptureErrorTranslator = new SavingScreenCaptureErrorTranslator();
    private static final OpenTIVarsForEditingErrorTranslator dataEditorErrorTranslator = new OpenTIVarsForEditingErrorTranslator();
    private static SaveProgramErrorTranslator saveProgramErrorTranslator = new SaveProgramErrorTranslator();
    private static DataImportErrorTranslator dataImportErrorTranslator = new DataImportErrorTranslator();
    protected static final ResourceBundle BUNDLE = CommonUISupportResourceBundle.BUNDLE;
    public static final String TITLE_DEVICE_TIMES_OUT_DURING_COMMUNICATION = "TITLE_DEVICE_TIMES_OUT_DURING_COMMUNICATION";
    public static final String ERROR_DEVICE_TIMES_OUT_DURING_COMMUNICATION = "ERROR_DEVICE_TIMES_OUT_DURING_COMMUNICATION";
    public static final String TITLE_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE = "TITLE_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE";
    public static final String ERROR_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE = "ERROR_CANNOT_CREATE_DIRECTORY_OR_FILE_ON_DEVICE";
    public static final String TITLE_INSUFFICIENT_MEMORY_ON_DEVICE = "TITLE_INSUFFICIENT_MEMORY_ON_DEVICE";
    public static final String ERROR_INSUFFICIENT_MEMORY_ON_DEVICE = "ERROR_INSUFFICIENT_MEMORY_ON_DEVICE";
    public static final String TITLE_FILE_ALREADY_EXISTS_ON_DEVICE = "TITLE_FILE_ALREADY_EXISTS_ON_DEVICE";
    public static final String PROMPT_FILE_ALREADY_EXISTS_ON_DEVICE = "PROMPT_FILE_ALREADY_EXISTS_ON_DEVICE";
    public static final String TITLE_CANNOT_LOAD_EARLIER_OS = "TITLE_CANNOT_LOAD_EARLIER_OS";
    public static final String ERROR_CANNOT_LOAD_EARLIER_OS = "ERROR_CANNOT_LOAD_EARLIER_OS";
    public static final String TITLE_FILE_TOO_LARGE_FOR_DEVICE = "TITLE_FILE_TOO_LARGE_FOR_DEVICE";
    public static final String ERROR_FILE_TOO_LARGE_FOR_DEVICE = "ERROR_FILE_TOO_LARGE_FOR_DEVICE";
    public static final String TITLE_UNSUPPORTED_CHARACTER_IN_STRING = "TITLE_UNSUPPORTED_CHARACTER_IN_STRING";
    public static final String ERROR_UNSUPPORTED_CHARACTER_IN_STRING = "ERROR_UNSUPPORTED_CHARACTER_IN_STRING";
    public static final String TITLE_BAD_CERTIFICATE = "TITLE_BAD_CERTIFICATE";
    public static final String ERROR_BAD_CERTIFICATE = "ERROR_BAD_CERTIFICATE";
    public static final String TITLE_VERSION_UNSUPPORTED = "TITLE_VERSION_UNSUPPORTED";
    public static final String ERROR_VERSION_UNSUPPORTED = "ERROR_VERSION_UNSUPPORTED";
    public static final String TITLE_CORRUPTED_FILE = "TITLE_CORRUPTED_FILE";
    public static final String ERROR_CORRUPTED_FILE = "ERROR_CORRUPTED_FILE";
    public static final String TITLE_INTERNAL_ERROR = "TITLE_INTERNAL_ERROR";
    public static final String ERROR_INTERNAL_ERROR = "ERROR_INTERNAL_ERROR";
    public static final String TITLE_CANNOT_ACCESS_FILE_ON_DEVICE = "TITLE_CANNOT_ACCESS_FILE_ON_DEVICE";
    public static final String ERROR_CANNOT_ACCESS_FILE_ON_DEVICE = "ERROR_CANNOT_ACCESS_FILE_ON_DEVICE";
    public static final String TITLE_CANNOT_DELETE_FILE_ON_DEVICE = "TITLE_CANNOT_DELETE_FILE_ON_DEVICE";
    public static final String ERROR_CANNOT_DELETE_FILE_ON_DEVICE = "ERROR_CANNOT_DELETE_FILE_ON_DEVICE";
    public static final String TITLE_DEVICE_IS_BUSY = "TITLE_DEVICE_IS_BUSY";
    public static final String ERROR_DEVICE_IS_BUSY = "ERROR_DEVICE_IS_BUSY";
    public static final String TITLE_FILE_ALREADY_EXISTS_ON_HOST = "TITLE_FILE_ALREADY_EXISTS_ON_HOST";
    public static final String PROMPT_FILE_ALREADY_EXISTS_ON_HOST = "PROMPT_FILE_ALREADY_EXISTS_ON_HOST";
    public static final String TITLE_INSUFFICIENT_SPACE_ON_HOST = "TITLE_INSUFFICIENT_SPACE_ON_HOST";
    public static final String ERROR_INSUFFICIENT_SPACE_ON_HOST = "ERROR_INSUFFICIENT_SPACE_ON_HOST";
    public static final String TITLE_INSUFFICIENT_PERMISSIONS_ON_HOST = "TITLE_INSUFFICIENT_PERMISSIONS_ON_HOST";
    public static final String ERROR_INSUFFICIENT_PERMISSIONS_ON_HOST = "ERROR_INSUFFICIENT_PERMISSIONS_ON_HOST";
    public static final String TITLE_INVALID_PATH_ON_HOST = "TITLE_INVALID_PATH_ON_HOST";
    public static final String ERROR_INVALID_PATH_ON_HOST = "ERROR_INVALID_PATH_ON_HOST";
    public static final String TITLE_SENT_HOST_FILE_INACCESSIBLE = "TITLE_SENT_HOST_FILE_INACCESSIBLE";
    public static final String ERROR_SENT_HOST_FILE_INACCESSIBLE = "ERROR_SENT_HOST_FILE_INACCESSIBLE";
    public static final String TITLE_PROGRAM_HOST_FILE_INACCESSIBLE = "TITLE_PROGRAM_HOST_FILE_INACCESSIBLE";
    public static final String ERROR_PROGRAM_HOST_FILE_INACCESSIBLE = "ERROR_PROGRAM_HOST_FILE_INACCESSIBLE";
    public static final String TITLE_UNSUPPORTED_PROGRAM = "TITLE_UNSUPPORTED_PROGRAM";
    public static final String ERROR_UNSUPPORTED_PROGRAM = "ERROR_UNSUPPORTED_PROGRAM";
    public static final String TITLE_NOT_A_PROGRAM = "TITLE_NOT_A_PROGRAM";
    public static final String ERROR_NOT_A_PROGRAM = "ERROR_NOT_A_PROGRAM";
    public static final String TITLE_PROGRAM_TRANSLATION_FAIL = "TITLE_PROGRAM_TRANSLATION_FAIL";
    public static final String ERROR_PROGRAM_TRANSLATION_FAIL = "ERROR_PROGRAM_TRANSLATION_FAIL";
    public static final String TITLE_PROGRAM_ALREADY_PRESENT = "TITLE_PROGRAM_ALREADY_PRESENT";
    public static final String ERROR_PROGRAM_ALREADY_PRESENT = "ERROR_PROGRAM_ALREADY_PRESENT";
    public static final String TITLE_CORRUPTED_VAR = "TITLE_CORRUPTED_VAR";
    public static final String ERROR_CORRUPTED_VAR = "ERROR_CORRUPTED_VAR";
    public static final String TITLE_PROGRAM_SAVING_TRANSLATION_FAIL = "TITLE_PROGRAM_SAVING_TRANSLATION_FAIL";
    public static final String ERROR_PROGRAM_SAVING_TRANSLATION_FAIL = "ERROR_PROGRAM_SAVING_TRANSLATION_FAIL";
    public static final String TITLE_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST = "TITLE_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST";
    public static final String ERROR_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST = "ERROR_SAVING_PROGRAM_INSUFFICIENT_PERMISSIONS_ON_HOST";
    public static final String TITLE_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE = "TITLE_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE";
    public static final String ERROR_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE = "ERROR_SAVING_PROGRAM_HOST_FILE_INACCESSIBLE";
    public static final String TITLE_SAVING_PROGRAM_INVALID_NAME = "TITLE_SAVING_PROGRAM_INVALID_NAME";
    public static final String ERROR_SAVING_PROGRAM_INVALID_NAME = "ERROR_SAVING_PROGRAM_INVALID_NAME";
    public static final String TITLE_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST = "TITLE_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST";
    public static final String ERROR_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST = "ERROR_SAVING_PROGRAM_INSUFFICIENT_SPACE_ON_HOST";
    public static final String TITLE_INVALID_CSV_FILE_FORMAT = "TITLE_INVALID_CSV_FILE_FORMAT";
    public static final String PROMPT_INVALID_CSV_FILE_FORMAT = "PROMPT_INVALID_CSV_FILE_FORMAT";

    public static String[] errorCodeToMessage(TransactionType transactionType, TIStatus tIStatus, TIDevice tIDevice, String string) {
        String[] arrstring = new String[]{null, null};
        ErrorTranslator errorTranslator = ErrorTranslator.selectTranslator(transactionType);
        String string2 = ErrorTranslator.getDeviceName(tIDevice);
        if (errorTranslator != null && (tIDevice != null || transactionType == TransactionType.SAVING_SCREEN_CAPTURES || transactionType == TransactionType.DATA_EDITING || transactionType == TransactionType.SAVING_PROGRAMS || transactionType == TransactionType.IMPORTING_DATA_FROM_FILE)) {
            arrstring = errorTranslator.errorCodeToMessage(tIStatus, string2, string);
        } else {
            arrstring = genericErrorTranslator.errorCodeToMessage(tIStatus, string2, string);
            TILogger.logError("ErrorTranslator", "Unable to find specific error translator or device is null");
        }
        return arrstring;
    }

    private static ErrorTranslator selectTranslator(TransactionType transactionType) {
        switch (transactionType) {
            case RECEIVING: 
            case SEND_OS: {
                return sendVarErrorTranslator;
            }
            case SENDING: {
                return getVarErrorTranslator;
            }
            case DELETING: {
                return deleteVarErrorTranslator;
            }
            case SAVING_SCREEN_CAPTURES: {
                return screenCaptureErrorTranslator;
            }
            case DATA_EDITING: {
                return dataEditorErrorTranslator;
            }
            case SAVING_PROGRAMS: {
                return saveProgramErrorTranslator;
            }
            case IMPORTING_DATA_FROM_FILE: {
                return dataImportErrorTranslator;
            }
        }
        return null;
    }

    private static String getDeviceName(TIDevice tIDevice) {
        String string = tIDevice == null ? BUNDLE.getString("calculator") : (tIDevice.getConnectionIDforName() == null || tIDevice.getConnectionIDforName().isEmpty() ? tIDevice.getDeviceName() : tIDevice.getDeviceName() + " - " + tIDevice.getConnectionIDforName());
        return string;
    }

    public String[] errorCodeToMessage(TIStatus tIStatus, String string, String string2) {
        String string3 = null;
        String string4 = null;
        switch (tIStatus.getStatusCode()) {
            case -6: 
            case 3: 
            case 5: 
            case 11: 
            case 13: 
            case 18: 
            case 28: 
            case 75: {
                string3 = BUNDLE.getString("TITLE_DEVICE_TIMES_OUT_DURING_COMMUNICATION");
                string4 = BUNDLE.getString("ERROR_DEVICE_TIMES_OUT_DURING_COMMUNICATION");
                string4 = MessageFormat.format(string4, string);
            }
        }
        return new String[]{string3, string4};
    }

}

