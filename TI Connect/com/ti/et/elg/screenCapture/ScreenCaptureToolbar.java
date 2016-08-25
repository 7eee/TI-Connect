/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.ReadOnlyDoubleProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Label
 *  javafx.scene.control.Slider
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.HBox
 */
package com.ti.et.elg.screenCapture;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIButton;
import com.ti.et.elg.screenCapture.actions.AddScreenBorderAction;
import com.ti.et.elg.screenCapture.actions.CopyAction;
import com.ti.et.elg.screenCapture.actions.DeleteAction;
import com.ti.et.elg.screenCapture.actions.RemoveScreenBorderAction;
import com.ti.et.elg.screenCapture.actions.SaveScreenAction;
import com.ti.et.elg.screenCapture.actions.ScreenCaptureActionManager;
import com.ti.et.elg.screenCapture.actions.SendToHHAction;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureToolbarInterface;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ScreenCaptureToolbar
implements ScreenCaptureToolbarInterface {
    @FXML
    TIButton butnScrCapSave;
    @FXML
    TIButton butnScrCapSendToHHs;
    @FXML
    TIButton butnScrCapCopy;
    @FXML
    TIButton butnScrCapDelete;
    @FXML
    TIButton butnScreenCaptureBorder;
    @FXML
    Label lblScrCapSize;
    @FXML
    Slider sliderScrCapSize;
    @FXML
    HBox toolBarSpacerHbox;
    private Node rootNode = null;
    private int offsetPadding = 11;

    @Override
    public void init(ScreenCaptureInterface screenCaptureInterface) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/screenCapture/screenCaptureToolbar.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.initToolBar();
        }
        catch (IOException var2_3) {
            TILogger.logError("ScreenCaptureToolbar", "init", var2_3);
        }
    }

    private void initToolBar() {
        this.butnScrCapSave.setAction(SaveScreenAction.getInstance());
        this.butnScrCapSendToHHs.setAction(SendToHHAction.getInstance());
        this.butnScrCapCopy.setAction(CopyAction.getInstance());
        this.butnScrCapDelete.setAction(DeleteAction.getInstance());
        this.butnScreenCaptureBorder.setAction(ScreenCaptureFactory.getScreenCaptureContainer().getBordersStateProperty().get() ? RemoveScreenBorderAction.getInstance() : AddScreenBorderAction.getInstance());
        this.sliderScrCapSize.setMin(0.0);
        this.sliderScrCapSize.setMax(5.0);
        this.sliderScrCapSize.setValue(2.0);
        this.lblScrCapSize.setText(scrCapSliderStrings[2]);
        this.sliderScrCapSize.setMajorTickUnit(1.0);
        this.sliderScrCapSize.setMinorTickCount(0);
        this.sliderScrCapSize.setBlockIncrement(1.0);
        this.sliderScrCapSize.setSnapToTicks(true);
        BorderPane borderPane = (BorderPane)ScreenCaptureFactory.getDeviceList().getRootNode();
        ScreenCaptureFactory.getScreenCaptureContainer().getBordersStateProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                if (bl != bl2) {
                    if (bl2.booleanValue()) {
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.getStyleClass().removeAll((Object[])new String[]{"border-off"});
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.getStyleClass().add((Object)"border-on");
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.setAction(RemoveScreenBorderAction.getInstance());
                    } else {
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.getStyleClass().removeAll((Object[])new String[]{"border-on"});
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.getStyleClass().add((Object)"border-off");
                        ScreenCaptureToolbar.this.butnScreenCaptureBorder.setAction(AddScreenBorderAction.getInstance());
                    }
                }
            }
        });
        this.sliderScrCapSize.valueProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if (number2 != null && number2.floatValue() >= 0.0f && number2.floatValue() <= 5.0f && !ScreenCaptureToolbar.this.lblScrCapSize.getText().equals(ScreenCaptureToolbarInterface.scrCapSliderStrings[Math.round(number2.floatValue())])) {
                    ScreenCaptureToolbar.this.lblScrCapSize.setText(ScreenCaptureToolbarInterface.scrCapSliderStrings[Math.round(number2.floatValue())]);
                    ScreenCaptureFactory.getScreenCaptureContainer().changeViewScale(Math.round(number2.floatValue()));
                    ScreenCaptureActionManager.getInstance().screenCaptureScaling(Math.round(number2.floatValue()));
                    UserPropertyManagement.setInteger("ScreenCapture.lastScale.value", Math.round(number2.floatValue()));
                }
            }
        });
        int n = UserPropertyManagement.getInteger("ScreenCapture.lastScale.value", 1);
        this.sliderScrCapSize.setValue((double)n);
        borderPane.widthProperty().addListener((ChangeListener)new ChangeListener<Object>(){

            public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                Double d = (Double)object2;
                ScreenCaptureToolbar.this.toolBarSpacerHbox.setPrefWidth(d - (double)ScreenCaptureToolbar.this.offsetPadding);
            }
        });
    }

    @Override
    public void changeScreenCaptureScaling(int n) {
        this.sliderScrCapSize.setValue((double)n);
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public int get050ValueSlider() {
        return 0;
    }

    @Override
    public int get075ValueSlider() {
        return 1;
    }

    @Override
    public int get100ValueSlider() {
        return 2;
    }

    @Override
    public int get200ValueSlider() {
        return 3;
    }

    @Override
    public int get300ValueSlider() {
        return 4;
    }

    @Override
    public int get400ValueSlider() {
        return 5;
    }

}

