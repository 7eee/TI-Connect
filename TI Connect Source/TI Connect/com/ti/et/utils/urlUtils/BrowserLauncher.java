/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.urlUtils;

import com.ti.et.utils.platformUtils.PlatformManager;
import java.lang.reflect.Method;

public class BrowserLauncher {
    public static void openURL(String string) throws Exception {
        if (PlatformManager.isMac()) {
            Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", String.class).invoke(null, string);
        } else if (PlatformManager.isWindows()) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + string);
        }
    }
}

