/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.commonUISupport.prompt;

public class PromptResult {
    int result;

    public PromptResult(int n) {
        this.result = n;
    }

    public synchronized int getResult() {
        while (this.result == -1) {
            try {
                this.wait();
            }
            catch (InterruptedException var1_1) {}
        }
        return this.result;
    }

    public synchronized int getResultNoWait() {
        return this.result;
    }

    public synchronized void setResult(int n) {
        this.result = n;
        this.notifyAll();
    }
}

