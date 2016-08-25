/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.Node
 *  javafx.scene.Scene
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyCodeCombination
 *  javafx.scene.input.KeyCodeCombinationBuilder
 *  javafx.scene.input.KeyCombination
 *  javafx.scene.input.KeyCombination$ModifierValue
 *  javafx.scene.input.KeyEvent
 */
package com.ti.et.elg.commonUISupport.UINavigation;

import com.ti.et.elg.commonUISupport.UINavigation.ChildFocusLostListener;
import com.ti.et.elg.commonUISupport.UINavigation.UINavigator;
import com.ti.et.elg.commonUISupport.utils.FXNodeUtils;
import com.ti.et.utils.logger.TILogger;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCodeCombinationBuilder;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class UIKeyNavigationEventHandler
implements EventHandler<KeyEvent>,
UINavigator,
ChangeListener<Node> {
    private final List<Node> cycleList = Collections.synchronizedList(new ArrayList());
    private int idx = 0;
    private static final String TAG = UIKeyNavigationEventHandler.class.getSimpleName();
    private Set<KeyCodeCombination> forwardTriggerKeys = new HashSet<KeyCodeCombination>();
    private Set<KeyCodeCombination> backwardTriggerKeys = new HashSet<KeyCodeCombination>();
    private KeyCodeCombinationBuilder kComboBuilder = KeyCodeCombinationBuilder.create();
    private Scene mainScene = null;
    private Node justFocusedNode = null;
    private boolean enabled = true;
    private WeakReference<Node> lastNodeRef = null;

    public boolean addForwardTriggerKeyCode(KeyCodeCombination keyCodeCombination) {
        if (null != keyCodeCombination) {
            return this.forwardTriggerKeys.add(keyCodeCombination);
        }
        return false;
    }

    public boolean addBackwardTriggerKeyCode(KeyCodeCombination keyCodeCombination) {
        if (null != keyCodeCombination) {
            return this.backwardTriggerKeys.add(keyCodeCombination);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setFocus(int n) {
        Node node = this.justFocusedNode;
        try {
            this.justFocusedNode = this.cycleList.get(n);
            this.justFocusedNode.requestFocus();
        }
        finally {
            this.justFocusedNode = node;
        }
    }

    public void handle(KeyEvent keyEvent) {
        if (this.enabled && null != keyEvent && !keyEvent.isConsumed() && !keyEvent.getCode().isModifierKey()) {
            this.kComboBuilder.code(keyEvent.getCode());
            this.kComboBuilder.alt(keyEvent.isAltDown() ? KeyCombination.ModifierValue.DOWN : KeyCombination.ModifierValue.UP);
            this.kComboBuilder.shift(keyEvent.isShiftDown() ? KeyCombination.ModifierValue.DOWN : KeyCombination.ModifierValue.UP);
            this.kComboBuilder.control(keyEvent.isControlDown() ? KeyCombination.ModifierValue.DOWN : KeyCombination.ModifierValue.UP);
            this.kComboBuilder.meta(keyEvent.isMetaDown() ? KeyCombination.ModifierValue.DOWN : KeyCombination.ModifierValue.UP);
            this.kComboBuilder.shortcut(keyEvent.isShortcutDown() ? KeyCombination.ModifierValue.DOWN : KeyCombination.ModifierValue.UP);
            KeyCodeCombination keyCodeCombination = this.kComboBuilder.build();
            if (this.forwardTriggerKeys.contains((Object)keyCodeCombination)) {
                int n = (this.idx + 1) % this.cycleList.size();
                this.setFocus(n);
                this.idx = n;
                TILogger.logDetail(TAG, this.cycleList.get(n).getId() + " (" + n + "/" + this.cycleList.size() + ")");
                keyEvent.consume();
            } else if (this.backwardTriggerKeys.contains((Object)keyCodeCombination)) {
                int n = (this.cycleList.size() + (this.idx - 1) % this.cycleList.size()) % this.cycleList.size();
                this.setFocus(n);
                this.idx = n;
                TILogger.logDetail(TAG, this.cycleList.get(n).getId() + " (" + n + "/" + this.cycleList.size() + ")");
                keyEvent.consume();
            }
        }
    }

    @Override
    public boolean addNodeToNavCycle(Node node) {
        boolean bl = false;
        if (null != node && !this.cycleList.contains((Object)node)) {
            try {
                bl = this.cycleList.add(node);
                if (bl && this.mainScene != node.getScene()) {
                    node.getScene().focusOwnerProperty().addListener((ChangeListener)this);
                    this.mainScene = node.getScene();
                }
                TILogger.logDetail(TAG, "Added " + node.getClass().getName() + " " + node.getId());
            }
            catch (Exception var3_3) {
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public boolean removeNodeFromNavCycle(Node node) {
        boolean bl = false;
        if (null != node) {
            try {
                bl = this.cycleList.remove((Object)node);
                if (this.idx < 0 || this.idx > this.cycleList.size()) {
                    this.idx = 0;
                }
                TILogger.logDetail(TAG, "Removed " + node.getClass().getName() + " " + node.getId());
            }
            catch (Exception var3_3) {
                bl = false;
            }
        }
        return bl;
    }

    @Override
    public void startAtPos(int n) {
        int n2 = this.cycleList.size();
        this.idx = (n2 + n % n2) % n2;
        this.cycleList.get(this.idx).requestFocus();
    }

    @Override
    public int getListSize() {
        return this.cycleList.size();
    }

    public void changed(ObservableValue<? extends Node> observableValue, Node node, Node node2) {
        Node node3;
        Object[] arrobject;
        if (!this.enabled) {
            return;
        }
        if (this.lastNodeRef != null && (node3 = this.lastNodeRef.get()) != null && node3 != node) {
            this.lastNodeRef = null;
            this.changed(observableValue, node3, node2);
            return;
        }
        this.lastNodeRef = node2 != null ? new WeakReference<Node>(node2) : null;
        Node[] arrnode = new Node[]{null};
        if (node2 != node && node != null && !this.cycleList.contains((Object)node) && (arrobject = FXNodeUtils.getImmediateChildFocusLostAncestorListeners(arrnode, this.cycleList, node)) != null) {
            TILogger.logDetail(TAG, "Child of " + arrnode[0].getId() + " lost focus");
            for (Object object : arrobject) {
                ((ChildFocusLostListener)object).onChildFocusLost(arrnode[0], node, node2);
            }
        }
        if (this.cycleList.contains((Object)node2)) {
            TILogger.logDetail(TAG, "Focused Node " + node2.getId());
            this.startAtPos(this.cycleList.indexOf((Object)node2));
        }
    }

    @Override
    public int getCurrentPos() {
        return this.idx;
    }

    public boolean justFocused(Node node) {
        boolean bl = node != null && node == this.justFocusedNode;
        return bl;
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void setEnabled(boolean bl) {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public void fireFocusChange(Node node) {
        this.changed(null, node, node);
    }
}

