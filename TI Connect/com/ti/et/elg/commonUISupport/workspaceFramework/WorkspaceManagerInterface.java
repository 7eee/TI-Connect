/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.control.TabPane
 */
package com.ti.et.elg.commonUISupport.workspaceFramework;

import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import javafx.scene.control.TabPane;

public interface WorkspaceManagerInterface {
    public void addTopLevelMenuAction(TopLevelMenuItem var1, TIAction var2);

    public void removeTopLevelMenuAction(TopLevelMenuItem var1);

    public void switchToWorkspace(WorkspaceType var1);

    public void setUIWorkspaceSelector(TabPane var1);

    public void setUINavigator(UINavigator var1);

    public UINavigator getUINavigator();

    public TIMenuItem getMenuItem(TopLevelMenuItem var1);

    public static enum WorkspaceType {
        DEVICE_EXPLORER,
        SCREEN_CAPTURE,
        PROGRAM_EDITOR,
        DATA_EDITOR,
        EMULATOR;
        

        private WorkspaceType() {
        }
    }

    public static enum TopLevelMenuItem {
        FILE_SAVE,
        FILE_SAVE_AS,
        FILE_EXIT,
        EDIT_UNDO,
        EDIT_REDO,
        EDIT_PASTE,
        EDIT_CUT,
        EDIT_COPY,
        EDIT_DELETE,
        EDIT_SELECT_ALL,
        EDIT_UNSELECT_ALL,
        EDIT_COPY_AS_FONT,
        EDIT_CLEAR_KPH,
        VIEW_REFRESH,
        VIEW_REFRESH_VIEW3,
        VIEW_ZOOM_050_ZOOM,
        VIEW_ZOOM_075_ZOOM,
        VIEW_ZOOM_100_ZOOM,
        VIEW_ZOOM_200_ZOOM,
        VIEW_ZOOM_300_ZOOM,
        VIEW_ZOOM_400_ZOOM,
        VIEW_SHOWHIDE_KPH,
        VIEW_SHOWHIDE_LARGESCREEN,
        VIEW_SHOWHIDE_VIEW3,
        VIEW_SHOWHIDE_SCREEN_CAPTURES,
        VIEW_HANDHELD_INFORMATION,
        ACTIONS_SEND_TO_HANDHELDS,
        ACTIONS_SEND_TO_COMPUTER,
        ACTIONS_ADD_TO_HANDHELDS,
        ACTIONS_CAPTURE_SCREEN,
        ACTIONS_DATA_IMPORT,
        ACTIONS_TOGGLE_SCREEN_BORDERS,
        ACTIONS_SEND_OS,
        ACTIONS_RESET_EMULATOR,
        HELP_HELP,
        AUTOMATIC_NOTIFICATION,
        HELP_VISIT_EDUCATION_TI_COM,
        HELP_VISIT_ACTIVITIES_EXCHANGE,
        HELP_ONLINE_TROUBLESHOOTING,
        HELP_ACTIVATE,
        HELP_PURCHASE,
        HELP_REMAINDER_SETTINGS,
        HELP_CONTACT_TI_SUPPORT,
        HELP_ABOUT,
        HELP_ANALYTICS,
        FILE_OPEN,
        FILE_NEW,
        FILE_CLOSE,
        FILE_CLOSE_ALL,
        FILE_CHANGE_LANGUAGE;
        

        private TopLevelMenuItem() {
        }
    }

}

