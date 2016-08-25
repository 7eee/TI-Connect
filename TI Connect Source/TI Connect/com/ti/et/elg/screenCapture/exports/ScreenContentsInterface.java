/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.BooleanProperty
 *  javafx.event.Event
 *  javafx.scene.Node
 *  javafx.scene.input.Clipboard
 */
package com.ti.et.elg.screenCapture.exports;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.overlayPanes.OverlayStackPaneBase;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.screenCapture.exports.ScreenCaptureInterface;
import com.ti.et.elg.screenCapture.exports.ScreenItemInterface;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.Clipboard;

public interface ScreenContentsInterface {
    public static final String DEFAULT_EXTENSION = "png";
    public static final String DEFAULT_SCREENCAPTURE_TITLE = "Capture";

    public Node getRootNode();

    public void init(ScreenCaptureInterface var1);

    public void createScreenCaptureItem(TIImage var1);

    public void deleteScreenCaptureItem(ScreenItemInterface var1);

    public List<ScreenItemInterface> getSelectedItems();

    public int countSelectedItems();

    public void changeViewScale(int var1);

    public void deleteSelectedScreenCaptureItems();

    public void copySelectedItemsToClipboard(Clipboard var1);

    public int saveSelectedItemsToDisk();

    public boolean hasUnsavedItems();

    public int saveUnsavedScreenCaptureItems();

    public int countUnsavedItems();

    public void sendSelectedItemsToHandhelds(Event var1);

    public void setSendToHHAction(TIAction var1);

    public void setCopyAction(TIAction var1);

    public void setSelectAllAction(TIAction var1);

    public void setSaveAsAction(TIAction var1);

    public void setDeleteAction(TIAction var1);

    public void set050ZoomAction(TIAction var1);

    public void set075ZoomAction(TIAction var1);

    public void set100ZoomAction(TIAction var1);

    public void set200ZoomAction(TIAction var1);

    public void set300ZoomAction(TIAction var1);

    public void set400ZoomAction(TIAction var1);

    public void selectItem(ScreenItemInterface var1);

    public void selectAllItems();

    public void unselectAllItems();

    public void shiftSelectItem(ScreenItemInterface var1);

    public void ctrlCmdSelectItem(ScreenItemInterface var1);

    public void rightClickedSelectItem(ScreenItemInterface var1);

    public boolean editTextTitleItem(ScreenItemInterface var1);

    public boolean isItemSelected(ScreenItemInterface var1);

    public void setParent(OverlayStackPaneBase var1);

    public void showPane(Node var1);

    public Node getFocusableNode();

    public void setScreenBorders(boolean var1);

    public BooleanProperty getBordersStateProperty();
}

