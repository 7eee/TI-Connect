/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.IOStreamInterface;
import com.ti.et.elg.connectServer.exports.TINode;
import java.util.concurrent.locks.ReentrantLock;

public interface TIDevice {
    public static final int PRODUCT_HV_TI83PLUS = 1;
    public static final int PRODUCT_HV_TI83PLUS_SILVER = 2;
    public static final int PRODUCT_HV_TI84PLUS = 3;
    public static final int PRODUCT_HV_TI84PLUS_SILVER = 4;
    public static final int PRODUCT_HV_NSP84PLUS_SILVER = 5;
    public static final int PRODUCT_HV_TI84PLUS_C_SILVER = 6;
    public static final int PRODUCT_HV_TI83PLUS_PREMIUM_CE = 8;
    public static final int TI83_DEVICE = 131;
    public static final int TI83PLUS_FAMILY_DEVICE = 115;
    public static final int UNSUPPORTED = -1;
    public static final int PID_TI_NOPRODUCT = 0;
    public static final int PID_TI83PLUS_PRODUCT = 4;
    public static final int PID_TI84PLUS_PRODUCT = 10;
    public static final int PID_TI82ADVANCED_PRODUCT = 11;
    public static final int PID_TI84PLUS_COLOR_PRODUCT = 15;
    public static final int PID_TI83PLUS_PREMIUM_CE = 19;
    public static final int PID_TI84PLUS_T_PRODUCT = 27;
    public static final int PRODUCT_VERSION_TI84PLUS_BNW = 0;
    public static final int PRODUCT_VERSION_TI84PLUS_COLOR = 10;
    public static final int PRODUCT_VERSION_TI83PREMIUM = 16;
    public static final int TOTAL_RAM_TI83PLUS_TI84PLUS_FAM = 131072;
    public static final int TOTAL_FLASH_TI84PLUS = 1048576;
    public static final int TOTAL_FLASH_TI84PLUS_SILVER_EDITION = 2097152;
    public static final int TOTAL_FLASH_TI84PLUS_COLOR_SE = 4194304;

    public String getNodeId();

    public String getDeviceName();

    public IOStreamInterface getAltIOStream();

    public ReentrantLock getLock();

    public int setDeviceInformation(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16, int var17, int var18, boolean var19);

    public int setDeviceDynamicInformation(int var1, int var2, int var3, int var4, int var5);

    public int setDeviceID(String var1);

    public String getConnectionID();

    public String getConnectionIDforName();

    public int getOsPatchNum();

    public int getOsBuildNum();

    public int getOsVersionUpperByte();

    public int getOsVersionLowerByte();

    public int getBootPatchNum();

    public int getBootBuildNum();

    public int getBootCodeUpperByte();

    public int getBootCodeLowerByte();

    public int getHardwareVersion();

    public void setHardwareVersion(int var1);

    public int getPrimaryLanguage();

    public String getPrimaryLanguageString();

    public int getFreeRAM();

    public int getTotalRAM();

    public int getFreeFLASH();

    public int getTotalFLASH();

    public boolean isOSpresent();

    public boolean isUnderOSUpdate();

    public void setUnderOSUpdate(boolean var1);

    public int getDeviceHeight();

    public int getDeviceWidth();

    public int getDeviceBitsPerPixel();

    public int getProductID();

    public boolean getExactMathCapability();

    public TINode getNode();

    public void updateDBUSDeviceName(String var1);

    public void setDisconnected();

    public boolean isDisconnected();

    public boolean isColorScreen();
}

