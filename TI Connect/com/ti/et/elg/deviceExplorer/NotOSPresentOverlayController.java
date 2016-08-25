/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.fxml.FXML
 */
package com.ti.et.elg.deviceExplorer;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.deviceExplorer.OverlayPaneController;
import com.ti.et.elg.deviceExplorer.actions.SendOSAction;
import javafx.fxml.FXML;

public class NotOSPresentOverlayController
extends OverlayPaneController {
    @FXML
    TIButton butnReinstallOS;

    @Override
    public void init() {
        super.init();
        this.butnReinstallOS.setAction(SendOSAction.getInstance());
    }

    @Override
    public String getFxmlFileName() {
        return "/com/ti/et/elg/deviceExplorer/notOSPresentOverlay.fxml";
    }
}

