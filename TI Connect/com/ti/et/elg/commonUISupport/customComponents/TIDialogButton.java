/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.control.Button
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 */
package com.ti.et.elg.commonUISupport.customComponents;

import com.ti.et.utils.platformUtils.PlatformManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TIDialogButton
extends Button {
    public TIDialogButton() {
        if (PlatformManager.isMac()) {
            final TIDialogButton tIDialogButton = this;
            tIDialogButton.setOnKeyPressed((EventHandler)new EventHandler<KeyEvent>(){

                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        keyEvent.consume();
                        tIDialogButton.fire();
                    }
                }
            });
        }
    }

}

