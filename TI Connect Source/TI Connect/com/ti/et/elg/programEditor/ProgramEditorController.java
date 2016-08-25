/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.programEditor;

import com.ti.et.elg.AnalyticsManager.AnalyticsManager;
import com.ti.et.elg.AnalyticsManager.ScreenHit;
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
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.actions.CaptureScreenAction;
import com.ti.et.elg.deviceExplorer.exports.TIOpenHandlerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.programEditor.actions.CloseAllProgramAction;
import com.ti.et.elg.programEditor.actions.CloseProgramAction;
import com.ti.et.elg.programEditor.actions.CopyAction;
import com.ti.et.elg.programEditor.actions.CreateProgramAction;
import com.ti.et.elg.programEditor.actions.CutAction;
import com.ti.et.elg.programEditor.actions.OpenProgramAction;
import com.ti.et.elg.programEditor.actions.PasteAction;
import com.ti.et.elg.programEditor.actions.RedoAction;
import com.ti.et.elg.programEditor.actions.SaveAsProgramAction;
import com.ti.et.elg.programEditor.actions.SaveProgramAction;
import com.ti.et.elg.programEditor.actions.SelectAllAction;
import com.ti.et.elg.programEditor.actions.SendProgramToHHAction;
import com.ti.et.elg.programEditor.actions.UndoAction;
import com.ti.et.elg.programEditor.actions.UnselectAllAction;
import com.ti.et.elg.programEditor.exports.ProgramEditorInterface;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import javafx.scene.Node;

public class ProgramEditorController
implements ProgramEditorInterface,
TIOpenHandlerInterface {
    private WorkspaceManagerInterface workspaceManager;

    @Override
    public void init(WorkspaceManagerInterface workspaceManagerInterface) {
        ProgramEditorFactory.getProgramEditorContents().init();
        ProgramEditorFactory.getProgramEditorCatalog().init();
        ProgramEditorFactory.getProgramEditorToolbar().init();
        this.workspaceManager = workspaceManagerInterface;
        DeviceExplorerFactory.getDeviceExplorer().registerOpenHandlerForType(5, this);
        DeviceExplorerFactory.getDeviceExplorer().registerOpenHandlerForType(6, this);
    }

    @Override
    public void notifyIsActive(boolean bl) {
        if (bl) {
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_NEW, CreateProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE, SaveProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE_AS, SaveAsProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_OPEN, OpenProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE, CloseProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE_ALL, CloseAllProgramAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE, ChangeAppLanguageAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT, ExitAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNDO, UndoAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_REDO, RedoAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_CUT, CutAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_COPY, CopyAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_PASTE, PasteAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL, SelectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL, UnselectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS, SendProgramToHHAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN, CaptureScreenAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP, HelpAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM, VisitTIEducationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE, VisitActivitiesExchangeAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING, OnlineTroubleshootingAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT, ContactTISupportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT, AboutAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS, AnalyticsOptInAction.getInstance());
            Node node = ProgramEditorFactory.getProgramEditorCatalog().getFocusableNode();
            this.workspaceManager.getUINavigator().addNodeToNavCycle(node.isDisabled() ? null : node);
            ProgramEditorFactory.getProgramEditorContents().updateFocusableNodes();
            for (Node node2 : ProgramEditorFactory.getProgramEditorContents().getFocusableNodes()) {
                this.workspaceManager.getUINavigator().addNodeToNavCycle(node2);
            }
            AnalyticsManager.postHit(ScreenHit.PROGRAM_EDITOR);
        } else {
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_NEW);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE_AS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_OPEN);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CLOSE_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNDO);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_REDO);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_CUT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_COPY);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_PASTE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS);
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(ProgramEditorFactory.getProgramEditorCatalog().getFocusableNode());
            for (Node node : ProgramEditorFactory.getProgramEditorContents().getFocusableNodes()) {
                this.workspaceManager.getUINavigator().removeNodeFromNavCycle(node);
            }
        }
    }

    @Override
    public void updateFocusableNodes() {
        if (this.workspaceManager != null && this.workspaceManager.getUINavigator() != null) {
            Node node = ProgramEditorFactory.getProgramEditorCatalog().getFocusableNode();
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(node);
            this.workspaceManager.getUINavigator().addNodeToNavCycle(node.isDisabled() ? null : node);
            for (Node node22 : ProgramEditorFactory.getProgramEditorContents().getFocusableNodes()) {
                this.workspaceManager.getUINavigator().removeNodeFromNavCycle(node22);
            }
            ProgramEditorFactory.getProgramEditorContents().updateFocusableNodes();
            for (Node node22 : ProgramEditorFactory.getProgramEditorContents().getFocusableNodes()) {
                this.workspaceManager.getUINavigator().addNodeToNavCycle(node22);
            }
        }
    }

    @Override
    public boolean shutdown() {
        boolean bl = true;
        if (ProgramEditorFactory.getProgramEditorContents().hasUnsavedItems()) {
            this.workspaceManager.switchToWorkspace(WorkspaceManagerInterface.WorkspaceType.PROGRAM_EDITOR);
            ProgramEditorFactory.getProgramEditorContents().closeAllPrograms();
            if (ProgramEditorFactory.getProgramEditorContents().hasUnsavedItems()) {
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void openVar(TIVar tIVar) {
        ProgramEditorFactory.getProgramEditorContents().openProgram(tIVar);
    }
}

