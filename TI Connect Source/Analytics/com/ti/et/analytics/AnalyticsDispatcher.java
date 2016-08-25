/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.AnalyticsCollector;
import com.ti.et.analytics.IAnalyticsData;
import com.ti.et.analytics.IAnalyticsDispatcher;
import com.ti.et.analytics.IAnalyticsTransport;
import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalyticsDispatcher
implements IAnalyticsDispatcher {
    private static final Logger LOGGER = Logger.getLogger(AnalyticsDispatcher.class.getCanonicalName());
    public Boolean keepSendingFlag = true;
    private BlockingQueue<IAnalyticsData> transferQueue = null;
    private IAnalyticsTransport transport = null;

    AnalyticsDispatcher(LinkedTransferQueue<IAnalyticsData> linkedTransferQueue) {
        this.transferQueue = linkedTransferQueue;
    }

    @Override
    public void initTransport(IAnalyticsTransport iAnalyticsTransport) {
        this.transport = iAnalyticsTransport;
    }

    @Override
    public void run() {
        IAnalyticsData iAnalyticsData = null;
        while (this.keepSendingFlag.booleanValue()) {
            try {
                iAnalyticsData = this.transferQueue.take();
                if (iAnalyticsData.isShutDownRequest().booleanValue()) {
                    this.keepSendingFlag = false;
                    AnalyticsCollector.shutdownComplete();
                } else {
                    this.sendGetToAnalyticsBackend(iAnalyticsData);
                }
            }
            catch (Exception var2_3) {
                LOGGER.log(Level.WARNING, "Analytics Dispatcher: " + var2_3.getMessage());
            }
            try {
                Thread.sleep(10);
            }
            catch (InterruptedException var2_4) {}
        }
    }

    private void sendGetToAnalyticsBackend(IAnalyticsData iAnalyticsData) {
        try {
            if (this.transport == null) {
                System.out.println("Dispatcher: Null Transport!!!");
            } else {
                this.transport.sendData(iAnalyticsData);
            }
        }
        catch (Exception var2_2) {
            LOGGER.log(Level.WARNING, "Analytics Dispatcher Error Sending To Transport: " + var2_2.getMessage());
        }
    }
}

