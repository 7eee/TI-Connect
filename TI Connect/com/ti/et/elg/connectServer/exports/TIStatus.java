/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

public class TIStatus {
    private int statusCode = 0;
    public static final int STATUS_OK = 0;
    public static final int STATUS_HOST_FILE_INACCESSIBLE = -1;
    public static final int STATUS_JNI_HANDLING_ERROR = -2;
    public static final int STATUS_OPERATION_CANCELED = 62;
    public static final int STATUS_DEVICE_FILE_INACCESSIBLE = 7;
    public static final int STATUS_DEVICE_FILE_ALREADY_EXISTS = 107;
    public static final int STATUS_INTERNAL_ERROR_NO_DATA = -5;
    public static final int STATUS_DEVICE_NON_RESPONSIVE = -6;
    public static final int STATUS_HOST_LOCATION_INACCESSIBLE = -7;
    public static final int STATUS_HOST_FILE_ALREADY_EXISTS = -8;
    public static final int STATUS_HOST_INSUFICIENT_PERMISSIONS = -9;
    public static final int STATUS_HOST_INSUFICIENT_SPACE = -10;
    public static final int STATUS_INVALID_REQUEST = 1;
    public static final int STATUS_NO_DEVICE_SUPPORT = 2;
    public static final int STATUS_BAD_PARAMETER_BLOCK = 3;
    public static final int STATUS_BAD_COMMUNICATION_CHECKSUM = 4;
    public static final int STATUS_COMMUNICATION_TIMEOUT = 5;
    public static final int STATUS_BATTERIES_TOO_LOW = 6;
    public static final int STATUS_ITEM_IS_LOCKED = 8;
    public static final int STATUS_ITEM_IS_ARCHIVED = 9;
    public static final int STATUS_REQUEST_IS_UNSUPPORTED = 10;
    public static final int STATUS_CHANNEL_IS_NOT_OPEN = 11;
    public static final int STATUS_CHANNEL_IS_ALREADY_OPEN = 12;
    public static final int STATUS_CHANNEL_IS_NOT_AVAILABLE = 13;
    public static final int STATUS_NO_PARAMETER_BLOCK = 14;
    public static final int STATUS_NO_DATA_DESCRIPTOR_PRESENT = 15;
    public static final int STATUS_PARAMETERBLOCK_IN_USE = 16;
    public static final int STATUS_INVALID_PARAMETERS = 17;
    public static final int STATUS_DEVICE_IS_NOT_READY = 18;
    public static final int STATUS_OBJECT_NAME_IS_REQUIRED = 19;
    public static final int STATUS_PORT_NAME_IS_REQUIRED = 20;
    public static final int STATUS_PORT_IS_NOT_FOUND = 21;
    public static final int STATUS_UNABLE_TO_ALLOCATE_BUFFER = 22;
    public static final int STATUS_UNABLE_TO_SEND_DATA = 23;
    public static final int STAUS_UNABLE_TO_RETRIEVE_DATA = 24;
    public static final int STATUS_UNABLE_TO_ALLOCATE_MEMORY = 25;
    public static final int STATUS_UNABLE_TO_CREATE_PB = 26;
    public static final int STATUS_NO_DIRECTORY_LIST_AVAILABLE = 27;
    public static final int STATUS_BAD_COMMUNICATIONS_SEQUENCE = 28;
    public static final int STATUS_REQUEST_IS_NOT_VALID = 29;
    public static final int STATUS_CALCULATOR_MEMORY_IS_FULL = 30;
    public static final int STATUS_FLASH_MEMORY_IS_FULL = 31;
    public static final int STATUS_FLASH_DUPLICATE_NAME = 32;
    public static final int STATUS_FLASH_INVALID_CERTIFICATE_HDR = 33;
    public static final int STATUS_FLASH_NO_CERTIFICATE = 34;
    public static final int STATUS_FLASH_BAD_SIGNATURE = 35;
    public static final int STATUS_FLASH_CERTIFICATE_EXPIRED = 36;
    public static final int STATUS_FLASH_CANNOT_REPLACE_CERTIFICATE = 37;
    public static final int STATUS_FLASH_BAD_LOAD_ADDRESS = 38;
    public static final int STATUS_FLASH_BATTERIES_ARE_TOO_LOW = 39;
    public static final int STATUS_FLASH_FRAME_TOO_LARGE = 40;
    public static final int STATUS_FLASH_ERROR_ERASING_FLASH = 41;
    public static final int STATUS_FLASH_ERROR_WRITING_FLASH = 42;
    public static final int STATUS_FLASH_UNKNOWN_ERROR_CODE = 43;
    public static final int STATUS_FLASH_BAD_OBJ_FILE_FORMAT = 44;
    public static final int STATUS_DEVICE_MISMATCH_ON_SEND = 45;
    public static final int STATUS_DEVICE_CHANNEL_IS_OPEN = 46;
    public static final int STATUS_CANNOT_DELETE_OBJECT = 47;
    public static final int STATUS_ILLEGAL_DATATYPE = 48;
    public static final int STATUS_MISMATCH_DATATYPE = 49;
    public static final int STATUS_FOLDER_IS_NOT_EMPTY = 50;
    public static final int STATUS_UNABLE_TO_DELETE_ITEM = 51;
    public static final int STATUS_OBJ_TYPE_IS_NOT_SUPPORTED = 52;
    public static final int STATUS_NEED_NEWER_OPERATING_SYSTEM = 53;
    public static final int STATUS_CANNOT_USE_THIS_OS_UPDATE = 54;
    public static final int STATUS_CANNOT_OVERWRITE_OBJECT = 55;
    public static final int STATUS_CANNOT_OVERWRITE_SYSTEM_RESERVED = 56;
    public static final int STATUS_NAME_IS_ILLEGAL_OR_INVALID = 57;
    public static final int STATUS_NAME_IS_RESERVED_OR_RESTRICTED = 58;
    public static final int STATUS_LOGIN_IS_INVALID = 59;
    public static final int STATUS_LOGIN_WAS_ABORTED = 60;
    public static final int STATUS_LOGIN_IS_PENDING = 61;
    public static final int STATUS_ITEM_CANNOT_TRANSLATE_FOR_DEVICE = 63;
    public static final int STATUS_DEVICE_DOES_NOT_SUPPORT_LOGIN = 64;
    public static final int STATUS_ITEM_VERSION_IS_TOO_NEW = 65;
    public static final int STATUS_PASSWORD_IS_INVALID = 66;
    public static final int STATUS_REQUIRES_AUTHENTICATION = 67;
    public static final int STATUS_AUTHENTICATION_WAS_INVALID = 68;
    public static final int STATUS_NEW_PASSWORD_IS_SAME_AS_OLD = 69;
    public static final int STATUS_INSUFFICENT_PRIVILEGES = 70;
    public static final int STATUS_UNABLE_TO_CHANGE_SHARINGSETTINGS = 71;
    public static final int STATUS_PASSWORD_ERROR = 72;
    public static final int STATUS_MUST_PROVIDE_NEW_PASSWORD = 73;
    public static final int STATUS_INSUFFICENT_RAM_AVAILABLE = 74;
    public static final int STATUS_OPERATION_CANCELED_ON_DEVICE = 75;
    public static final int STATUS_DEVICE_IS_BUSY = 76;
    public static final int STATUS_INVALID_FILE_SPEC = 100;
    public static final int STATUS_FILE_NOT_RECOGNIZED = 101;
    public static final int STATUS_FILE_RECOGNIZED_BUT_TOO_NEW = 102;
    public static final int STATUS_UNRECOGNIZED_OBJECT_FORMAT = 103;
    public static final int STATUS_ASYNCH_OPERATION_NOT_ALLOWED = 104;
    public static final int STATUS_CANNOT_ACCESS_DESCRIPTORS = 105;
    public static final int STATUS_CANNOT_ACCESS_RESOURCES = 106;
    public static final int STATUS_UNRECOGNIZED_DEVICE_ID = 108;
    public static final int STATUS_ILLEGAL_PB_OPERATION = 109;
    public static final int STATUS_DONT_ALLOW_TO_WRITE_FILE = 110;
    public static final int STATUS_VAR_NOT_A_PROGRAM = 111;
    public static final int STATUS_PROGRAM_ALREADY_PRESENT = 112;
    public static final int STATUS_VAR_IS_CORRUPTED = 113;
    public static final int STATUS_VAR_WITH_BAD_CHECKSUM = 114;
    public static final int STATUS_INVALID_DATA_RANGE = 300;
    public static final int STATUS_INVALID_CSV_FILE_FORMAT = 115;
    private static final int[] TERMINAL_ERRORS = new int[]{3, 62, -6, 5, 11, 13, 18, 75};

    public TIStatus(int n) {
        this.statusCode = n;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public boolean isFailure() {
        return this.statusCode != 0;
    }

    public boolean isTerminalFailure() {
        for (int i = 0; i < TERMINAL_ERRORS.length; ++i) {
            if (this.statusCode != TERMINAL_ERRORS[i]) continue;
            return true;
        }
        return false;
    }
}

