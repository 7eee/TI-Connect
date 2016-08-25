/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.HostServices
 *  javafx.scene.input.DataFormat
 *  javafx.stage.Stage
 */
package com.ti.et.elg.commonUISupport.commonConstants;

import javafx.application.HostServices;
import javafx.scene.input.DataFormat;
import javafx.stage.Stage;

public class CommonConstants {
    public static final String TI_FONT_NAME = "TI Uni";
    public static final int TI_FONT_SIZE = 13;
    public static String PRODUCT_NAME = "TI Connect CE";
    public static String PRODUCT_NAME_WITH_TRADEMARK;
    public static final String HELP_FILE_NAME = "TIConnectHelp";
    public static final String HELP_FILE_EXTENSION = ".pdf";
    public static final String helpCmd = "open ";
    public static final String helpFolder = "help/";
    public static String MAIN_STYLE_SHEET;
    public static String SC_STYLE_SHEET;
    public static Stage MAIN_STAGE;
    public static Stage SPLASH_STAGE;
    public static Stage SCREEN_CAPTURE_STAGE;
    public static HostServices hostServices;
    public static final String USER_PROPERTY_LAST_WINDOW_WIDTH = "Window.lastWidth.value";
    public static final String USER_PROPERTY_LAST_WINDOW_HEIGHT = "Window.lastHeight.value";
    public static final String USER_PROPERTY_LAST_WINDOW_X = "Window.lastX.value";
    public static final String USER_PROPERTY_LAST_WINDOW_Y = "Window.lastY.value";
    public static final String USER_PROPERTY_LAST_SPLITTER_POSITION = "DeviceListSplitter.position.value";
    public static final String USER_PROPERTY_LAST_PE_SPLITTER_POSITION = "PECatalogSplitter.position.value";
    public static final String USER_PROPERTY_LAST_IMG_SCALE_VALUE = "ScreenCapture.lastScale.value";
    public static final String USER_PROPERTY_SORT_ORDER = "TableContent.lastSortOrder.values";
    public static final String USER_PROPERTY_SORT_TYPE = "TableContent.lastSortOrderType.value";
    public static final String USER_PROPERTY_LAST_INSTALL_OS_FOLDER = "InstallOS.lastFolder.path";
    public static final String USER_PROPERTY_LAST_ADDFILES_FOLDER = "AddFiles.lastFolder.path";
    public static final String USER_PROPERTY_LAST_SAVEFILES_FOLDER = "Save.lastFolder.path";
    public static final String USER_PROPERTY_CROP_SREEN_CAPTURE = "ScreenCapture.CropImage.enabled";
    public static final String USER_PROPERTY_SENDTOHH_REPLACE = "SendToHH.ReplaceFiles.enabled";
    public static final String USER_PROPERTY_CAPTURE_WITH_BORDER = "ScreenCapture.withBorder.enabled";
    public static final String USER_PROPERTY_PROGRAM_EDITOR_FOLDER = "ProgramEditor.lastOpenOrSaveFolder.path";
    public static final String USER_PROPERTY_PROGRAM_EDITOR_CATALOG_TYPE = "ProgramEditor.lastCatalog.value";
    public static final String USER_PROPERTY_LAST_PE_CATALOG_SYNTAX_SPLITTER_POSITION = "PECatalogSyntaxSplitter.position.value";
    public static final String USER_PROPERTY_GET_DEVICE_NAME_FROM_DRIVER = "GetDeviceNameFromDriver.enabled";
    public static final String USER_PROPERTY_ANALYTICS_ENABLED = "Analytics.enabled";
    public static final String USER_PROPERTY_OVERRIDE_LANGUAGE = "Application.OverrideLanguage";
    public static final String USER_PROPERTY_OVERRIDE_COUNTRY = "Application.OverrideCountry";
    public static final String USER_PROPERTY_LAST_DATA_IMPORT_FOLDER = "DataImport.lastFolder.path";
    public static final String USER_PROPERTY_TIOSUPDATE_SHOW_BUILD_NUMBER_PANEL = "Application.TIOSUpdate.ShowBuildNumberPanel";
    public static final DataFormat CUSTOM_DATAFORMAT;
    public static final String PROPERTY_LOCALE_SV_VARIANT = "SMART";
    public static final int SMARTVIEW_TOOLBAR_LEFT_PADDING = 100;
    public static final int WS_SCREENCAPTURE_ID = 1;
    public static final int WS_HH_EXPLORER_ID = 2;
    public static final String TI83P_PROGRAM_EXTENSION = "*.8xp";
    public static final String TI83P_PROGRAM_DESCRIPTION = "openProgram.fileChooser.8xp.description";
    public static final String CONNECT_STRING = "Connect";
    public static final String SMARTVIEW_STRING = "SmartView";
    public static final String UPDATE_STRING = "Update";
    public static final String TRADEMARK_SYMBOL = "\u2122";

    public static String addTrademarkToProductName(int n, String string) {
        String string2 = n == 0 ? "Connect" : "SmartView";
        StringBuilder stringBuilder = new StringBuilder(string);
        string2 = n == 3 ? "Update" : string2;
        int n2 = stringBuilder.lastIndexOf(string2);
        if (n2 > -1) {
            stringBuilder.insert(n2 + string2.length(), "\u2122");
        }
        return stringBuilder.toString();
    }

    public static void setSplashStage(Stage stage) {
        SPLASH_STAGE = stage;
    }

    public static void setProductName(String string) {
        PRODUCT_NAME = string;
    }

    static {
        MAIN_STYLE_SHEET = "";
        SC_STYLE_SHEET = "";
        CUSTOM_DATAFORMAT = new DataFormat(new String[]{"ti.dataFormat"});
    }

    public static enum TIDataFormat {
        KEY_PRESS_HISTORY,
        KPH_AS_FONT,
        SCREEN_CAPTURE,
        PROGRAM_EDITOR;
        

        private TIDataFormat() {
        }
    }

}

