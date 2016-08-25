/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.analytics;

import com.ti.et.analytics.IAnalyticsData;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public abstract class AbstractAnalyticsData
implements IAnalyticsData {
    private static final String EMPTY_STRING = "EmptyString";

    @Override
    public Boolean isShutDownRequest() {
        return false;
    }

    protected String getURLEncoding(String string) {
        String string2 = "EmptyString";
        try {
            if (string != null) {
                string2 = URLEncoder.encode(string, "UTF-8");
            }
        }
        catch (UnsupportedEncodingException var3_3) {
            string2 = "URLEncodingFailed for " + string;
        }
        return string2;
    }
}

