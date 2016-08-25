/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.exports.IOStreamInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TINode;
import com.ti.et.utils.logger.TILogger;
import java.util.concurrent.locks.ReentrantLock;

public class TIDeviceImpl
implements TIDevice {
    TINode node = null;
    IOStreamInterface altIOStream = null;
    private ReentrantLock lock = new ReentrantLock(true);
    private String connectionID;
    private String connectionIDforName = null;
    private int osVersionUpperByte;
    private int osVersionLowerByte;
    private int osPatchNum;
    private int osBuildNum;
    private int bootCodeUpperByte;
    private int bootCodeLowerByte;
    private int bootPatchNum;
    private int bootBuildNum;
    private int hardwareVersion;
    private int primaryLanguage;
    private String primaryLanguageString;
    private int freeRAM = 0;
    private int freeFLASH = 0;
    private int totalRAM = 0;
    private int totalFLASH = 0;
    private boolean OSpresent = true;
    private boolean isUnderOSUpdate = false;
    private int deviceHeight = 0;
    private int deviceWidth = 0;
    private int deviceBitsPerPixel = 0;
    private int productID;
    private boolean exactMathCapability = false;
    private boolean disconnected = false;

    public TIDeviceImpl(TINode tINode) {
        this.node = tINode;
    }

    public TIDeviceImpl(IOStreamInterface iOStreamInterface) {
        this.altIOStream = iOStreamInterface;
    }

    public String toString() {
        String string = this.getConnectionIDforName();
        if (string == null || string.length() == 0) {
            return "TIDevice[NULL OR EMPTY ID]";
        }
        if (string.equalsIgnoreCase("FFFF")) {
            return "TIDevice[ELG WITH NO ID]";
        }
        return "TIDevice[" + string + "]";
    }

    @Override
    public IOStreamInterface getAltIOStream() {
        return this.altIOStream;
    }

    @Override
    public ReentrantLock getLock() {
        return this.lock;
    }

    @Override
    public String getNodeId() {
        if (this.node != null) {
            return this.node.getNodeId();
        }
        return null;
    }

    @Override
    public String getDeviceName() {
        if (this.node != null) {
            return this.node.getDeviceName();
        }
        return null;
    }

    @Override
    public int setDeviceInformation(int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, int n8, int n9, int n10, int n11, int n12, int n13, int n14, int n15, int n16, int n17, boolean bl2) {
        TILogger.logInfo("TIDeviceImpl", "setDeviceInformation:\nosVersionUpperByte=" + n + "\nosVersionLowerByte" + n2 + "\nbootCodeUpperByte=" + n3 + "\nbootCodeLowerByte=" + n4 + "\nhardwareVersion=" + n5 + "\nprimaryLanguage=" + n6 + "\nfreeRAM=" + this.freeRAM + "\ntotalRAM=" + n11 + "\nfreeFLASH=" + n7 + "\ntotalFLASH=" + n12 + "\nisOSpresent=" + bl + "\ndeviceNumRows=" + n8 + "\ndeviceNumCols=" + n9 + "\ndeviceBitsPerPixel=" + n10 + "\nosPatchNum=" + n13 + "\nbootPatchNum=" + n14 + "\nosBuildNum=" + n15 + "\nbootBuildNum=" + n16 + "\nproductID=" + n17 + "\nexactMathCapability=" + bl2);
        this.setOsVersionUpperByte(n);
        this.setOsVersionLowerByte(n2);
        this.setOsPatchNum(n13);
        this.setOsBuildNum(n15);
        this.setBootCodeUpperByte(n3);
        this.setBootCodeLowerByte(n4);
        this.setBootPatchNum(n14);
        this.setBootBuildNum(n16);
        this.setHardwareVersion(n5);
        this.setPrimaryLanguage(n6);
        this.mapLanguageIDtoString(this.getPrimaryLanguage());
        this.setTotalRAM(n11);
        this.setFreeFLASH(n7);
        this.setTotalFLASH(n12);
        this.setIsOSpresent(bl);
        this.setDeviceHeight(n8);
        this.setDeviceWidth(n9);
        this.setDeviceBitsPerPixel(n10);
        this.setProductID(n17);
        this.setExactMathCapability(bl2);
        return 0;
    }

    @Override
    public int setDeviceDynamicInformation(int n, int n2, int n3, int n4, int n5) {
        TILogger.logInfo("TIDeviceImpl", "setDeviceDynamicInformation:\nprimaryLanguage=" + n + "\nfreeRAM=" + n2 + "\ntotalRAM=" + n3 + "\nfreeFLASH=" + n4 + "\ntotalFLASH=" + n5);
        this.setPrimaryLanguage(n);
        this.mapLanguageIDtoString(this.getPrimaryLanguage());
        this.setFreeRAM(n2);
        this.setTotalRAM(n3);
        this.setFreeFLASH(n4);
        this.setTotalFLASH(n5);
        return 0;
    }

    @Override
    public int setDeviceID(String string) {
        TILogger.logInfo("TIDeviceImpl", "setDeviceID:\nconnectionID=" + string);
        this.setConnectionID(string);
        if (this.getConnectionID().length() == 0) {
            this.setConnectionIDforName("");
        } else if (this.getConnectionID().length() >= 4) {
            this.setConnectionIDforName(string.substring(this.getConnectionID().length() - 4, this.getConnectionID().length()));
        }
        return 0;
    }

    private void mapLanguageIDtoString(int n) {
        switch (n) {
            case 0: {
                this.setPrimaryLanguageString("NEUTRAL");
                break;
            }
            case 1: {
                this.setPrimaryLanguageString("ARABIC");
                break;
            }
            case 2: {
                this.setPrimaryLanguageString("BULGARIAN");
                break;
            }
            case 3: {
                this.setPrimaryLanguageString("CATALAN");
                break;
            }
            case 4: {
                this.setPrimaryLanguageString("CHINESE");
                break;
            }
            case 5: {
                this.setPrimaryLanguageString("CZECH");
                break;
            }
            case 6: {
                this.setPrimaryLanguageString("DANISH");
                break;
            }
            case 7: {
                this.setPrimaryLanguageString("GERMAN");
                break;
            }
            case 8: {
                this.setPrimaryLanguageString("GREEK");
                break;
            }
            case 9: {
                this.setPrimaryLanguageString("ENGLISH");
                break;
            }
            case 10: {
                this.setPrimaryLanguageString("SPANISH");
                break;
            }
            case 11: {
                this.setPrimaryLanguageString("FINNISH");
                break;
            }
            case 12: {
                this.setPrimaryLanguageString("FRENCH");
                break;
            }
            case 13: {
                this.setPrimaryLanguageString("HEBREW");
                break;
            }
            case 14: {
                this.setPrimaryLanguageString("HUNGARIAN");
                break;
            }
            case 15: {
                this.setPrimaryLanguageString("ICELANDIC");
                break;
            }
            case 16: {
                this.setPrimaryLanguageString("ITALIAN");
                break;
            }
            case 17: {
                this.setPrimaryLanguageString("JAPANESE");
                break;
            }
            case 18: {
                this.setPrimaryLanguageString("KOREAN");
                break;
            }
            case 19: {
                this.setPrimaryLanguageString("DUTCH");
                break;
            }
            case 20: {
                this.setPrimaryLanguageString("NORWEGIAN");
                break;
            }
            case 21: {
                this.setPrimaryLanguageString("POLISH");
                break;
            }
            case 22: {
                this.setPrimaryLanguageString("PORTUGUESE");
                break;
            }
            case 24: {
                this.setPrimaryLanguageString("ROMANIAN");
                break;
            }
            case 25: {
                this.setPrimaryLanguageString("RUSSIAN");
                break;
            }
            case 26: {
                this.setPrimaryLanguageString("CROATIAN");
                break;
            }
            case 27: {
                this.setPrimaryLanguageString("SLOVAK");
                break;
            }
            case 28: {
                this.setPrimaryLanguageString("ALBANIAN");
                break;
            }
            case 29: {
                this.setPrimaryLanguageString("SWEDISH");
                break;
            }
            case 30: {
                this.setPrimaryLanguageString("THAI");
                break;
            }
            case 31: {
                this.setPrimaryLanguageString("TURKISH");
                break;
            }
            case 33: {
                this.setPrimaryLanguageString("INDONESIAN");
                break;
            }
            case 34: {
                this.setPrimaryLanguageString("UKRAINIAN");
                break;
            }
            case 35: {
                this.setPrimaryLanguageString("BYELORUSSIAN");
                break;
            }
            case 36: {
                this.setPrimaryLanguageString("SLOVENIAN");
                break;
            }
            case 37: {
                this.setPrimaryLanguageString("ESTONIAN");
                break;
            }
            case 38: {
                this.setPrimaryLanguageString("LATVIAN");
                break;
            }
            case 39: {
                this.setPrimaryLanguageString("LITHUANNIAN");
                break;
            }
            case 41: {
                this.setPrimaryLanguageString("FARSI");
                break;
            }
            case 45: {
                this.setPrimaryLanguageString("BASQUE");
                break;
            }
            case 54: {
                this.setPrimaryLanguageString("AFRIKAANS");
                break;
            }
            case 56: {
                this.setPrimaryLanguageString("FAEROESE");
                break;
            }
            default: {
                this.setPrimaryLanguageString("English");
            }
        }
    }

    @Override
    public String getConnectionID() {
        return this.connectionID;
    }

    private void setConnectionID(String string) {
        this.connectionID = string;
    }

    @Override
    public String getConnectionIDforName() {
        return this.connectionIDforName;
    }

    private void setConnectionIDforName(String string) {
        this.connectionIDforName = string;
    }

    @Override
    public int getOsVersionUpperByte() {
        return this.osVersionUpperByte;
    }

    private void setOsVersionUpperByte(int n) {
        this.osVersionUpperByte = n;
    }

    @Override
    public int getOsVersionLowerByte() {
        return this.osVersionLowerByte;
    }

    private void setOsVersionLowerByte(int n) {
        this.osVersionLowerByte = n;
    }

    @Override
    public int getBootCodeUpperByte() {
        return this.bootCodeUpperByte;
    }

    private void setBootCodeUpperByte(int n) {
        this.bootCodeUpperByte = n;
    }

    @Override
    public int getBootCodeLowerByte() {
        return this.bootCodeLowerByte;
    }

    private void setBootCodeLowerByte(int n) {
        this.bootCodeLowerByte = n;
    }

    @Override
    public int getHardwareVersion() {
        return this.hardwareVersion;
    }

    @Override
    public void setHardwareVersion(int n) {
        this.hardwareVersion = n;
    }

    @Override
    public int getPrimaryLanguage() {
        return this.primaryLanguage;
    }

    private void setPrimaryLanguage(int n) {
        this.primaryLanguage = n;
    }

    @Override
    public String getPrimaryLanguageString() {
        return this.primaryLanguageString;
    }

    private void setPrimaryLanguageString(String string) {
        this.primaryLanguageString = string;
    }

    @Override
    public int getFreeRAM() {
        return this.freeRAM;
    }

    private void setFreeRAM(int n) {
        this.freeRAM = n;
    }

    @Override
    public int getFreeFLASH() {
        return this.freeFLASH;
    }

    private void setFreeFLASH(int n) {
        this.freeFLASH = n;
    }

    @Override
    public boolean isOSpresent() {
        return this.OSpresent;
    }

    private void setIsOSpresent(boolean bl) {
        this.OSpresent = bl;
    }

    @Override
    public boolean isUnderOSUpdate() {
        return this.isUnderOSUpdate;
    }

    @Override
    public void setUnderOSUpdate(boolean bl) {
        this.isUnderOSUpdate = bl;
    }

    @Override
    public int getDeviceHeight() {
        return this.deviceHeight;
    }

    private void setDeviceHeight(int n) {
        this.deviceHeight = n;
    }

    @Override
    public int getDeviceWidth() {
        return this.deviceWidth;
    }

    private void setDeviceWidth(int n) {
        this.deviceWidth = n;
    }

    @Override
    public int getDeviceBitsPerPixel() {
        return this.deviceBitsPerPixel;
    }

    private void setDeviceBitsPerPixel(int n) {
        this.deviceBitsPerPixel = n;
    }

    @Override
    public int getTotalRAM() {
        return this.totalRAM;
    }

    private void setTotalRAM(int n) {
        this.totalRAM = n;
    }

    @Override
    public int getTotalFLASH() {
        return this.totalFLASH;
    }

    private void setTotalFLASH(int n) {
        this.totalFLASH = n;
    }

    @Override
    public int getOsPatchNum() {
        return this.osPatchNum;
    }

    private void setOsPatchNum(int n) {
        this.osPatchNum = n;
    }

    @Override
    public int getBootPatchNum() {
        return this.bootPatchNum;
    }

    private void setBootPatchNum(int n) {
        this.bootPatchNum = n;
    }

    @Override
    public int getOsBuildNum() {
        return this.osBuildNum;
    }

    private void setOsBuildNum(int n) {
        this.osBuildNum = n;
    }

    @Override
    public int getBootBuildNum() {
        return this.bootBuildNum;
    }

    private void setBootBuildNum(int n) {
        this.bootBuildNum = n;
    }

    private void setProductID(int n) {
        this.productID = n;
    }

    private void setExactMathCapability(boolean bl) {
        this.exactMathCapability = bl;
    }

    @Override
    public boolean getExactMathCapability() {
        return this.exactMathCapability;
    }

    @Override
    public int getProductID() {
        return this.productID;
    }

    @Override
    public TINode getNode() {
        return this.node;
    }

    @Override
    public void updateDBUSDeviceName(String string) {
        this.node.setNodeParams(this.node.getNodeId(), string);
    }

    @Override
    public boolean isDisconnected() {
        return this.disconnected;
    }

    @Override
    public void setDisconnected() {
        this.disconnected = true;
    }

    @Override
    public boolean isColorScreen() {
        int n = this.getProductID();
        if (n == 4 || n == 10 || n == 11 || n == 27 || this.getHardwareVersion() < 6) {
            return false;
        }
        return true;
    }
}

