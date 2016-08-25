/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.application.HostServices
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.Property
 *  javafx.beans.property.ReadOnlyDoubleProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.concurrent.Task
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventType
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Label
 *  javafx.scene.control.MenuBar
 *  javafx.scene.control.SingleSelectionModel
 *  javafx.scene.control.SplitPane
 *  javafx.scene.control.SplitPane$Divider
 *  javafx.scene.control.Tab
 *  javafx.scene.control.TabPane
 *  javafx.scene.image.Image
 *  javafx.scene.input.Clipboard
 *  javafx.scene.input.ClipboardContent
 *  javafx.scene.input.DataFormat
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyCodeCombination
 *  javafx.scene.input.KeyCombination
 *  javafx.scene.input.KeyCombination$Modifier
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.layout.BorderPane
 *  javafx.scene.layout.VBox
 *  javafx.scene.text.Font
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.stage.Window
 *  javafx.stage.WindowEvent
 */
package com.ti.et.elg.tiConnect;

import com.ti.et.elg.AnalyticsManager.AnalyticsManager;
import com.ti.et.elg.AnalyticsManager.EventHit;
import com.ti.et.elg.commonUISupport.CommonUISupportResourceBundle;
import com.ti.et.elg.commonUISupport.ShowErrorsPromptsBuiltInTest;
import com.ti.et.elg.commonUISupport.UINavigation.UIKeyNavigationEventHandler;
import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.commonActions.AnalyticsOptInAction;
import com.ti.et.elg.commonUISupport.commonActions.ExitAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.connectServer.autoDetect.AutoDetect;
import com.ti.et.elg.connectServer.autoDetect.mock.AutoDetectMock;
import com.ti.et.elg.connectServer.commManager.CommManager;
import com.ti.et.elg.connectServer.commManager.TestAutomationAPI;
import com.ti.et.elg.connectServer.deviceComm.DeviceComm;
import com.ti.et.elg.connectServer.deviceComm.mock.DeviceCommMock;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.Constants;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDeviceConnectionListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.connectServer.translator.Translator;
import com.ti.et.elg.deviceExplorer.actions.CaptureScreenAction;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.notifications.actions.CheckForNotificationAction;
import com.ti.et.elg.programEditor.factory.ProgramEditorFactory;
import com.ti.et.elg.screenCapture.factory.ScreenCaptureFactory;
import com.ti.et.elg.tiConnect.CloseDialog;
import com.ti.et.elg.tiConnect.TIConnectAutomationAPI;
import com.ti.et.elg.tiConnect.TIConnectResourceBundle;
import com.ti.et.elg.tiConnect.WorkspaceManager;
import com.ti.et.utils.fileUtils.FileUtils;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class TIConnectApplication
extends Application {
    protected static boolean useMockAutoDetect = false;
    private final String LOGSNAME = "javaLogs";
    private final String LOGSEXTENSION = ".txt";
    Class<?> tiConnectClass = TIConnectApplication.class;
    @FXML
    BorderPane paneTIConnectCE;
    @FXML
    VBox menuBox;
    @FXML
    TabPane tabPaneWorkSpaces;
    @FXML
    Tab tabPaneToolbar;
    @FXML
    Tab tabPaneHHExplorer;
    @FXML
    Tab tabPaneScreenCapture;
    @FXML
    Tab tabPaneProgramEditor;
    @FXML
    BorderPane deviceExplorerToolbar;
    @FXML
    BorderPane panelHandheldList;
    @FXML
    BorderPane panelContentDeviceExplorer;
    @FXML
    BorderPane screenCaptureDeviceList;
    @FXML
    BorderPane panelContentScreenCapture;
    @FXML
    BorderPane screenCaptureToolbar;
    @FXML
    SplitPane splitPaneDeviceExplorer;
    @FXML
    SplitPane splitPaneScreenCapture;
    @FXML
    Label lblTIConnectVersion;
    @FXML
    BorderPane programEditorLibrary;
    @FXML
    BorderPane panelContentProgramEditor;
    @FXML
    BorderPane programEditorToolbar;
    @FXML
    SplitPane splitPaneProgramEditor;
    private CommManagerInterface commManager = null;
    private TIDeviceConnectionListener connectionListener = null;
    private Vector<TIDevice> connectedDevices = null;
    private WorkspaceManager workspaceManager = new WorkspaceManager();
    private CloseDialog closeDialog;
    private static final int MIN_WIDTH = 900;
    private static final int MIN_HEIGHT = PlatformManager.isMac() ? 510 : 550;
    private static final int INITIAL_WIDTH = 1024;
    private static final int INITIAL_HEIGHT = 768;
    protected static final double INITIAL_SPLITTER_POSITION = 0.3867334167709637;
    protected static double initialProgramEditorSplitterPosition = 0.23753976670201485;
    private Runnable closeRoutine;
    private BooleanProperty ready = new SimpleBooleanProperty(false);
    protected volatile boolean canClose = true;
    boolean hiddenSplitterInitted = false;
    boolean PESplitterInitted = false;
    private UIKeyNavigationEventHandler tabNavigator = new UIKeyNavigationEventHandler();
    private static final String LOG_TAG = TIConnectApplication.class.getSimpleName();

    private void init(Stage stage) {
        UserPropertyManagement.loadUserProperties();
        this.initConnectServer();
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.tiConnectClass.getResource("tic.fxml"));
            fXMLLoader.setResources((ResourceBundle)TIConnectResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Object object = fXMLLoader.load();
            this.workspaceManager.setUIWorkspaceSelector(this.tabPaneWorkSpaces);
            this.workspaceManager.setUINavigator(this.tabNavigator);
            this.createMainMenu();
            this.tabPaneToolbar.setOnSelectionChanged((EventHandler)new EventHandler<Event>(){

                public void handle(Event event) {
                    if (TIConnectApplication.this.tabPaneToolbar.isSelected()) {
                        TIConnectApplication.this.workspaceManager.switchToWorkspace(WorkspaceManagerInterface.WorkspaceType.SCREEN_CAPTURE);
                    }
                }
            });
            this.initDeviceExplorerTab();
            this.initScreenCaptureTab();
            this.initProgramEditorTab();
            int n = UserPropertyManagement.getInteger("Window.lastWidth.value", 1024);
            int n2 = UserPropertyManagement.getInteger("Window.lastHeight.value", 768);
            Scene scene = new Scene((Parent)object, (double)n, (double)n2);
            CommonConstants.MAIN_STYLE_SHEET = this.tiConnectClass.getResource("tic_theme.css").toExternalForm();
            CommonConstants.SC_STYLE_SHEET = this.getClass().getResource("/com/ti/et/elg/screenCapture/sc_style.css").toExternalForm();
            scene.getStylesheets().add((Object)CommonConstants.MAIN_STYLE_SHEET);
            scene.getStylesheets().add((Object)CommonConstants.SC_STYLE_SHEET);
            CommonConstants.MAIN_STAGE = stage;
            ShowErrorsPromptsBuiltInTest.ticBundle = TIConnectResourceBundle.BUNDLE;
            CommonConstants.hostServices = this.getHostServices();
            stage.setScene(scene);
            stage.setMinHeight((double)MIN_HEIGHT);
            stage.setMinWidth(900.0);
            stage.setTitle(CommonConstants.PRODUCT_NAME_WITH_TRADEMARK);
            this.applySavedGUIState(stage, scene);
            this.setGUIPropertiesListeners(stage);
            if (PlatformManager.isWindows()) {
                stage.getIcons().add((Object)new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
                PromptDialog.setDialogIcon(new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/TI Connect CE.png").toExternalForm()));
            }
            this.setTraversalFocusPolicy();
            stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)this.tabNavigator);
            this.setClosingRoutine(stage, scene);
            AnalyticsManager.postHit(EventHit.LAUNCH);
        }
        catch (IOException var2_3) {
            TILogger.logError(LOG_TAG, "init", var2_3);
        }
    }

    protected void setTraversalFocusPolicy() {
        KeyCodeCombination keyCodeCombination = new KeyCodeCombination(KeyCode.TAB, new KeyCombination.Modifier[0]);
        this.tabNavigator.addForwardTriggerKeyCode(keyCodeCombination);
        KeyCodeCombination keyCodeCombination2 = new KeyCodeCombination(KeyCode.TAB, new KeyCombination.Modifier[]{KeyCodeCombination.SHIFT_DOWN});
        this.tabNavigator.addBackwardTriggerKeyCode(keyCodeCombination2);
        this.tabNavigator.addNodeToNavCycle((Node)this.tabPaneWorkSpaces);
        this.tabPaneWorkSpaces.requestFocus();
        this.tabNavigator.startAtPos(this.tabNavigator.getListSize() - 1);
    }

    private void applySavedGUIState(Stage stage, Scene scene) {
        double d = UserPropertyManagement.getDouble("DeviceListSplitter.position.value", 0.3867334167709637);
        ((SplitPane.Divider)this.splitPaneDeviceExplorer.getDividers().get(0)).setPosition(d);
        ((SplitPane.Divider)this.splitPaneScreenCapture.getDividers().get(0)).setPosition(d);
        stage.setWidth(scene.getWidth());
        stage.setHeight(scene.getHeight());
        this.tabPaneWorkSpaces.getSelectionModel().select((Object)this.tabPaneScreenCapture);
    }

    private void setGUIPropertiesListeners(Stage stage) {
        stage.widthProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                int n = 900;
                if (number2.intValue() > 900) {
                    n = number2.intValue();
                }
                UserPropertyManagement.setInteger("Window.lastWidth.value", n);
            }
        });
        stage.heightProperty().addListener((ChangeListener)new ChangeListener<Number>(){

            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                int n = MIN_HEIGHT;
                if (number2.intValue() > MIN_HEIGHT) {
                    n = number2.intValue();
                }
                UserPropertyManagement.setInteger("Window.lastHeight.value", n);
            }
        });
    }

    private void setClosingRoutine(final Stage stage, Scene scene) {
        this.closeRoutine = new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void run() {
                var1_1 = stage.isFullScreen();
                var2_2 = false;
                var3_3 = Clipboard.getSystemClipboard();
                var4_4 = var3_3.getContent(CommonConstants.CUSTOM_DATAFORMAT);
                if (var4_4 != null) {
                    TILogger.logDetail(TIConnectApplication.access$200(), "CLOSING_ROUTINE - Clipboard has contents: " + var4_4);
                    var5_5 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.btnCancel"), "btnCancel", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.btnDiscard"), "btnDiscard"};
                    var6_7 = PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.discardClipboardContents.message"), "discardClipboardContents", var5_5, new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
                    if (var6_7 != 1) {
                        TIConnectApplication.this.canClose = false;
                        Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                        return;
                    }
                    var2_2 = true;
                    TIConnectApplication.this.canClose = true;
                    Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                } else {
                    TIConnectApplication.this.canClose = true;
                    Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                }
                TILogger.logDetail(TIConnectApplication.access$200(), "CLOSING_ROUTINE - Can we close? " + TIConnectApplication.this.canClose);
                var5_6 = -1;
                if (DeviceExplorerFactory.getDeviceExplorer().hasActiveTransactions()) {
                    for (TIDevice var7_9 : DeviceExplorerFactory.getDeviceList().getConnectedDevices()) {
                        if (!var7_9.isUnderOSUpdate()) continue;
                        PromptDialog.showUserError(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithOSTransactions.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithOSTransactions.Message"));
                        TIConnectApplication.this.canClose = false;
                        Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                        return;
                    }
                    var6_8 = new String[]{CommonUISupportResourceBundle.BUNDLE.getString("Cancel"), "closeWT.cancelBtn", CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.CloseButton"), "closeWT.closeBtn"};
                    var5_6 = PromptDialog.showMutipleChoiceDialog(CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.Title"), CommonUISupportResourceBundle.BUNDLE.getString("Prompt.closeWithActiveTransactions.Message"), "closeWithTransactions", var6_8, new Image(this.getClass().getResource("/com/ti/et/elg/commonUISupport/icons/dialog_warning.png").toExternalForm()));
                    if (0 == var5_6) {
                        TIConnectApplication.this.canClose = false;
                        Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                        return;
                    }
                    TIConnectApplication.this.canClose = true;
                    Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                } else {
                    TIConnectApplication.this.canClose = true;
                    Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                }
                TILogger.logDetail(TIConnectApplication.access$200(), "CLOSING_ROUTINE - Can we close? " + TIConnectApplication.this.canClose);
                if (!ScreenCaptureFactory.getScreenCapture().shutdown() || !ProgramEditorFactory.getProgramEditor().shutdown()) ** GOTO lbl46
                TIConnectApplication.this.canClose = true;
                Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                TILogger.logDetail(TIConnectApplication.access$200(), "CLOSING_ROUTINE - Can we close? " + TIConnectApplication.this.canClose);
                if (!TIConnectApplication.this.canClose) ** GOTO lbl55
                ** GOTO lbl49
lbl46: // 1 sources:
                TIConnectApplication.this.canClose = false;
                Platform.setImplicitExit((boolean)TIConnectApplication.this.canClose);
                return;
lbl49: // 1 sources:
                if (var2_2) {
                    var3_3.clear();
                    if (PlatformManager.isMac()) {
                        var6_7 = (Object)new ClipboardContent();
                        var6_7.putString("");
                        var3_3.setContent((Map)var6_7);
                    }
                }
lbl55: // 6 sources:
                if (!(!stage.isShowing() || PlatformManager.isMac() && var1_1)) {
                    stage.close();
                }
                TIConnectApplication.access$300(TIConnectApplication.this).show();
                var6_7 = new Task<Void>(){

                    protected Void call() throws Exception {
                        PromptDialog.setSuppressPrompts(true);
                        DeviceExplorerFactory.getDeviceExplorer().shutDown();
                        TIConnectApplication.this.commManager.shutDown();
                        ConnectServerFactory.getDeviceComm().shutDown();
                        TIConnectApplication.this.closeDialog.close();
                        TILogger.logInfo(LOG_TAG, "Finished shutting down native libraries");
                        AnalyticsManager.shutDown();
                        TILogger.logInfo(LOG_TAG, "Finished shutting down Analytics");
                        return null;
                    }
                };
                var7_10 = new Thread((Runnable)var6_7);
                var7_10.setName("Shutdown Thread");
                var7_10.start();
                if (PlatformManager.isMac() == false) return;
                if (var1_1 == false) return;
                try {
                    var7_10.join();
                }
                catch (InterruptedException var8_11) {
                    TILogger.logError(TIConnectApplication.access$200(), "Error Shutting down native libraries", var8_11);
                }
                Platform.exit();
            }

        };
        ExitAction.getInstance().setClosingRoutine(this.closeRoutine);
        this.closeDialog = new CloseDialog();
        scene.getWindow().setOnCloseRequest((EventHandler)new EventHandler<WindowEvent>(){

            public void handle(WindowEvent windowEvent) {
                if (PlatformManager.isMac() && CommonConstants.MAIN_STAGE.isFullScreen()) {
                    windowEvent.consume();
                }
                TIConnectApplication.this.closeRoutine.run();
                if (!TIConnectApplication.this.canClose) {
                    windowEvent.consume();
                }
            }
        });
    }

    private void initConnectServer() {
        if (useMockAutoDetect) {
            ConnectServerFactory.configureFactory(new CommManager(), new DeviceCommMock(), Translator.getInstance(), new AutoDetectMock(), new TestAutomationAPI());
        } else {
            ConnectServerFactory.configureFactory(new CommManager(), new DeviceComm(), Translator.getInstance(), AutoDetect.getInstance(), new TestAutomationAPI());
        }
        this.commManager = ConnectServerFactory.getCommManager();
        this.commManager.init();
        DeviceCommInterface deviceCommInterface = ConnectServerFactory.getDeviceComm();
        deviceCommInterface.init(true, UserPropertyManagement.getBoolean("GetDeviceNameFromDriver.enabled", false), true);
        this.connectedDevices = new Vector();
        this.connectionListener = new TIDeviceConnectionListener(){

            @Override
            public void deviceConnected(final TIDevice tIDevice) {
                TIConnectApplication.this.connectedDevices.add(tIDevice);
                Platform.runLater((Runnable)new Runnable(){

                    @Override
                    public void run() {
                        DeviceExplorerFactory.getDeviceExplorer().addTIDevice(tIDevice);
                    }
                });
            }

            @Override
            public void deviceDisconnected(final TIDevice tIDevice) {
                TIConnectApplication.this.connectedDevices.remove(tIDevice);
                Platform.runLater((Runnable)new Runnable(){

                    @Override
                    public void run() {
                        DeviceExplorerFactory.getDeviceExplorer().removeTIDevice(tIDevice);
                    }
                });
            }

        };
        this.commManager.addDeviceConnectionListener(this.connectionListener);
    }

    private void createMainMenu() {
        this.menuBox = new VBox();
        MenuBar menuBar = this.workspaceManager.createMenuBar();
        menuBar.setId("barMainMenu");
        if (PlatformManager.isWindows()) {
            this.paneTIConnectCE.setTop((Node)this.menuBox);
            this.menuBox.getChildren().addAll((Object[])new Node[]{menuBar});
        } else {
            this.paneTIConnectCE.getChildren().add((Object)menuBar);
        }
        TIConnectAutomationAPI.getInstance().setMainMenu(this.paneTIConnectCE, menuBar, this.menuBox);
    }

    private void initDeviceExplorerTab() {
        DeviceExplorerFactory.configureFactory();
        DeviceExplorerFactory.getDeviceExplorer().init(this.workspaceManager);
        DeviceExplorerFactory.getDeviceExplorer().setDeviceExplorerProductMode(DeviceExplorerInterface.ProductMode.TI_CONNECT);
        OverlayStackPaneBase overlayStackPaneBase = new OverlayStackPaneBase();
        DeviceExplorerFactory.getContentTable().setParent(overlayStackPaneBase);
        DeviceExplorerFactory.getNothingConnectedPane().setParent(overlayStackPaneBase);
        overlayStackPaneBase.showPane(DeviceExplorerFactory.getContentTable().getRootNode());
        overlayStackPaneBase.showPane(DeviceExplorerFactory.getNothingConnectedPane().getRootNode());
        this.panelContentDeviceExplorer.setCenter((Node)overlayStackPaneBase);
        this.deviceExplorerToolbar.setCenter(DeviceExplorerFactory.getDeviceExplorerToolbar().getRootNode());
        this.panelHandheldList.setCenter(DeviceExplorerFactory.getDeviceList().getRootNode());
        CaptureScreenAction.getInstance().setWorkSpacesManager(this.workspaceManager);
        this.tabPaneHHExplorer.setOnSelectionChanged((EventHandler)new EventHandler<Event>(){

            public void handle(Event event) {
                DeviceExplorerFactory.getDeviceExplorer().notifyIsActive(TIConnectApplication.this.tabPaneHHExplorer.isSelected());
                if (TIConnectApplication.this.tabPaneHHExplorer.isSelected()) {
                    TIConnectApplication.this.screenCaptureDeviceList.setCenter(null);
                    TIConnectApplication.this.panelHandheldList.setCenter(DeviceExplorerFactory.getDeviceList().getRootNode());
                    for (TIDevice tIDevice : DeviceExplorerFactory.getDeviceList().getConnectedDevices()) {
                        TIConnectApplication.this.commManager.getIDDeviceInfo(tIDevice);
                        DeviceExplorerFactory.getDeviceList().findDeviceHolder(tIDevice).setIDAvailableProperty(tIDevice.getConnectionIDforName());
                    }
                    DeviceExplorerFactory.getDeviceList().setMode(0);
                    TIConnectApplication.this.setupSplitter(TIConnectApplication.this.splitPaneDeviceExplorer, 2);
                    DeviceExplorerFactory.getContentTable().refreshCurrentTIDevice();
                }
            }
        });
    }

    private void initScreenCaptureTab() {
        ScreenCaptureFactory.configureFactory();
        ScreenCaptureFactory.getScreenCapture().init(this.workspaceManager);
        OverlayStackPaneBase overlayStackPaneBase = new OverlayStackPaneBase();
        ScreenCaptureFactory.getScreenCaptureContainer().setParent(overlayStackPaneBase);
        ScreenCaptureFactory.getNoScreenCapturePresentPane().setParent(overlayStackPaneBase);
        ScreenCaptureFactory.getNothingConnectedPane().setParent(overlayStackPaneBase);
        overlayStackPaneBase.showPane(ScreenCaptureFactory.getScreenCaptureContainer().getRootNode());
        overlayStackPaneBase.showPane(ScreenCaptureFactory.getNothingConnectedPane().getRootNode());
        this.panelContentScreenCapture.setCenter((Node)overlayStackPaneBase);
        this.screenCaptureToolbar.setCenter(ScreenCaptureFactory.getScreenCaptureToolbar().getRootNode());
        this.tabPaneScreenCapture.setOnSelectionChanged((EventHandler)new EventHandler<Event>(){

            public void handle(Event event) {
                ScreenCaptureFactory.getScreenCapture().notifyIsActive(TIConnectApplication.this.tabPaneScreenCapture.isSelected());
                if (TIConnectApplication.this.tabPaneScreenCapture.isSelected()) {
                    TIConnectApplication.this.panelHandheldList.setCenter(null);
                    TIConnectApplication.this.screenCaptureDeviceList.setCenter(ScreenCaptureFactory.getDeviceList().getRootNode());
                    DeviceExplorerFactory.getDeviceList().setMode(1);
                    TIConnectApplication.this.setupSplitter(TIConnectApplication.this.splitPaneScreenCapture, 1);
                }
            }
        });
    }

    private void initProgramEditorTab() {
        ProgramEditorFactory.configureFactory();
        ProgramEditorFactory.getProgramEditor().init(this.workspaceManager);
        this.panelContentProgramEditor.setCenter(ProgramEditorFactory.getProgramEditorContents().getRootNode());
        this.programEditorLibrary.setCenter(ProgramEditorFactory.getProgramEditorCatalog().getRootNode());
        this.programEditorToolbar.setCenter(ProgramEditorFactory.getProgramEditorToolbar().getRootNode());
        initialProgramEditorSplitterPosition = UserPropertyManagement.getDouble("PECatalogSplitter.position.value", initialProgramEditorSplitterPosition);
        ((SplitPane.Divider)this.splitPaneProgramEditor.getDividers().get(0)).positionProperty().addListener((ChangeListener)new ChangeListener<Object>(){

            public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                Double d = (Double)object2;
                UserPropertyManagement.setDouble("PECatalogSplitter.position.value", d);
            }
        });
        this.tabPaneProgramEditor.setOnSelectionChanged((EventHandler)new EventHandler<Event>(){

            public void handle(Event event) {
                ProgramEditorFactory.getProgramEditor().notifyIsActive(TIConnectApplication.this.tabPaneProgramEditor.isSelected());
                if (TIConnectApplication.this.tabPaneProgramEditor.isSelected() && !TIConnectApplication.this.PESplitterInitted) {
                    TIConnectApplication.this.PESplitterInitted = true;
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ((SplitPane.Divider)TIConnectApplication.this.splitPaneProgramEditor.getDividers().get(0)).setPosition(TIConnectApplication.initialProgramEditorSplitterPosition);
                        }
                    });
                }
            }

        });
        this.tabPaneProgramEditor.setDisable(false);
    }

    private void setupSplitter(final SplitPane splitPane, int n) {
        if (1 != n && !this.hiddenSplitterInitted) {
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    TIConnectApplication.this.hiddenSplitterInitted = true;
                    double d = UserPropertyManagement.getDouble("DeviceListSplitter.position.value", 0.3867334167709637);
                    ((SplitPane.Divider)splitPane.getDividers().get(0)).setPosition(d);
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            ((SplitPane.Divider)TIConnectApplication.this.splitPaneScreenCapture.getDividers().get(0)).positionProperty().bindBidirectional((Property)((SplitPane.Divider)TIConnectApplication.this.splitPaneDeviceExplorer.getDividers().get(0)).positionProperty());
                        }
                    });
                }

            });
        } else if (!this.hiddenSplitterInitted) {
            this.setSplitPaneChangeListener(splitPane);
        }
    }

    private void setSplitPaneChangeListener(SplitPane splitPane) {
        ((SplitPane.Divider)splitPane.getDividers().get(0)).positionProperty().addListener((ChangeListener)new ChangeListener<Object>(){

            public void changed(ObservableValue<?> observableValue, Object object, Object object2) {
                Double d = (Double)object2;
                UserPropertyManagement.setDouble("DeviceListSplitter.position.value", d);
            }
        });
    }

    private void loadFonts() {
        Font.loadFont((String)"file:resources/fonts/OpenSans-Bold.ttf", (double)14.0);
        Font.loadFont((String)"file:resources/fonts/OpenSans-Regular.ttf", (double)14.0);
        Font.loadFont((String)"file:resources/fonts/OpenSans-Semibold.ttf", (double)14.0);
        Font.loadFont((String)this.getClass().getResource("/com/ti/et/elg/tiConnect/fonts/TIUNI__.TTF").toExternalForm(), (double)14.0);
        Font.loadFont((String)this.getClass().getResource("/com/ti/et/elg/tiConnect/fonts/TIUNIBD.TTF").toExternalForm(), (double)14.0);
    }

    public void start(Stage stage) throws Exception {
        PlatformManager.init(CommonConstants.PRODUCT_NAME, Constants.PRODUCT_VERSION_STRING);
        Level level = TIConnectApplication.determineLogLevel();
        String string = PlatformManager.getApplicationLogsFolder() + "javaLogs" + "-" + level.getName() + ".txt";
        FileUtils.renameFileAsBackup(string);
        FileUtils.renameFileAsBackup(PlatformManager.getApplicationLogsFolder() + "cLog.txt");
        TILogger.init(level, string, true);
        String string2 = UserPropertyManagement.getString("Application.OverrideLanguage", null);
        String string3 = UserPropertyManagement.getString("Application.OverrideCountry", null);
        if (string2 != null) {
            if (string3 == null) {
                Locale.setDefault(new Locale(string2));
            } else {
                Locale.setDefault(new Locale(string2, string3));
            }
        }
        String string4 = "";
        if (PlatformManager.isWindows()) {
            string4 = TIConnectResourceBundle.BUNDLE.getString("software.ticonnect.webkey.win");
        } else if (PlatformManager.isMac()) {
            string4 = TIConnectResourceBundle.BUNDLE.getString("software.ticonnect.webkey.mac");
        }
        PlatformManager.setUpdateName(string4);
        TIConnectApplication.logConfigInfo();
        this.loadFonts();
        if (PlatformManager.isWindows() && TIConnectApplication.isApplicationAlreadyRunning()) {
            TILogger.logError(LOG_TAG, "Can't launch 2nd concurrent instance of the application");
            Platform.exit();
        } else if (PlatformManager.isMac32BitModeKernelRunning()) {
            TILogger.logError(LOG_TAG, "Can't run TI Connect CE in 32-bit kernel mode.");
            PromptDialog.showUserError(TIConnectResourceBundle.BUNDLE.getString("mac32bitError.title"), TIConnectResourceBundle.BUNDLE.getString("mac32bitError.message"));
            Platform.exit();
        } else {
            CommonConstants.SPLASH_STAGE = stage;
            this.showSplash(stage);
            this.ready.addListener((ChangeListener)new ChangeListener<Boolean>(){

                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean bl, Boolean bl2) {
                    if (Boolean.TRUE.equals(bl2)) {
                        Platform.runLater((Runnable)new Runnable(){

                            @Override
                            public void run() {
                                CommonConstants.MAIN_STAGE.show();
                                CommonConstants.SPLASH_STAGE.hide();
                                if (PlatformManager.isMacConnectManagerRunning()) {
                                    PromptDialog.showUserNotification(TIConnectResourceBundle.BUNDLE.getString("macConnectCommManagerWarning.title"), TIConnectResourceBundle.BUNDLE.getString("macConnectCommManagerWarning.message"));
                                }
                            }
                        });
                    }
                }

            });
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    CommonConstants.PRODUCT_NAME_WITH_TRADEMARK = CommonConstants.addTrademarkToProductName(DeviceExplorerInterface.ProductMode.TI_CONNECT.ordinal(), CommonConstants.PRODUCT_NAME);
                    if (UserPropertyManagement.getUserProperty("Analytics.enabled") == null) {
                        AnalyticsOptInAction.getInstance().getEventHandler().handle((Event)new ActionEvent());
                    } else if (UserPropertyManagement.getBoolean("Analytics.enabled", true)) {
                        AnalyticsManager.enableAnalytics(CommonConstants.PRODUCT_NAME_WITH_TRADEMARK, Constants.PRODUCT_VERSION_STRING);
                    }
                    Stage stage = new Stage();
                    TIConnectApplication.this.init(stage);
                    TIConnectApplication.this.ready.setValue(Boolean.TRUE);
                    if (UserPropertyManagement.getUserProperty("autoUpgradeEnabled") == null) {
                        UserPropertyManagement.setUserProperty("autoUpgradeEnabled", "1");
                    }
                    if (UserPropertyManagement.getUserProperty("autoUpgradeEnabled").equals("1")) {
                        CheckForNotificationAction.getInstance().checkForNotifications();
                    }
                }
            });
        }
    }

    private void showSplash(Stage stage) {
        try {
            FXMLLoader fXMLLoader = new FXMLLoader(this.tiConnectClass.getResource("splashScreen.fxml"));
            fXMLLoader.setResources((ResourceBundle)TIConnectResourceBundle.BUNDLE);
            fXMLLoader.setController((Object)this);
            Object object = fXMLLoader.load();
            this.lblTIConnectVersion.setText(Constants.PRODUCT_VERSION_STRING);
            stage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene((Parent)object);
            scene.getStylesheets().add((Object)this.tiConnectClass.getResource("commonUI.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException var2_3) {
            TILogger.logError(LOG_TAG, "showSplash", var2_3);
        }
    }

    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] arrstring) {
        TIConnectApplication.launch((String[])arrstring);
    }

    private static Level determineLogLevel() {
        File file = new File(PlatformManager.getApplicationLogsFolder() + "VERBOSE");
        File file2 = new File(PlatformManager.getApplicationLogsFolder() + "VERBOSE.txt");
        if (file.exists() || file2.exists()) {
            return Level.FINEST;
        }
        return Level.INFO;
    }

    private static void fixOSNameAndVersionIfNecessary() {
        if (PlatformManager.isWindows()) {
            String string = System.getProperty("os.name");
            String string2 = System.getProperty("os.version");
            if (string.startsWith("Windows 8") || string2.startsWith("6.2") || string2.startsWith("6.3")) {
                try {
                    Process process = Runtime.getRuntime().exec("cmd ver");
                    InputStream inputStream = process.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String string3 = bufferedReader.readLine();
                    if (string3 != null) {
                        int n = string3.indexOf("[");
                        if (n != -1) {
                            int n2 = string3.indexOf(" ", n);
                            if (n2 != -1) {
                                int n3 = string3.indexOf("]", n2);
                                if (n3 != -1) {
                                    String string4 = string3.substring(n2 + 1, n3);
                                    System.setProperty("os.version", string4);
                                    if (string4.startsWith("6.3")) {
                                        System.setProperty("os.name", "Windows 8.1");
                                    } else if (string4.startsWith("10")) {
                                        System.setProperty("os.name", "Windows 10");
                                    } else {
                                        TILogger.logError("CONFIG", "Unable to determine Windows OS Name since major version wasn't 10 or 8.1, so leaving os.name as-is");
                                    }
                                } else {
                                    TILogger.logError("CONFIG", "Unable to determine Windows OS Version due to missing ]");
                                }
                            } else {
                                TILogger.logError("CONFIG", "Unable to determine Windows OS Version due to missing space after [");
                            }
                        } else {
                            TILogger.logError("CONFIG", "Unable to determine Windows OS Version due to missing [");
                        }
                    } else {
                        TILogger.logError("CONFIG", "Unable to determine Windows 8 vs. Windows 10 due to problem getting response from executing cmd ver");
                    }
                }
                catch (IOException var2_3) {
                    TILogger.logError("CONFIG", "Unable to determine Windows 8 vs. Windows 10 due to problem executing cmd ver");
                }
            }
        }
    }

    private static void logConfigInfo() {
        TIConnectApplication.fixOSNameAndVersionIfNecessary();
        TILogger.logInfo("CONFIG", "Build version   :" + Constants.PRODUCT_VERSION_STRING);
        TILogger.logInfo("CONFIG", "os.arch         :" + System.getProperty("os.arch"));
        TILogger.logInfo("CONFIG", "os.name         :" + System.getProperty("os.name"));
        TILogger.logInfo("CONFIG", "os.version      :" + System.getProperty("os.version"));
        TILogger.logInfo("CONFIG", "OS Locale       :" + System.getProperty("user.language") + "_" + System.getProperty("user.country"));
        TILogger.logInfo("CONFIG", "App Locale      :" + Locale.getDefault().getDisplayName());
        TILogger.logInfo("CONFIG", "Home folder     :" + System.getProperty("user.home"));
        TILogger.logInfo("CONFIG", "Temp folder     :" + System.getProperty("java.io.tmpdir"));
        TILogger.logInfo("CONFIG", "App Log level   :" + TIConnectApplication.determineLogLevel().getName());
        TILogger.logInfo("CONFIG", "App Log Folder :" + PlatformManager.getApplicationLogsFolder());
        TILogger.logInfo("CONFIG", "App Preferences Folder :" + PlatformManager.getApplicationPreferencesFolder());
        TILogger.logInfo("CONFIG", "Mac Connect 4.0 Server running :" + PlatformManager.isMacConnectManagerRunning());
        TILogger.logInfo("CONFIG", "Mac - Is 32bit kernel mode running :" + PlatformManager.isMac32BitModeKernelRunning());
        TILogger.logInfo("CONFIG", "App JRE version :" + System.getProperty("java.version"));
    }

    private static boolean isApplicationAlreadyRunning() {
        try {
            File file = new File(PlatformManager.getApplicationLogsFolder() + "concurrencyCheck");
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            FileLock fileLock = randomAccessFile.getChannel().tryLock();
            if (fileLock == null) {
                return true;
            }
            return false;
        }
        catch (FileNotFoundException var0_1) {
            TILogger.logError(LOG_TAG, "Unable to check if application is already running", var0_1);
            return false;
        }
        catch (IOException var0_2) {
            TILogger.logError(LOG_TAG, "Unable to check if application is already running", var0_2);
            return false;
        }
    }

}

