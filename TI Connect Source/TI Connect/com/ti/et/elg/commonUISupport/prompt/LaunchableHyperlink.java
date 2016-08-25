/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.scene.control.Hyperlink
 */
package com.ti.et.elg.commonUISupport.prompt;

import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.urlUtils.BrowserLauncher;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

public class LaunchableHyperlink
extends Hyperlink {
    public LaunchableHyperlink(final String string) {
        super(string);
        this.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                try {
                    BrowserLauncher.openURL(string);
                }
                catch (Exception var2_2) {
                    TILogger.logError("PromptDialog", "Unable to browse " + string, var2_2);
                }
            }
        });
    }

}

