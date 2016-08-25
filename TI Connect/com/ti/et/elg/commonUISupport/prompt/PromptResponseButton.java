/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.stage.Stage
 */
package com.ti.et.elg.commonUISupport.prompt;

import com.ti.et.elg.commonUISupport.customComponents.TIDialogButton;
import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Stage;

class PromptResponseButton
extends TIDialogButton {
    int response = -1;
    PromptResult promptResult;
    Stage dialogStage;
    boolean useModifiedResponse = false;
    int modifiedResponse = -1;

    public PromptResponseButton(String string, int n, PromptResult promptResult, Stage stage) {
        this.setText(string);
        this.setId("butnPromptResponse");
        this.response = n;
        this.promptResult = promptResult;
        this.dialogStage = stage;
        this.setOnAction((EventHandler)new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                PromptResponseButton.this.dialogStage.close();
                PromptResponseButton.this.promptResult.setResult(PromptResponseButton.this.useModifiedResponse ? PromptResponseButton.this.modifiedResponse : PromptResponseButton.this.response);
            }
        });
    }

    public void setModifiedResponse(int n) {
        this.modifiedResponse = n;
    }

    public void useModifiedResponse(boolean bl) {
        this.useModifiedResponse = bl;
    }

}

