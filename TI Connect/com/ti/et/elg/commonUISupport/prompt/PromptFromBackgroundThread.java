/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.BooleanProperty
 *  javafx.beans.property.SimpleBooleanProperty
 */
package com.ti.et.elg.commonUISupport.prompt;

import com.ti.et.elg.commonUISupport.prompt.PromptResult;
import java.util.concurrent.locks.ReentrantLock;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class PromptFromBackgroundThread {
    private static final ReentrantLock oneAtATimeLock = new ReentrantLock(true);
    private static final BooleanProperty hidePromptsWhenClosing = new SimpleBooleanProperty(false);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int promptAndWaitForResult() {
        int n;
        oneAtATimeLock.lock();
        try {
            final PromptResult promptResult = new PromptResult(-1);
            if (hidePromptsWhenClosing.get()) {
                promptResult.setResult(0);
            } else {
                this.runOnUIThread(new Runnable(){

                    @Override
                    public void run() {
                        PromptFromBackgroundThread.this.showPrompt(promptResult);
                    }
                });
            }
            n = promptResult.getResult();
        }
        finally {
            oneAtATimeLock.unlock();
        }
        return n;
    }

    public abstract void showPrompt(PromptResult var1);

    private void runOnUIThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater((Runnable)runnable);
        }
    }

    public static void setAppClosingProperty(boolean bl) {
        hidePromptsWhenClosing.set(bl);
    }

}

