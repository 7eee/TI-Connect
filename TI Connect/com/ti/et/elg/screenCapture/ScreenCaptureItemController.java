/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.ReadOnlyBooleanProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.property.SimpleDoubleProperty
 *  javafx.beans.property.StringProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.IndexRange
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.image.PixelFormat
 *  javafx.scene.image.PixelReader
 *  javafx.scene.image.PixelWriter
 *  javafx.scene.image.WritableImage
 *  javafx.scene.image.WritablePixelFormat
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseButton
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.layout.BorderPane
 */
package com.ti.et.elg.screenCapture;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.imageUtils.ImageManipulationUtilities;
import com.ti.et.elg.screenCapture.ScreenCaptureResourceBundle;
import com.ti.et.elg.screenCapture.exports.ScreenItemInterface;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ScreenCaptureItemController
implements ScreenItemInterface {
    @FXML
    ImageView imgScreenCaptureImage;
    @FXML
    TextField txtFldScreenCapture;
    @FXML
    Button butnScreenCaptureClose;
    @FXML
    BorderPane borderPaneShadowItem;
    @FXML
    BorderPane screenCaptureItemPane;
    @FXML
    BorderPane borderImgScreenCapture;
    @FXML
    Label lblSavedScreenCapture;
    private Node rootNode = null;
    private final double IMAGE_SCALE_050 = 0.5;
    private final double IMAGE_SCALE_075 = 0.75;
    private final double IMAGE_SCALE_100 = 1.0;
    private final double IMAGE_SCALE_200 = 2.0;
    private final double IMAGE_SCALE_300 = 3.0;
    private final double IMAGE_SCALE_400 = 4.0;
    private DoubleProperty currentScale = new SimpleDoubleProperty(1.0);
    private double originalHeight;
    private double originalWidth;
    private static final double EDGE_PADDING_SIZE = 10.0;
    private static final double CLOSE_BUTTON_PREDEF_WIDTH = 32.0;
    private final int DEFAULT_BORDER_PIXELS_PER_ORIENTATION = 4;
    private final int DEFAULT_OUTER_BORDER_R = 0;
    private final int DEFAULT_OUTER_BORDER_G = 0;
    private final int DEFAULT_OUTER_BORDER_B = 0;
    private final int DEFAULT_OUTER_BORDER_A = 255;
    private final int DEFAULT_INNER_BORDER_R = 255;
    private final int DEFAULT_INNER_BORDER_B = 255;
    private final int DEFAULT_INNER_BORDER_G = 255;
    private final int DEFAULT_INNER_BORDER_A = 255;
    private ScreenItemInterface currentItem;
    protected static int indexCounter;
    private boolean shouldShowBorder;
    private boolean isDirty = true;
    private boolean isNewlySelected = false;
    private String lastAcceptedCaptionText = "";

    @Override
    public void init(Image image, int n) {
        try {
            this.currentItem = this;
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/screenCapture/screenCaptureItem.fxml"));
            fXMLLoader.setResources((ResourceBundle)CommonUISupportResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this.currentItem);
            this.rootNode = (Node)fXMLLoader.load();
            this.imgScreenCaptureImage.setImage(image);
            this.originalHeight = (int)image.getHeight();
            this.originalWidth = (int)image.getWidth();
            this.changeScaleImage(n);
            this.borderPaneShadowItem.getStyleClass().add((Object)"contentPanelItem");
            this.setBorder(this.shouldShowBorder);
            this.screenCaptureItemPane.setOnMouseClicked((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    if (ScreenCaptureFactory.getScreenCaptureContainer().isItemSelected(ScreenCaptureItemController.this.currentItem)) {
                        if (!ScreenCaptureItemController.this.isNewlySelected) {
                            ScreenCaptureItemController.this.selectionEvent(mouseEvent);
                        } else {
                            ScreenCaptureItemController.this.isNewlySelected = false;
                        }
                        mouseEvent.consume();
                    }
                }
            });
            this.screenCaptureItemPane.setOnMousePressed((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    if (!ScreenCaptureFactory.getScreenCaptureContainer().isItemSelected(ScreenCaptureItemController.this.currentItem)) {
                        ScreenCaptureItemController.this.selectionEvent(mouseEvent);
                        ScreenCaptureItemController.this.isNewlySelected = true;
                    }
                    mouseEvent.consume();
                }
            });
            this.txtFldScreenCapture.setText(ScreenCaptureResourceBundle.BUNDLE.getString("ScreenCapture.Title") + " " + ++indexCounter);
            this.lastAcceptedCaptionText = this.txtFldScreenCapture.getText();
            this.txtFldScreenCapture.setOnMousePressed((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    boolean bl = ScreenCaptureFactory.getScreenCaptureContainer().editTextTitleItem(ScreenCaptureItemController.this.currentItem);
                    if (!bl && ((IndexRange)ScreenCaptureItemController.this.txtFldScreenCapture.selectionProperty().get()).getLength() > 0) {
                        ScreenCaptureItemController.this.txtFldScreenCapture.deselect();
                    }
                    if (bl != ScreenCaptureItemController.this.txtFldScreenCapture.editableProperty().get()) {
                        ScreenCaptureItemController.this.txtFldScreenCapture.editableProperty().set(bl);
                    }
                    ScreenCaptureItemController.this.selectionEvent(mouseEvent);
                    mouseEvent.consume();
                }
            });
            this.txtFldScreenCapture.focusedProperty().addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (!bl2.booleanValue() && ScreenCaptureItemController.this.txtFldScreenCapture.getText().trim().isEmpty()) {
                        ScreenCaptureItemController.this.txtFldScreenCapture.setText(ScreenCaptureItemController.this.lastAcceptedCaptionText);
                    } else if (!ScreenCaptureItemController.this.txtFldScreenCapture.getText().trim().isEmpty()) {
                        ScreenCaptureItemController.this.lastAcceptedCaptionText = ScreenCaptureItemController.this.txtFldScreenCapture.getText();
                    }
                }
            });
            this.txtFldScreenCapture.textProperty().addListener((ChangeListener)new ChangeListener<String>(){

                public void changed(ObservableValue<? extends String> observableValue, String string, String string2) {
                    ScreenCaptureItemController.this.isDirty(!string2.equals(string));
                    ScreenCaptureItemController.this.updateDirtyFlag();
                }
            });
            this.txtFldScreenCapture.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

                public void handle(ActionEvent actionEvent) {
                    if (ScreenCaptureItemController.this.txtFldScreenCapture.getText().trim().isEmpty()) {
                        ScreenCaptureItemController.this.txtFldScreenCapture.setText(ScreenCaptureItemController.this.lastAcceptedCaptionText);
                    } else {
                        ScreenCaptureItemController.this.lastAcceptedCaptionText = ScreenCaptureItemController.this.txtFldScreenCapture.getText();
                    }
                    ScreenCaptureItemController.this.borderPaneShadowItem.requestFocus();
                }
            });
            this.txtFldScreenCapture.addEventFilter(KeyEvent.KEY_TYPED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    String string = keyEvent.getCharacter();
                    if (PlatformManager.getNonValidChars().contains(string)) {
                        keyEvent.consume();
                    }
                }
            });
            this.butnScreenCaptureClose.setOnMouseClicked((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    ScreenCaptureFactory.getScreenCaptureContainer().selectItem(ScreenCaptureItemController.this.currentItem);
                    ScreenCaptureFactory.getScreenCaptureContainer().deleteSelectedScreenCaptureItems();
                    mouseEvent.consume();
                }
            });
            this.butnScreenCaptureClose.setId("butnScreenCaptureClose" + indexCounter);
            this.txtFldScreenCapture.setId("txtFldScreenCapture" + indexCounter);
            this.lblSavedScreenCapture.setId("lblSavedScreenCapture" + indexCounter);
            this.imgScreenCaptureImage.setId("imgScreenCapture" + indexCounter);
            this.screenCaptureItemPane.setId("paneScreenCapture_" + indexCounter);
        }
        catch (IOException var3_4) {
            TILogger.logError("ScreenCaptureItemsController", "init", var3_4);
        }
    }

    private void selectionEvent(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals((Object)MouseButton.PRIMARY) && (PlatformManager.isWindows() || !mouseEvent.isControlDown())) {
            if (mouseEvent.isShiftDown()) {
                ScreenCaptureFactory.getScreenCaptureContainer().shiftSelectItem(this.currentItem);
            } else if (mouseEvent.isControlDown() || mouseEvent.isMetaDown()) {
                ScreenCaptureFactory.getScreenCaptureContainer().ctrlCmdSelectItem(this.currentItem);
            } else {
                ScreenCaptureFactory.getScreenCaptureContainer().selectItem(this.currentItem);
            }
        } else if (mouseEvent.getButton().equals((Object)MouseButton.SECONDARY) || !PlatformManager.isWindows() && mouseEvent.isControlDown() && mouseEvent.getButton().equals((Object)MouseButton.PRIMARY)) {
            ScreenCaptureFactory.getScreenCaptureContainer().rightClickedSelectItem(this.currentItem);
        }
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public void changeScaleImage(int n) {
        switch (n) {
            case 0: {
                this.currentScale.set(0.5);
                break;
            }
            case 1: {
                this.currentScale.set(0.75);
                break;
            }
            case 3: {
                this.currentScale.set(2.0);
                break;
            }
            case 4: {
                this.currentScale.set(3.0);
                break;
            }
            case 5: {
                this.currentScale.set(4.0);
                break;
            }
            default: {
                this.currentScale.set(1.0);
            }
        }
        this.imgScreenCaptureImage.setFitHeight(this.originalHeight * this.currentScale.doubleValue());
        this.imgScreenCaptureImage.setFitWidth(this.originalWidth * this.currentScale.doubleValue());
        this.txtFldScreenCapture.setPrefWidth(this.originalWidth * this.currentScale.doubleValue() - 32.0 - 10.0);
    }

    @Override
    public void selectImage() {
        this.borderPaneShadowItem.getStyleClass().removeAll((Object[])new String[]{"contentPanelItem"});
        this.borderPaneShadowItem.getStyleClass().add((Object)"contentPanelItem-Selected");
        this.borderPaneShadowItem.requestFocus();
    }

    @Override
    public void unselectImage() {
        this.borderPaneShadowItem.getStyleClass().removeAll((Object[])new String[]{"contentPanelItem-Selected"});
        this.borderPaneShadowItem.getStyleClass().add((Object)"contentPanelItem");
        ActionEvent actionEvent = new ActionEvent();
        this.txtFldScreenCapture.getOnAction().handle((Event)actionEvent);
    }

    @Override
    public void selectTitleText() {
        this.txtFldScreenCapture.requestFocus();
        this.txtFldScreenCapture.selectAll();
    }

    @Override
    public Image getImage() throws IllegalAccessError {
        Image image = this.imgScreenCaptureImage.getImage();
        if (this.currentScale.doubleValue() > 1.0) {
            image = ImageManipulationUtilities.scaleUpImagePixelPerPixelNoKeepRatio(this.imgScreenCaptureImage.getImage(), (int)this.currentScale.doubleValue(), (int)this.currentScale.doubleValue());
        } else if (this.currentScale.doubleValue() < 1.0) {
            image = ImageManipulationUtilities.scaleImageNoKeepRatioTransform(this.imgScreenCaptureImage.getImage(), (int)(this.currentScale.doubleValue() * 100.0), (int)(this.currentScale.doubleValue() * 100.0));
        }
        if (null != image) {
            return image;
        }
        throw new IllegalAccessError("The Image you are trying to reach has not been set.");
    }

    @Override
    public Image getOriginalImage() {
        if (null != this.imgScreenCaptureImage.getImage()) {
            return this.imgScreenCaptureImage.getImage();
        }
        throw new IllegalAccessError("The Image you are trying to reach has not been set.");
    }

    @Override
    public String getTitle() {
        return this.txtFldScreenCapture.getText();
    }

    @Override
    public boolean hasBorder() {
        return this.shouldShowBorder;
    }

    @Override
    public void setBorder(boolean bl) {
        this.shouldShowBorder = bl;
        if (this.borderImgScreenCapture != null) {
            if (bl) {
                this.borderImgScreenCapture.getStyleClass().removeAll((Object[])new String[]{"no-border-sc"});
                this.borderImgScreenCapture.getStyleClass().add((Object)"border-sc");
            } else {
                this.borderImgScreenCapture.getStyleClass().removeAll((Object[])new String[]{"border-sc"});
                this.borderImgScreenCapture.getStyleClass().add((Object)"no-border-sc");
            }
        }
    }

    @Override
    public boolean isDirty() {
        return this.isDirty;
    }

    @Override
    public void isDirty(boolean bl) {
        this.isDirty = bl;
        this.updateDirtyFlag();
    }

    @Override
    public void updateDirtyFlag() {
        if (!this.isDirty) {
            this.lblSavedScreenCapture.setText("");
        } else {
            this.lblSavedScreenCapture.setText("*");
        }
    }

    @Override
    public WritableImage getImageWithBorder(boolean bl) {
        Image image = null;
        image = bl ? this.getOriginalImage() : this.getImage();
        if (null != image) {
            int n = 0;
            int n2 = 0;
            if (this.hasBorder()) {
                n = (int)image.getWidth() + 4;
                n2 = (int)image.getHeight() + 4;
            } else {
                n = (int)image.getWidth();
                n2 = (int)image.getHeight();
            }
            WritableImage writableImage = new WritableImage(n, n2);
            PixelWriter pixelWriter = writableImage.getPixelWriter();
            WritablePixelFormat writablePixelFormat = PixelFormat.getByteBgraInstance();
            if (this.hasBorder()) {
                int n3;
                int n4;
                byte[] arrby = new byte[4 * n];
                byte[] arrby2 = new byte[4 * n2];
                for (n3 = 0; n3 < n; ++n3) {
                    arrby[4 * n3] = 0;
                    arrby[4 * n3 + 1] = 0;
                    arrby[4 * n3 + 2] = 0;
                    arrby[4 * n3 + 3] = -1;
                }
                for (n3 = 0; n3 < n2; ++n3) {
                    arrby2[4 * n3] = 0;
                    arrby2[4 * n3 + 1] = 0;
                    arrby2[4 * n3 + 2] = 0;
                    arrby2[4 * n3 + 3] = -1;
                }
                byte[] arrby3 = new byte[4 * (n - 2)];
                byte[] arrby4 = new byte[4 * (n2 - 2)];
                for (n4 = 0; n4 < n - 2; ++n4) {
                    arrby3[4 * n4] = -1;
                    arrby3[4 * n4 + 1] = -1;
                    arrby3[4 * n4 + 2] = -1;
                    arrby3[4 * n4 + 3] = -1;
                }
                for (n4 = 0; n4 < n2 - 2; ++n4) {
                    arrby4[4 * n4] = -1;
                    arrby4[4 * n4 + 1] = -1;
                    arrby4[4 * n4 + 2] = -1;
                    arrby4[4 * n4 + 3] = -1;
                }
                pixelWriter.setPixels(0, 0, n, 1, (PixelFormat)writablePixelFormat, arrby, 0, 4);
                pixelWriter.setPixels(0, n2 - 1, n, 1, (PixelFormat)writablePixelFormat, arrby, 0, 4);
                pixelWriter.setPixels(0, 0, 1, n2, (PixelFormat)writablePixelFormat, arrby2, 0, 4);
                pixelWriter.setPixels(n - 1, 0, 1, n2, (PixelFormat)writablePixelFormat, arrby2, 0, 4);
                pixelWriter.setPixels(1, 1, n - 2, 1, (PixelFormat)writablePixelFormat, arrby3, 0, 4);
                pixelWriter.setPixels(1, n2 - 2, n - 2, 1, (PixelFormat)writablePixelFormat, arrby3, 0, 4);
                pixelWriter.setPixels(1, 1, 1, n2 - 2, (PixelFormat)writablePixelFormat, arrby4, 0, 4);
                pixelWriter.setPixels(n - 2, 1, 1, n2 - 2, (PixelFormat)writablePixelFormat, arrby4, 0, 4);
                pixelWriter.setPixels(2, 2, (int)image.getWidth(), (int)image.getHeight(), image.getPixelReader(), 0, 0);
            } else {
                pixelWriter.setPixels(0, 0, (int)image.getWidth(), (int)image.getHeight(), image.getPixelReader(), 0, 0);
            }
            return writableImage;
        }
        return null;
    }

    @Override
    public void setSelected(boolean bl) {
        if (bl) {
            this.selectImage();
        } else {
            this.unselectImage();
        }
    }

}

