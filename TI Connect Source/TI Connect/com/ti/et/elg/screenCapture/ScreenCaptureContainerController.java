/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.collections.ObservableSet
 *  javafx.embed.swing.SwingFXUtils
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.geometry.Bounds
 *  javafx.geometry.VPos
 *  javafx.scene.Node
 *  javafx.scene.control.ContextMenu
 *  javafx.scene.control.Menu
 *  javafx.scene.control.MenuBuilder
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.MenuItemBuilder
 *  javafx.scene.control.ScrollPane
 *  javafx.scene.control.SeparatorMenuItem
 *  javafx.scene.image.Image
 *  javafx.scene.image.PixelReader
 *  javafx.scene.image.WritableImage
 *  javafx.scene.input.Clipboard
 *  javafx.scene.input.ClipboardContent
 *  javafx.scene.input.ContextMenuEvent
 *  javafx.scene.input.DataFormat
 *  javafx.scene.input.DragEvent
 *  javafx.scene.input.Dragboard
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.input.TransferMode
 *  javafx.scene.layout.FlowPane
 *  javafx.scene.transform.Transform
 *  javafx.stage.DirectoryChooser
 *  javafx.stage.FileChooser
 *  javafx.stage.FileChooser$ExtensionFilter
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package com.ti.et.elg.screenCapture;

import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.commonUISupport.UINavigation.FXSelectableRowBasedListActionHandler;
import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.action.TIMenuItem;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.deviceList.NotifyConnectedListener;
import com.ti.et.elg.commonUISupport.deviceList.TakeScreenListener;
import com.ti.et.elg.commonUISupport.errorTranslator.ErrorTranslator;
import com.ti.et.elg.commonUISupport.imageUtils.ImageManipulationUtilities;
import com.ti.et.elg.commonUISupport.imageUtils.TIConvertedImageVarMultiplexer;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.commonUISupport.utils.FXNodeInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.deviceExplorer.transaction.SendVarsTransaction;
import com.ti.et.elg.screenCapture.ScreenCaptureItemController;
import com.ti.et.elg.screenCapture.actions.DeleteAction;
import com.ti.et.elg.screenCapture.actions.ScreenCaptureActionManager;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.exports.ScreenContentsInterface;
import com.ti.et.elg.screenCapture.exports.ScreenItemInterface;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.elg.screenCapture.transaction.GetTIImageScreenCaptureTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.elg.transactionManager.TransactionQueueListener;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBuilder;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Transform;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;

public class ScreenCaptureContainerController
implements ScreenContentsInterface,
TakeScreenListener,
NotifyConnectedListener {
    @FXML
    protected FlowPane screenCaptureContainer;
    @FXML
    ScrollPane scrollContainer;
    private Node rootNode = null;
    protected TIMenuItem popupmenuSendToHandhelds;
    protected TIMenuItem popupmenuCopy;
    protected TIMenuItem popupmenuSelectAll;
    protected TIMenuItem popupmenuSaveAs;
    protected TIMenuItem popupmenuDelete;
    protected TIMenuItem popmenuZoom50;
    protected TIMenuItem popmenuZoom75;
    protected TIMenuItem popmenuZoom100;
    protected TIMenuItem popmenuZoom200;
    protected TIMenuItem popmenuZoom300;
    protected TIMenuItem popmenuZoom400;
    protected ContextMenu contextMenu;
    private int scale;
    private List<ScreenItemInterface> screenCaptureItemList = new ArrayList<ScreenItemInterface>();
    private List<ScreenItemInterface> selectedItemList = new ArrayList<ScreenItemInterface>();
    private List<ScreenItemInterface> unsavedSCItemsList = new ArrayList<ScreenItemInterface>();
    private int selectionStart;
    private int selectionEnd;
    private int lastSizeItemList;
    private boolean selectScreenCaptureTitle;
    private boolean autoScrollContainer;
    private boolean noDirectivity = true;
    private final BooleanProperty screenCaptureBordersState = new SimpleBooleanProperty(true);
    private BooleanProperty isVisibleOverlayPaneProperty = new SimpleBooleanProperty();
    OverlayStackPaneBase stackPaneController;
    private boolean alreadyDragging = false;
    private final String START_HTML_TAG = "<html>";
    private final String END_HTML_TAG = "</html>";
    private final String IMG_TAG_0 = "<img src=\"";
    private final String IMG_TAG_1 = "\" alt=\"";
    private final String IMG_TAG_2 = "\" align=\"bottom\"/>";
    private static final int handleReplacePrompt_ReturnArrayLength = 2;
    private final int LOCAL_TO_SCENE_SC_ITEM = 121;
    private final int DEFAULT_INITIAL_SCALING_FOR_TI84P_SCREENS = 2;
    private final String TAG = ScreenCaptureContainerController.class.getSimpleName();
    private SendScreenCaptureToHandheldEventHandler sendScreensToDevicesHandler;
    static int promptResult = -1;

    public ScreenCaptureContainerController() {
        this.sendScreensToDevicesHandler = new SendScreenCaptureToHandheldEventHandler();
    }

    @Override
    public void init(ScreenCaptureInterface screenCaptureInterface) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.getClass().getResource("/com/ti/et/elg/screenCapture/screenCapturesContainer.fxml"));
            fXMLLoader.setResources(this.getResourceBundle());
            fXMLLoader.setController((Object)this);
            this.rootNode = (Node)fXMLLoader.load();
            this.scrollContainer.viewportBoundsProperty().addListener((ChangeListener)new ChangeListener<Bounds>(){

                public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, final Bounds bounds2) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ScreenCaptureContainerController.this.screenCaptureContainer.setPrefWidth(bounds2.getWidth());
                            if (ScreenCaptureContainerController.this.lastSizeItemList < ScreenCaptureContainerController.this.screenCaptureItemList.size()) {
                                ScreenCaptureContainerController.this.scrollContainer.setVvalue(ScreenCaptureContainerController.this.scrollContainer.getVmax());
                                ScreenCaptureContainerController.this.lastSizeItemList = ScreenCaptureContainerController.this.screenCaptureItemList.size();
                            }
                        }
                    });
                }

            });
            this.scrollContainer.setOnMouseClicked((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isConsumed()) {
                        ScreenCaptureContainerController.this.unselectAllItems();
                    }
                }
            });
            this.setSendScreenCaptureEvent();
            this.scale = ScreenCaptureFactory.getScreenCaptureToolbar().get100ValueSlider();
            this.setScreenCaptureListener();
            this.createContextMenu();
            this.rootNode.setOnContextMenuRequested((EventHandler)new EventHandler<ContextMenuEvent>(){

                public void handle(ContextMenuEvent contextMenuEvent) {
                    ScreenCaptureContainerController.this.contextMenu.show(ScreenCaptureContainerController.this.rootNode, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                }
            });
            this.screenCaptureContainer.setRowValignment(VPos.TOP);
            if (PlatformManager.isMac()) {
                this.rootNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (EventHandler)new EventHandler<MouseEvent>(){

                    public void handle(MouseEvent mouseEvent) {
                        if (mouseEvent.isControlDown()) {
                            ScreenCaptureContainerController.this.contextMenu.show(ScreenCaptureContainerController.this.rootNode, mouseEvent.getScreenX(), mouseEvent.getScreenY());
                        }
                    }
                });
                this.rootNode.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                    public void handle(KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
                            DeleteAction.getInstance().getEventHandler().handle(null);
                        }
                    }
                });
            }
            this.screenCaptureContainer.addEventHandler(KeyEvent.KEY_PRESSED, (EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    new FXSelectableRowBasedListActionHandler(){

                        protected List getItemList() {
                            return ScreenCaptureContainerController.this.screenCaptureItemList;
                        }

                        @Override
                        protected int[] getSelection() {
                            return new int[]{ScreenCaptureContainerController.this.selectionStart, ScreenCaptureContainerController.this.selectionEnd};
                        }

                        @Override
                        protected void onSelectionEndChange(int n) {
                            ScreenCaptureContainerController.this.scrollToScreenCaptureItem((Node)ScreenCaptureContainerController.this.screenCaptureContainer.getChildren().get(n));
                        }

                        protected List getSelectedItemList() {
                            return ScreenCaptureContainerController.this.selectedItemList;
                        }

                        @Override
                        protected void setSelection(int n, int n2, boolean bl) {
                            ScreenCaptureContainerController.this.selectionStart = n;
                            ScreenCaptureContainerController.this.selectionEnd = n2;
                            ScreenCaptureContainerController.this.noDirectivity = bl;
                        }

                        @Override
                        protected boolean getNoDirectivity() {
                            return ScreenCaptureContainerController.this.noDirectivity;
                        }

                        @Override
                        protected void shiftSelectItem(FXNodeInterface fXNodeInterface) {
                            ScreenCaptureContainerController.this.shiftSelectItem((ScreenItemInterface)fXNodeInterface);
                        }

                        @Override
                        protected void selectItem(FXNodeInterface fXNodeInterface) {
                            ScreenCaptureContainerController.this.selectItem((ScreenItemInterface)fXNodeInterface);
                        }
                    }.handle(keyEvent);
                }

            });
            this.screenCaptureContainer.setOnDragDetected((EventHandler)new EventHandler<MouseEvent>(){

                public void handle(MouseEvent mouseEvent) {
                    if (!ScreenCaptureContainerController.this.alreadyDragging) {
                        ScreenCaptureContainerController.this.alreadyDragging = true;
                        Dragboard dragboard = ScreenCaptureContainerController.this.screenCaptureContainer.startDragAndDrop(new TransferMode[]{TransferMode.COPY});
                        dragboard.clear();
                        TILogger.logInfo(ScreenCaptureContainerController.this.TAG, "Dragging");
                        ScreenCaptureContainerController.this.copySelectedItemsToClipboard((Clipboard)dragboard);
                    }
                    mouseEvent.consume();
                }
            });
            this.screenCaptureContainer.setOnDragDone((EventHandler)new EventHandler<DragEvent>(){

                public void handle(DragEvent dragEvent) {
                    ScreenCaptureContainerController.this.alreadyDragging = false;
                }
            });
            this.isVisibleOverlayPaneProperty.bind((ObservableValue)ScreenCaptureActionManager.getInstance().getIsAnItemInPanelProperty());
            this.isVisibleOverlayPaneProperty.addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (!bl2.booleanValue()) {
                        if (ScreenCaptureFactory.getDeviceList() != null && ScreenCaptureFactory.getDeviceList().getConnectedDevices().isEmpty()) {
                            ScreenCaptureFactory.getScreenCaptureContainer().showPane(ScreenCaptureFactory.getNothingConnectedPane().getRootNode());
                        } else {
                            ScreenCaptureFactory.getScreenCaptureContainer().showPane(ScreenCaptureFactory.getNoScreenCapturePresentPane().getRootNode());
                        }
                    } else {
                        ScreenCaptureFactory.getNoScreenCapturePresentPane().hidePane(ScreenCaptureFactory.getNoScreenCapturePresentPane().getRootNode());
                    }
                }
            });
            this.screenCaptureBordersState.addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (null != bl2 && null != bl && bl != bl2) {
                        for (ScreenItemInterface screenItemInterface : ScreenCaptureContainerController.this.screenCaptureItemList) {
                            screenItemInterface.setBorder(bl2);
                        }
                    }
                }
            });
            this.addTransactionQueueListener();
        }
        catch (IOException var2_3) {
            TILogger.logError(this.TAG, "init", var2_3);
        }
    }

    private void scrollToScreenCaptureItem(Node node) {
        double d = this.scrollContainer.getVvalue();
        double d2 = node.getLocalToSceneTransform().getTy();
        double d3 = this.scrollContainer.getContent().getBoundsInLocal().getHeight();
        for (int i = 1; (int)d2 != 121 && i <= 21 && (d += (double)((int)d2 - 121) / d3) < 1.0; ++i) {
            this.scrollContainer.setVvalue(d);
            d2 = node.getLocalToSceneTransform().getTy();
        }
    }

    protected void addTransactionQueueListener() {
        TransactionManager.getInstance().addTransactionQueueListener(new TransactionQueueListener(){

            @Override
            public void transactionQueueEmpty(TIDevice tIDevice) {
                if (ScreenCaptureContainerController.this.selectScreenCaptureTitle && !ScreenCaptureContainerController.this.screenCaptureItemList.isEmpty()) {
                    ScreenCaptureContainerController.this.selectScreenCaptureTitle = false;
                    ScreenCaptureContainerController.this.selectItem((ScreenItemInterface)ScreenCaptureContainerController.this.screenCaptureItemList.get(ScreenCaptureContainerController.this.screenCaptureItemList.size() - 1));
                }
                if (ScreenCaptureContainerController.this.autoScrollContainer) {
                    ScreenCaptureContainerController.this.scrollContainer.setVvalue(ScreenCaptureContainerController.this.scrollContainer.getVmax());
                    ScreenCaptureContainerController.this.autoScrollContainer = false;
                }
            }

            @Override
            public void transactionQueueNonEmpty(TIDevice tIDevice) {
            }
        });
    }

    public void setScreenCaptureListener() {
        ScreenCaptureFactory.getDeviceList().addTakeScreenListener(this);
        ScreenCaptureFactory.getDeviceList().addnotifyConnectedHandheldsListener(this);
    }

    public void setSendScreenCaptureEvent() {
        if (ScreenCaptureFactory.getDeviceList() != null) {
            ScreenCaptureFactory.getDeviceList().setSendScreenCaptureEventHandler(this.sendScreensToDevicesHandler);
        }
        DeviceExplorerFactory.getContentTable().setSendScreenCaptureEventHandler(this.sendScreensToDevicesHandler);
    }

    protected void createContextMenu() {
        if (this.contextMenu == null) {
            this.contextMenu = new ContextMenu();
            this.contextMenu.impl_showRelativeToWindowProperty().set(true);
            this.popupmenuSendToHandhelds = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.sendToHandheld"));
            this.popupmenuSendToHandhelds.setId("popupmenuSendToHandhelds");
            this.popupmenuCopy = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.copy"));
            this.popupmenuCopy.setId("popupmenuCopy");
            this.popupmenuSelectAll = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.selectAll"));
            this.popupmenuSelectAll.setId("popupmenuSelectAll");
            this.popupmenuSaveAs = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.save"));
            this.popupmenuSaveAs.setId("popupmenuSaveAs");
            this.popupmenuDelete = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.delete"));
            this.popupmenuDelete.setId("popupmenuDelete");
            this.popmenuZoom50 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.050"));
            this.popmenuZoom50.setId("popmenuZoom50");
            this.popmenuZoom75 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.075"));
            this.popmenuZoom75.setId("popmenuZoom75");
            this.popmenuZoom100 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.100"));
            this.popmenuZoom100.setId("popmenuZoom100");
            this.popmenuZoom200 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.200"));
            this.popmenuZoom200.setId("popmenuZoom200");
            this.popmenuZoom300 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.300"));
            this.popmenuZoom300.setId("popmenuZoom300");
            this.popmenuZoom400 = new TIMenuItem(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize.400"));
            this.popmenuZoom400.setId("popmenuZoom400");
            Menu menu = ((MenuBuilder)MenuBuilder.create().text(CommonUISupportResourceBundle.BUNDLE.getString("contextMenuItem.changeScreenCaptureSize"))).items(new MenuItem[]{this.popmenuZoom50.getMenuItem(), this.popmenuZoom75.getMenuItem(), this.popmenuZoom100.getMenuItem(), this.popmenuZoom200.getMenuItem(), this.popmenuZoom300.getMenuItem(), this.popmenuZoom400.getMenuItem()}).build();
            menu.setId("popupmenuZoom");
            this.contextMenu.getItems().addAll((Object[])new MenuItem[]{this.popupmenuSendToHandhelds.getMenuItem(), new SeparatorMenuItem(), this.popupmenuCopy.getMenuItem(), this.popupmenuDelete.getMenuItem(), new SeparatorMenuItem(), this.popupmenuSelectAll.getMenuItem(), new SeparatorMenuItem(), this.popupmenuSaveAs.getMenuItem(), new SeparatorMenuItem(), menu});
        }
    }

    @Override
    public Node getRootNode() {
        return this.rootNode;
    }

    @Override
    public Node getFocusableNode() {
        return this.screenCaptureContainer;
    }

    @Override
    public void takeScreenCapture(TIDevice tIDevice) {
        if (tIDevice != null && tIDevice.isOSpresent()) {
            this.getTIImageScreenCaptureTransaction(tIDevice);
        }
    }

    @Override
    public void notifyDisconnectedOnScreenCapture() {
        if (this.screenCaptureItemList.isEmpty()) {
            ScreenCaptureFactory.getNoScreenCapturePresentPane().hidePane(ScreenCaptureFactory.getNoScreenCapturePresentPane().getRootNode());
            ScreenCaptureFactory.getScreenCaptureContainer().showPane(ScreenCaptureFactory.getNothingConnectedPane().getRootNode());
        }
    }

    @Override
    public void notifyConnectedOnScreenCapture() {
        if (this.screenCaptureItemList.isEmpty()) {
            ScreenCaptureFactory.getNothingConnectedPane().hidePane(ScreenCaptureFactory.getNothingConnectedPane().getRootNode());
            ScreenCaptureFactory.getScreenCaptureContainer().showPane(ScreenCaptureFactory.getNoScreenCapturePresentPane().getRootNode());
        }
    }

    private void getTIImageScreenCaptureTransaction(TIDevice tIDevice) {
        if (tIDevice != null && tIDevice.isOSpresent() && !tIDevice.isUnderOSUpdate()) {
            this.autoScrollContainer = true;
            this.selectScreenCaptureTitle = true;
            TransactionManager.getInstance().addTransaction(tIDevice, new GetTIImageScreenCaptureTransaction(tIDevice), null);
        }
    }

    @Override
    public void takeScreenCapture(List<TIDevice> list) {
        this.getTIImageScreenCaptureTransaction(list);
    }

    private void getTIImageScreenCaptureTransaction(List<TIDevice> list) {
        TransactionGroup transactionGroup = list.size() > 1 ? new TransactionGroup() : null;
        for (TIDevice tIDevice : list) {
            if (tIDevice == null || !tIDevice.isOSpresent() || tIDevice.isUnderOSUpdate()) continue;
            this.autoScrollContainer = true;
            this.selectScreenCaptureTitle = true;
            TransactionManager.getInstance().addTransaction(tIDevice, new GetTIImageScreenCaptureTransaction(tIDevice), transactionGroup);
        }
    }

    @Override
    public void createScreenCaptureItem(TIImage tIImage) {
        if (tIImage != null) {
            Image image = this.createImage(tIImage);
            ScreenItemInterface screenItemInterface = this.getNewSCItem();
            screenItemInterface.setBorder(this.screenCaptureBordersState.get());
            screenItemInterface.init(image, this.scale);
            this.lastSizeItemList = this.screenCaptureItemList.size();
            this.screenCaptureItemList.add(screenItemInterface);
            this.screenCaptureContainer.getChildren().add((Object)screenItemInterface.getRootNode());
            FlowPane.clearConstraints((Node)screenItemInterface.getRootNode());
        }
        ScreenCaptureActionManager.getInstance().screenCapturesInPanel(!this.screenCaptureItemList.isEmpty());
    }

    public ScreenItemInterface getNewSCItem() {
        return new ScreenCaptureItemController();
    }

    Image createImage(TIImage tIImage) {
        if (null != tIImage) {
            BufferedImage bufferedImage = null;
            bufferedImage = tIImage.getDeviceProductID() > 0 && tIImage.getDeviceProductID() < 15 || tIImage.getDeviceHardwareVersion() > 0 && tIImage.getDeviceHardwareVersion() < 6 ? this.createBnWBufferedImage(tIImage.getImageData(), tIImage.getPixelsWide(), tIImage.getPixelsHeight()) : this.createColorBufferedImage(tIImage.getImageDataAsARGB(), tIImage.getPixelsWide(), tIImage.getPixelsHeight());
            return SwingFXUtils.toFXImage((BufferedImage)bufferedImage, (WritableImage)null);
        }
        return null;
    }

    private BufferedImage createColorBufferedImage(int[] arrn, int n, int n2) {
        BufferedImage bufferedImage = null;
        bufferedImage = new BufferedImage(n, n2, 1);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        int[] arrn2 = ((DataBufferInt)dataBuffer).getData();
        System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
        return bufferedImage;
    }

    private BufferedImage createBnWBufferedImage(byte[] arrby, int n, int n2) {
        BufferedImage bufferedImage = null;
        byte[] arrby2 = new byte[]{-1, 0};
        IndexColorModel indexColorModel = new IndexColorModel(1, 2, arrby2, arrby2, arrby2);
        bufferedImage = new BufferedImage(n, n2, 12, indexColorModel);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        byte[] arrby3 = ((DataBufferByte)dataBuffer).getData();
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        bufferedImage = ImageManipulationUtilities.scaleUpBImagePixelPerPixelNoKeepRatio(bufferedImage, 2, 2);
        return bufferedImage;
    }

    @Override
    public void changeViewScale(int n) {
        this.scale = n;
        if (this.screenCaptureContainer != null && !this.screenCaptureContainer.getChildren().isEmpty()) {
            for (ScreenItemInterface screenItemInterface : this.screenCaptureItemList) {
                screenItemInterface.changeScaleImage(n);
            }
        }
    }

    @Override
    public void deleteScreenCaptureItem(ScreenItemInterface screenItemInterface) {
        this.selectedItemList.remove(screenItemInterface);
        this.screenCaptureItemList.remove(screenItemInterface);
        this.screenCaptureContainer.getChildren().remove((Object)screenItemInterface.getRootNode());
        ScreenCaptureActionManager.getInstance().screenCapturesSelected(!this.selectedItemList.isEmpty());
        ScreenCaptureActionManager.getInstance().screenCapturesInPanel(!this.screenCaptureItemList.isEmpty());
    }

    @Override
    public void selectItem(ScreenItemInterface screenItemInterface) {
        this.selectionEnd = this.selectionStart = FXSelectableRowBasedListActionHandler.selectItem(this.screenCaptureItemList, this.selectedItemList, screenItemInterface, new Runnable(){

            @Override
            public void run() {
                ScreenCaptureActionManager.getInstance().screenCapturesSelected(!ScreenCaptureContainerController.this.selectedItemList.isEmpty());
            }
        });
    }

    @Override
    public void shiftSelectItem(ScreenItemInterface screenItemInterface) {
        boolean[] arrbl = new boolean[]{this.noDirectivity};
        int[] arrn = FXSelectableRowBasedListActionHandler.shiftSelectItem(this.screenCaptureItemList, this.selectedItemList, this.selectionStart, this.selectionEnd, screenItemInterface, arrbl);
        this.selectionStart = arrn[0];
        this.selectionEnd = arrn[1];
        this.noDirectivity = arrbl[0];
        ScreenCaptureActionManager.getInstance().screenCapturesSelected(!this.selectedItemList.isEmpty());
    }

    @Override
    public void rightClickedSelectItem(ScreenItemInterface screenItemInterface) {
        if (!this.selectedItemList.contains(screenItemInterface)) {
            this.selectItem(screenItemInterface);
        }
    }

    @Override
    public void selectAllItems() {
        this.unselectAllItems();
        for (ScreenItemInterface screenItemInterface : this.screenCaptureItemList) {
            screenItemInterface.selectImage();
            this.selectedItemList.add(screenItemInterface);
        }
        this.selectionStart = 0;
        this.selectionEnd = this.screenCaptureItemList.isEmpty() ? 0 : this.screenCaptureItemList.size() - 1;
        this.noDirectivity = this.selectionStart == this.selectionEnd;
        ScreenCaptureActionManager.getInstance().screenCapturesSelected(!this.selectedItemList.isEmpty());
    }

    @Override
    public void unselectAllItems() {
        for (ScreenItemInterface screenItemInterface : this.selectedItemList) {
            screenItemInterface.unselectImage();
        }
        this.selectionStart = 0;
        this.selectionEnd = 0;
        this.noDirectivity = true;
        this.selectedItemList.clear();
        ScreenCaptureActionManager.getInstance().screenCapturesSelected(false);
    }

    @Override
    public void ctrlCmdSelectItem(ScreenItemInterface screenItemInterface) {
        boolean[] arrbl = new boolean[]{this.noDirectivity};
        int[] arrn = FXSelectableRowBasedListActionHandler.ctrlCmdSelectItem(this.screenCaptureItemList, this.selectedItemList, this.selectionStart, this.selectionEnd, screenItemInterface, arrbl);
        this.selectionStart = arrn[0];
        this.selectionEnd = arrn[1];
        this.noDirectivity = arrbl[0];
        ScreenCaptureActionManager.getInstance().screenCapturesSelected(!this.selectedItemList.isEmpty());
    }

    @Override
    public boolean editTextTitleItem(ScreenItemInterface screenItemInterface) {
        if (this.selectedItemList.size() == 1 && this.selectedItemList.get(0) == screenItemInterface) {
            return true;
        }
        return false;
    }

    @Override
    public void setSendToHHAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuSendToHandhelds.setAction(tIAction);
        }
    }

    @Override
    public void setCopyAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuCopy.setAction(tIAction);
        }
    }

    @Override
    public void setSelectAllAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuSelectAll.setAction(tIAction);
        }
    }

    @Override
    public void setSaveAsAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuSaveAs.setAction(tIAction);
        }
    }

    @Override
    public void setDeleteAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popupmenuDelete.setAction(tIAction);
        }
    }

    @Override
    public void set050ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom50.setAction(tIAction);
        }
    }

    @Override
    public void set075ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom75.setAction(tIAction);
        }
    }

    @Override
    public void set100ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom100.setAction(tIAction);
        }
    }

    @Override
    public void set200ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom200.setAction(tIAction);
        }
    }

    @Override
    public void set300ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom300.setAction(tIAction);
        }
    }

    @Override
    public void set400ZoomAction(TIAction tIAction) {
        if (tIAction != null) {
            this.popmenuZoom400.setAction(tIAction);
        }
    }

    @Override
    public List<ScreenItemInterface> getSelectedItems() {
        return Collections.unmodifiableList(this.selectedItemList);
    }

    @Override
    public void deleteSelectedScreenCaptureItems() {
        Stage stage;
        Stage stage2 = stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
        if (PromptDialog.showTwoChoicePrompt(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Title"), "dlgDelete", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.Message"), CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "butnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.DeleteVar.DeleteButton"), "butnDelete", stage) == 2) {
            int n = this.selectedItemList.size();
            for (int i = 0; i < n; ++i) {
                this.deleteScreenCaptureItem(this.selectedItemList.get(0));
            }
        }
    }

    @Override
    public void copySelectedItemsToClipboard(Clipboard clipboard) {
        Clipboard clipboard2 = null != clipboard ? clipboard : Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        switch (this.selectedItemList.size()) {
            case 0: {
                break;
            }
            case 1: {
                if (PlatformManager.isWindows()) {
                    try {
                        TILogger.logDetail(this.TAG, this.selectedItemList.get(0).getImage().getPixelReader().toString());
                        WritableImage writableImage = this.selectedItemList.get(0).getImageWithBorder(false);
                        clipboardContent.put((Object)DataFormat.IMAGE, (Object)writableImage);
                        if (clipboardContent.hasImage()) {
                            TILogger.logDetail(this.TAG, "Image Added to clipboard sucessfully");
                        } else {
                            TILogger.logInfo(this.TAG, "Image Added to clipboard. There was a previous Image in the clipboard");
                        }
                    }
                    catch (IllegalAccessError var4_5) {
                        TILogger.logError(this.TAG, "The image was not set so cannot be copied", var4_5);
                        return;
                    }
                    clipboardContent.put((Object)CommonConstants.CUSTOM_DATAFORMAT, (Object)CommonConstants.TIDataFormat.SCREEN_CAPTURE);
                    clipboard2.setContent((Map)clipboardContent);
                    break;
                }
            }
            default: {
                String string = "<html>";
                for (int i = 0; i < this.selectedItemList.size(); ++i) {
                    File file = null;
                    String string2 = "";
                    String string3 = this.selectedItemList.get(i).getTitle().replaceAll(" ", "") + "-" + Long.toString(System.currentTimeMillis());
                    boolean bl = PromptDialog.arePromptsSupressed();
                    PromptDialog.setSuppressPrompts(true);
                    file = this.saveItemToDisk(string2, string3, null, this.selectedItemList.get(i), false);
                    if (null == file) continue;
                    PromptDialog.setSuppressPrompts(bl);
                    string = string + "<img src=\"" + file.toURI() + "\" alt=\"" + this.selectedItemList.get(i).getTitle() + "\" align=\"bottom\"/>";
                    file.deleteOnExit();
                }
                string = string + "</html>";
                TILogger.logInfo(this.TAG, "Copied " + string + " to clipboard.");
                clipboardContent.putRtf(string);
                clipboardContent.putString(string);
                clipboardContent.putHtml(string);
                clipboardContent.put((Object)CommonConstants.CUSTOM_DATAFORMAT, (Object)CommonConstants.TIDataFormat.SCREEN_CAPTURE);
                clipboard2.setContent((Map)clipboardContent);
                TILogger.logDetail(this.TAG, "HTML was added to clipboard " + string);
            }
        }
    }

    public File saveItemToDisk(String string, String string2, String string3, ScreenItemInterface screenItemInterface, boolean bl) {
        File file = null;
        String string4 = null != string2 && !string2.isEmpty() ? string2 : screenItemInterface.getTitle();
        String string5 = null != string && !string.isEmpty() ? string : PlatformManager.getTempFolder();
        if (!string5.endsWith(File.separator)) {
            string5 = string5 + File.separator;
        }
        String string6 = null != string3 && !string3.isEmpty() ? (string3.startsWith(".") ? string3 : "." + string3) : ".png";
        File file2 = new File(string5);
        if (!file2.exists()) {
            try {
                file2.mkdirs();
            }
            catch (SecurityException var11_11) {
                TILogger.logError(this.TAG, "Cannot write/read to/from location " + string5 + " selected by the user, switching to temporary folder");
                string5 = PlatformManager.getTempFolder();
            }
        } else if (!file2.isDirectory()) {
            string5 = PlatformManager.getTempFolder();
        }
        file = new File(string5 + string4 + string6);
        file.setWritable(true);
        return this.saveItemToDisk(file, screenItemInterface, bl);
    }

    public File saveItemToDisk(File file, ScreenItemInterface screenItemInterface, boolean bl) {
        WritableImage writableImage = null;
        writableImage = screenItemInterface.getImageWithBorder(bl);
        File file2 = file;
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((Image)writableImage, (BufferedImage)null);
        Stage stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
        try {
            boolean bl2 = ImageIO.write((RenderedImage)bufferedImage, "png", file);
            file2 = bl2 ? file : null;
        }
        catch (FileAlreadyExistsException var8_9) {
            TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk." + "There's already a file with that name at that location and that was added " + "while we were attempting to save, please try again.");
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-8), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            file2 = null;
        }
        catch (FileNotFoundException var8_10) {
            if (var8_10.getMessage().contains("(Permission denied)")) {
                TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk." + " Permission denied.");
                String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-9), null, file.getAbsolutePath());
                PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            } else if (var8_10.getMessage().contains("(No space left on device)")) {
                TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk." + " Permission denied.");
                String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-9), null, file.getAbsolutePath());
                PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            }
            file2 = null;
        }
        catch (IOException var8_11) {
            TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk. ");
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-1), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            file2 = null;
        }
        catch (IllegalArgumentException var8_12) {
            TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk. " + "You probably do not have permissions to write to this location");
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-1), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            file2 = null;
        }
        catch (NullPointerException var8_13) {
            TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk. " + "You probably do not have permissions to write to this location");
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-9), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            file2 = null;
        }
        catch (Exception var8_14) {
            TILogger.logError(this.TAG, "Couldn't save file " + file.getAbsolutePath() + " to disk. UNKNOWN REASON.", var8_14);
            String[] arrstring = ErrorTranslator.errorCodeToMessage(TransactionType.SAVING_SCREEN_CAPTURES, new TIStatus(-5), null, file.getAbsolutePath());
            PromptDialog.showUserError(arrstring[0], arrstring[1], stage);
            file2 = null;
        }
        return file2;
    }

    @Override
    public void setParent(OverlayStackPaneBase overlayStackPaneBase) {
        this.stackPaneController = overlayStackPaneBase;
    }

    @Override
    public void showPane(Node node) {
        this.stackPaneController.showPane(node);
    }

    @Override
    public int saveSelectedItemsToDisk() {
        if (this.selectedItemList.size() == 1) {
            return this.saveItem(this.selectedItemList.get(0));
        }
        return this.saveAllItemsFromList(this.selectedItemList);
    }

    public File getLastSaveLocation() {
        File file;
        String string = UserPropertyManagement.getUserProperty("Save.lastFolder.path");
        if (null == string) {
            string = PlatformManager.getDocsFolder();
        }
        if (null == (file = new File(string)) || !file.exists()) {
            string = PlatformManager.getDocsFolder();
            file = new File(string);
        }
        return file;
    }

    private int saveItem(ScreenItemInterface screenItemInterface) {
        File file = this.getLastSaveLocation();
        int n = 0;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(file);
        String string = screenItemInterface.getTitle().replaceAll(PlatformManager.getNonValidChars(), "");
        fileChooser.setInitialFileName(string);
        fileChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("PNG", new String[]{"*.png"})});
        PromptDialog.setFileChooserVisible(true);
        Stage stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
        File file2 = fileChooser.showSaveDialog((Window)stage);
        PromptDialog.setFileChooserVisible(false);
        if (null == file2) {
            TILogger.logDetail(this.TAG, "User may have cancelled the saving of a file.");
            n = -1;
        } else {
            String string2;
            File file3 = new File(file2.getParent());
            if (!file3.canRead()) {
                TILogger.logError(this.TAG, "We cannot read from that location, so we'll default to the user folder");
                file3 = new File(PlatformManager.getDocsFolder());
            }
            String string3 = "";
            if (!file3.getAbsolutePath().endsWith(File.separator)) {
                string3 = File.separator;
            }
            if (!(string2 = file2.getName().replaceAll(PlatformManager.getNonValidChars(), "")).toUpperCase().endsWith(".PNG")) {
                string2 = string2 + ".png";
            }
            File file4 = new File(file3 + string3 + string2);
            if (PlatformManager.isWindows()) {
                boolean[] arrbl = this.handleReplacePrompt(screenItemInterface, file4, true);
                if (arrbl != null && arrbl[0]) {
                    ++n;
                }
            } else {
                File file5 = this.saveItemToDisk(file4, screenItemInterface, false);
                if (null != file5) {
                    ++n;
                    screenItemInterface.isDirty(false);
                }
            }
            TILogger.logDetail(this.TAG, "Last Save Location = " + file3.getAbsolutePath());
            UserPropertyManagement.setUserProperty("Save.lastFolder.path", file3.getAbsolutePath());
        }
        return n;
    }

    private int saveAllItemsFromList(List<ScreenItemInterface> list) {
        Object object;
        File file = this.getLastSaveLocation();
        File file2 = null;
        int n = 0;
        if (!PromptDialog.arePromptsSupressed()) {
            object = new DirectoryChooser();
            object.setInitialDirectory(file);
            Stage stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
            file2 = object.showDialog((Window)stage);
        } else {
            file2 = file;
        }
        if (null == file2) {
            TILogger.logDetail(this.TAG, "User may have cancelled the saving of a file.");
            n = -1;
        } else {
            if (!file2.canRead()) {
                TILogger.logError(this.TAG, "We cannot read from that location, so we'll default to the user folder");
                file2 = new File(PlatformManager.getDocsFolder());
            }
            object = "";
            if (!file2.getAbsolutePath().endsWith(File.separator)) {
                object = File.separator;
            }
            boolean bl = false;
            boolean bl2 = true;
            for (int i = 0; i < list.size() && !bl; ++i) {
                String string = list.get(i).getTitle().replaceAll(PlatformManager.getNonValidChars(), "");
                File file3 = new File(file2 + (String)object + string + "." + "png");
                boolean[] arrbl = this.handleReplacePrompt(list.get(i), file3, bl2);
                bl2 = false;
                if (arrbl == null || arrbl.length != 2) continue;
                if (arrbl[0]) {
                    ++n;
                }
                bl = arrbl[1];
            }
            TILogger.logDetail(this.TAG, "Last Save Location = " + file2.getAbsolutePath());
            UserPropertyManagement.setUserProperty("Save.lastFolder.path", file2.getAbsolutePath());
        }
        return n;
    }

    private boolean[] handleReplacePrompt(ScreenItemInterface screenItemInterface, File file, boolean bl) {
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        File file2 = null;
        if (bl) {
            promptResult = -1;
        }
        if (file.exists() && promptResult == -1) {
            Stage stage = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? CommonConstants.SCREEN_CAPTURE_STAGE : CommonConstants.MAIN_STAGE;
            promptResult = PromptDialog.showReplaceFilePromptAndWait(file.getName(), stage);
        }
        switch (promptResult) {
            case 0: {
                promptResult = -1;
                bl2 = true;
                break;
            }
            case 2: {
                if (!file.exists()) {
                    file2 = this.saveItemToDisk(file, screenItemInterface, false);
                    if (null != file2) {
                        bl2 = true;
                        screenItemInterface.isDirty(false);
                        break;
                    }
                    bl3 = true;
                    break;
                }
                bl2 = true;
                break;
            }
            case 4: {
                bl3 = true;
                break;
            }
            case 1: {
                promptResult = -1;
                bl4 = true;
                break;
            }
            default: {
                bl4 = true;
            }
        }
        if (bl4) {
            file2 = this.saveItemToDisk(file, screenItemInterface, false);
            if (null != file2) {
                bl2 = true;
                screenItemInterface.isDirty(false);
            } else {
                bl3 = true;
            }
        }
        return new boolean[]{bl2, bl3};
    }

    @Override
    public boolean hasUnsavedItems() {
        this.unsavedSCItemsList.clear();
        for (ScreenItemInterface screenItemInterface : this.screenCaptureItemList) {
            if (!screenItemInterface.isDirty()) continue;
            this.unsavedSCItemsList.add(screenItemInterface);
        }
        return !this.unsavedSCItemsList.isEmpty();
    }

    @Override
    public int countUnsavedItems() {
        return this.unsavedSCItemsList.size();
    }

    @Override
    public int saveUnsavedScreenCaptureItems() {
        return this.saveAllItemsFromList(this.unsavedSCItemsList);
    }

    @Override
    public boolean isItemSelected(ScreenItemInterface screenItemInterface) {
        return this.selectedItemList.contains(screenItemInterface);
    }

    @Override
    public int countSelectedItems() {
        return this.selectedItemList.size();
    }

    @Override
    public void sendSelectedItemsToHandhelds(Event event) {
        Object object;
        Object object2;
        Object object32;
        if (this.selectedItemList.isEmpty()) {
            return;
        }
        boolean bl = ScreenCaptureFactory.getScreenCapture().getScreenCaptureProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW;
        ArrayList arrayList = null;
        ArrayList<TIVarMultiplexer> arrayList2 = new ArrayList<TIVarMultiplexer>();
        ObservableSet<TIDevice> observableSet = ScreenCaptureFactory.getDeviceList() != null ? ScreenCaptureFactory.getDeviceList().getBusyDeviceSet() : DeviceExplorerFactory.getDeviceList().getBusyDeviceSet();
        ObservableList observableList = FXCollections.observableArrayList();
        for (Object object32 : observableSet) {
            observableList.add(object32);
        }
        Object object4 = SendToHandheldDialog.HandHeldTargets.SELECTED;
        if (event.getEventType() == DragEvent.DRAG_DROPPED) {
            object32 = ((DragEvent)event).getGestureSource();
            if (object32 == this.screenCaptureContainer || this.screenCaptureContainer.getChildren().contains(object32)) {
                arrayList = ScreenCaptureFactory.getDeviceList().getTargetedDevices();
            } else {
                arrayList = new ArrayList();
                arrayList.add(DeviceExplorerFactory.getContentTable().getSelectedDevice());
            }
        } else if (event.getEventType() == ActionEvent.ACTION) {
            arrayList = new ArrayList();
            object32 = ScreenCaptureFactory.getDeviceList() == null ? DeviceExplorerFactory.getDeviceList().getConnectedDevices() : ScreenCaptureFactory.getDeviceList().getConnectedDevices();
            arrayList.addAll(object32);
            object4 = SendToHandheldDialog.HandHeldTargets.ALL_CONNECTED;
        } else {
            return;
        }
        for (int i = 0; i < this.selectedItemList.size(); ++i) {
            object2 = this.selectedItemList.get(i);
            File file = new File(PlatformManager.getTempFolder() + object2.getTitle());
            if (ImageManipulationUtilities.isABnWScreenCapture(object2.getOriginalImage())) {
                object = TIConvertedImageVarMultiplexer.ImageKind.FROM_BLACK_AND_WHITE_SCREEN_SHOT;
            } else if (ImageManipulationUtilities.isAColorScreenCapture(object2.getOriginalImage())) {
                object = TIConvertedImageVarMultiplexer.ImageKind.FROM_COLOR_SCREEN_SHOT;
            } else {
                TILogger.logError(this.TAG, "Somehow we have a screen shot of an unexpected size");
                object = TIConvertedImageVarMultiplexer.ImageKind.FROM_WHAT_THE_HECK;
            }
            TIConvertedImageVarMultiplexer tIConvertedImageVarMultiplexer = null;
            if (bl && !arrayList.isEmpty() && arrayList.get(0) != null) {
                int n = ((TIDevice)arrayList.get(0)).getHardwareVersion();
                object = n <= 4 ? TIConvertedImageVarMultiplexer.ImageKind.FROM_BLACK_AND_WHITE_SCREEN_SHOT : TIConvertedImageVarMultiplexer.ImageKind.FROM_COLOR_SCREEN_SHOT;
                tIConvertedImageVarMultiplexer = new TIConvertedImageVarMultiplexer((TIConvertedImageVarMultiplexer.ImageKind)((Object)object), file, (i + 1) % 10, n);
            } else {
                tIConvertedImageVarMultiplexer = new TIConvertedImageVarMultiplexer((TIConvertedImageVarMultiplexer.ImageKind)((Object)object), file, (i + 1) % 10);
            }
            arrayList2.add(tIConvertedImageVarMultiplexer);
        }
        SendToHandheldDialog sendToHandheldDialog = new SendToHandheldDialog(arrayList2, arrayList, (SendToHandheldDialog.HandHeldTargets)((Object)object4), bl ? SendToHandheldDialog.DialogMode.SV_SCREEN_CAPTURE : SendToHandheldDialog.DialogMode.SCREEN_CAPTURE, observableList);
        sendToHandheldDialog.showAndWait();
        if (sendToHandheldDialog.isDialogCancelled()) {
            return;
        }
        object2 = SizingType.SCALE;
        if (sendToHandheldDialog.isGraphAreaOnly()) {
            object2 = SizingType.CROP;
        }
        if (!arrayList.isEmpty()) {
            TILogger.logDetail(this.TAG, "About to send ScreenCaptures to devices. " + arrayList.toString());
        } else {
            TILogger.logDetail(this.TAG, "About to send ScreenCaptures to 0 devices.");
        }
        block6 : for (int j = 0; j < arrayList2.size(); ++j) {
            object = null;
            for (int k = 0; k < this.selectedItemList.size(); ++k) {
                if (!this.selectedItemList.get(k).getTitle().equals(arrayList2.get(j).getTIVar().getHostFile().getName().substring(0, arrayList2.get(j).getTIVar().getHostFile().getName().length() - 4))) continue;
                object = this.selectedItemList.get(k);
                break;
            }
            if (null == object) continue;
            Image image = object.getOriginalImage();
            switch (13.$SwitchMap$com$ti$et$elg$screenCapture$ScreenCaptureContainerController$SizingType[object2.ordinal()]) {
                case 1: {
                    ((TIConvertedImageVarMultiplexer)arrayList2.get(j)).setImage(image, true);
                    continue block6;
                }
                case 2: {
                    ((TIConvertedImageVarMultiplexer)arrayList2.get(j)).setImage(image);
                    continue block6;
                }
                default: {
                    return;
                }
            }
        }
        TransactionGroup transactionGroup = arrayList.size() > 1 ? new TransactionGroup() : null;
        for (int k = 0; !arrayList2.isEmpty() && k < arrayList.size(); ++k) {
            SendVarsTransaction sendVarsTransaction = new SendVarsTransaction(TIVarMultiplexer.convertList(arrayList2, (TIDevice)arrayList.get(k)), (TIDevice)arrayList.get(k), TransactionManager.getInstance());
            if (sendToHandheldDialog.isConfirmOverride()) {
                sendVarsTransaction.getListener().setConfirmOverride(3);
            }
            TransactionManager.getInstance().addTransaction((TIDevice)arrayList.get(k), sendVarsTransaction, transactionGroup);
        }
    }

    @Override
    public void setScreenBorders(boolean bl) {
        this.screenCaptureBordersState.set(bl);
    }

    @Override
    public BooleanProperty getBordersStateProperty() {
        return this.screenCaptureBordersState;
    }

    protected ResourceBundle getResourceBundle() {
        return CommonUISupportResourceBundle.BUNDLE;
    }

    static class 13 {
        static final /* synthetic */ int[] $SwitchMap$com$ti$et$elg$screenCapture$ScreenCaptureContainerController$SizingType;

        static {
            $SwitchMap$com$ti$et$elg$screenCapture$ScreenCaptureContainerController$SizingType = new int[SizingType.values().length];
            try {
                13.$SwitchMap$com$ti$et$elg$screenCapture$ScreenCaptureContainerController$SizingType[SizingType.CROP.ordinal()] = 1;
            }
            catch (NoSuchFieldError var0) {
                // empty catch block
            }
            try {
                13.$SwitchMap$com$ti$et$elg$screenCapture$ScreenCaptureContainerController$SizingType[SizingType.SCALE.ordinal()] = 2;
            }
            catch (NoSuchFieldError var0_1) {
                // empty catch block
            }
        }
    }

    public class SendScreenCaptureToHandheldEventHandler
    implements EventHandler<Event> {
        public void handle(Event event) {
            ScreenCaptureFactory.getScreenCaptureContainer().sendSelectedItemsToHandhelds(event);
        }
    }

    private static enum SizingType {
        CROP,
        SCALE;
        

        private SizingType() {
        }
    }

}

