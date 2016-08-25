/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 */
package com.ti.et.elg.screenCapture.actions;

import com.ti.et.elg.screenCapture.actions.CopyAction;
import com.ti.et.elg.screenCapture.actions.CutAction;
import com.ti.et.elg.screenCapture.actions.DeleteAction;
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
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ScreenCaptureActionManager {
    private static final ScreenCaptureActionManager INSTANCE = new ScreenCaptureActionManager();
    private boolean isAnItemSelected = false;
    private boolean isAnItemInPanel = false;
    private boolean is050Zoom = false;
    private boolean is075Zoom = false;
    private boolean is100Zoom = true;
    private boolean is200Zoom = false;
    private boolean is300Zoom = false;
    private boolean is400Zoom = false;
    private BooleanProperty isAnItemInPanelProperty = new SimpleBooleanProperty();

    private ScreenCaptureActionManager() {
    }

    public static ScreenCaptureActionManager getInstance() {
        return INSTANCE;
    }

    public void screenCapturesSelected(boolean bl) {
        this.isAnItemSelected = bl;
        this.updateActions();
    }

    public void screenCapturesInPanel(boolean bl) {
        this.isAnItemInPanel = bl;
        this.isAnItemInPanelProperty.setValue(Boolean.valueOf(bl));
        this.updateActions();
    }

    public void screenCaptureScaling(int n) {
        this.is050Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get050ValueSlider();
        this.is075Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get075ValueSlider();
        this.is100Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get100ValueSlider();
        this.is200Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get200ValueSlider();
        this.is300Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get300ValueSlider();
        this.is400Zoom = n == ScreenCaptureFactory.getScreenCaptureToolbar().get400ValueSlider();
        this.updateActions();
    }

    public void updateActions() {
        DeleteAction.getInstance().setDisabled(!this.isAnItemSelected);
        UnselectAllAction.getInstance().setDisabled(!this.isAnItemSelected);
        CopyAction.getInstance().setDisabled(!this.isAnItemSelected);
        CutAction.getInstance().setDisabled(!this.isAnItemSelected);
        SaveScreenAction.getInstance().setDisabled(!this.isAnItemSelected);
        SendToHHAction.getInstance().setDisabled(!this.isAnItemSelected);
        SelectAllAction.getInstance().setDisabled(!this.isAnItemInPanel);
        Scaling050Action.getInstance().setDisabled(this.is050Zoom);
        Scaling075Action.getInstance().setDisabled(this.is075Zoom);
        Scaling100Action.getInstance().setDisabled(this.is100Zoom);
        Scaling200Action.getInstance().setDisabled(this.is200Zoom);
        Scaling300Action.getInstance().setDisabled(this.is300Zoom);
        Scaling400Action.getInstance().setDisabled(this.is400Zoom);
    }

    public BooleanProperty getIsAnItemInPanelProperty() {
        return this.isAnItemInPanelProperty;
    }
}

