/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.Node
 */
package com.ti.et.elg.dataEditor;

import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonActions.AboutAction;
import com.ti.et.elg.commonUISupport.commonActions.ContactTISupportAction;
import com.ti.et.elg.commonUISupport.commonActions.ExitAction;
import com.ti.et.elg.commonUISupport.commonActions.HelpAction;
import com.ti.et.elg.commonUISupport.commonActions.VisitTIEducationAction;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.dataEditor.actions.SaveDataAction;
import com.ti.et.elg.dataEditor.exports.DataEditorInterface;
import com.ti.et.elg.dataEditor.factory.DataEditorFactory;
import javafx.scene.Node;

public class DataEditorController
implements DataEditorInterface {
    private WorkspaceManagerInterface workspaceManager;

    @Override
    public void init(WorkspaceManagerInterface workspaceManagerInterface) {
        this.workspaceManager = workspaceManagerInterface;
    }

    @Override
    public void notifyIsActive(boolean bl) {
        if (bl) {
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE, SaveDataAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT, ExitAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP, HelpAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM, VisitTIEducationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT, ContactTISupportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT, AboutAction.getInstance());
            this.workspaceManager.getUINavigator().addNodeToNavCycle(DataEditorFactory.getDataEditorContents().getFocusableNode());
        } else {
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_SAVE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT);
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(DataEditorFactory.getDataEditorContents().getFocusableNode());
        }
    }

    @Override
    public void shutdown() {
        this.workspaceManager = null;
    }
}

