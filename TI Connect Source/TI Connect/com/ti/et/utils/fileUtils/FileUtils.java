/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.utils.fileUtils;

import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URL;
import java.net.URLDecoder;

public class FileUtils {
    private static final String LOG_TAG = FileUtils.class.getSimpleName();
    public static final int FILE_GOOD_TO_WRITE = 0;
    public static final int FILE_IS_NULL = -1;
    public static final int FILE_CANNOT_WRITE_TO_LOCATION = -2;
    public static final int MAX_BACKUPS = 5;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] readBytesFromFile(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
        byte[] arrby = new byte[(int)file.length()];
        try {
            randomAccessFile.read(arrby);
        }
        finally {
            randomAccessFile.close();
            if (PlatformManager.isWindows()) {
                randomAccessFile = null;
                System.gc();
            }
        }
        return arrby;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeBytesToFile(File file, byte[] arrby) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        try {
            randomAccessFile.write(arrby);
        }
        finally {
            randomAccessFile.close();
        }
    }

    public static long calculateChecksum(byte[] arrby) {
        long l = 0;
        for (int i = 0; i < arrby.length; ++i) {
            l += (long)(arrby[i] & 255);
        }
        return l;
    }

    public static boolean renameFileAsBackup(String string) {
        File file = new File(string);
        if (file.exists()) {
            return FileUtils.recursiveRenameFileAsBackup(file, 1);
        }
        return false;
    }

    private static boolean recursiveRenameFileAsBackup(File file, int n) {
        File file2;
        String string = file.getAbsolutePath();
        if (n > 1) {
            string = string.substring(0, string.lastIndexOf("."));
        }
        if ((file2 = new File(string + "." + n)).exists()) {
            if (n != 5) {
                FileUtils.recursiveRenameFileAsBackup(file2, ++n);
            }
            file2.delete();
        }
        return file.renameTo(file2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static StringBuilder readContentFromURL(URL uRL) {
        StringBuilder stringBuilder;
        stringBuilder = null;
        BufferedReader bufferedReader = null;
        try {
            if (uRL == null) {
                TILogger.logInfo(LOG_TAG, "Could not get URL");
            } else {
                String string;
                bufferedReader = new BufferedReader(new InputStreamReader(uRL.openStream(), "UTF-8"));
                stringBuilder = new StringBuilder();
                while ((string = bufferedReader.readLine()) != null) {
                    stringBuilder.append(string);
                }
            }
        }
        catch (IOException var3_5) {
            TILogger.logWarning(LOG_TAG, "Could not read content", var3_5);
            stringBuilder = null;
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException var3_6) {
                    TILogger.logWarning(LOG_TAG, "Error closing BufferedReader", var3_6);
                }
            }
        }
        return stringBuilder;
    }

    public static File fixFileIfInURLFormat(File file) {
        if (!FileUtils.isGoodReadPath(file)) {
            try {
                file = new File(URLDecoder.decode(file.getAbsolutePath(), "UTF-8"));
            }
            catch (Exception var1_1) {
                TILogger.logError(LOG_TAG, var1_1.getMessage(), var1_1);
                return null;
            }
        }
        return file;
    }

    public static boolean isGoodReadPath(File file) {
        if (file != null && file.exists() && file.canRead()) {
            return true;
        }
        return false;
    }

    public static int isGoodWritePath(File file) {
        if (file != null) {
            if (file.canWrite() || file.getParentFile().canWrite()) {
                return 0;
            }
            return -2;
        }
        return -1;
    }
}

