/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.animation.KeyFrame
 *  javafx.animation.KeyValue
 *  javafx.animation.Timeline
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.value.WritableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 *  javafx.scene.layout.StackPane
 *  javafx.util.Duration
 */
package com.ti.et.elg.commonUISupport.overlayPanes;

import com.ti.et.elg.connectServer.exports.TIDevice;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class OverlayStackPaneBase
extends StackPane {
    private HashMap<TIDevice, Node> panes = new HashMap();

    public void addPane(TIDevice tIDevice, Node node) {
        this.panes.put(tIDevice, node);
    }

    public boolean isOverlayPaneLoaded(TIDevice tIDevice) {
        if (this.panes.get(tIDevice) != null) {
            return true;
        }
        return false;
    }

    public boolean isNodeShown(TIDevice tIDevice) {
        if (this.getChildren().contains((Object)this.panes.get(tIDevice))) {
            return true;
        }
        return false;
    }

    public boolean isNodeShown(Node node) {
        if (this.getChildren().contains((Object)node)) {
            return true;
        }
        return false;
    }

    public void showPane(TIDevice tIDevice) {
        if (this.isOverlayPaneLoaded(tIDevice) && !this.isNodeShown(tIDevice)) {
            this.getChildren().add((Object)this.panes.get(tIDevice));
        }
    }

    public void showPane(Node node) {
        if (!this.isNodeShown(node)) {
            this.getChildren().add((Object)node);
        }
    }

    public void setPaneFadeIn(TIDevice tIDevice) {
        if (this.isOverlayPaneLoaded(tIDevice) && !this.isNodeShown(tIDevice)) {
            DoubleProperty doubleProperty = this.opacityProperty();
            this.setOpacity(0.0);
            this.getChildren().add((Object)this.panes.get(tIDevice));
            Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)0.0)}), new KeyFrame(new Duration(250.0), new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)1.0)})});
            timeline.play();
        }
    }

    public void hidePane() {
        if (!this.getChildren().isEmpty()) {
            this.getChildren().remove(this.getChildren().size() - 1);
        }
    }

    public void hidePane(TIDevice tIDevice) {
        if (this.isNodeShown(tIDevice)) {
            this.getChildren().remove((Object)this.panes.get(tIDevice));
        }
    }

    public void hidePane(Node node) {
        if (this.isNodeShown(node)) {
            this.getChildren().remove((Object)node);
        }
    }

    public void hidePaneFadeIn(TIDevice tIDevice) {
        if (this.isNodeShown(tIDevice)) {
            this.getChildren().remove((Object)this.panes.get(tIDevice));
            DoubleProperty doubleProperty = this.opacityProperty();
            this.setOpacity(0.1);
            Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)0.1)}), new KeyFrame(new Duration(250.0), new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)1.0)})});
            timeline.play();
        }
    }

    public void setRemovePane(TIDevice tIDevice) {
        if (this.isOverlayPaneLoaded(tIDevice) && !this.isNodeShown(tIDevice)) {
            if (!this.getChildren().isEmpty()) {
                this.getChildren().remove(this.getChildren().size() - 1);
                this.getChildren().add((Object)this.panes.get(tIDevice));
            } else {
                this.showPane(tIDevice);
            }
        }
    }

    public void setRemovePaneFadeIn(final TIDevice tIDevice) {
        if (this.isOverlayPaneLoaded(tIDevice) && !this.isNodeShown(tIDevice)) {
            if (!this.getChildren().isEmpty()) {
                final DoubleProperty doubleProperty = this.opacityProperty();
                Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)1.0)}), new KeyFrame(new Duration(2500.0), (EventHandler)new EventHandler<ActionEvent>(){

                    public void handle(ActionEvent actionEvent) {
                        OverlayStackPaneBase.this.getChildren().remove(OverlayStackPaneBase.this.getChildren().size() - 1);
                        OverlayStackPaneBase.this.getChildren().add(OverlayStackPaneBase.this.panes.get(tIDevice));
                        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.ZERO, new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)0.0)}), new KeyFrame(new Duration(2500.0), new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)1.0)})});
                        timeline.play();
                    }
                }, new KeyValue[]{new KeyValue((WritableValue)doubleProperty, (Object)0.0)})});
                timeline.play();
            } else {
                this.showPane(tIDevice);
            }
        }
    }

    public void unloadPane(TIDevice tIDevice) {
        this.panes.remove(tIDevice);
    }

    public Node getPane(TIDevice tIDevice) {
        return this.panes.get(tIDevice);
    }

}

