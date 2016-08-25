/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.scene.Node
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 */
package com.ti.et.elg.deviceExplorer;

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
import com.ti.et.elg.commonUISupport.deviceList.TIDeviceHolder;
import com.ti.et.elg.commonUISupport.hiddenDialog.HiddenDialog;
import com.ti.et.elg.commonUISupport.imageUtils.ImageManipulationUtilities;
import com.ti.et.elg.commonUISupport.imageUtils.TIConvertedImageVarMultiplexer;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.commonUISupport.prompt.PromptDialog;
import com.ti.et.elg.commonUISupport.sendToHandheldDialog.SendToHandheldDialog;
import com.ti.et.elg.commonUISupport.workspaceFramework.WorkspaceManagerInterface;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.dataimport.DIException;
import com.ti.et.elg.dataimport.DIFactory;
import com.ti.et.elg.dataimport.converter.impl.ConverterException;
import com.ti.et.elg.dataimport.model.IDITIVarData;
import com.ti.et.elg.dataimport.util.CommonValidationUtils;
import com.ti.et.elg.deviceExplorer.actions.AddToHandheldAction;
import com.ti.et.elg.deviceExplorer.actions.CaptureScreenAction;
import com.ti.et.elg.deviceExplorer.actions.DataImportAction;
import com.ti.et.elg.deviceExplorer.actions.DeleteVarsAction;
import com.ti.et.elg.deviceExplorer.actions.DeviceExplorerActionManager;
import com.ti.et.elg.deviceExplorer.actions.DeviceRefreshAction;
import com.ti.et.elg.deviceExplorer.actions.GetHHInformationAction;
import com.ti.et.elg.deviceExplorer.actions.SaveVarsAction;
import com.ti.et.elg.deviceExplorer.actions.SelectAllAction;
import com.ti.et.elg.deviceExplorer.actions.SendOSAction;
import com.ti.et.elg.deviceExplorer.actions.SendToHHAction;
import com.ti.et.elg.deviceExplorer.actions.UnselectAllAction;
import com.ti.et.elg.deviceExplorer.dataimport.converter.impl.DITIVarConverter;
import com.ti.et.elg.deviceExplorer.exports.DeviceExplorerInterface;
import com.ti.et.elg.deviceExplorer.exports.TIOpenHandlerInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.deviceExplorer.transaction.GetThenSendVarsTransaction;
import com.ti.et.elg.deviceExplorer.transaction.SendVarsTransaction;
import com.ti.et.elg.deviceExplorer.transaction.TransferOSTransaction;
import com.ti.et.elg.transactionManager.Transaction;
import com.ti.et.elg.transactionManager.TransactionGroup;
import com.ti.et.elg.transactionManager.TransactionListener;
import com.ti.et.elg.transactionManager.TransactionManager;
import com.ti.et.elg.transactionManager.TransactionQueueListener;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DeviceExplorerController
implements DeviceExplorerInterface {
    private static final String LOG_TAG = DeviceExplorerController.class.getSimpleName();
    protected TransactionManager transactionManager = TransactionManager.getInstance();
    protected WorkspaceManagerInterface workspaceManager;
    protected ObservableList<TIDevice> busyDeviceList = FXCollections.observableArrayList();
    protected Map<Integer, TIOpenHandlerInterface> mapTypeToOpenHandler = new HashMap<Integer, TIOpenHandlerInterface>();
    protected DeviceExplorerInterface.ProductMode productMode = DeviceExplorerInterface.ProductMode.TI_CONNECT;
    protected DITIVarConverter varConverter = null;

    @Override
    public void init(WorkspaceManagerInterface workspaceManagerInterface) {
        this.workspaceManager = workspaceManagerInterface;
        DeviceExplorerFactory.getContentTable().init(this);
        DeviceExplorerFactory.getDeviceList().init(this);
        DeviceExplorerFactory.getDeviceExplorerToolbar().init(this);
        DeviceExplorerFactory.getNothingConnectedPane().init();
        DeviceExplorerFactory.getDeviceList().setDeviceRefreshAction(DeviceRefreshAction.getInstance());
        DeviceExplorerFactory.getDeviceList().setHHInfoSAction(GetHHInformationAction.getInstance());
        DeviceExplorerFactory.getDeviceList().setSendOSAction(SendOSAction.getInstance());
        DeviceExplorerFactory.getContentTable().setDeleteAction(DeleteVarsAction.getInstance());
        DeviceExplorerFactory.getContentTable().setSendToComputerAction(SaveVarsAction.getInstance());
        DeviceExplorerFactory.getContentTable().setDeviceRefreshAction(DeviceRefreshAction.getInstance());
        DeviceExplorerFactory.getContentTable().setSelectAllAction(SelectAllAction.getInstance());
        DeviceExplorerFactory.getContentTable().setSendToHHAction(SendToHHAction.getInstance());
        TransactionManager.getInstance().addTransactionQueueListener(new TransactionQueueListener(){

            @Override
            public void transactionQueueEmpty(TIDevice tIDevice) {
                DeviceExplorerController.this.busyDeviceList.remove((Object)tIDevice);
            }

            @Override
            public void transactionQueueNonEmpty(TIDevice tIDevice) {
                if (!DeviceExplorerController.this.busyDeviceList.contains((Object)tIDevice)) {
                    DeviceExplorerController.this.busyDeviceList.add((Object)tIDevice);
                }
            }
        });
        DIFactory.configureFactory();
        this.varConverter = new DITIVarConverter();
    }

    @Override
    public void notifyIsActive(boolean bl) {
        if (bl) {
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE, ChangeAppLanguageAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT, ExitAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_DELETE, DeleteVarsAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL, SelectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL, UnselectAllAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_REFRESH, DeviceRefreshAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_HANDHELD_INFORMATION, GetHHInformationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS, SendToHHAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_COMPUTER, SaveVarsAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_ADD_TO_HANDHELDS, AddToHandheldAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN, CaptureScreenAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_DATA_IMPORT, DataImportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_OS, SendOSAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP, HelpAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM, VisitTIEducationAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE, VisitActivitiesExchangeAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING, OnlineTroubleshootingAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT, ContactTISupportAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT, AboutAction.getInstance());
            this.workspaceManager.addTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS, AnalyticsOptInAction.getInstance());
            this.workspaceManager.getUINavigator().addNodeToNavCycle(DeviceExplorerFactory.getDeviceList().getFocusableNode());
            this.workspaceManager.getUINavigator().addNodeToNavCycle(DeviceExplorerFactory.getContentTable().getFocusableNode());
            AnalyticsManager.postHit(ScreenHit.CALCULATOR_EXPLORER);
        } else {
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_CHANGE_LANGUAGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.FILE_EXIT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_DELETE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_SELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.EDIT_UNSELECT_ALL);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_REFRESH);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_HANDHELDS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_TO_COMPUTER);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_ADD_TO_HANDHELDS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.VIEW_HANDHELD_INFORMATION);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_CAPTURE_SCREEN);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_DATA_IMPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.ACTIONS_SEND_OS);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_HELP);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_EDUCATION_TI_COM);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_VISIT_ACTIVITIES_EXCHANGE);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ONLINE_TROUBLESHOOTING);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_CONTACT_TI_SUPPORT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ABOUT);
            this.workspaceManager.removeTopLevelMenuAction(WorkspaceManagerInterface.TopLevelMenuItem.HELP_ANALYTICS);
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(DeviceExplorerFactory.getDeviceList().getFocusableNode());
            this.workspaceManager.getUINavigator().removeNodeFromNavCycle(DeviceExplorerFactory.getContentTable().getFocusableNode());
        }
        DeviceExplorerActionManager.getInstance().deviceExplorerActiveWorkspace(bl);
    }

    @Override
    public void getVarsFromDeviceToOtherDevices(final TIDevice tIDevice, final TIVar[] arrtIVar, final List<TIDevice> list, final SendToHandheldDialog.HandHeldTargets handHeldTargets) {
        if (tIDevice != null && arrtIVar != null && arrtIVar.length > 0 && list != null && list.size() > 0) {
            final List<TIVar> list2 = Arrays.asList(arrtIVar);
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    if (handHeldTargets != SendToHandheldDialog.HandHeldTargets.SELECTED) {
                        SendToHandheldDialog sendToHandheldDialog = new SendToHandheldDialog(TIVarMultiplexer.convertList(list2), list, tIDevice, handHeldTargets, SendToHandheldDialog.DialogMode.HANDHELD_TO_HANDHELD, DeviceExplorerController.this.busyDeviceList);
                        sendToHandheldDialog.showAndWait();
                        if (!sendToHandheldDialog.isDialogCancelled() && list2.size() > 0) {
                            GetThenSendVarsTransaction getThenSendVarsTransaction = new GetThenSendVarsTransaction(tIDevice, list2.toArray(new TIVar[0]), list, DeviceExplorerController.this.transactionManager);
                            if (sendToHandheldDialog.isConfirmOverride()) {
                                getThenSendVarsTransaction.getListener().setConfirmOverride(3);
                            }
                            DeviceExplorerController.this.transactionManager.addTransaction(tIDevice, getThenSendVarsTransaction, null);
                        }
                    } else {
                        GetThenSendVarsTransaction getThenSendVarsTransaction = new GetThenSendVarsTransaction(tIDevice, arrtIVar, list, DeviceExplorerController.this.transactionManager);
                        DeviceExplorerController.this.transactionManager.addTransaction(tIDevice, getThenSendVarsTransaction, null);
                    }
                }
            });
        }
    }

    @Override
    public void addTIDevice(TIDevice tIDevice) {
        DeviceExplorerFactory.getDeviceList().addTIDevice(tIDevice);
        if (DeviceExplorerFactory.getDeviceList().getMode() != 1) {
            ConnectServerFactory.getCommManager().getIDDeviceInfo(tIDevice);
            DeviceExplorerFactory.getDeviceList().findDeviceHolder(tIDevice).setIDAvailableProperty(tIDevice.getConnectionIDforName());
        }
    }

    @Override
    public void removeTIDevice(TIDevice tIDevice) {
        DeviceExplorerFactory.getDeviceList().removeTIDevice(tIDevice);
    }

    protected List<TIVarMultiplexer> validateSendFilesToTIDevices(List<File> list, List<TIDevice> list2) {
        List<TIVar> list3;
        Object object;
        Object object2;
        CommManagerInterface commManagerInterface = ConnectServerFactory.getCommManager();
        ArrayList<TIVarMultiplexer> arrayList = new ArrayList<TIVarMultiplexer>(list.size());
        boolean bl = false;
        int n = 1;
        Collections.sort(list, new Comparator<File>(){

            @Override
            public int compare(File file, File file2) {
                String string = file.getName();
                String string2 = file2.getName();
                return Collator.getInstance().compare(string, string2);
            }
        });
        int n2 = 0;
        for (File object32 : list) {
            if (CommonValidationUtils.isCsvFile(object32.getAbsolutePath())) {
                ++n2;
                continue;
            }
            object = ImageManipulationUtilities.isImageFile(object32);
            if (object != null) {
                list3 = null;
                list3 = this.getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW ? new TIConvertedImageVarMultiplexer(TIConvertedImageVarMultiplexer.ImageKind.FROM_USER_IMAGE_FILE, object32, n, list2.get(0).getHardwareVersion()) : new TIConvertedImageVarMultiplexer(TIConvertedImageVarMultiplexer.ImageKind.FROM_USER_IMAGE_FILE, object32, n);
                boolean bl2 = list3.setImage((Image)object);
                if (bl2) {
                    arrayList.add((TIVarMultiplexer)((Object)list3));
                    if (++n <= 9) continue;
                    n = 0;
                    continue;
                }
                bl = true;
                continue;
            }
            list3 = commManagerInterface.createTIVar(object32.getAbsolutePath());
            if (list3 != null && list3.size() > 0) {
                for (TIVar tIVar : list3) {
                    if (tIVar.getOwnerCalculatorID() == -1 || tIVar.isACorruptedVar()) {
                        bl = true;
                        continue;
                    }
                    if (tIVar.isFlashObject() && tIVar.getVarType() == 35 && (list.size() > 1 || this.getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW)) {
                        bl = true;
                        continue;
                    }
                    if (tIVar.isFlashObject() && this.getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_SMARTVIEW && (tIVar.getDeviceFileName().equalsIgnoreCase("EasyData") || tIVar.getDeviceFileName().startsWith("CBL/CBR"))) {
                        bl = true;
                        continue;
                    }
                    arrayList.add(new TIVarMultiplexer(tIVar));
                }
                continue;
            }
            TILogger.logError(LOG_TAG, "No Vars created from File:" + object32.getAbsolutePath());
            bl = true;
        }
        if (bl) {
            object2 = CommonUISupportResourceBundle.BUNDLE;
            PromptDialog.showUserNotification(object2.getString("error"), object2.getString("oneOrMoreFilesWasInvalid"));
        }
        if (n2 > 0) {
            try {
                object2 = DIFactory.getDataImportWizard();
                List<IDITIVarData> list4 = object2.importFiles(list);
                object = this.varConverter.convert(list4);
                list3 = object.iterator();
                while (list3.hasNext()) {
                    TIVar tIVar = (TIVar)list3.next();
                    arrayList.add(new TIVarMultiplexer(tIVar));
                }
            }
            catch (DIException | ConverterException var8_9) {
                TILogger.logError(LOG_TAG, "There was an error converting CSV file to TIVars");
            }
        }
        return arrayList;
    }

    @Override
    public void sendFilesToTIDevices(List<File> list, final List<TIDevice> list2, final Class<? extends Transaction<Object, TIDevice>> class_) {
        final List<TIVarMultiplexer> list3 = this.validateSendFilesToTIDevices(list, list2);
        if (list3 == null || list3.size() == 0) {
            return;
        }
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                DeviceExplorerController.removeDevicesNoLongerConnected(list2);
                SendToHandheldDialog sendToHandheldDialog = new SendToHandheldDialog(list3, list2, SendToHandheldDialog.HandHeldTargets.SELECTED, DeviceExplorerController.this.getDeviceExplorerProductMode() == DeviceExplorerInterface.ProductMode.TI_CONNECT ? SendToHandheldDialog.DialogMode.DEVICE_EXPLORER : SendToHandheldDialog.DialogMode.SV_EMULATOR_EXPLORER, DeviceExplorerController.this.busyDeviceList);
                sendToHandheldDialog.showAndWait();
                if (!sendToHandheldDialog.isDialogCancelled()) {
                    DeviceExplorerController.removeDevicesNoLongerConnected(list2);
                    if (list3.size() > 0) {
                        TransactionGroup transactionGroup = list2.size() > 1 ? new TransactionGroup() : null;
                        for (TIDevice tIDevice : list2) {
                            Transaction transaction;
                            if (list3.size() == 1 && ((TIVarMultiplexer)list3.get(0)).getTIVar().isFlashObject() && ((TIVarMultiplexer)list3.get(0)).getTIVar().getVarType() == 35 && !tIDevice.isUnderOSUpdate()) {
                                tIDevice.setUnderOSUpdate(true);
                                transaction = new TransferOSTransaction(((TIVarMultiplexer)list3.get(0)).getTIVar(), tIDevice, TransactionManager.getInstance());
                                if (sendToHandheldDialog.isConfirmOverride()) {
                                    transaction.getListener().setConfirmOverride(3);
                                }
                                TransactionManager.getInstance().addTransaction(tIDevice, transaction, transactionGroup);
                                continue;
                            }
                            if (!tIDevice.isOSpresent() || tIDevice.isUnderOSUpdate()) continue;
                            transaction = null;
                            if (class_ == null) {
                                transaction = new SendVarsTransaction(TIVarMultiplexer.convertList(list3, tIDevice), tIDevice, TransactionManager.getInstance());
                            } else {
                                try {
                                    Transaction transaction2 = (Transaction)class_.newInstance();
                                    transaction2.setParameters(TIVarMultiplexer.convertList(list3, tIDevice), tIDevice, TransactionManager.getInstance());
                                    transaction = transaction2;
                                }
                                catch (Exception var6_7) {
                                    TILogger.logError(LOG_TAG, "Error Transaction");
                                }
                            }
                            if (sendToHandheldDialog.isConfirmOverride()) {
                                transaction.getListener().setConfirmOverride(3);
                            }
                            TransactionManager.getInstance().addTransaction(tIDevice, transaction, transactionGroup);
                        }
                    }
                }
            }
        });
    }

    protected static void removeDevicesNoLongerConnected(List<TIDevice> list) {
        Collection<TIDevice> collection = ConnectServerFactory.getCommManager().getConnectedDevices();
        ArrayList<TIDevice> arrayList = new ArrayList<TIDevice>();
        for (TIDevice tIDevice : list) {
            if (collection.contains(tIDevice)) continue;
            arrayList.add(tIDevice);
        }
        list.removeAll(arrayList);
    }

    @Override
    public void shutDown() {
        this.transactionManager.shutDown();
    }

    @Override
    public void sendFilesToPromptedDevice(File[] arrfile) {
    }

    @Override
    public boolean hasActiveTransactions() {
        return this.transactionManager.hasActiveTransactions();
    }

    @Override
    public WorkspaceManagerInterface getWorkSpaceManager() {
        return this.workspaceManager;
    }

    @Override
    public void registerOpenHandlerForType(int n, TIOpenHandlerInterface tIOpenHandlerInterface) {
        if (tIOpenHandlerInterface != null) {
            Integer n2 = n;
            if (this.mapTypeToOpenHandler.containsKey(n2)) {
                this.mapTypeToOpenHandler.remove(n2);
            }
            this.mapTypeToOpenHandler.put(n2, tIOpenHandlerInterface);
        }
    }

    @Override
    public TIOpenHandlerInterface getOpenHandlerForType(int n) {
        return this.mapTypeToOpenHandler.get(n);
    }

    @Override
    public void setDeviceExplorerProductMode(DeviceExplorerInterface.ProductMode productMode) {
        this.productMode = productMode;
    }

    @Override
    public DeviceExplorerInterface.ProductMode getDeviceExplorerProductMode() {
        return this.productMode;
    }

    @Override
    public void commonSendFilesToTIDevicesDropHandler(final List<File> list, final List<TIDevice> list2) {
        if (PlatformManager.isMac()) {
            final Stage stage = HiddenDialog.createPixelOffScreenDialog(CommonConstants.MAIN_STAGE);
            stage.show();
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException var1_1) {
                        TILogger.logError(LOG_TAG, var1_1.getMessage(), var1_1);
                    }
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            stage.close();
                            DeviceExplorerController.this.sendFilesToTIDevices(list, list2, null);
                        }
                    });
                }

            }).start();
        } else {
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    DeviceExplorerController.this.sendFilesToTIDevices(list, list2, null);
                }
            });
        }
    }

}

