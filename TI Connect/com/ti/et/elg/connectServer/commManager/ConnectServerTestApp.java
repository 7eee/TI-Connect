/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.autoDetect.AutoDetect;
import com.ti.et.elg.connectServer.commManager.ArrayUtils;
import com.ti.et.elg.connectServer.commManager.Assert;
import com.ti.et.elg.connectServer.commManager.CommManager;
import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.commManager.ReportAssertsThread;
import com.ti.et.elg.connectServer.commManager.TIImageImpl;
import com.ti.et.elg.connectServer.commManager.TestAutomationAPI;
import com.ti.et.elg.connectServer.deviceComm.DeviceComm;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.exports.TestAutomationInterface;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.elg.connectServer.translator.Translator;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import com.ti.et.utils.propertiesManager.UserPropertyManagement;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class ConnectServerTestApp {
    static TestAutomationInterface testAutomationAPI;
    static File dataFolder;
    static File ti83p_dataFolder;
    private static File tempFolder;
    public static final String FROM_COMMONCONSTANTS_USER_PROPERTY_AUTODETECT_84PFAMILY = "AutoDetect.84PFamily.enabled";
    public static final String FROM_COMMONCONSTANTS_USER_PROPERTY_GET_DEVICE_NAME_FROM_DRIVER = "GetDeviceNameFromDriver.enabled";
    public static final String FROM_COMMONCONSTANTS_PRODUCT_NAME = "TI Connect CE";
    public static final String FROM_COMMONCONSTANTS_PRODUCT_VERSION = "5.0.0.TEST";
    private static final String noError;
    private static final String errInvalidCert;
    private static final String errBadSignature;
    private static final String errDevBadAccess;
    private static final String errInvalidName;
    private static final String errCommTimeout;
    private static final String errReqUnsupported;
    public static final int TEST_GET_DEVICE_INFO = 1;
    public static final int TEST_GET_ANS_ON_ELG = 2;
    public static final int TEST_IS_BUSY = 4;
    public static final int TEST_IMAGE_CONVERSION = 8;
    public static final int TEST_DEVICE_LAST_ERROR_ELG = 16;
    public static final int TEST_PROGRAM_EXECUTION = 32;
    public static final int TEST_SEND_GET_DELETE = 64;
    public static final int TEST_GET_INACCESSIBLE_VAR = 128;
    private static final int SEND_GET_DELETE_LOOPS = 5;
    private static final String[] filesForELGonly;
    private static final String[] filesForELGEmuOnly;
    private static final String[] filesForELGExactOnly;
    private static final String[] filesForROYEmuonly;
    private static final String[] filesForROYonly;
    private static final String[] filesFor84BnWEmuonly;
    private static final String[] filesFor82Pand84PTonly;
    private static final String[] filesFor84BnWonlyAppSupport;
    private static final String[] filesFor84BnWonly;
    private static final String[] commonColorFiles;
    private static final String[] commonBaseFiles;

    private static String getRootFolder() {
        System.out.println("Please enter the parent folder that contains \"ti83p_data\" folder:");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String string = "";
        try {
            string = bufferedReader.readLine();
        }
        catch (IOException var2_2) {
            System.out.println("Cannot read root folder input");
        }
        return string;
    }

    public static void runTests(String string, int n, boolean bl) {
        dataFolder = new File(string);
        ti83p_dataFolder = new File(dataFolder, "ti83p_data");
        try {
            tempFolder = File.createTempFile("foo", "bar").getParentFile();
        }
        catch (IOException var3_3) {
            var3_3.printStackTrace();
        }
        int n2 = 0;
        PlatformManager.init("TI Connect CE", "5.0.0.TEST");
        TILogger.init(Level.FINER, PlatformManager.getApplicationLogsFolder() + ConnectServerTestApp.class.getSimpleName() + ".txt", true);
        if (!bl) {
            ConnectServerFactory.configureFactory(new CommManager(), new DeviceComm(), Translator.getInstance(), AutoDetect.getInstance(), new TestAutomationAPI());
        }
        testAutomationAPI = ConnectServerFactory.getTestAutomationAPI();
        if (!bl) {
            testAutomationAPI.init(true, UserPropertyManagement.getBoolean("GetDeviceNameFromDriver.enabled", false));
        }
        System.out.println("Using Connect Server Version: " + testAutomationAPI.getVersionString());
        System.out.println("Make sure device is connected and on");
        ArrayList<String> arrayList = new ArrayList<String>();
        while (arrayList.size() == 0) {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException var5_6) {
                // empty catch block
            }
            n2 = testAutomationAPI.getConnectedDevices(arrayList);
            Assert.assertEquals(0, n2);
        }
        ArrayList<> arrayList2 = new ArrayList<>();
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Object object;
            Object object2 = object = iterator.next();
            ReportAssertsThread reportAssertsThread = new ReportAssertsThread((String)object, (String)object2, n, bl){
                final /* synthetic */ String val$oneDeviceID;
                final /* synthetic */ int val$testsToRun;
                final /* synthetic */ boolean val$forEmulatedDevice;

                @Override
                public void myRun() {
                    String[] arrstring;
                    TIDevice tIDevice = ConnectServerTestApp.testAutomationAPI.getTIDevice(this.val$oneDeviceID);
                    if ((this.val$testsToRun & 1) != 0) {
                        ConnectServerTestApp.testGetDeviceInfo(tIDevice);
                    }
                    if (tIDevice.getHardwareVersion() <= 4) {
                        String[][] arrstring2 = new String[2][];
                        arrstring2[0] = this.val$forEmulatedDevice ? filesFor84BnWEmuonly : filesFor84BnWonly;
                        arrstring2[1] = commonBaseFiles;
                        arrstring = ArrayUtils.concat(arrstring2);
                        arrstring = !this.val$forEmulatedDevice && tIDevice.getProductID() != 11 && tIDevice.getProductID() != 27 ? ArrayUtils.concat(arrstring, filesFor84BnWonlyAppSupport) : ArrayUtils.concat(arrstring, filesFor82Pand84PTonly);
                    } else if (tIDevice.getHardwareVersion() == 6) {
                        String[][] arrstring3 = new String[3][];
                        arrstring3[0] = this.val$forEmulatedDevice ? filesForROYEmuonly : filesForROYonly;
                        arrstring3[1] = commonColorFiles;
                        arrstring3[2] = commonBaseFiles;
                        arrstring = ArrayUtils.concat(arrstring3);
                    } else if (tIDevice.getExactMathCapability()) {
                        String[][] arrstring4 = new String[4][];
                        arrstring4[0] = filesForELGExactOnly;
                        arrstring4[1] = this.val$forEmulatedDevice ? filesForELGEmuOnly : filesForELGonly;
                        arrstring4[2] = commonColorFiles;
                        arrstring4[3] = commonBaseFiles;
                        arrstring = ArrayUtils.concat(arrstring4);
                    } else {
                        String[][] arrstring5 = new String[3][];
                        arrstring5[0] = this.val$forEmulatedDevice ? filesForELGEmuOnly : filesForELGonly;
                        arrstring5[1] = commonColorFiles;
                        arrstring5[2] = commonBaseFiles;
                        arrstring = ArrayUtils.concat(arrstring5);
                    }
                    if ((this.val$testsToRun & 2) != 0) {
                        ConnectServerTestApp.testGetAnsOnELG(this.val$oneDeviceID, tIDevice);
                    }
                    if ((this.val$testsToRun & 4) != 0) {
                        ConnectServerTestApp.testIsBusy(this.val$oneDeviceID, tIDevice, this.val$forEmulatedDevice);
                    }
                    if ((this.val$testsToRun & 8) != 0) {
                        ConnectServerTestApp.testImageConversion(this.val$oneDeviceID, tIDevice);
                    }
                    if ((this.val$testsToRun & 16) != 0) {
                        ConnectServerTestApp.testDeviceLastErrorELG(this.val$oneDeviceID, tIDevice);
                    }
                    if ((this.val$testsToRun & 32) != 0) {
                        ConnectServerTestApp.testProgramExecution(this.val$oneDeviceID);
                    }
                    if ((this.val$testsToRun & 128) != 0) {
                        ConnectServerTestApp.testGetInaccessibleVar(this.val$oneDeviceID, this.val$forEmulatedDevice);
                    }
                    if ((this.val$testsToRun & 64) != 0) {
                        ConnectServerTestApp.testSendGetDelete(this.val$oneDeviceID, tIDevice, arrstring);
                        ConnectServerTestApp.sendManyGetManyDeleteMany(this.val$oneDeviceID, tIDevice, commonBaseFiles);
                    }
                }
            };
            arrayList2.add(()reportAssertsThread);
            reportAssertsThread.start();
        }
        boolean bl2 = false;
        while (!bl2) {
            bl2 = true;
            for (Thread thread : arrayList2) {
                if (!thread.isAlive()) continue;
                bl2 = false;
            }
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException var7_11) {
                var7_11.printStackTrace();
            }
        }
        System.out.println("Test Shutting Down!");
        if (!bl) {
            testAutomationAPI.shutDown();
        }
        System.out.println(ReportAssertsThread.getSummary());
    }

    public static void main(String[] arrstring) {
        String string;
        String string2 = string = arrstring.length > 0 ? arrstring[0] : null;
        if (string == null || !new File(string).exists()) {
            string = ConnectServerTestApp.getRootFolder();
        }
        if (!new File(string).exists()) {
            return;
        }
        ConnectServerTestApp.runTests(string, 255, false);
    }

    private static void printForDeviceInLoop(int n, String string, String string2) {
        ConnectServerTestApp.printForDevice(string, "Loop#" + n + "->" + string2);
    }

    private static void printForDevice(String string, String string2) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        System.out.println("<" + new Timestamp(System.currentTimeMillis()) + ", " + tIDevice.getConnectionIDforName() + "> " + string2);
    }

    private static String bytesToHexString(byte[] arrby) {
        StringBuffer stringBuffer = new StringBuffer(arrby.length * 2);
        for (int i = 0; i < arrby.length; ++i) {
            byte by = arrby[i];
            int n = (by & 240) >> 4;
            int n2 = by & 15;
            char c = (char)(n < 10 ? 48 + n : 65 + n - 10);
            char c2 = (char)(n2 < 10 ? 48 + n2 : 65 + n2 - 10);
            stringBuffer.append(c);
            stringBuffer.append(c2);
        }
        return stringBuffer.toString();
    }

    private static void testSendImageVar(String string) {
        Object object;
        int n = -1;
        File file = new File(ti83p_dataFolder, "rawdata.tiff");
        byte[] arrby = null;
        try {
            int n2 = 0;
            object = new BufferedInputStream(new FileInputStream(file));
            arrby = new byte[(int)file.length()];
            while (n2 < arrby.length) {
                int n3 = arrby.length - n2;
                int n4 = object.read(arrby, n2, n3);
                if (n4 <= 0) continue;
                n2 += n4;
            }
            object.close();
        }
        catch (IOException var4_5) {
            System.out.println(var4_5);
        }
        TIImageImpl tIImageImpl = new TIImageImpl(arrby);
        ConnectServerFactory.getTranslator().convertImage(tIImageImpl, TIImageImpl.typeTIFF, TIImageImpl.typeTI84PlusCImage);
        object = ConnectServerFactory.getCommManager().createTIVar(null, "Image9", null, 0, 10, 0, 26, true, 15, 115, false);
        object.setData(tIImageImpl.getImageData());
        byte[] arrby2 = new byte[]{60, 8};
        object.setObjectName(arrby2);
        n = testAutomationAPI.sendVariable(string, (TIVar)object);
        Assert.assertEquals(0, n);
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        n = testAutomationAPI.getDirectory(string, arrayList);
        Assert.assertEquals(0, n);
        TIVar tIVar = object.clone();
        tIVar.setData(null);
        tIVar.setHostFile(null);
        n = testAutomationAPI.getVariable(string, tIVar);
        Assert.assertEquals(0, n);
        if (!object.isFlashObject()) {
            Assert.assertTrue(Arrays.equals(object.getData(), tIVar.getData()));
        }
        ConnectServerTestApp.printForDevice(string, "...got the Image back, and the data matches the original");
        n = testAutomationAPI.deleteVariable(string, (TIVar)object);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "And the Image was deleted successfully");
    }

    private static void testGetInaccessibleVar(String string, boolean bl) throws Error {
        ConnectServerTestApp.printForDevice(string, "\n**************************\n* testGetInaccessibleVar *\n**************************");
        File file = new File(ti83p_dataFolder, "A.8xn");
        TIVar tIVar = testAutomationAPI.createTIVar(file.getAbsolutePath());
        int n = testAutomationAPI.sendVariable(string, tIVar);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Sent the Var [A.8xn] successfully");
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        n = testAutomationAPI.getDirectory(string, arrayList);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Device directory updated");
        n = testAutomationAPI.deleteVariable(string, tIVar);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Var [A.8xn] deleted successfully");
        n = testAutomationAPI.getVariable(string, tIVar);
        Assert.assertEquals(7, n);
        ConnectServerTestApp.printForDevice(string, "Var [A.8xn] not found as excpected!");
    }

    private static void testGetDeviceInfo(TIDevice tIDevice) throws Error {
        int n = tIDevice.getBootCodeUpperByte();
        int n2 = tIDevice.getBootCodeLowerByte();
        int n3 = tIDevice.getBootPatchNum();
        String string = tIDevice.getDeviceName();
        int n4 = tIDevice.getHardwareVersion();
        int n5 = tIDevice.getOsVersionUpperByte();
        int n6 = tIDevice.getOsVersionLowerByte();
        int n7 = tIDevice.getOsPatchNum();
        String string2 = tIDevice.getPrimaryLanguageString();
        int n8 = tIDevice.getFreeFLASH();
        int n9 = tIDevice.getFreeRAM();
        int n10 = testAutomationAPI.getDeviceInfo(tIDevice);
        Assert.assertEquals(0, n10);
        Assert.assertEquals(n, tIDevice.getBootCodeUpperByte());
        Assert.assertEquals(n2, tIDevice.getBootCodeLowerByte());
        Assert.assertEquals(n3, tIDevice.getBootPatchNum());
        Assert.assertEquals(string, tIDevice.getDeviceName());
        Assert.assertEquals(n4, tIDevice.getHardwareVersion());
        Assert.assertEquals(n5, tIDevice.getOsVersionUpperByte());
        Assert.assertEquals(n6, tIDevice.getOsVersionLowerByte());
        Assert.assertEquals(n7, tIDevice.getOsPatchNum());
        Assert.assertEquals(string2, tIDevice.getPrimaryLanguageString());
        Assert.assertEquals(n8, tIDevice.getFreeFLASH());
        Assert.assertEquals(n9, tIDevice.getFreeRAM());
    }

    private static void testGetAnsOnELG(String string, TIDevice tIDevice) throws Error {
        if (tIDevice.getHardwareVersion() == 8) {
            ConnectServerTestApp.printForDevice(string, "Getting ANSVariable...");
            TIVar tIVar = ConnectServerFactory.getCommManager().createTIVar(null, "", null, 0, 0, 0, 0, false, 0, 0, false);
            int n = testAutomationAPI.getANSVariable(string, tIVar);
            Assert.assertEquals(0, n);
            ConnectServerTestApp.printForDevice(string, "getANSVariable done! VarType: " + tIVar.getVarType() + " VarDataSize: " + tIVar.getDataSize());
        }
    }

    private static int doResetAll(String string) throws Error {
        ConnectServerTestApp.printForDevice(string, "Reset all calculator memory...");
        int n = testAutomationAPI.sendKey(string, 64);
        ConnectServerTestApp.printForDevice(string, "Key - Go Home, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 54);
        ConnectServerTestApp.printForDevice(string, "Key - kMem, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 149);
        ConnectServerTestApp.printForDevice(string, "Key - k7, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 1);
        ConnectServerTestApp.printForDevice(string, "Key - kRight, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 1);
        ConnectServerTestApp.printForDevice(string, "Key - kRight, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 143);
        ConnectServerTestApp.printForDevice(string, "Key - k1, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 144);
        ConnectServerTestApp.printForDevice(string, "Key - k2, is done! rc=" + n);
        return n;
    }

    private static void testIsBusy(String string, TIDevice tIDevice, boolean bl) throws Error {
        int n = ConnectServerTestApp.doResetAll(string);
        Assert.assertEquals(5, n);
        if (tIDevice.getHardwareVersion() == 8) {
            try {
                Thread.sleep(2000);
            }
            catch (InterruptedException var4_4) {
                var4_4.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(5000);
            }
            catch (InterruptedException var4_5) {
                var4_5.printStackTrace();
            }
        }
        while (n != 0) {
            ConnectServerTestApp.printForDevice(string, "waiting...");
            n = testAutomationAPI.checkIfDeviceIsBusy(string);
            ConnectServerTestApp.printForDevice(string, "Device IS busy! rc=" + n);
        }
        ConnectServerTestApp.printForDevice(string, "Checking if this device is busy..");
        n = testAutomationAPI.checkIfDeviceIsBusy(string);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Device is NOT busy!");
        int n2 = 20000;
        ConnectServerTestApp.printForDevice(string, "Change the Timeout to " + n2 / 1000 + " seconds!");
        n = testAutomationAPI.setOverwriteTimeout(string, n2);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Put this device in a busy state, get it graphing...");
        n = testAutomationAPI.sendKey(string, 64);
        ConnectServerTestApp.printForDevice(string, "Key - Go Home, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 73);
        ConnectServerTestApp.printForDevice(string, "Key - Y=, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 9);
        ConnectServerTestApp.printForDevice(string, "Key - Clear, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 183);
        ConnectServerTestApp.printForDevice(string, "Key - SIN, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 180);
        ConnectServerTestApp.printForDevice(string, "Key - X, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 189);
        ConnectServerTestApp.printForDevice(string, "Key - ^2, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 1);
        ConnectServerTestApp.printForDevice(string, "Key - Right, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 134);
        ConnectServerTestApp.printForDevice(string, "Key - Right Parenthesis, is done!");
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 68);
        if (n == 0) {
            Assert.assertEquals(0, n);
            ConnectServerTestApp.waitWhileDeviceStillBusy(string, 500);
            ConnectServerTestApp.printForDevice(string, "Key - Graph, is done!");
        } else {
            Assert.assertEquals(5, n);
            ConnectServerTestApp.printForDevice(string, "Device will reboot! Waiting for 5 seconds...");
            ConnectServerTestApp.waitWhileDeviceStillBusy(string, 10000);
        }
        ConnectServerTestApp.printForDevice(string, "Checking if this device is busy now..");
        n = testAutomationAPI.checkIfDeviceIsBusy(string);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Device IS busy!");
        ConnectServerTestApp.waitWhileDeviceStillBusy(string, 1000);
        ConnectServerTestApp.printForDevice(string, "Change the Timeout to OVERWRITE_CARS_DEFAULT_TIMEOUT");
        n = testAutomationAPI.setOverwriteTimeout(string, -1);
        Assert.assertEquals(0, n);
    }

    private static void testImageConversion(String string, TIDevice tIDevice) throws Error {
        if (tIDevice.getHardwareVersion() > 4) {
            ConnectServerTestApp.printForDevice(string, "Converting an image (Tiff->ImageVar) and send it afterwards (as Image9)");
            ConnectServerTestApp.testSendImageVar(string);
        }
        File file = new File(tempFolder.getAbsolutePath() + File.separatorChar + "capture.jpg");
        file.deleteOnExit();
        File file2 = new File(tempFolder.getAbsolutePath() + File.separatorChar + "capture.png");
        file2.deleteOnExit();
        File file3 = new File(tempFolder.getAbsolutePath() + File.separatorChar + "capture.bmp");
        file3.deleteOnExit();
        ConnectServerTestApp.printForDevice(string, "Screens will be saved in temp folder: " + tempFolder.getAbsolutePath());
        int n = testAutomationAPI.getScreen(string, file.getAbsolutePath());
        Assert.assertEquals(0, n);
        n = testAutomationAPI.getScreen(string, file2.getAbsolutePath());
        Assert.assertEquals(0, n);
        n = testAutomationAPI.getScreen(string, file3.getAbsolutePath());
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 64);
        Assert.assertEquals(0, n);
    }

    private static void testDeviceLastErrorELG(String string, TIDevice tIDevice) throws Error {
        if (tIDevice.getHardwareVersion() >= 8) {
            int n = testAutomationAPI.sendKey(string, 143);
            ConnectServerTestApp.printForDevice(string, "Key - k2, is done! rc=" + n);
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 128);
            ConnectServerTestApp.printForDevice(string, "Key - kAdd, is done! rc=" + n);
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 143);
            ConnectServerTestApp.printForDevice(string, "Key - k2, is done! rc=" + n);
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 5);
            ConnectServerTestApp.printForDevice(string, "Key - kEnter, is done! rc=" + n);
            Assert.assertEquals(0, n);
            DeviceErrorHolder deviceErrorHolder = new DeviceErrorHolder();
            n = testAutomationAPI.queryLastError(string, deviceErrorHolder);
            ConnectServerTestApp.printForDevice(string, "queryLastError! rc=" + n + " devErr=" + deviceErrorHolder.getDeviceLastError());
            Assert.assertEquals(0, n);
            Assert.assertEquals(0, deviceErrorHolder.getDeviceLastError());
            n = testAutomationAPI.sendKey(string, 144);
            ConnectServerTestApp.printForDevice(string, "Key - k2, is done! rc=" + n);
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 128);
            ConnectServerTestApp.printForDevice(string, "Key - kAdd, is done! rc=" + n);
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 5);
            ConnectServerTestApp.printForDevice(string, "Key - kEnter, is done! rc=" + n);
            Assert.assertEquals(0, n);
            deviceErrorHolder.setDeviceLastError(0);
            n = testAutomationAPI.queryLastError(string, deviceErrorHolder);
            ConnectServerTestApp.printForDevice(string, "queryLastError! rc=" + n + " devErr=" + deviceErrorHolder.getDeviceLastError());
            Assert.assertEquals(0, n);
            Assert.assertTrue(deviceErrorHolder.getDeviceLastError() != 0);
            n = testAutomationAPI.sendKey(string, 9);
            ConnectServerTestApp.printForDevice(string, "Key - Clear, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 73);
            ConnectServerTestApp.printForDevice(string, "Key - Y=, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 9);
            ConnectServerTestApp.printForDevice(string, "Key - Clear, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 53);
            ConnectServerTestApp.printForDevice(string, "Key - Vars, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 146);
            ConnectServerTestApp.printForDevice(string, "Key - k4, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 5);
            ConnectServerTestApp.printForDevice(string, "Key - kEnter, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 68);
            Assert.assertEquals(0, n);
            ConnectServerTestApp.waitWhileDeviceStillBusy(string, 500);
            deviceErrorHolder.setDeviceLastError(0);
            n = testAutomationAPI.queryLastError(string, deviceErrorHolder);
            ConnectServerTestApp.printForDevice(string, "queryLastError! rc=" + n + " devErr=" + deviceErrorHolder.getDeviceLastError());
            Assert.assertEquals(0, n);
            Assert.assertTrue(deviceErrorHolder.getDeviceLastError() != 0);
            n = testAutomationAPI.sendKey(string, 9);
            ConnectServerTestApp.printForDevice(string, "Key - Clear, is done!");
            Assert.assertEquals(0, n);
            n = testAutomationAPI.sendKey(string, 9);
            ConnectServerTestApp.printForDevice(string, "Key - Clear, is done!");
            Assert.assertEquals(0, n);
        }
    }

    private static void waitWhileDeviceStillBusy(String string, long l) {
        int n = 0;
        do {
            try {
                Thread.sleep(l);
            }
            catch (InterruptedException var4_3) {
                var4_3.printStackTrace();
            }
            n = testAutomationAPI.checkIfDeviceIsBusy(string);
            ConnectServerTestApp.printForDevice(string, "Is device busy?! status=" + n);
        } while (n != 0);
    }

    private static void testProgramExecution(String string) throws Error {
        File file = new File(ti83p_dataFolder, "CYLINDER.8xp");
        TIVar tIVar = testAutomationAPI.createTIVar(file.getAbsolutePath());
        int n = testAutomationAPI.sendVariable(string, tIVar);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Sent the Var [CYLINDER.8xp] successfully");
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        n = testAutomationAPI.getDirectory(string, arrayList);
        Assert.assertEquals(0, n);
        n = testAutomationAPI.executeVariable(string, tIVar);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Execute the Var [CYLINDER.8xp] successfully");
        try {
            Thread.sleep(4000);
        }
        catch (InterruptedException var5_5) {
            var5_5.printStackTrace();
        }
        n = testAutomationAPI.sendKey(string, 64);
        Assert.assertEquals(0, n);
        n = testAutomationAPI.deleteVariable(string, tIVar);
        Assert.assertEquals(0, n);
        n = testAutomationAPI.sendKey(string, 9);
        ConnectServerTestApp.printForDevice(string, "Key - kClear, is done!");
        ArrayList<TIVar> arrayList2 = new ArrayList<TIVar>();
        n = testAutomationAPI.getDirectory(string, arrayList2);
        Assert.assertEquals(0, n);
        ConnectServerTestApp.printForDevice(string, "Contents of device: ");
        for (TIVar tIVar2 : arrayList2) {
            ConnectServerTestApp.printForDevice(string, "Name: " + tIVar2.getDeviceFileName() + " Type: " + tIVar2.getVarType() + " Size: " + tIVar2.getDataSize() + " Location: " + tIVar2.isVarFlagArchive());
            if (tIVar2.getDeviceFileName().equals("IDList")) continue;
            n = testAutomationAPI.getVariable(string, tIVar2);
            Assert.assertEquals(0, n);
            ConnectServerTestApp.printForDevice(string, " Data: " + ConnectServerTestApp.bytesToHexString(tIVar2.getData()));
        }
    }

    private static void sendManyGetManyDeleteMany(String string, TIDevice tIDevice, String[] arrstring) throws Error {
        int n;
        TIVar tIVar;
        int n2;
        ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
        System.out.println();
        ConnectServerTestApp.printForDevice(string, "SendManyAtOnce: This is a final \"loop\" that sends a bunch of files, then gets them all, then deletes them all. ");
        for (n2 = 0; n2 < arrstring.length; n2 += 4) {
            Iterator iterator = new File(ti83p_dataFolder, arrstring[n2]);
            TIVar tIVar22 = testAutomationAPI.createTIVar(iterator.getAbsolutePath());
            arrayList.add(tIVar22);
            Assert.assertTrue(tIVar22.getOwnerCalculatorID() != -1);
            ConnectServerTestApp.printForDevice(string, "TIVar[" + tIVar22.getDeviceFileName() + "] from file [" + arrstring[n2] + "] created successfully");
            n = testAutomationAPI.sendVariable(string, tIVar22);
            Assert.assertEquals(Integer.parseInt(arrstring[n2 + 1]), n);
            if (n == 0) {
                ConnectServerTestApp.printForDevice(string, "Sent the Var [" + arrstring[n2] + "] successfully");
                continue;
            }
            ConnectServerTestApp.printForDevice(string, "As expected, unable to Send the Var [" + arrstring[n2] + "]");
        }
        ArrayList<TIVar> arrayList2 = new ArrayList<TIVar>();
        n = testAutomationAPI.getDirectory(string, arrayList2);
        Assert.assertEquals(0, n);
        n2 = 0;
        for (TIVar tIVar22 : arrayList) {
            tIVar = tIVar22.clone();
            tIVar.setData(null);
            tIVar.setHostFile(null);
            n = testAutomationAPI.getVariable(string, tIVar);
            Assert.assertEquals(Integer.parseInt(arrstring[n2 + 2]), n);
            if (!tIVar22.isFlashObject() && !tIVar22.isMultiVarFile() && n == 0) {
                Assert.assertTrue(Arrays.equals(tIVar22.getData(), tIVar.getData()));
                ConnectServerTestApp.printForDevice(string, "Got the Var [" + tIVar22.getDeviceFileName() + "] and the data matches");
            }
            n2 += 4;
        }
        n2 = 0;
        for (TIVar tIVar22 : arrayList) {
            tIVar = tIVar22.clone();
            tIVar.setData(null);
            tIVar.setHostFile(null);
            if (!tIVar22.isMultiVarFile()) {
                n = testAutomationAPI.deleteVariable(string, tIVar);
                Assert.assertEquals(Integer.parseInt(arrstring[n2 + 3]), n);
            }
            if (n == 0) {
                ConnectServerTestApp.printForDevice(string, "Deleted the Var [" + tIVar22.getDeviceFileName() + "] successfully");
            } else {
                ConnectServerTestApp.printForDevice(string, "Unable to Delete the Var [" + tIVar22.getDeviceFileName() + "]");
            }
            n2 += 4;
        }
    }

    private static void testSendGetDelete(String string, TIDevice tIDevice, String[] arrstring) throws Error {
        for (int i = 1; i <= 5; ++i) {
            System.out.println();
            ConnectServerTestApp.printForDevice(string, "Loop #" + i + ":");
            for (int j = 0; j < arrstring.length; j += 4) {
                File file = new File(ti83p_dataFolder, arrstring[j]);
                TIVar tIVar = testAutomationAPI.createTIVar(file.getAbsolutePath());
                Assert.assertTrue(tIVar.getOwnerCalculatorID() != -1);
                ConnectServerTestApp.printForDeviceInLoop(i, string, "TIVar[" + tIVar.getDeviceFileName() + "] from file [" + arrstring[j] + "] created successfully");
                int n = testAutomationAPI.sendVariable(string, tIVar);
                Assert.assertEquals(Integer.parseInt(arrstring[j + 1]), n);
                if (n == 0) {
                    ConnectServerTestApp.printForDeviceInLoop(i, string, "Sent the Var [" + arrstring[j] + "] successfully");
                } else {
                    ConnectServerTestApp.printForDeviceInLoop(i, string, "As expected, unable to Send the Var [" + arrstring[j] + "]");
                }
                if (tIDevice.getHardwareVersion() < 8 && (n == 35 || n == 33)) {
                    try {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException var9_10) {
                        var9_10.printStackTrace();
                    }
                }
                ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
                n = testAutomationAPI.getDirectory(string, arrayList);
                Assert.assertEquals(0, n);
                TIVar tIVar2 = tIVar.clone();
                tIVar2.setData(null);
                tIVar2.setHostFile(null);
                n = testAutomationAPI.getVariable(string, tIVar2);
                Assert.assertEquals(Integer.parseInt(arrstring[j + 2]), n);
                if (!tIVar.isFlashObject() && n == 0) {
                    Assert.assertTrue(Arrays.equals(tIVar.getData(), tIVar2.getData()));
                    ConnectServerTestApp.printForDeviceInLoop(i, string, "Got the Var [" + arrstring[j] + "] and the data matches");
                } else {
                    ConnectServerTestApp.printForDeviceInLoop(i, string, "Either Flash Object OR as expected, unable to Get the Var [" + arrstring[j] + "]");
                }
                n = testAutomationAPI.deleteVariable(string, tIVar);
                Assert.assertEquals(Integer.parseInt(arrstring[j + 3]), n);
                if (n == 0) {
                    ConnectServerTestApp.printForDeviceInLoop(i, string, "Deleted the Var [" + arrstring[j] + "] successfully");
                    continue;
                }
                ConnectServerTestApp.printForDeviceInLoop(i, string, "As expected, unable to Delete the Var [" + arrstring[j] + "]");
            }
        }
    }

    static {
        noError = Integer.toString(0);
        errInvalidCert = Integer.toString(33);
        errBadSignature = Integer.toString(35);
        errDevBadAccess = Integer.toString(7);
        errInvalidName = Integer.toString(57);
        errCommTimeout = Integer.toString(5);
        errReqUnsupported = Integer.toString(10);
        filesForELGonly = new String[]{"ieq4030a.8ck", errBadSignature, errDevBadAccess, errDevBadAccess, "french.8ek", noError, noError, noError};
        filesForELGEmuOnly = new String[]{"french.8ek", noError, noError, noError};
        filesForELGExactOnly = new String[]{"ComPi.8xc", noError, noError, noError, "ComPiSimplFract.8xc", noError, noError, noError, "ComRadical.8xc", noError, noError, noError, "ComSimplFract.8xc", noError, noError, noError, "ExactComList.8xl", noError, noError, noError, "ExactList.8xl", noError, noError, noError, "Pi.8xn", noError, noError, noError, "PiSimplFract.8xn", noError, noError, noError, "Radical.8xn", noError, noError, noError, "SimpleFraction.8xn", noError, noError, noError};
        filesForROYEmuonly = new String[]{"puzzpack.8xk", errBadSignature, errCommTimeout, noError, "ieq4030a.8ck", noError, noError, noError};
        filesForROYonly = new String[]{"puzzpack.8xk", errBadSignature, errDevBadAccess, errDevBadAccess, "ieq4030a.8ck", noError, noError, noError};
        filesFor84BnWEmuonly = new String[]{"puzzpack.8xk", noError, noError, noError, "SCREEN01.8XI", noError, noError, noError, "SCREEN06.8XI", noError, noError, noError, "TI84PlusSilverEdition.8xg", noError, noError, noError};
        filesFor82Pand84PTonly = new String[]{"inequal.8xk", errReqUnsupported, errDevBadAccess, errDevBadAccess};
        filesFor84BnWonlyAppSupport = new String[]{"puzzpack.8xk", noError, noError, noError, "ieq4030a.8ck", errInvalidCert, errDevBadAccess, errDevBadAccess};
        filesFor84BnWonly = new String[]{"SCREEN01.8XI", noError, noError, noError, "SCREEN06.8XI", noError, noError, noError, "ImageRoy.8ca", errInvalidName, errDevBadAccess, errDevBadAccess, "TI84PlusSilverEdition.8xg", noError, noError, noError};
        commonColorFiles = new String[]{"Pic1.8ci", noError, noError, noError, "ImageRoy.8ca", noError, noError, noError, "TI84PlusCSilverEdition.8cg", noError, noError, noError};
        commonBaseFiles = new String[]{"GROUP.8xg", noError, noError, noError, "TableSetup.8xt", noError, noError, errDevBadAccess, "WindowRange.8xw", noError, noError, errDevBadAccess, "UserZoomWin.8xz", noError, noError, errDevBadAccess, "A.8xn", noError, noError, noError, "Number.8Xn", noError, noError, noError, "C.8xc", noError, noError, noError, "F.8xc", noError, noError, noError, "AppList4.8xv", noError, noError, noError, "CYLINDER.8xp", noError, noError, noError, "A_theta_B_theta_C.8xp", noError, noError, noError, "GDB1.8xd", noError, noError, noError, "GDB2.8xd", noError, noError, noError, "L_1_.8xl", noError, noError, noError, "L_2_.8xl", noError, noError, noError, "List.8Xl", noError, noError, noError, "Matrix.8xm", noError, noError, noError, "Str1.8xs", noError, noError, noError, "Y_1_.8xy", noError, noError, noError, "Y_2_.8xy", noError, noError, noError, "Y_3_.8xy", noError, noError, noError, "theta.8xn", noError, noError, noError, "RealList_G.8xl", noError, noError, noError, "thetaABC.8xl", noError, noError, noError, "r1.8xy", noError, noError, noError, "r2.8xy", noError, noError, noError, "u.8xy", noError, noError, noError, "v.8xy", noError, noError, noError, "w.8xy", noError, noError, noError, "X1t.8xy", noError, noError, noError, "X2t.8xy", noError, noError, noError, "Y1t.8xy", noError, noError, noError, "Y2t.8xy", noError, noError, noError};
    }

}

