/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.propertiesManager;

import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class UserPropertyManagement {
    private static Properties user_properties = null;
    private static final File PREF_FILE = new File(PlatformManager.getApplicationPreferencesFolder() + "preferences.properties");
    private static final String LOG_TAG = UserPropertyManagement.class.getSimpleName();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean loadUserProperties() {
        reference var0 = UserPropertyManagement.class;
        synchronized (UserPropertyManagement.class) {
            if (null == user_properties) {
                TILogger.logInfo(LOG_TAG, "Looking for the file: " + PREF_FILE.getAbsolutePath());
                user_properties = new Properties();
            }
            // ** MonitorExit[var0] (shouldn't be in output)
            try {
                if (PREF_FILE.exists() && PREF_FILE.canRead()) {
                    user_properties.load(new FileInputStream(PREF_FILE));
                } else {
                    TILogger.logDetail(LOG_TAG, "Preference file does not exist yet.");
                }
            }
            catch (Exception var0_1) {
                TILogger.logError(LOG_TAG, "Problem reading properties file: " + PREF_FILE.getAbsolutePath(), var0_1);
                return false;
            }
            return true;
        }
    }

    public static boolean storeUserProperties() {
        if (null == user_properties) {
            return false;
        }
        try {
            if (!PREF_FILE.exists() && !PREF_FILE.getParentFile().exists()) {
                if (!PREF_FILE.getParentFile().mkdirs()) {
                    return false;
                }
                if (!PREF_FILE.exists() && !PREF_FILE.createNewFile()) {
                    return false;
                }
            }
            user_properties.store(new FileOutputStream(PREF_FILE), null);
        }
        catch (Exception var0) {
            TILogger.logError(LOG_TAG, "Problem saving properties file: " + PREF_FILE.getAbsolutePath(), var0);
            return false;
        }
        return true;
    }

    public static void setUserProperty(String string, String string2) {
        user_properties.put(string, string2);
        UserPropertyManagement.storeUserProperties();
    }

    public static String getUserProperty(String string) {
        UserPropertyManagement.loadUserProperties();
        return (String)user_properties.get(string);
    }

    public static String getString(String string, String string2) {
        String string3 = UserPropertyManagement.getUserProperty(string);
        if (string3 == null) {
            string3 = string2;
        }
        return string3;
    }

    public static void setInteger(String string, int n) {
        UserPropertyManagement.setUserProperty(string, Integer.toString(n));
    }

    public static int getInteger(String string, int n) {
        int n2 = n;
        try {
            String string2 = UserPropertyManagement.getUserProperty(string);
            if (string2 != null) {
                n2 = Integer.parseInt(string2);
            }
        }
        catch (NumberFormatException var3_4) {
            TILogger.logError(LOG_TAG, "Reading integer from property file for " + string, var3_4);
        }
        return n2;
    }

    public static void setBoolean(String string, boolean bl) {
        UserPropertyManagement.setUserProperty(string, Boolean.toString(bl));
    }

    public static boolean getBoolean(String string, boolean bl) {
        boolean bl2 = bl;
        String string2 = UserPropertyManagement.getUserProperty(string);
        if (null != string2) {
            bl2 = Boolean.parseBoolean(string2);
        }
        return bl2;
    }

    public static void setDouble(String string, double d) {
        UserPropertyManagement.setUserProperty(string, Double.toString(d));
    }

    public static double getDouble(String string, double d) {
        double d2 = d;
        try {
            String string2 = UserPropertyManagement.getUserProperty(string);
            if (string2 != null && !string2.isEmpty()) {
                d2 = Double.parseDouble(string2);
            }
        }
        catch (Exception var5_4) {
            TILogger.logError(LOG_TAG, "Reading double from property file for " + string, var5_4);
        }
        return d2;
    }

    public static void setIntArray(String string, int[] arrn) {
        String string2 = "";
        for (int i = 0; i < arrn.length; ++i) {
            if (i > 0) {
                string2 = string2 + ",";
            }
            string2 = string2 + arrn[i];
        }
        UserPropertyManagement.setUserProperty(string, string2);
    }

    public static int[] getIntArray(String string) {
        String string2 = UserPropertyManagement.getUserProperty(string);
        if (null == string2) {
            return new int[0];
        }
        String[] arrstring = string2.split(",");
        int[] arrn = new int[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            arrn[i] = Integer.parseInt(arrstring[i]);
        }
        return arrn;
    }
}

