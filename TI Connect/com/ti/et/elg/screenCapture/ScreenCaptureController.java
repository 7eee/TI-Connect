/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.scene.Node
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 */
package com.ti.et.elg.screenCapture;

import com.ti.et.elg.AnalyticsManager.AnalyticsManager;
import com.ti.et.elg.AnalyticsManager.ScreenHit;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonActions.AboutAction;
import com.ti.et.elg.commonUISupport.commonActions.AnalyticsOptInAction;
import com.ti.et.elg.commonUISupport.commonActions.ChangeAppLanguageAction;
import com.ti.et.elg.commonUISupport.commonActions.ContactTISupportAction;
import com.ti.et.elg.commonUISupport.commonActions.ExitAction;
import com.ti.et.elg.commonUISupport.commonActions.HelpAction;
import com.ti.et.elg.commonUISupport.commonActions.OnlineTroubleshootingAction;
import com.ti.et.elg.commonUISupport.commonActions.VisitActivitiesExchangeAction;
import com.ti.et.elg.commonUISupport.commonActions.VisitTIEducationAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.deviceExplorer.actions.AddToHandheldAction;
import com.ti.et.elg.deviceExplorer.actions.CaptureScreenAction;
import com.ti.et.elg.deviceExplorer.actions.DataImportAction;
import com.ti.et.elg.deviceExplorer.actions.GetHHInformationAction;
import com.ti.et.elg.deviceExplorer.actions.SendOSAction;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.screenCapture.actions.AddScreenBorderAction;
import com.ti.et.elg.screenCapture.actions.CopyAction;
import com.ti.et.elg.screenCapture.actions.DeleteAction;
import com.ti.et.elg.screenCapture.actions.RemoveScreenBorderAction;
import com.ti.et.elg.screenCapture.actions.SaveScreenAction;
import com.ti.et.elg.screenCapture.actions.Scaling050Action;
import com.ti.et.elg.screenCapture.actions.Scaling075Action;
import com.ti.et.elg.screenCapture.actions.Scaling100Action;
import com.ti.et.elg.screenCapture.actions.Scaling200Action;
import com.ti.et.elg.screenCapture.actions.Scaling300Action;
import com.ti.et.elg.screenCapture.actions.Scaling400Action;
import com.ti.et.elg.screenCapture.actions.SelectAllAction;
import com.ti.et.elg.screenCapture.actions.SendToHHAction;
import com.ti.et.elg.screenCapture.actions.UnselectAllAction;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.net.URL;
import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ScreenCaptureController
implements ScreenCaptureInterface {
    private WorkspaceManagerInterface workspaceManager;
    protected DeviceExplorerInterface.ProductMode productMode = DeviceExplorerInterface.ProductMode.TI_CONNECT;

    @Override
    public void init(WorkspaceManagerInterface workspaceManagerInterface) {
        this.workspaceManager = workspaceManagerInterface;
        ScreenCaptureFactory.getScreenCaptureContainer().init(this);
        ScreenCaptureFactory.getScreenCaptureToolbar().init(this);
        ScreenCaptureFactory.getNoScreenCapturePresentPane().init();
        ScreenCaptureFactory.getNothingConnectedPane().init();
        ScreenCaptureFactory.getScreenCaptureContainer().setCopyAction(CopyAction.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().setDeleteAction(DeleteAction.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().setSaveAsAction(SaveScreenAction.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().setSelectAllAction(SelectAllAction.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().setSendToHHAction(SendToHHAction.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set050ZoomAction(Scaling050Action.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set075ZoomAction(Scaling075Action.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set100ZoomAction(Scaling100Action.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set200ZoomAction(Scaling200Action.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set300ZoomAction(Scaling300Action.getInstance());
        ScreenCaptureFactory.getScreenCaptureContainer().set400ZoomAction(Scaling400Action.getInstance());
    }

    @Override
    public void notifyIsActive(boolean bl) {
        if (bl) {
            TIAction tIAction = ScreenCaptureFactory.getScreenCaptureContainer().getBordersStateProperty().get() ? RemoveScreenBorderAction.getInstance() : AddScreenBorderAction.getInstance();
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE_AS, SaveScreenAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE, ChangeAppLanguageAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT, ExitAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_COPY, CopyAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_DELETE, DeleteAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL, SelectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL, UnselectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_050_ZOOM, Scaling050Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_075_ZOOM, Scaling075Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_100_ZOOM, Scaling100Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_200_ZOOM, Scaling200Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_300_ZOOM, Scaling300Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_400_ZOOM, Scaling400Action.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_HANDHELD_INFORMATION, GetHHInformationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS, SendToHHAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_ADD_TO_HANDHELDS, AddToHandheldAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN, CaptureScreenAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_DATA_IMPORT, DataImportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_TOGGLE_SCREEN_BORDERS, tIAction);
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_OS, SendOSAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP, HelpAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM, VisitTIEducationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE, VisitActivitiesExchangeAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING, OnlineTroubleshootingAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT, ContactTISupportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT, AboutAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS, AnalyticsOptInAction.getInstance());
            this.workspaceManager.getUINavigator().addNodeToNavCycle(ScreenCaptureFactory.getDeviceList().getFocusableNode());
            this.workspaceManager.getUINavigator().addNodeToNavCycle(ScreenCaptureFactory.getScreenCaptureContainer().getFocusableNode());
            AnalyticsManager.postHit(ScreenHit.SCREEN_CAPTURE);
        } else {
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE_AS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_COPY);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_DELETE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_050_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_075_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_100_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_200_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_300_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_ZOOM_400_ZOOM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_HANDHELD_INFORMATION);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_ADD_TO_HANDHELDS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_DATA_IMPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_TOGGLE_SCREEN_BORDERS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_OS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS);
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(ScreenCaptureFactory.getDeviceList().getFocusableNode());
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(ScreenCaptureFactory.getScreenCaptureContainer().getFocusableNode());
        }
    }

    @Override
    public boolean shutdown() {
        String[] arrstring = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("No"), "butnNo", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.SaveAllButton"), "butnSaveAll"};
        int n = -1;
        if (ScreenCaptureFactory.getScreenCaptureContainer().hasUnsavedItems()) {
            if (null != this.workspaceManager) {
                this.workspaceManager.switchToWorkspace(WorkspaceManagerInterface.WorkspaceType.SCREEN_CAPTURE);
            }
            Stage stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
            n = PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.SaveUnsavedScreenCaptures.Message"), "dlgSaveUnsaved", arrstring, new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()), stage);
            if (n == 2 && ScreenCaptureFactory.getScreenCaptureContainer().saveUnsavedScreenCaptureItems() != ScreenCaptureFactory.getScreenCaptureContainer().countUnsavedItems()) {
                n = 0;
            }
        } else {
            n = 1;
        }
        return n == 2 || n == 1;
    }

    @Override
    public void setScreenCaptureProductMode(DeviceExplorerInterface.ProductMode productMode) {
        this.productMode = productMode;
    }

    @Override
    public DeviceExplorerInterface.ProductMode getScreenCaptureProductMode() {
        return this.productMode;
    }
}

