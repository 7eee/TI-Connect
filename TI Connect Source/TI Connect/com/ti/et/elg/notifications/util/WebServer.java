/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.notifications.util;

import com.ti.et.elg.notifications.AutomaticNotificationResourceBundle;
import com.ti.et.utils.resourceManager.TIResourceBundle;

public enum WebServer {
    TEST(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotification.URL.test")),
    DEVELOPMENT(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotification.URL.dev")),
    PRE_PRODUCTION(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotification.URL.preprod")),
    PRODUCTION(AutomaticNotificationResourceBundle.BUNDLE.getString("automaticNotification.URL.prod")),
    CUSTOM("http://127.0.0.1");
    
    private String serverURL;

    private WebServer(String string2) {
        this.setServerURL(string2);
    }

    public String getServerURL() {
        return this.serverURL;
    }

    public void setServerURL(String string) {
        this.serverURL = string;
    }
}

