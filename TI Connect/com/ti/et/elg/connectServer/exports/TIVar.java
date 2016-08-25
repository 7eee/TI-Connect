/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

import com.ti.et.elg.connectServer.exports.TIStatus;
import java.io.File;

public interface TIVar
extends Cloneable {
    public static final int TIVAR_TYPE_UNKNOWN = -1;
    public static final int typeTI83PlusReal = 0;
    public static final int typeTI83PlusList = 1;
    public static final int typeTI83PlusMatrix = 2;
    public static final int typeTI83PlusEquation = 3;
    public static final int typeTI83PlusString = 4;
    public static final int typeTI83PlusProgram = 5;
    public static final int typeTI83PlusProtectedProgram = 6;
    public static final int typeTI83PlusPicture = 7;
    public static final int typeTI83PlusGraphDataBase = 8;
    public static final int typeTI83PlusNewEquation = 11;
    public static final int typeTI83PlusComplex = 12;
    public static final int typeTI83PlusComplexList = 13;
    public static final int typeTI83PlusWindow = 15;
    public static final int typeTI83PlusRecallWindow = 16;
    public static final int typeTI83PlusTableRange = 17;
    public static final int typeTI83PlusApplicationVariable = 21;
    public static final int typeTI83PlusTemporaryItem = 22;
    public static final int typeTI83PlusGroupObject = 23;
    public static final int typeTI83PlusOperatingSystem = 35;
    public static final int typeTI83PlusFlashApplication = 36;
    public static final int typeTI83PlusCertificate = 37;
    public static final int typeTI83PlusCertificateMemory = 39;
    public static final int typeTI83PlusFlashLicense = 62;
    public static final int typeTI84PlusClock = 41;
    public static final int typeTI84PlusCImage = 26;
    public static final int typeTI84PlusCPicture = 7;
    public static final int typeTI83PlusPComplexSimpleFraction = 27;
    public static final int typeTI83PlusPComplexRadical = 29;
    public static final int typeTI83PlusPComplexPi = 30;
    public static final int typeTI83PlusPComplexPiSimpleFraction = 31;
    public static final int typeTI83PlusPRadical = 28;
    public static final int typeTI83PlusPPi = 32;
    public static final int typeTI83PlusPPiSimpleFraction = 33;
    public static final int typeTI83PlusEquationFunction = 144;
    public static final int typeTI83PlusEquationParametric = 145;
    public static final int typeTI83PlusEquationPolar = 146;
    public static final int typeTI83PlusEquationSequence = 147;

    public TIVar clone();

    public String getDeviceFileName();

    public String getHostFileNameFromDeviceFileName();

    public File getHostFile();

    public byte[] getDisplayableNameBytes();

    public void setHostFile(File var1);

    public void setVarFlagArchive(boolean var1);

    public boolean isVarFlagArchive();

    public int getOwnerProductID();

    public void setOwnerProductID(int var1);

    public int getOwnerCalculatorID();

    public void setOwnerCalculatorID(int var1);

    public int getVarVersion();

    public void setVarVersion(int var1);

    public int getVarType();

    public byte[] getObjectName();

    public void setObjectName(byte[] var1);

    public int getVarDescriptor();

    public void setVarType(int var1);

    public void setDeviceFileName(String var1);

    public void setDeviceFileNameBytes(byte[] var1);

    public String getProperFileExtension();

    public TIStatus loadFromDisk();

    public TIStatus saveToDisk(byte[] var1);

    public byte[] getData();

    public void setData(byte[] var1);

    public void setRawDataSize(int var1);

    public void buildDisplayableName();

    public void setFlashObject(boolean var1);

    public boolean isFlashObject();

    public void setRevisionNumberMajor(int var1);

    public int getRevisionNumberMajor();

    public void setRevisionNumberMinor(int var1);

    public int getRevisionNumberMinor();

    public void setFileFormatFlags(int var1);

    public int getFileFormatFlags();

    public void setObjectCodeType(int var1);

    public int getObjectCodeType();

    public int getRevisionDateDay();

    public void setRevisionDateDay(int var1);

    public int getRevisionDateMonth();

    public void setRevisionDateMonth(int var1);

    public int getRevisionDateYearMajor();

    public void setRevisionDateYearMajor(int var1);

    public int getRevisionDateYearMinor();

    public void setRevisionDateYearMinor(int var1);

    public int getObjectNameLength();

    public void setObjectNameLength(int var1);

    public byte[] getDeviceCodeDataTypePairs();

    public void setDeviceCodeDataTypePairs(byte[] var1);

    public boolean isBinaryFormat();

    public void setBinaryFormat(boolean var1);

    public int getMsgBlock2Length();

    public boolean isMultiVarFile();

    public boolean isACorruptedVar();

    public void setCorruptedVarState(boolean var1);

    public boolean isBadCheckSum();

    public int getDataSize();

    public int getFormattedDataSize();

    public byte[] frameDataWithDataFormat();

    public String getNameFromVarType(int var1);
}

