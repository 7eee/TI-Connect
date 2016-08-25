/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.binding.BooleanBinding
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.value.ObservableBooleanValue
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.scene.control.Menu
 *  javafx.scene.control.MenuBar
 *  javafx.scene.control.MenuBuilder
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.MenuItemBuilder
 *  javafx.scene.control.SeparatorMenuItem
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.TabPane
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyCodeCombination
 *  javafx.scene.input.KeyCombination
 *  javafx.scene.input.KeyCombination$Modifier
 */
package com.ti.et.elg.tiConnect;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.notifications.actions.CheckForNotificationAction;
import com.ti.et.elg.tiConnect.TIConnectResourceBundle;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

public class WorkspaceManager
implements WorkspaceManagerInterface {
    public static final int WORKSPACE_TAB_INDEX_SCREEN_CAPTURE = 1;
    public static final int WORKSPACE_TAB_INDEX_DEVICE_EXPLORER = 2;
    public static final int WORKSPACE_TAB_INDEX_PROGRAM_EDITOR = 3;
    public static final int WORKSPACE_TAB_INDEX_DATA_EDITOR = 4;
    private Map<WorkspaceManagerInterface.TopLevelMenuItem, TIMenuItem> menuMap = new HashMap<WorkspaceManagerInterface.TopLevelMenuItem, TIMenuItem>();
    private TabPane tabPaneWorkSpaces;
    private UINavigator uiNavigator = null;

    @Override
    public void addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem topLevelMenuItem, TIAction tIAction) {
        TIMenuItem tIMenuItem = this.menuMap.get((Object)topLevelMenuItem);
        if (tIMenuItem != null) {
            tIMenuItem.setAction(tIAction);
        }
    }

    @Override
    public void removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem topLevelMenuItem) {
        TIMenuItem tIMenuItem = this.menuMap.get((Object)topLevelMenuItem);
        if (tIMenuItem != null) {
            tIMenuItem.setAction(null);
        }
    }

    protected MenuBar createMenuBar() {
        KeyCombination.Modifier modifier;
        KeyCombination.Modifier modifier2;
        TIResourceBundle tIResourceBundle = TIConnectResourceBundle.BUNDLE;
        MenuBar menuBar = new MenuBar();
        menuBar.setId("barMenu");
        if (PlatformManager.isMac()) {
            modifier2 = KeyCombination.SHIFT_DOWN;
            modifier = KeyCombination.META_DOWN;
        } else {
            modifier2 = KeyCombination.SHIFT_DOWN;
            modifier = KeyCombination.CONTROL_DOWN;
        }
        TIMenuItem tIMenuItem = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.save"));
        tIMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE, tIMenuItem);
        tIMenuItem.setId("menuSave");
        TIMenuItem tIMenuItem2 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.saveas"));
        tIMenuItem2.setAccelerator(new KeyCodeCombination(KeyCode.S, new KeyCombination.Modifier[]{modifier2, modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE_AS, tIMenuItem2);
        tIMenuItem2.setId("menuSaveAs");
        TIMenuItem tIMenuItem3 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.open"));
        tIMenuItem3.setAccelerator(new KeyCodeCombination(KeyCode.O, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_OPEN, tIMenuItem3);
        tIMenuItem3.setId("menuOpen");
        TIMenuItem tIMenuItem4 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.exit"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT, tIMenuItem4);
        tIMenuItem4.setId("menuExit");
        TIMenuItem tIMenuItem5 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.new"));
        tIMenuItem5.setAccelerator(new KeyCodeCombination(KeyCode.N, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_NEW, tIMenuItem5);
        tIMenuItem5.setId("menuNew");
        TIMenuItem tIMenuItem6 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.close"));
        tIMenuItem6.setAccelerator(new KeyCodeCombination(KeyCode.W, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE, tIMenuItem6);
        tIMenuItem6.setId("menuClose");
        TIMenuItem tIMenuItem7 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.closeAll"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE_ALL, tIMenuItem7);
        tIMenuItem7.setId("menuCloseAll");
        TIMenuItem tIMenuItem8 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.file.changeLanguage"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE, tIMenuItem8);
        tIMenuItem8.setId("menuChangeLanguage");
        TIMenuItem tIMenuItem9 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.undo"));
        tIMenuItem9.setAccelerator(new KeyCodeCombination(KeyCode.Z, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNDO, tIMenuItem9);
        tIMenuItem9.setId("menuUndo");
        TIMenuItem tIMenuItem10 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.redo"));
        if (PlatformManager.isMac()) {
            tIMenuItem10.setAccelerator(new KeyCodeCombination(KeyCode.Z, new KeyCombination.Modifier[]{modifier2, modifier}));
        } else {
            tIMenuItem10.setAccelerator(new KeyCodeCombination(KeyCode.Y, new KeyCombination.Modifier[]{modifier}));
        }
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_REDO, tIMenuItem10);
        tIMenuItem10.setId("menuRedo");
        TIMenuItem tIMenuItem11 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.cut"));
        tIMenuItem11.setAccelerator(new KeyCodeCombination(KeyCode.X, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_CUT, tIMenuItem11);
        tIMenuItem11.setId("menuCut");
        TIMenuItem tIMenuItem12 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.copy"));
        tIMenuItem12.setAccelerator(new KeyCodeCombination(KeyCode.C, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_COPY, tIMenuItem12);
        tIMenuItem12.setId("menuCopy");
        TIMenuItem tIMenuItem13 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.paste"));
        tIMenuItem13.setAccelerator(new KeyCodeCombination(KeyCode.V, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_PASTE, tIMenuItem13);
        tIMenuItem13.setId("menuPaste");
        TIMenuItem tIMenuItem14 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.delete"));
        if (PlatformManager.isWindows()) {
            tIMenuItem14.setAccelerator(new KeyCodeCombination(KeyCode.DELETE, new KeyCombination.Modifier[0]));
        }
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_DELETE, tIMenuItem14);
        tIMenuItem14.setId("menuDelete");
        TIMenuItem tIMenuItem15 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.selectAll"));
        tIMenuItem15.setAccelerator(new KeyCodeCombination(KeyCode.A, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL, tIMenuItem15);
        tIMenuItem15.setId("menuSelectAll");
        TIMenuItem tIMenuItem16 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.edit.unselectAll"));
        tIMenuItem16.setAccelerator(new KeyCodeCombination(KeyCode.A, new KeyCombination.Modifier[]{modifier2, modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL, tIMenuItem16);
        tIMenuItem16.setId("menuUnselectAll");
        TIMenuItem tIMenuItem17 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.view.refresh"));
        tIMenuItem17.setAccelerator(new KeyCodeCombination(KeyCode.R, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_REFRESH, tIMenuItem17);
        tIMenuItem17.setId("menuRefresh");
        TIMenuItem tIMenuItem18 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.view.handheldInformation"));
        tIMenuItem18.setAccelerator(new KeyCodeCombination(KeyCode.D, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_HANDHELD_INFORMATION, tIMenuItem18);
        tIMenuItem18.setId("menuHandheldInformation");
        TIMenuItem tIMenuItem19 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.sendToHandheld"));
        tIMenuItem19.setAccelerator(new KeyCodeCombination(KeyCode.E, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS, tIMenuItem19);
        tIMenuItem19.setId("menuSendToHandhelds");
        TIMenuItem tIMenuItem20 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.sendToComputer"));
        tIMenuItem20.setAccelerator(new KeyCodeCombination(KeyCode.L, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_COMPUTER, tIMenuItem20);
        tIMenuItem20.setId("menuSendToComputer");
        TIMenuItem tIMenuItem21 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.addToHandheld"));
        tIMenuItem21.setAccelerator(new KeyCodeCombination(KeyCode.M, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_ADD_TO_HANDHELDS, tIMenuItem21);
        tIMenuItem21.setId("menuAddToHandhelds");
        TIMenuItem tIMenuItem22 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.captureAllScreens"));
        tIMenuItem22.setAccelerator(new KeyCodeCombination(KeyCode.T, new KeyCombination.Modifier[]{modifier}));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN, tIMenuItem22);
        tIMenuItem22.setId("menuCaptureScreen");
        TIMenuItem tIMenuItem23 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.importData"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_DATA_IMPORT, tIMenuItem23);
        tIMenuItem23.setId("menuDataImport");
        TIMenuItem tIMenuItem24 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.050"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_050_ZOOM, tIMenuItem24);
        tIMenuItem24.setId("menuZoom50");
        TIMenuItem tIMenuItem25 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.075"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_075_ZOOM, tIMenuItem25);
        tIMenuItem24.setId("menuZoom75");
        TIMenuItem tIMenuItem26 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.100"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_100_ZOOM, tIMenuItem26);
        tIMenuItem26.setId("menuZoom100");
        TIMenuItem tIMenuItem27 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.200"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_200_ZOOM, tIMenuItem27);
        tIMenuItem27.setId("menuZoom200");
        TIMenuItem tIMenuItem28 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.300"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_300_ZOOM, tIMenuItem28);
        tIMenuItem24.setId("menuZoom300");
        TIMenuItem tIMenuItem29 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize.400"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_400_ZOOM, tIMenuItem29);
        tIMenuItem26.setId("menuZoom400");
        Menu menu = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.actions.changeScreenCaptureSize"))).items(new MenuItem[]{tIMenuItem24.getMenuItem(), tIMenuItem25.getMenuItem(), tIMenuItem26.getMenuItem(), tIMenuItem27.getMenuItem(), tIMenuItem28.getMenuItem(), tIMenuItem29.getMenuItem()}).build();
        menu.setId("menuZoom");
        menu.setDisable(true);
        menu.disableProperty().bind((ObservableValue)tIMenuItem24.getMenuItem().disableProperty().and((ObservableBooleanValue)tIMenuItem26.getMenuItem().disableProperty().and((ObservableBooleanValue)tIMenuItem27.getMenuItem().disableProperty())));
        TIMenuItem tIMenuItem30 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("topLevelMenuItem.actions.captureWithBorders"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_TOGGLE_SCREEN_BORDERS, tIMenuItem30);
        tIMenuItem30.setId("menuAddRemoveScreenBorders");
        TIMenuItem tIMenuItem31 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.actions.sendOS"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_OS, tIMenuItem31);
        tIMenuItem31.setId("menuSendOS");
        TIMenuItem tIMenuItem32 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.tiConnectHelp"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP, tIMenuItem32);
        tIMenuItem32.setId("menuTIConnectHelp");
        TIMenuItem tIMenuItem33 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.checkForNotification"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.AUTOMATIC_NOTIFICATION, tIMenuItem33);
        tIMenuItem33.setId("menuNotificationTI");
        tIMenuItem33.setAction(CheckForNotificationAction.getInstance());
        TIMenuItem tIMenuItem34 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.visitEducationTiCom"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM, tIMenuItem34);
        tIMenuItem34.setId("menuVisitEducation");
        TIMenuItem tIMenuItem35 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.contactTISupport"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT, tIMenuItem35);
        tIMenuItem35.setId("menuContactTISupport");
        TIMenuItem tIMenuItem36 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.about"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT, tIMenuItem36);
        tIMenuItem36.setId("menuAboutTIConnect");
        TIMenuItem tIMenuItem37 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.visitActivitiesExchange"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE, tIMenuItem37);
        tIMenuItem34.setId("menuVisitActivitiesExchange");
        TIMenuItem tIMenuItem38 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.onlineTroubleshooting"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING, tIMenuItem38);
        tIMenuItem34.setId("menuOnlineTroubleshooting");
        TIMenuItem tIMenuItem39 = new TIMenuItem(tIResourceBundle.getString("topLevelMenuItem.help.analyticsDialog"));
        this.menuMap.put(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS, tIMenuItem39);
        tIMenuItem39.setId("menuAnalyticsDialog");
        Menu menu2 = null;
        if (PlatformManager.isMac()) {
            menu2 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.file"))).items(new MenuItem[]{tIMenuItem5.getMenuItem(), tIMenuItem3.getMenuItem(), new SeparatorMenuItem(), tIMenuItem6.getMenuItem(), tIMenuItem7.getMenuItem(), tIMenuItem.getMenuItem(), tIMenuItem2.getMenuItem(), new SeparatorMenuItem(), tIMenuItem8.getMenuItem()}).build();
        } else {
            tIMenuItem32.setAccelerator(new KeyCodeCombination(KeyCode.F1, new KeyCombination.Modifier[0]));
            menu2 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.file"))).items(new MenuItem[]{tIMenuItem5.getMenuItem(), tIMenuItem3.getMenuItem(), new SeparatorMenuItem(), tIMenuItem6.getMenuItem(), tIMenuItem7.getMenuItem(), tIMenuItem.getMenuItem(), tIMenuItem2.getMenuItem(), new SeparatorMenuItem(), tIMenuItem8.getMenuItem(), new SeparatorMenuItem(), tIMenuItem4.getMenuItem()}).build();
        }
        Menu menu3 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.edit"))).items(new MenuItem[]{tIMenuItem9.getMenuItem(), tIMenuItem10.getMenuItem(), new SeparatorMenuItem(), tIMenuItem11.getMenuItem(), tIMenuItem12.getMenuItem(), tIMenuItem13.getMenuItem(), tIMenuItem14.getMenuItem(), new SeparatorMenuItem(), tIMenuItem15.getMenuItem(), tIMenuItem16.getMenuItem()}).build();
        Menu menu4 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.view"))).items(new MenuItem[]{tIMenuItem17.getMenuItem(), new SeparatorMenuItem(), tIMenuItem18.getMenuItem()}).build();
        Menu menu5 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.actions"))).items(new MenuItem[]{tIMenuItem19.getMenuItem(), tIMenuItem20.getMenuItem(), new SeparatorMenuItem(), tIMenuItem23.getMenuItem(), new SeparatorMenuItem(), tIMenuItem21.getMenuItem(), new SeparatorMenuItem(), tIMenuItem22.getMenuItem(), new SeparatorMenuItem(), menu, tIMenuItem30.getMenuItem(), new SeparatorMenuItem(), tIMenuItem31.getMenuItem()}).build();
        Menu menu6 = ((MenuBuilder)MenuBuilder.create().text(tIResourceBundle.getString("topLevelMenuItem.help"))).items(new MenuItem[]{tIMenuItem32.getMenuItem(), tIMenuItem33.getMenuItem(), new SeparatorMenuItem(), tIMenuItem34.getMenuItem(), tIMenuItem37.getMenuItem(), tIMenuItem39.getMenuItem(), tIMenuItem38.getMenuItem(), tIMenuItem35.getMenuItem(), new SeparatorMenuItem(), tIMenuItem36.getMenuItem()}).build();
        menuBar.getMenus().addAll((Object[])new Menu[]{menu2, menu3, menu4, menu5, menu6});
        if (PlatformManager.isMac()) {
            menuBar.setUseSystemMenuBar(true);
        }
        return menuBar;
    }

    @Override
    public TIMenuItem getMenuItem(WorkspaceManagerInterface.TopLevelMenuItem topLevelMenuItem) {
        TIMenuItem tIMenuItem = this.menuMap.get((Object)topLevelMenuItem);
        return tIMenuItem != null ? tIMenuItem : null;
    }

    @Override
    public void switchToWorkspace(WorkspaceManagerInterface.WorkspaceType workspaceType) {
        switch (workspaceType) {
            case DEVICE_EXPLORER: {
                if (null == this.tabPaneWorkSpaces || this.tabPaneWorkSpaces.getSelectionModel().isSelected(2)) break;
                this.tabPaneWorkSpaces.getSelectionModel().select(2);
                break;
            }
            case SCREEN_CAPTURE: {
                if (null == this.tabPaneWorkSpaces || this.tabPaneWorkSpaces.getSelectionModel().isSelected(1)) break;
                this.tabPaneWorkSpaces.getSelectionModel().select(1);
                break;
            }
            case PROGRAM_EDITOR: {
                if (this.tabPaneWorkSpaces == null) break;
                this.tabPaneWorkSpaces.getSelectionModel().select(3);
                break;
            }
            case DATA_EDITOR: {
                if (null == this.tabPaneWorkSpaces) break;
                this.tabPaneWorkSpaces.getSelectionModel().select(4);
                break;
            }
        }
    }

    @Override
    public void setUIWorkspaceSelector(TabPane tabPane) {
        if (null != tabPane) {
            this.tabPaneWorkSpaces = tabPane;
        }
    }

    @Override
    public void setUINavigator(UINavigator uINavigator) {
        this.uiNavigator = uINavigator;
    }

    @Override
    public UINavigator getUINavigator() {
        return this.uiNavigator;
    }

}

