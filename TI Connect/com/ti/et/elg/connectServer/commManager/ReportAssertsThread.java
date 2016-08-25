/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import java.io.PrintStream;

abstract class ReportAssertsThread
extends Thread {
    String failMessage;
    private static String summary = "\n\n*****Test Results Summary*****\n";
    String deviceID;

    public ReportAssertsThread(String string) {
        this.deviceID = string;
    }

    abstract void myRun();

    @Override
    public void run() {
        try {
            this.myRun();
            ReportAssertsThread.appendToSummary("Final Result for [" + this.deviceID + "] is SUCCESS\n");
        }
        catch (Throwable var1_1) {
            this.failMessage = var1_1.getMessage();
            if (this.failMessage == null) {
                this.failMessage = "Assertion or other exception occured!";
            }
            System.err.println(this.failMessage);
            ReportAssertsThread.appendToSummary("Final Result for [" + this.deviceID + "] is FAIL: " + this.failMessage + "\n");
        }
    }

    static synchronized void appendToSummary(String string) {
        summary = summary + string;
    }

    static synchronized String getSummary() {
        return summary;
    }
}

