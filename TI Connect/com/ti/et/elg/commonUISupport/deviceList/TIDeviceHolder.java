/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.FloatProperty
 *  javafx.beans.property.SimpleBooleanProperty
 *  javafx.beans.property.SimpleFloatProperty
 *  javafx.beans.property.SimpleStringProperty
 *  javafx.beans.property.StringProperty
 *  javafx.scene.Node
 */
package com.ti.et.elg.commonUISupport.deviceList;

import com.ti.et.elg.commonUISupport.TransactionType;
import com.ti.et.elg.connectServer.exports.IOStreamInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import java.util.concurrent.locks.ReentrantLock;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;

public class TIDeviceHolder {
    private TIDevice device;
    private FloatProperty transactionProgress = new SimpleFloatProperty();
    private BooleanProperty progressBarEnabled = new SimpleBooleanProperty();
    private StringProperty transactionType = new SimpleStringProperty();
    private BooleanProperty screenCaptureEnabled = new SimpleBooleanProperty();
    private BooleanProperty dragOverAccepted = new SimpleBooleanProperty();
    private BooleanProperty cancelButtonEnabled = new SimpleBooleanProperty();
    private BooleanProperty isErrorReported = new SimpleBooleanProperty();
    private boolean isTransactionCanceled;
    private BooleanProperty distressDevice = new SimpleBooleanProperty();
    private boolean isFadeInAnimationPending = true;
    private boolean isFadeAnimationPending = true;
    private Node rootNode;
    private StringProperty idAvailable = new SimpleStringProperty();

    public TIDeviceHolder(TIDevice tIDevice) {
        this.device = tIDevice;
    }

    public TIDevice getTIDevice() {
        return this.device;
    }

    public String getNodeId() {
        return this.device.getNodeId();
    }

    public String getDeviceName() {
        return this.device.getDeviceName();
    }

    public IOStreamInterface getAltIOStream() {
        return this.device.getAltIOStream();
    }

    public ReentrantLock getLock() {
        return this.device.getLock();
    }

    public FloatProperty transactionProgressProperty() {
        return this.transactionProgress;
    }

    public void setTransactionProgress(float f) {
        this.transactionProgress.setValue((Number)Float.valueOf(f));
    }

    public float getTransactionProgress() {
        return this.transactionProgress.getValue().floatValue();
    }

    public BooleanProperty progressBarEnabledProperty() {
        return this.progressBarEnabled;
    }

    public void setProgressBarEnabled(boolean bl) {
        this.progressBarEnabled.setValue(Boolean.valueOf(bl));
    }

    public boolean isProgressBarEnabled() {
        return this.progressBarEnabled.getValue();
    }

    public StringProperty idAvailableProperty() {
        return this.idAvailable;
    }

    public StringProperty transactionTypeProperty() {
        return this.transactionType;
    }

    public void setIDAvailableProperty(String string) {
        this.idAvailable.setValue(string);
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType.setValue(transactionType.toString());
    }

    public BooleanProperty screenCaptureEnabledProperty() {
        return this.screenCaptureEnabled;
    }

    public void setScreenCaptureEnabled(boolean bl) {
        this.screenCaptureEnabled.setValue(Boolean.valueOf(bl));
    }

    public BooleanProperty dragOverAcceptedProperty() {
        return this.dragOverAccepted;
    }

    public void setDragOverAccepted(boolean bl) {
        this.dragOverAccepted.setValue(Boolean.valueOf(bl));
    }

    public BooleanProperty cancelButtonEnabledProperty() {
        return this.cancelButtonEnabled;
    }

    public void setCancelButtonEnabled(boolean bl) {
        this.cancelButtonEnabled.setValue(Boolean.valueOf(bl));
    }

    public void setTransactionCanceled(boolean bl) {
        this.isTransactionCanceled = bl;
    }

    public boolean hasBeenCanceled() {
        return this.isTransactionCanceled;
    }

    public BooleanProperty isErrorReportedProperty() {
        return this.isErrorReported;
    }

    public void setErrorReported(boolean bl) {
        this.isErrorReported.setValue(Boolean.valueOf(bl));
    }

    public BooleanProperty distressDeviceProperty() {
        return this.distressDevice;
    }

    public void setDistressDeviceProperty(boolean bl) {
        this.distressDevice.setValue(Boolean.valueOf(bl));
    }

    public boolean isFadeInAnimationPending() {
        return this.isFadeInAnimationPending;
    }

    public boolean isFadeAnimationPending() {
        return this.isFadeAnimationPending;
    }

    public void setFadeInAnimationPending(boolean bl) {
        this.isFadeInAnimationPending = bl;
    }

    public void setFadeAnimationPending(boolean bl) {
        this.isFadeAnimationPending = bl;
    }

    public Node getRootNode() {
        return this.rootNode;
    }

    public void setRootNode(Node node) {
        this.rootNode = node;
    }

    public boolean isSendOSTransferInProgress() {
        return this.device.isUnderOSUpdate() || this.transactionType.getValueSafe().equalsIgnoreCase(TransactionType.SEND_OS.toString());
    }
}

