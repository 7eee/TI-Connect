/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.FileSupport;
import com.ti.et.elg.connectServer.commManager.VarDataParser;
import com.ti.et.elg.connectServer.exports.Constants;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.fileUtils.FileUtils;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class TIVarImpl
implements TIVar {
    private static final String LOG_TAG = TIVarImpl.class.getSimpleName();
    public static final int UNICODE_CHARS_START = 61440;
    public static final int EXTRA_FORMATTING_BYTES = 74;
    public static final int EXTRA_FLASH_FORMATTING_BYTES = 78;
    private static final int BIT_SHIFT_16K_PAGE = 14;
    private static final int PAGE_ADDR_START = 16384;
    private static final byte HEX_FILE_BYTES_PER_LINE = 32;
    private static final String FILE_LINE_TERMINATOR = "\r\n";
    private static final int NUM_APP_PAD_BYTES = 27;
    private File hostFile = null;
    private byte[] data;
    private int dataSize;
    private String displayableNameUTF8 = null;
    private byte[] objectName;
    private int varVersion = 0;
    private int varDescriptor = -16711936;
    private int varType = -1;
    private boolean varFlagArchive = false;
    private int ownerProductID = 0;
    private int ownerCalculatorID = 0;
    private final int BUFFER_PREDATA_SIZE = 17;
    private boolean isFlashObject = false;
    private int revisionNumberMajor = 0;
    private int revisionNumberMinor = 0;
    private int fileFormatFlags = 0;
    private int objectCodeType = 0;
    private int revisionDateDay = 0;
    private int revisionDateMonth = 0;
    private int revisionDateYearMajor = 0;
    private int revisionDateYearMinor = 0;
    private int objectNameLength = 0;
    private byte[] deviceCodeDataTypePairs = null;
    private boolean isBinaryFormat = false;
    private int msgBlock2Length = 0;
    private boolean isMultiVarFile = false;
    private boolean isACorruptedVar = false;
    private boolean badCheckSum = false;
    private boolean overrideCheckSum = false;

    public TIVarImpl(String string, String string2, byte[] arrby, int n, int n2, int n3, int n4, boolean bl, int n5, int n6, boolean bl2) {
        this.setDeviceFileName(string2);
        this.setObjectName(arrby);
        this.dataSize = n;
        this.setVarDescriptor(n3);
        this.setVarType(n4);
        this.setVarFlagArchive(bl);
        this.setOwnerProductID(n5);
        this.setOwnerCalculatorID(n6);
        this.setFlashObject(bl2);
        this.setVarVersion(n2);
        if (string != null) {
            this.hostFile = new File(string);
            File file = FileUtils.fixFileIfInURLFormat(this.getHostFile());
            if (file == null) {
                this.setOwnerCalculatorID(-1);
                TILogger.logDetail(LOG_TAG, "Could not read from file, marking it as UNSUPPORTED");
                return;
            }
            this.hostFile = file;
        }
        if (n4 == -1) {
            this.loadData();
        } else {
            this.setHostFileExtensionPerType();
        }
    }

    public TIVarImpl(String string, boolean bl) {
        this.setOverrideCheckSum(bl);
        if (string != null) {
            this.hostFile = new File(string);
            File file = FileUtils.fixFileIfInURLFormat(this.getHostFile());
            if (file == null) {
                this.setOwnerCalculatorID(-1);
                TILogger.logDetail(LOG_TAG, "Could not read from file, marking it as UNSUPPORTED");
                return;
            }
            this.hostFile = file;
        }
        this.loadData();
    }

    public TIVarImpl(File file, byte[] arrby) {
        this.setHostFile(file);
        if (file != null) {
            File file2 = FileUtils.fixFileIfInURLFormat(this.getHostFile());
            if (file2 == null) {
                this.setOwnerCalculatorID(-1);
                TILogger.logDetail(LOG_TAG, "Could not read from file, marking it as UNSUPPORTED");
                return;
            }
            this.hostFile = file2;
        }
        if (arrby != null) {
            this.setData(arrby);
            this.setPropertiesFromDataAndStripFileFormating();
        } else {
            this.setOwnerCalculatorID(-1);
        }
    }

    @Override
    public TIVar clone() {
        try {
            TIVar tIVar = (TIVar)super.clone();
            return tIVar;
        }
        catch (CloneNotSupportedException var1_2) {
            TILogger.logError(LOG_TAG, "clone", var1_2);
            return null;
        }
    }

    public static boolean isVarAFlashObject(int n) {
        switch (n) {
            case 35: 
            case 36: 
            case 37: 
            case 39: 
            case 62: {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDeviceFileName() {
        return this.displayableNameUTF8;
    }

    @Override
    public byte[] getDisplayableNameBytes() {
        try {
            return this.displayableNameUTF8.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException var1_1) {
            TILogger.logError("TIVarImpl", "getDisplayableNameBytes", var1_1);
            return null;
        }
    }

    @Override
    public String getHostFileNameFromDeviceFileName() {
        StringBuilder stringBuilder = new StringBuilder(this.displayableNameUTF8.length());
        block15 : for (int i = 0; i < this.displayableNameUTF8.length(); ++i) {
            switch (this.displayableNameUTF8.charAt(i)) {
                case '\uf038': {
                    stringBuilder.append('T');
                    continue block15;
                }
                case '\u2080': {
                    stringBuilder.append('0');
                    continue block15;
                }
                case '\u2081': {
                    stringBuilder.append('1');
                    continue block15;
                }
                case '\u2082': {
                    stringBuilder.append('2');
                    continue block15;
                }
                case '\u2083': {
                    stringBuilder.append('3');
                    continue block15;
                }
                case '\u2084': {
                    stringBuilder.append('4');
                    continue block15;
                }
                case '\u2085': {
                    stringBuilder.append('5');
                    continue block15;
                }
                case '\u2086': {
                    stringBuilder.append('6');
                    continue block15;
                }
                case '\u2087': {
                    stringBuilder.append('7');
                    continue block15;
                }
                case '\u2088': {
                    stringBuilder.append('8');
                    continue block15;
                }
                case '\u2089': {
                    stringBuilder.append('9');
                    continue block15;
                }
                case '\u03b8': {
                    stringBuilder.append("THETA");
                    continue block15;
                }
                case '/': {
                    stringBuilder.append("_");
                    continue block15;
                }
                default: {
                    stringBuilder.append(this.displayableNameUTF8.charAt(i));
                }
            }
        }
        String string = stringBuilder.toString();
        return string + this.getProperFileExtension();
    }

    @Override
    public void setDeviceFileName(String string) {
        this.displayableNameUTF8 = string;
    }

    @Override
    public void setDeviceFileNameBytes(byte[] arrby) {
        try {
            this.displayableNameUTF8 = new String(arrby, "UTF-16BE");
        }
        catch (UnsupportedEncodingException var2_2) {
            TILogger.logError("TIVarImpl", "setDeviceFileNameBytes", var2_2);
        }
    }

    @Override
    public void buildDisplayableName() {
        if (this.getHostFile() != null && this.getDeviceFileName() == null && this.getVarType() != -1) {
            if (this.getVarType() == 15) {
                this.setDeviceFileName("Window");
            } else if (this.getVarType() == 16) {
                this.setDeviceFileName("RclWindw");
            } else if (this.getVarType() == 17) {
                this.setDeviceFileName("TblSet");
            } else {
                TIStatus tIStatus = ConnectServerFactory.getTranslator().getDisplayableNamefromObjectName(this);
                if (tIStatus != null && tIStatus.isFailure()) {
                    this.setCorruptedVarState(true);
                }
            }
        }
    }

    @Override
    public File getHostFile() {
        return this.hostFile;
    }

    @Override
    public void setHostFile(File file) {
        this.hostFile = file;
        this.setHostFileExtensionPerType();
    }

    @Override
    public int getVarType() {
        return this.varType;
    }

    @Override
    public void setVarType(int n) {
        this.varType = n;
    }

    @Override
    public String getProperFileExtension() {
        if (this.ownerProductID == 19) {
            switch (this.varType) {
                case 36: {
                    return ".8ek";
                }
            }
        }
        if (this.ownerProductID == 15 || this.ownerProductID == 19) {
            switch (this.varType) {
                case 26: {
                    return ".8ca";
                }
                case 7: {
                    return ".8ci";
                }
                case 36: {
                    return ".8ck";
                }
            }
        }
        switch (this.varType) {
            case 12: 
            case 27: 
            case 29: 
            case 30: 
            case 31: {
                return ".8xc";
            }
            case 0: 
            case 28: 
            case 32: 
            case 33: {
                return ".8xn";
            }
            case 21: {
                return ".8xv";
            }
            case 37: {
                return ".8xq";
            }
            case 39: {
                return "";
            }
            case 13: {
                return ".8xl";
            }
            case 36: {
                return ".8xk";
            }
            case 8: {
                return ".8xd";
            }
            case 23: {
                return ".8xg";
            }
            case 1: {
                return ".8xl";
            }
            case 2: {
                return ".8xm";
            }
            case 3: 
            case 11: {
                return ".8xy";
            }
            case 35: {
                return ".8xu";
            }
            case 7: {
                return ".8xi";
            }
            case 5: {
                return ".8xp";
            }
            case 6: {
                return ".8xp";
            }
            case 16: {
                return ".8xz";
            }
            case 4: {
                return ".8xs";
            }
            case 17: {
                return ".8xt";
            }
            case 22: {
                return "";
            }
            case 15: {
                return ".8xw";
            }
            case 41: {
                return "";
            }
        }
        return "";
    }

    @Override
    public TIStatus loadFromDisk() {
        if (FileUtils.isGoodReadPath(this.hostFile)) {
            try {
                this.data = FileUtils.readBytesFromFile(this.hostFile);
                this.dataSize = this.data.length;
            }
            catch (IOException var1_1) {
                return new TIStatus(-1);
            }
            return new TIStatus(0);
        }
        return new TIStatus(-1);
    }

    @Override
    public TIStatus saveToDisk(byte[] arrby) {
        int n = FileUtils.isGoodWritePath(this.hostFile);
        if (n == 0) {
            try {
                if (!this.hostFile.createNewFile()) {
                    this.hostFile.delete();
                    this.hostFile.createNewFile();
                }
                if (arrby != null) {
                    FileUtils.writeBytesToFile(this.hostFile, arrby);
                } else {
                    FileUtils.writeBytesToFile(this.hostFile, this.data);
                }
            }
            catch (IOException var3_3) {
                if (var3_3.getMessage() != null && (var3_3.getMessage().contains("No space left") || var3_3.getMessage().contains("not enough space"))) {
                    return new TIStatus(-10);
                }
                return new TIStatus(-1);
            }
            return new TIStatus(0);
        }
        if (n == -2) {
            return new TIStatus(-9);
        }
        return new TIStatus(-1);
    }

    @Override
    public byte[] getData() {
        return this.data;
    }

    @Override
    public void setData(byte[] arrby) {
        this.data = arrby;
        if (arrby != null) {
            this.dataSize = arrby.length;
        }
    }

    @Override
    public void setRawDataSize(int n) {
        this.dataSize = n;
    }

    private void setHostFileExtensionPerType() {
        int n;
        String string;
        if (this.hostFile != null && (n = (string = this.hostFile.getName()).lastIndexOf(46)) == -1) {
            String string2 = this.getProperFileExtension();
            this.hostFile = new File(this.hostFile.getAbsolutePath() + string2);
        }
    }

    @Override
    public int getDataSize() {
        return this.dataSize;
    }

    public int calculateFlashAppSize() {
        int n;
        int n2 = 27;
        int n3 = n = this.dataSize + n2;
        int n4 = 0;
        byte by = -1;
        for (int i = 0; i < n3; ++i) {
            int n5;
            byte by2 = (byte)(i >> 14);
            if (by2 == by) continue;
            by = by2;
            int n6 = by << 14;
            if (n6 < 0) {
                n6 = 0;
            }
            if ((n5 = by + 1 << 14) > n3) {
                n5 = n3;
            }
            int n7 = n5 - n6;
            int n8 = 0;
            n4 += 17;
            int n9 = 0;
            for (int j = 0; j < n7; ++j) {
                if (n9 == 0) {
                    n8 += 5;
                }
                ++n8;
                if ((n9 = (int)((byte)(n9 + 1))) != 32) continue;
                n4 += (n8 + 1) * 2 + 1;
                n8 = 0;
                n9 = 0;
            }
            if (n9 <= 0) continue;
            n4 += (n8 + 1) * 2 + 1;
            n8 = 0;
        }
        return n4 += 13;
    }

    @Override
    public int getFormattedDataSize() {
        if (this.isFlashObject()) {
            if (this.getOwnerProductID() < 19) {
                return this.calculateFlashAppSize() + 78;
            }
            return this.dataSize + 78;
        }
        return this.dataSize + 74;
    }

    @Override
    public int getVarVersion() {
        return this.varVersion;
    }

    @Override
    public void setVarVersion(int n) {
        this.varVersion = n;
        if (!this.isFlashObject()) {
            if (n == 16) {
                this.setOwnerProductID(19);
            } else if (n == 10) {
                this.setOwnerProductID(15);
            } else if (n < 10 && this.getOwnerProductID() != 11 && this.getOwnerProductID() != 27) {
                this.setOwnerProductID(10);
            }
        }
    }

    @Override
    public boolean isVarFlagArchive() {
        return this.varFlagArchive;
    }

    @Override
    public void setVarFlagArchive(boolean bl) {
        this.varFlagArchive = bl;
    }

    @Override
    public int getOwnerProductID() {
        return this.ownerProductID;
    }

    @Override
    public void setOwnerProductID(int n) {
        this.ownerProductID = n;
    }

    @Override
    public int getOwnerCalculatorID() {
        return this.ownerCalculatorID;
    }

    @Override
    public void setOwnerCalculatorID(int n) {
        this.ownerCalculatorID = n;
    }

    @Override
    public byte[] getObjectName() {
        return this.objectName;
    }

    @Override
    public void setObjectName(byte[] arrby) {
        this.objectName = arrby;
    }

    @Override
    public int getVarDescriptor() {
        return this.varDescriptor;
    }

    public void setVarDescriptor(int n) {
        this.varDescriptor = n;
    }

    private void handleDifferentObjNameMacVsPConWrite() {
        byte[] arrby = this.getObjectName();
        if (this.getVarType() == 3) {
            this.setObjectName(VarDataParser.buildEquationObjectName(arrby));
        } else if (this.getVarType() == 1 && arrby[0] != 93 && arrby[0] > 9) {
            byte[] arrby2 = new byte[arrby.length + 1];
            arrby2[0] = 93;
            for (int i = 1; i <= arrby.length; ++i) {
                arrby2[i] = arrby[i - 1];
            }
            this.setObjectName(arrby2);
        }
    }

    @Override
    public byte[] frameDataWithDataFormat() {
        byte[] arrby;
        if (this.getVarType() == 26) {
            this.setOwnerProductID(15);
        }
        if (this.isFlashObject()) {
            byte[] arrby2 = null;
            byte[] arrby3 = null;
            if (this.getOwnerProductID() >= 19) {
                arrby2 = this.createFlashFileHeaderFormat(false);
                arrby3 = this.createFlashFileBodyBinaryFormat();
            } else {
                arrby2 = this.createFlashFileHeaderFormat(true);
                arrby3 = this.createFlashFileBodyHexFormat(true);
            }
            arrby = new byte[arrby2.length + arrby3.length];
            System.arraycopy(arrby2, 0, arrby, 0, arrby2.length);
            System.arraycopy(arrby3, 0, arrby, arrby2.length, arrby3.length);
            arrby3 = null;
            arrby2 = null;
        } else {
            this.handleDifferentObjNameMacVsPConWrite();
            byte[] arrby4 = this.createFileHeaderFormat();
            byte[] arrby5 = this.createFileBodyFormat();
            arrby = new byte[arrby4.length + arrby5.length];
            System.arraycopy(arrby4, 0, arrby, 0, arrby4.length);
            System.arraycopy(arrby5, 0, arrby, arrby4.length, arrby5.length);
            arrby5 = null;
            arrby4 = null;
        }
        return arrby;
    }

    protected void loadData() {
        TIStatus tIStatus = new TIStatus(0);
        if (this.getHostFile() != null) {
            tIStatus = this.loadFromDisk();
            if (!tIStatus.isFailure() && this.data != null) {
                this.setPropertiesFromDataAndStripFileFormating();
            } else {
                this.setOwnerCalculatorID(-1);
            }
        }
    }

    protected void setPropertiesFromDataAndStripFileFormating() {
        if (FileSupport.myTI_IDFile(this.data)) {
            this.setFlashObject(true);
            FileSupport.myTI_IDFlashFile(this);
        } else if (this.data.length > 72 && VarDataParser.getOwnerCalculatorID(this.data) != -1) {
            byte[] arrby;
            if (this.validateChecksum() && (arrby = VarDataParser.getChecksum(this.data)) != null && (arrby[0] != this.data[this.data.length - 2] || arrby[1] != this.data[this.data.length - 1])) {
                this.setBadCheckSum(true);
                this.setCorruptedVarState(true);
                return;
            }
            this.setVarType(VarDataParser.getVarTypeFromData(this.data));
            this.setObjectName(VarDataParser.getObjectNameFromData(this.getVarType(), this.data));
            this.setOwnerProductID(VarDataParser.getOwnerProductID(this.data));
            this.setVarVersion(VarDataParser.getVarVersionFromData(this.data));
            this.setVarFlagArchive(VarDataParser.getVarFlagArchiveFromData(this.data));
            this.setOwnerCalculatorID(VarDataParser.getOwnerCalculatorID(this.data));
            this.setVarDescriptor(VarDataParser.getVarDescriptor(this.data));
            this.buildDisplayableName();
            if (this.isACorruptedVar()) {
                return;
            }
            int n = VarDataParser.getMsgBlock2Length(this.data);
            if (n == 65535) {
                this.setCorruptedVarState(true);
                return;
            }
            this.setMsgBlock2Length(n);
            byte[] arrby2 = VarDataParser.reduceToRawData(this.data, this.getMsgBlock2Length());
            if (this.data.length > 70 + this.getMsgBlock2Length() + 2 + 2) {
                this.setMultiVarFile(true);
            }
            this.setData(arrby2);
        } else {
            this.setOwnerCalculatorID(-1);
        }
    }

    protected byte[] createFlashFileHeaderFormat(boolean bl) {
        byte[] arrby = new byte[74];
        System.arraycopy("**TIFL**".getBytes(), 0, arrby, 0, "**TIFL**".getBytes().length);
        arrby[8] = 0;
        arrby[9] = 0;
        if (bl) {
            arrby[10] = 1;
            arrby[11] = -120;
        } else {
            arrby[10] = 0;
            arrby[11] = 0;
        }
        arrby[16] = (byte)this.getObjectName().length;
        System.arraycopy(this.getObjectName(), 0, arrby, 17, this.getObjectName().length);
        arrby[48] = (byte)this.getOwnerCalculatorID();
        arrby[49] = (byte)this.getVarType();
        byte by = (byte)this.getOwnerProductID();
        if (by < 19) {
            by = 0;
        }
        arrby[73] = by;
        return arrby;
    }

    protected byte[] createFileHeaderFormat() {
        byte[] arrby = new byte[11];
        byte[] arrby2 = new byte[42];
        byte[] arrby3 = new byte[2];
        String string = new String("Created by TI Connect CE " + Constants.PRODUCT_VERSION_STRING);
        switch (this.getOwnerCalculatorID()) {
            case 115: {
                System.arraycopy("**TI83F*".getBytes(), 0, arrby, 0, "**TI83F*".getBytes().length);
                arrby[8] = 26;
                arrby[9] = 10;
                arrby[10] = (byte)this.getOwnerProductID();
                System.arraycopy(string.getBytes(), 0, arrby2, 0, string.getBytes().length);
                break;
            }
            default: {
                TILogger.logError(LOG_TAG, "Error creating File Header: Device family not supported");
            }
        }
        byte[] arrby4 = new byte[arrby.length + arrby2.length + arrby3.length];
        System.arraycopy(arrby, 0, arrby4, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby4, arrby.length, arrby2.length);
        arrby3[0] = (byte)(17 + this.getDataSize() & 255);
        arrby3[1] = (byte)(17 + this.getDataSize() >> 8 & 255);
        System.arraycopy(arrby3, 0, arrby4, arrby.length + arrby2.length, arrby3.length);
        arrby3 = null;
        arrby2 = null;
        arrby = null;
        return arrby4;
    }

    protected byte[] createFlashFileBodyBinaryFormat() {
        byte[] arrby = new byte[4];
        byte[] arrby2 = new byte[this.getDataSize() + arrby.length];
        int n = this.getDataSize();
        arrby[0] = (byte)(n & 255);
        arrby[1] = (byte)(n >> 8 & 255);
        arrby[2] = (byte)(n >> 16 & 255);
        arrby[3] = (byte)(n >> 24 & 255);
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        System.arraycopy(this.getData(), 0, arrby2, arrby.length, this.getDataSize());
        arrby = null;
        return arrby2;
    }

    protected byte calcHexRecChecksum(Vector<Byte> vector) {
        byte by = 0;
        for (int i = 1; i < vector.size(); ++i) {
            by = (byte)(by + vector.elementAt(i).byteValue());
        }
        by = (byte)(256 - by);
        return by;
    }

    protected String hexRecToStr(Vector<Byte> vector) {
        String string = ":";
        for (int i = 1; i < vector.size(); ++i) {
            string = string + String.format("%02X", vector.elementAt(i));
        }
        string = string + "\r\n";
        return string;
    }

    protected void loadExtendedAddrHexRec(Vector<Byte> vector, int n) {
        Vector<Byte> vector2 = new Vector<Byte>();
        vector2.add(Byte.valueOf(58));
        vector2.add(Byte.valueOf(2));
        vector2.add(Byte.valueOf(0));
        vector2.add(Byte.valueOf(0));
        vector2.add(Byte.valueOf(2));
        vector2.add(Byte.valueOf((byte)(n >> 8 & 255)));
        vector2.add(Byte.valueOf((byte)(n >> 0 & 255)));
        vector2.add(Byte.valueOf(this.calcHexRecChecksum(vector2)));
        String string = this.hexRecToStr(vector2);
        for (int i = 0; i < string.length(); ++i) {
            vector.add(Byte.valueOf((byte)string.charAt(i)));
        }
    }

    protected void loadEndOfFileHexRec(Vector<Byte> vector) {
        Vector<Byte> vector2 = new Vector<Byte>();
        vector2.add(Byte.valueOf(58));
        vector2.add(Byte.valueOf(0));
        vector2.add(Byte.valueOf(0));
        vector2.add(Byte.valueOf(0));
        vector2.add(Byte.valueOf(1));
        vector2.add(Byte.valueOf(this.calcHexRecChecksum(vector2)));
        String string = this.hexRecToStr(vector2);
        for (int i = 0; i < string.length(); ++i) {
            vector.add(Byte.valueOf((byte)string.charAt(i)));
        }
    }

    protected void loadHexRec(Vector<Byte> vector, Vector<Byte> vector2, byte by) {
        vector2.set(1, Byte.valueOf(by));
        vector2.add(Byte.valueOf(this.calcHexRecChecksum(vector2)));
        String string = this.hexRecToStr(vector2);
        for (int i = 0; i < string.length(); ++i) {
            vector.add(Byte.valueOf((byte)string.charAt(i)));
        }
        vector2.clear();
    }

    protected void startNewDataHexRec(Vector<Byte> vector, int n) {
        vector.add(Byte.valueOf(58));
        vector.add(Byte.valueOf(0));
        vector.add(Byte.valueOf((byte)(n >> 8 & 255)));
        vector.add(Byte.valueOf((byte)(n >> 0 & 255)));
        vector.add(Byte.valueOf(0));
    }

    protected void loadHexFilePageData(Vector<Byte> vector, Vector<Byte> vector2, byte by, int n) {
        Vector<Byte> vector3 = new Vector<Byte>();
        this.loadExtendedAddrHexRec(vector, by);
        byte by2 = 0;
        for (int i = 0; i < n; ++i) {
            if (by2 == 0) {
                this.startNewDataHexRec(vector3, i + 16384);
            }
            byte by3 = vector2.elementAt(i).byteValue();
            vector3.add(Byte.valueOf(by3));
            by2 = (byte)(by2 + 1);
            if (by2 != 32) continue;
            this.loadHexRec(vector, vector3, 32);
            by2 = 0;
        }
        if (by2 > 0) {
            this.loadHexRec(vector, vector3, by2);
        }
    }

    void loadHexFileData(Vector<Byte> vector, byte[] arrby, int n) {
        byte by = -1;
        for (int i = 0; i < n; ++i) {
            int n2;
            byte by2 = (byte)(i >> 14);
            if (by2 == by) continue;
            by = by2;
            int n3 = by << 14;
            if (n3 < 0) {
                n3 = 0;
            }
            if ((n2 = by + 1 << 14) > n) {
                n2 = n;
            }
            Vector<Byte> vector2 = new Vector<Byte>();
            int n4 = n2 - n3;
            for (int j = 0; j < n4; ++j) {
                vector2.add(Byte.valueOf(arrby[n3 + j]));
            }
            this.loadHexFilePageData(vector, vector2, by, n4);
        }
        this.loadEndOfFileHexRec(vector);
    }

    protected byte[] createFlashFileBodyHexFormat(boolean bl) {
        int n;
        Vector<Byte> vector = new Vector<Byte>();
        int n2 = this.dataSize;
        int n3 = 27;
        if (n3 < 0) {
            n3 = 0;
        }
        if (bl) {
            n2 += n3;
        }
        byte[] arrby = new byte[n2];
        System.arraycopy(this.data, 0, arrby, 0, this.dataSize);
        if (bl) {
            for (n = this.dataSize; n < arrby.length; ++n) {
                arrby[n] = -1;
            }
        }
        vector.add(Byte.valueOf(0));
        vector.add(Byte.valueOf(0));
        vector.add(Byte.valueOf(0));
        vector.add(Byte.valueOf(0));
        this.loadHexFileData(vector, arrby, arrby.length);
        n = vector.size() - 4;
        vector.set(0, Byte.valueOf((byte)(n >> 0 & 255)));
        vector.set(1, Byte.valueOf((byte)(n >> 8 & 255)));
        vector.set(2, Byte.valueOf((byte)(n >> 16 & 255)));
        vector.set(3, Byte.valueOf((byte)(n >> 24 & 255)));
        byte[] arrby2 = new byte[vector.size()];
        for (int i = 0; i < arrby2.length; ++i) {
            arrby2[i] = vector.elementAt(i).byteValue();
        }
        return arrby2;
    }

    protected byte[] createFileBodyFormat() {
        byte[] arrby = new byte[17];
        byte[] arrby2 = new byte[this.getDataSize()];
        byte[] arrby3 = new byte[arrby.length + arrby2.length];
        byte[] arrby4 = new byte[2];
        int n = 13;
        long l = 0;
        arrby[0] = (byte)n;
        l = this.getDataSize();
        arrby[2] = (byte)(l & 255);
        arrby[3] = (byte)(l >> 8 & 255);
        arrby[4] = (byte)(this.getVarType() & 63);
        System.arraycopy(this.getObjectName(), 0, arrby, 5, this.getObjectName().length);
        arrby[n + 2] = arrby[2];
        arrby[n + 3] = arrby[3];
        n += 4;
        if (this.getOwnerCalculatorID() == 115) {
            if (this.getVarVersion() != 0) {
                arrby[13] = (byte)this.getVarVersion();
            } else {
                int n2 = (this.getVarDescriptor() >> 8 & 255) + 1;
                arrby[13] = n2 > 10 ? 10 : n2;
                byte by = (byte)(this.getVarDescriptor() & 255);
                if (by == 26) {
                    arrby[13] = 10;
                    arrby[14] = -128;
                }
            }
            if (this.isVarFlagArchive()) {
                arrby[14] = -128;
            }
        }
        System.arraycopy(this.data, 0, arrby2, 0, this.getDataSize());
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
        long l2 = FileUtils.calculateChecksum(arrby3);
        arrby4[0] = (byte)(l2 & 255);
        arrby4[1] = (byte)(l2 >> 8 & 255);
        byte[] arrby5 = new byte[arrby3.length + arrby4.length];
        System.arraycopy(arrby3, 0, arrby5, 0, arrby3.length);
        System.arraycopy(arrby4, 0, arrby5, arrby3.length, arrby4.length);
        arrby4 = null;
        arrby3 = null;
        arrby2 = null;
        arrby = null;
        return arrby5;
    }

    @Override
    public boolean isFlashObject() {
        return this.isFlashObject;
    }

    @Override
    public void setFlashObject(boolean bl) {
        this.isFlashObject = bl;
    }

    @Override
    public int getRevisionNumberMajor() {
        return this.revisionNumberMajor;
    }

    @Override
    public void setRevisionNumberMajor(int n) {
        this.revisionNumberMajor = n;
    }

    @Override
    public int getRevisionNumberMinor() {
        return this.revisionNumberMinor;
    }

    @Override
    public void setRevisionNumberMinor(int n) {
        this.revisionNumberMinor = n;
    }

    @Override
    public int getFileFormatFlags() {
        return this.fileFormatFlags;
    }

    @Override
    public void setFileFormatFlags(int n) {
        this.fileFormatFlags = n;
    }

    @Override
    public int getObjectCodeType() {
        return this.objectCodeType;
    }

    @Override
    public void setObjectCodeType(int n) {
        this.objectCodeType = n;
    }

    @Override
    public int getRevisionDateDay() {
        return this.revisionDateDay;
    }

    @Override
    public void setRevisionDateDay(int n) {
        this.revisionDateDay = n;
    }

    @Override
    public int getRevisionDateMonth() {
        return this.revisionDateMonth;
    }

    @Override
    public void setRevisionDateMonth(int n) {
        this.revisionDateMonth = n;
    }

    @Override
    public int getRevisionDateYearMajor() {
        return this.revisionDateYearMajor;
    }

    @Override
    public void setRevisionDateYearMajor(int n) {
        this.revisionDateYearMajor = n;
    }

    @Override
    public int getRevisionDateYearMinor() {
        return this.revisionDateYearMinor;
    }

    @Override
    public void setRevisionDateYearMinor(int n) {
        this.revisionDateYearMinor = n;
    }

    @Override
    public int getObjectNameLength() {
        return this.objectNameLength;
    }

    @Override
    public void setObjectNameLength(int n) {
        this.objectNameLength = n;
    }

    @Override
    public byte[] getDeviceCodeDataTypePairs() {
        return this.deviceCodeDataTypePairs;
    }

    @Override
    public void setDeviceCodeDataTypePairs(byte[] arrby) {
        this.deviceCodeDataTypePairs = arrby;
    }

    @Override
    public boolean isBinaryFormat() {
        return this.isBinaryFormat;
    }

    @Override
    public void setBinaryFormat(boolean bl) {
        this.isBinaryFormat = bl;
    }

    @Override
    public String getNameFromVarType(int n) {
        switch (n) {
            case 12: 
            case 27: 
            case 29: 
            case 30: 
            case 31: {
                return "ComplexNumber";
            }
            case 0: 
            case 28: 
            case 32: 
            case 33: {
                return "RealNumber";
            }
            case 21: {
                return "AppVar";
            }
            case 37: {
                return "Certificate";
            }
            case 39: {
                return "CertificateMemory";
            }
            case 13: {
                return "ComplexList";
            }
            case 3: 
            case 11: {
                return "Equation";
            }
            case 36: {
                return "FlashApplication";
            }
            case 8: {
                return "GraphDatabase";
            }
            case 23: {
                return "GroupObject";
            }
            case 1: {
                return "RealList";
            }
            case 2: {
                return "Matrix";
            }
            case 35: {
                return "BaseOSCode";
            }
            case 7: {
                return "Picture";
            }
            case 5: {
                return "Program";
            }
            case 6: {
                return "ProtectedProgram";
            }
            case 16: {
                return "UserZoomRecallWindow";
            }
            case 4: {
                return "String";
            }
            case 17: {
                return "TableSetup";
            }
            case 22: {
                return "TemporaryItem";
            }
            case 15: {
                return "Window";
            }
            case 26: {
                return "BackgroundImage";
            }
            case 41: {
                return "Clock";
            }
            case -1: {
                return "Unknown";
            }
        }
        return "Unknown";
    }

    @Override
    public int getMsgBlock2Length() {
        return this.msgBlock2Length;
    }

    public void setMsgBlock2Length(int n) {
        this.msgBlock2Length = n;
    }

    @Override
    public boolean isMultiVarFile() {
        return this.isMultiVarFile;
    }

    public void setMultiVarFile(boolean bl) {
        this.isMultiVarFile = bl;
    }

    @Override
    public boolean isACorruptedVar() {
        return this.isACorruptedVar;
    }

    @Override
    public void setCorruptedVarState(boolean bl) {
        this.isACorruptedVar = bl;
    }

    @Override
    public boolean isBadCheckSum() {
        return this.badCheckSum;
    }

    public void setBadCheckSum(boolean bl) {
        this.badCheckSum = bl;
    }

    public boolean validateChecksum() {
        return !this.overrideCheckSum;
    }

    public void setOverrideCheckSum(boolean bl) {
        this.overrideCheckSum = bl;
    }
}

