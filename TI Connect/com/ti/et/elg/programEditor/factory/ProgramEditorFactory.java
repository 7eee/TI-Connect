/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.programEditor.factory;

import com.ti.et.elg.commonUISupport.overlayPanes.IDOverlappingPanelsType;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.programEditor.ProgramEditorCatalog;
import com.ti.et.elg.programEditor.ProgramEditorContents;
import com.ti.et.elg.programEditor.ProgramEditorController;
import com.ti.et.elg.programEditor.ProgramEditorToolbar;
import com.ti.et.elg.programEditor.VarsTransactionOverlayController;
import com.ti.et.elg.programEditor.dataProcessing.ProgramDataProcessing;
import com.ti.et.elg.programEditor.exports.OverlayForDeviceInterface;
import com.ti.et.elg.programEditor.exports.ProgramDataProcessingInterface;
import com.ti.et.elg.programEditor.exports.ProgramEditorCatalogInterface;
import com.ti.et.elg.programEditor.exports.ProgramEditorContentsInterface;
import com.ti.et.elg.programEditor.exports.ProgramEditorInterface;
import com.ti.et.elg.programEditor.exports.ProgramEditorToolbarInterface;
import java.util.HashMap;

public class ProgramEditorFactory {
    private static ProgramEditorInterface programEditor;
    private static ProgramEditorToolbarInterface programEditorToolbar;
    private static ProgramEditorContentsInterface programEditorContents;
    private static ProgramEditorCatalogInterface programEditorCatalog;
    private static ProgramDataProcessingInterface programDataProcessing;
    private static HashMap<TIDevice, OverlayForDeviceInterface> overlayPanes;

    private ProgramEditorFactory() {
    }

    public static void configureFactory() {
        programEditor = new ProgramEditorController();
        programEditorToolbar = new ProgramEditorToolbar();
        programEditorContents = new ProgramEditorContents();
        programEditorCatalog = new ProgramEditorCatalog();
        programDataProcessing = new ProgramDataProcessing();
    }

    public static void configureFactory(ProgramEditorContentsInterface programEditorContentsInterface) {
        programEditor = new ProgramEditorController();
        programEditorToolbar = new ProgramEditorToolbar();
        programEditorContents = programEditorContentsInterface;
        programEditorCatalog = new ProgramEditorCatalog();
        programDataProcessing = new ProgramDataProcessing();
    }

    public static ProgramEditorInterface getProgramEditor() {
        return programEditor;
    }

    public static ProgramEditorToolbarInterface getProgramEditorToolbar() {
        return programEditorToolbar;
    }

    public static ProgramEditorContentsInterface getProgramEditorContents() {
        return programEditorContents;
    }

    public static ProgramEditorCatalogInterface getProgramEditorCatalog() {
        return programEditorCatalog;
    }

    public static ProgramDataProcessingInterface getProgramDataProcessing() {
        return programDataProcessing;
    }

    public static void loadOverlayPane(TIDevice tIDevice) {
        VarsTransactionOverlayController varsTransactionOverlayController = new VarsTransactionOverlayController(IDOverlappingPanelsType.RECEIVING);
        ProgramEditorFactory.loadOverlayPane(tIDevice, varsTransactionOverlayController);
    }

    public static void loadOverlayPane(TIDevice tIDevice, OverlayForDeviceInterface overlayForDeviceInterface) {
        ProgramEditorFactory.addOverlayPanes(tIDevice, overlayForDeviceInterface);
        overlayPanes.get(tIDevice).loadPane(tIDevice);
        overlayPanes.get(tIDevice).showPane(tIDevice);
    }

    public static void addOverlayPanes(TIDevice tIDevice, OverlayForDeviceInterface overlayForDeviceInterface) {
        overlayPanes.put(tIDevice, overlayForDeviceInterface);
    }

    public static void unloadOverlayPane(TIDevice tIDevice) {
        if (tIDevice != null && ProgramEditorFactory.isOverlayPaneLoaded(tIDevice)) {
            overlayPanes.get(tIDevice).unloadPane(tIDevice);
            overlayPanes.remove(tIDevice);
        }
    }

    public static OverlayForDeviceInterface getOverlayPane(TIDevice tIDevice) {
        return overlayPanes.get(tIDevice);
    }

    public static boolean isOverlayPaneLoaded(TIDevice tIDevice) {
        if (overlayPanes.get(tIDevice) != null) {
            return true;
        }
        return false;
    }

    static {
        overlayPanes = new HashMap();
    }
}

