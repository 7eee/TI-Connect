/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.collections.ObservableList
 */
package com.ti.et.elg.commonUISupport.sendToHandheldDialog;

import com.ti.et.elg.commonUISupport.sendToHandheldDialog.TIDeviceData;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDeviceConnectionListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

public class SendToHHDialogConnectionListener
implements TIDeviceConnectionListener {
    private ObservableList<TIDeviceData> deviceDataList;
    private boolean selectNewDevices = false;

    public SendToHHDialogConnectionListener(ObservableList<TIDeviceData> observableList) {
        this.deviceDataList = observableList;
    }

    @Override
    public void deviceConnected(final TIDevice tIDevice) {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                TIDeviceData tIDeviceData = new TIDeviceData(tIDevice);
                ConnectServerFactory.getCommManager().getIDDeviceInfo(tIDevice);
                SendToHHDialogConnectionListener.this.deviceDataList.add((Object)tIDeviceData);
                tIDeviceData.selectedProperty().set(SendToHHDialogConnectionListener.this.selectNewDevices);
            }
        });
    }

    @Override
    public void deviceDisconnected(final TIDevice tIDevice) {
        Platform.runLater((Runnable)new Runnable(){

            @Override
            public void run() {
                TIDeviceData tIDeviceData = SendToHHDialogConnectionListener.this.findDeviceData(tIDevice);
                if (tIDeviceData != null) {
                    tIDeviceData.selectedProperty().set(false);
                    SendToHHDialogConnectionListener.this.deviceDataList.remove((Object)tIDeviceData);
                }
            }
        });
    }

    private TIDeviceData findDeviceData(TIDevice tIDevice) {
        for (TIDeviceData tIDeviceData : this.deviceDataList) {
            if (tIDeviceData.getTIDevice() != tIDevice) continue;
            return tIDeviceData;
        }
        return null;
    }

    public void setSelectNewDevices(boolean bl) {
        this.selectNewDevices = bl;
    }

}

