/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.deviceComm.mock;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.commManager.TIImageImpl;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.fileUtils.FileUtils;
import com.ti.et.utils.logger.TILogger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class DeviceCommMock
implements DeviceCommInterface {
    private File rootFolder = null;
    Set<TIDevice> devicesWhichHaveBeenPopulated = new HashSet<TIDevice>();
    private boolean isAlive = true;

    public DeviceCommMock() {
        try {
            this.rootFolder = File.createTempFile("DeviceCommMock", "");
            this.rootFolder.delete();
            if (!this.rootFolder.mkdir()) {
                throw new IOException("mkdir returned false");
            }
            this.rootFolder.deleteOnExit();
        }
        catch (IOException var1_1) {
            TILogger.logError(DeviceCommMock.class.getName(), "Unable to access Temp Folder", var1_1);
        }
    }

    @Override
    public TIStatus sendVar(TIDevice tIDevice, TIVar tIVar, boolean bl, TIProgressListener tIProgressListener) {
        if (this.isAlive) {
            String string;
            File file = new File(this.rootFolder, tIDevice.toString());
            if (!file.exists()) {
                file.mkdirs();
                file.deleteOnExit();
            }
            if ((string = tIVar.getDeviceFileName()) == null) {
                int n = tIVar.getHostFile().getName().lastIndexOf(46);
                if (n < 0) {
                    n = tIVar.getHostFile().getName().length();
                }
                string = tIVar.getHostFile().getName().substring(0, n);
            }
            File file2 = this.makeFakeDeviceFile(file, string, tIVar.getProperFileExtension());
            file2.deleteOnExit();
            if (!bl && file2.exists()) {
                return new TIStatus(107);
            }
            try {
                try {
                    Thread.sleep(10);
                }
                catch (InterruptedException var8_9) {
                    // empty catch block
                }
                if (tIVar.getObjectName() == null) {
                    tIVar.setObjectName(tIVar.getDisplayableNameBytes());
                    tIVar.setObjectNameLength(tIVar.getObjectName().length);
                }
                FileUtils.writeBytesToFile(file2, tIVar.frameDataWithDataFormat());
                this.fakeProgress(tIProgressListener);
                TILogger.logBytes(DeviceCommMock.class.getName(), "Wrote bytes to device " + tIDevice.toString(), tIVar.getData());
                return new TIStatus(0);
            }
            catch (IOException var8_10) {
                TILogger.logError(DeviceCommMock.class.getName(), "Unable to write to mock device file", var8_10);
                return new TIStatus(7);
            }
        }
        return new TIStatus(62);
    }

    @Override
    public TIStatus getVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        if (this.isAlive) {
            File file = new File(this.rootFolder, tIDevice.toString());
            File file2 = this.makeFakeDeviceFile(file, tIVar.getDeviceFileName(), tIVar.getProperFileExtension());
            try {
                byte[] arrby = FileUtils.readBytesFromFile(file2);
                arrby = DeviceCommMock.reduceToRawData(arrby);
                this.fakeProgress(tIProgressListener);
                tIVar.setData(arrby);
                TILogger.logBytes(DeviceCommMock.class.getName(), "Read bytes from device " + tIDevice.toString(), arrby);
                return new TIStatus(0);
            }
            catch (IOException var6_7) {
                TILogger.logError(DeviceCommMock.class.getName(), "Unable to read from mock device file", var6_7);
                return new TIStatus(7);
            }
        }
        return new TIStatus(62);
    }

    private static byte[] reduceToRawData(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length - 74];
        System.arraycopy(arrby, 72, arrby2, 0, arrby.length - 74);
        return arrby2;
    }

    @Override
    public TIStatus getDirectory(TIDevice tIDevice, TIDirectory tIDirectory, TIProgressListener tIProgressListener) {
        if (this.isAlive) {
            File[] arrfile;
            File file = new File(this.rootFolder, tIDevice.toString());
            if (!this.devicesWhichHaveBeenPopulated.contains(tIDevice)) {
                this.populateDummyVars(file);
                this.devicesWhichHaveBeenPopulated.add(tIDevice);
            }
            if ((arrfile = file.listFiles()) != null) {
                for (File file2 : arrfile) {
                    try {
                        Thread.sleep(10);
                    }
                    catch (InterruptedException var10_11) {
                        // empty catch block
                    }
                    TIVar tIVar = null;
                    tIVar = ConnectServerFactory.getCommManager().createTIVar(file2.getAbsolutePath(), null, null, 0, 0, 0, -1, false, 0, 0, false);
                    int n = file2.getName().lastIndexOf(46);
                    if (n < 0) {
                        n = file2.getName().length();
                    }
                    if (n > 8) {
                        n = 8;
                    }
                    String string = file2.getName().substring(0, n);
                    tIVar.setDeviceFileName(string);
                    tIVar.setObjectName(string.getBytes());
                    tIVar.setObjectNameLength(string.getBytes().length);
                    tIVar.setHostFile(null);
                    tIVar.setData(null);
                    tIDirectory.addVar(tIVar);
                }
            }
            return new TIStatus(0);
        }
        return new TIStatus(62);
    }

    @Override
    public TIStatus deleteVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        if (this.isAlive) {
            File file = new File(this.rootFolder, tIDevice.toString());
            File file2 = this.makeFakeDeviceFile(file, tIVar.getDeviceFileName(), tIVar.getProperFileExtension());
            this.fakeProgress(tIProgressListener);
            boolean bl = file2.delete();
            if (bl) {
                return new TIStatus(0);
            }
            return new TIStatus(7);
        }
        return new TIStatus(62);
    }

    @Override
    public TIStatus sendKey(TIDevice tIDevice, int n, TIProgressListener tIProgressListener) {
        return null;
    }

    @Override
    public TIStatus getStaticDeviceInfo(TIDevice tIDevice) {
        int n = tIDevice.setDeviceInformation(0, 0, 0, 0, 8, 0, 0, true, 240, 320, 16, 0, 0, 0, 0, 0, 0, 19, true);
        return new TIStatus(n);
    }

    @Override
    public TIStatus getDynamicDeviceInfo(TIDevice tIDevice) {
        return null;
    }

    @Override
    public TIStatus getIDDeviceInfo(TIDevice tIDevice) {
        int n = tIDevice.setDeviceID("12345678901234");
        return new TIStatus(n);
    }

    private File makeFakeDeviceFile(File file, String string, String string2) {
        return new File(file, string + string2);
    }

    private void populateDummyVars(File file) {
        if (!file.exists()) {
            file.mkdirs();
            file.deleteOnExit();
        }
        InputStream inputStream = DeviceCommMock.class.getClassLoader().getResourceAsStream("com/ti/et/elg/connectServer/commManager/TestData/ti83p_data/A.8xn");
        InputStream inputStream2 = DeviceCommMock.class.getClassLoader().getResourceAsStream("com/ti/et/elg/connectServer/commManager/TestData/ti83p_data/L_1_.8xl");
        File file2 = this.makeFakeDeviceFile(file, "A", ".8xn");
        File file3 = this.makeFakeDeviceFile(file, "L_1_", ".8xl");
        try {
            this.copyInputStreamToFile(inputStream, file2);
            this.copyInputStreamToFile(inputStream2, file3);
            file2.deleteOnExit();
            file3.deleteOnExit();
        }
        catch (Exception var6_6) {
            TILogger.logError(DeviceCommMock.class.getName(), "Unable to create new dummy vars", var6_6);
        }
    }

    private void copyInputStreamToFile(InputStream inputStream, File file) throws FileNotFoundException, IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        int n = -1;
        while ((n = inputStream.read()) != -1) {
            fileOutputStream.write(n);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    private void fakeProgress(TIProgressListener tIProgressListener) {
        for (int i = 1; i < 100; i += 5) {
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException var3_3) {
                // empty catch block
            }
            tIProgressListener.setPercentage(i);
        }
    }

    @Override
    public void shutDown() {
        this.stopTransactions();
        TILogger.logInfo(DeviceCommMock.class.getName(), "Shutting down DeviceComm");
    }

    @Override
    public TIStatus getScreen(TIDevice tIDevice, TIImage tIImage, TIProgressListener tIProgressListener) {
        if (null != tIDevice) {
            int n = tIDevice.getDeviceHeight();
            int n2 = tIDevice.getDeviceWidth();
            tIImage.setPixelsHeight((short)n);
            tIImage.setPixelsWide((short)n2);
            if (tIDevice.getProductID() > 0 && tIDevice.getProductID() < 15 || tIDevice.getHardwareVersion() > 0 && tIDevice.getHardwareVersion() < 6) {
                byte[] arrby = new byte[n * n2 / 8];
                tIImage.setImageData(arrby);
                for (int i = 0; i < arrby.length; ++i) {
                    arrby[i] = (byte)Math.round(Math.random() * 255.0);
                }
            } else {
                byte[] arrby = new byte[n * n2 * 2];
                byte by = (byte)(Math.random() * 255.0);
                byte by2 = (byte)(Math.random() * 255.0);
                tIImage.setImageData(arrby);
                for (int i = 0; i < arrby.length; ++i) {
                    arrby[i] = i % 2 == 0 ? by : by2;
                }
            }
            ((TIImageImpl)tIImage).setDeviceHardwareVersion(tIDevice.getHardwareVersion());
            ((TIImageImpl)tIImage).setDeviceProductID(tIDevice.getProductID());
        } else {
            byte[] arrby = new byte[153600];
            byte by = (byte)(Math.random() * 255.0);
            byte by3 = (byte)(Math.random() * 255.0);
            tIImage.setImageData(arrby);
            for (int i = 0; i < arrby.length; ++i) {
                arrby[i] = i % 2 == 0 ? by : by3;
            }
        }
        return new TIStatus(0);
    }

    @Override
    public TIStatus singleQuery(TIDevice tIDevice, int n, DeviceErrorHolder deviceErrorHolder) {
        return null;
    }

    @Override
    public TIStatus setOverwriteTimeout(TIDevice tIDevice, int n) {
        return null;
    }

    @Override
    public TIStatus sendOSUpdate(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return null;
    }

    @Override
    public TIStatus executeVar(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return null;
    }

    @Override
    public TIStatus getANSVariable(TIDevice tIDevice, TIVar tIVar, TIProgressListener tIProgressListener) {
        return null;
    }

    @Override
    public TIStatus setIDListValue(TIDevice tIDevice, byte[] arrby) {
        return null;
    }

    @Override
    public void init(boolean bl, boolean bl2, boolean bl3) {
    }

    @Override
    public void stopTransactions() {
        this.isAlive = false;
    }

    @Override
    public void continueTransactions() {
        this.isAlive = true;
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }
}

