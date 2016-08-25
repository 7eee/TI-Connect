/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.platformUtils;

import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class PlatformManager {
    private static final String ksHomeFolderPath = System.getProperty("user.home");
    private static String ksApplicationName;
    private static String sOS;
    private static String ksApplicationFolder;
    private static final String MAC_CONNECT_COMM_MANAGER = "/Library/Services/TI Connect Manager X.app/Contents/MacOS/TI Connect Manager X";
    private static final String MAC_32_BIT_KERNEL_MODE = "RELEASE_I386 i386";
    private static final String NON_VALID_CHARS = "[\\/:*?\"<>|\\\\]";
    private static final int timeOut = 5000;
    private static String shortAutoUpdateName;
    private static String AppHome;
    private static String ksApplicationVersion;

    public static boolean isMac() {
        return sOS.indexOf("mac") >= 0;
    }

    public static boolean isWindows() {
        return sOS.indexOf("win") >= 0;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isMacConnectManagerRunning() {
        if (!PlatformManager.isMac()) {
            return false;
        }
        try {
            int n;
            String string;
            Process process;
            Object object = process = Runtime.getRuntime().exec("ps ax");
            synchronized (object) {
                process.wait(5000);
            }
            object = process.getInputStream();
            byte[] arrby = new byte[1024];
            do {
                if ((n = PlatformManager.readLine((InputStream)object, arrby)) != 0) continue;
                return false;
            } while (!(string = new String(arrby, 0, n)).contains("/Library/Services/TI Connect Manager X.app/Contents/MacOS/TI Connect Manager X"));
            return true;
        }
        catch (Exception var0_1) {
            TILogger.logError("PlatformManager", "Exception checking if connect manager running", var0_1);
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isMac32BitModeKernelRunning() {
        if (!PlatformManager.isMac()) {
            return false;
        }
        try {
            Process process;
            Object object = process = Runtime.getRuntime().exec("uname -a");
            synchronized (object) {
                process.wait(5000);
            }
            object = process.getInputStream();
            byte[] arrby = new byte[1024];
            int n = PlatformManager.readLine((InputStream)object, arrby);
            if (n == 0) {
                return false;
            }
            String string = new String(arrby, 0, n);
            if (string.contains("RELEASE_I386 i386")) {
                return true;
            }
        }
        catch (Exception var0_1) {
            TILogger.logError("PlatformManager", var0_1.getMessage());
        }
        return false;
    }

    private static int readLine(InputStream inputStream, byte[] arrby) throws Exception {
        int n = 0;
        int n2 = inputStream.read();
        while (n2 != -1 && n2 != 10 && n < arrby.length) {
            arrby[n++] = (byte)n2;
            n2 = inputStream.read();
        }
        return n;
    }

    public static void init(String string, String string2) {
        ksApplicationName = string;
        ksApplicationFolder = System.getProperty("user.home") + File.separator;
        ksApplicationVersion = string2;
    }

    public static String getApplicationPreferencesFolder() {
        String string = PlatformManager.getPreferencesFolder() + File.separatorChar + "Texas Instruments" + File.separatorChar + ksApplicationName + File.separatorChar;
        File file = new File(string);
        if (!file.exists()) {
            file.mkdirs();
        }
        return string;
    }

    public static String getApplicationLogsFolder() {
        String string = PlatformManager.getLogsFolder() + File.separatorChar + "Texas Instruments" + File.separatorChar + ksApplicationName + File.separatorChar;
        File file = new File(string);
        if (!file.exists()) {
            file.mkdirs();
        }
        return string;
    }

    public static String getApplicationUnitestLogsFolder() {
        String string = PlatformManager.getApplicationLogsFolder() + File.separator + "unitestLogs" + File.separator;
        File file = new File(string);
        if (!file.exists()) {
            file.mkdirs();
        }
        return string;
    }

    public static String getLogsFolder() {
        String string = null;
        string = PlatformManager.isMac() ? ksHomeFolderPath + "/Library/Logs" : PlatformManager.getPreferencesFolder();
        return string;
    }

    public static String getPreferencesFolder() {
        String string = null;
        if (PlatformManager.isMac()) {
            string = ksHomeFolderPath + "/Library/Preferences";
        } else {
            string = System.getenv("AppData");
            if (null == string) {
                string = ksHomeFolderPath + "\\Application Data";
            }
        }
        return string;
    }

    public static String getTempFolder() {
        String string = System.getProperty("java.io.tmpdir");
        if (!string.endsWith(File.separator)) {
            string = string + File.separator;
        }
        if (PlatformManager.isWindows()) {
            string = string + "Texas Instruments" + File.separator + ksApplicationName + File.separator;
        }
        return string;
    }

    public static String getDocsFolder() {
        return ksApplicationFolder;
    }

    public static String getNonValidChars() {
        return "[\\/:*?\"<>|\\\\]";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static final String getAppHome() {
        if (AppHome != null) {
            return AppHome;
        }
        String string = System.getProperty("ticonnect.app.home");
        if (string == null) {
            try {
                ProtectionDomain protectionDomain = PlatformManager.class.getProtectionDomain();
                CodeSource codeSource = protectionDomain.getCodeSource();
                URL uRL = codeSource.getLocation();
                String string2 = uRL.getPath();
                try {
                    string2 = URLDecoder.decode(uRL.getPath(), "UTF-8");
                }
                catch (Exception var5_6) {
                    TILogger.logError(PlatformManager.class.getSimpleName(), "getAppHome", var5_6);
                }
                File file = new File(string2).getParentFile().getParentFile();
                if (PlatformManager.isMac() && "Contents".equals(file.getName())) {
                    file = new File(file, "Resources");
                }
                string = file.getAbsolutePath() + File.separatorChar;
            }
            catch (Exception var1_2) {
                TILogger.logError("PlatformManager", "In this parallel universe UTF-8 doesn't exist", var1_2);
            }
            finally {
                if (string == null) {
                    string = "./";
                }
            }
        }
        AppHome = string = new File(string).getAbsolutePath() + File.separator;
        return string;
    }

    public static String getApplicationName() {
        return ksApplicationName;
    }

    public static void setUpdateName(String string) {
        shortAutoUpdateName = string;
    }

    public static String getUpdateName() {
        return shortAutoUpdateName;
    }

    public static String getProductVersion() {
        return ksApplicationVersion;
    }

    static {
        sOS = System.getProperty("os.name").toLowerCase();
        shortAutoUpdateName = null;
    }
}

