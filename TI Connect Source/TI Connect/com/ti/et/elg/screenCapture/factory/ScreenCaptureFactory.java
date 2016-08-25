/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.screenCapture.factory;

import com.ti.et.elg.commonUISupport.overlayPanes.OverlayBaseInterface;
import com.ti.et.elg.deviceExplorer.NothingConnectedOverlayController;
import com.ti.et.elg.deviceExplorer.exports.DeviceListInterface;
import com.ti.et.elg.deviceExplorer.factory.DeviceExplorerFactory;
import com.ti.et.elg.screenCapture.NoScreenCapturePresentOverlayController;
import com.ti.et.elg.screenCapture.ScreenCaptureContainerController;
import com.ti.et.elg.screenCapture.ScreenCaptureController;
import com.ti.et.elg.screenCapture.ScreenCaptureToolbar;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureToolbarInterface;
import com.ti.et.elg.screenCapture.exports.ScreenContentsInterface;
import com.ti.et.elg.screenCapture.mock.ScreenCaptureToolbarMock;

public class ScreenCaptureFactory {
    private static DeviceListInterface deviceList;
    private static ScreenContentsInterface screensContainer;
    private static ScreenCaptureInterface screenCapture;
    private static ScreenCaptureToolbarInterface screenCaptureToolbar;
    private static OverlayBaseInterface noScreenCapturePresentPane;
    private static OverlayBaseInterface nothingConnectedPane;
    private static boolean isMockAllowed;

    private ScreenCaptureFactory() {
    }

    public static void configureFactory(ScreenCaptureInterface screenCaptureInterface, DeviceListInterface deviceListInterface, ScreenContentsInterface screenContentsInterface, ScreenCaptureToolbarInterface screenCaptureToolbarInterface, OverlayBaseInterface overlayBaseInterface, OverlayBaseInterface overlayBaseInterface2) {
        screenCapture = screenCaptureInterface;
        deviceList = deviceListInterface;
        screensContainer = screenContentsInterface;
        screenCaptureToolbar = screenCaptureToolbarInterface;
        noScreenCapturePresentPane = overlayBaseInterface;
        nothingConnectedPane = overlayBaseInterface2;
    }

    public static void configureFactory() {
        ScreenCaptureController screenCaptureController = new ScreenCaptureController();
        ScreenCaptureContainerController screenCaptureContainerController = new ScreenCaptureContainerController();
        ScreenCaptureToolbarInterface screenCaptureToolbarInterface = null;
        screenCaptureToolbarInterface = isMockAllowed ? new ScreenCaptureToolbarMock() : new ScreenCaptureToolbar();
        DeviceListInterface deviceListInterface = DeviceExplorerFactory.getDeviceList();
        NoScreenCapturePresentOverlayController noScreenCapturePresentOverlayController = new NoScreenCapturePresentOverlayController();
        NothingConnectedOverlayController nothingConnectedOverlayController = new NothingConnectedOverlayController();
        ScreenCaptureFactory.configureFactory(screenCaptureController, deviceListInterface, screenCaptureContainerController, screenCaptureToolbarInterface, noScreenCapturePresentOverlayController, nothingConnectedOverlayController);
    }

    public static ScreenCaptureInterface getScreenCapture() {
        return screenCapture;
    }

    public static ScreenCaptureToolbarInterface getScreenCaptureToolbar() {
        return screenCaptureToolbar;
    }

    public static DeviceListInterface getDeviceList() {
        return deviceList;
    }

    public static ScreenContentsInterface getScreenCaptureContainer() {
        return screensContainer;
    }

    public static OverlayBaseInterface getNoScreenCapturePresentPane() {
        return noScreenCapturePresentPane;
    }

    public static OverlayBaseInterface getNothingConnectedPane() {
        return nothingConnectedPane;
    }

    public static void allowMock(boolean bl) {
        isMockAllowed = bl;
    }

    static {
        isMockAllowed = false;
    }
}

