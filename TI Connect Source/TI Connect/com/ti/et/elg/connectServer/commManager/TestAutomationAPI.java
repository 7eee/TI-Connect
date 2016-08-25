/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.commManager;

import com.ti.et.elg.connectServer.commManager.DeviceErrorHolder;
import com.ti.et.elg.connectServer.commManager.SimpleErrorListener;
import com.ti.et.elg.connectServer.exports.Constants;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIDirectory;
import com.ti.et.elg.connectServer.exports.TIImage;
import com.ti.et.elg.connectServer.exports.TIProgressListener;
import com.ti.et.elg.connectServer.exports.TIStatus;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.exports.TestAutomationInterface;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;

public class TestAutomationAPI
implements TestAutomationInterface {
    private final int DEFAULT_INITIAL_SCALING_FOR_TI84P_SCREENS = 2;

    @Override
    public String getVersionString() {
        return Constants.PRODUCT_VERSION_STRING;
    }

    @Override
    public void init(boolean bl, boolean bl2) {
        ConnectServerFactory.getCommManager().init();
        ConnectServerFactory.getDeviceComm().init(bl, bl2, true);
    }

    @Override
    public void shutDown() {
        ConnectServerFactory.getCommManager().shutDown();
        ConnectServerFactory.getDeviceComm().shutDown();
    }

    @Override
    public int getConnectedDevices(List<String> list) {
        Collection<TIDevice> collection = ConnectServerFactory.getCommManager().getConnectedDevices();
        list.clear();
        for (TIDevice tIDevice : collection) {
            list.add(tIDevice.getConnectionID());
        }
        return 0;
    }

    @Override
    public int sendKey(String string, int n) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().sendKey(tIDevice, n, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    public static BufferedImage convert(byte[] arrby) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrby);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(byteArrayInputStream);
        }
        catch (IOException var3_3) {
            TILogger.logError("TestAutomationAPI", "convert", var3_3);
        }
        return bufferedImage;
    }

    @Override
    public int getScreen(String string, String string2) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            int n = tIDevice.getDeviceWidth();
            int n2 = tIDevice.getDeviceHeight();
            TIImage tIImage = ConnectServerFactory.getCommManager().createTIImage(tIDevice);
            BufferedImage bufferedImage = null;
            ConnectServerFactory.getCommManager().getScreenCapture(tIDevice, tIImage, simpleErrorListener);
            if (tIImage != null && simpleErrorListener.getErrorCode() == 0) {
                bufferedImage = tIImage.getDeviceProductID() > 0 && tIImage.getDeviceProductID() < 15 || tIImage.getDeviceHardwareVersion() > 0 && tIImage.getDeviceHardwareVersion() < 6 ? this.createBnWBufferedImage(tIImage.getImageData(), tIImage.getPixelsWide(), tIImage.getPixelsHeight()) : this.createColorBufferedImage(tIImage.getImageDataAsARGB(), n, n2);
                try {
                    File file = new File(string2);
                    String string3 = "";
                    int n3 = string2.lastIndexOf(".");
                    int n4 = string2.lastIndexOf(File.separatorChar);
                    if (n3 > n4) {
                        string3 = string2.substring(n3 + 1).toLowerCase();
                    }
                    if (string3.isEmpty()) {
                        return 7;
                    }
                    ImageIO.write((RenderedImage)bufferedImage, string3, file);
                }
                catch (IOException var9_10) {
                    TILogger.logError("TestAutomationAPI", "getScreen", var9_10);
                }
            }
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    private BufferedImage createColorBufferedImage(int[] arrn, int n, int n2) {
        BufferedImage bufferedImage = new BufferedImage(n, n2, 1);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        int[] arrn2 = ((DataBufferInt)dataBuffer).getData();
        System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
        return bufferedImage;
    }

    private BufferedImage createBnWBufferedImage(byte[] arrby, int n, int n2) {
        BufferedImage bufferedImage = null;
        byte[] arrby2 = new byte[]{-1, 0};
        IndexColorModel indexColorModel = new IndexColorModel(1, 2, arrby2, arrby2, arrby2);
        bufferedImage = new BufferedImage(n, n2, 12, indexColorModel);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        byte[] arrby3 = ((DataBufferByte)dataBuffer).getData();
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        bufferedImage = this.scaleUpBImagePixelPerPixelNoKeepRatio(bufferedImage, 2, 2);
        return bufferedImage;
    }

    private BufferedImage scaleUpBImagePixelPerPixelNoKeepRatio(BufferedImage bufferedImage, int n, int n2) {
        BufferedImage bufferedImage2 = null;
        if (null != bufferedImage) {
            bufferedImage2 = new BufferedImage(bufferedImage.getWidth() * n, bufferedImage.getHeight() * n2, 1);
            for (int i = 0; i < bufferedImage.getWidth(); ++i) {
                for (int j = 0; j < bufferedImage.getHeight(); ++j) {
                    int n3 = bufferedImage.getRGB(i, j);
                    for (int k = 0; k < n; ++k) {
                        for (int i2 = 0; i2 < n2; ++i2) {
                            bufferedImage2.setRGB(k + i * n, i2 + j * n2, n3);
                        }
                    }
                }
            }
        }
        return bufferedImage2;
    }

    @Override
    public int checkIfDeviceIsBusy(String string) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().isDeviceBusy(tIDevice);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int queryLastError(String string, DeviceErrorHolder deviceErrorHolder) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().queryLastError(tIDevice, deviceErrorHolder);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int setOverwriteTimeout(String string, int n) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().setOverwriteTimeout(tIDevice, n);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int getDirectory(String string, List<TIVar> list) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            TIDirectory tIDirectory = ConnectServerFactory.getCommManager().createTIDirectory();
            ConnectServerFactory.getCommManager().getDirectory(tIDevice, tIDirectory, simpleErrorListener);
            Iterator<TIVar> iterator = tIDirectory.getIterator();
            list.clear();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int getDeviceInfo(TIDevice tIDevice) {
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().getDeviceInfo(tIDevice);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int getDeviceDynamicInfo(TIDevice tIDevice) {
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().getDeviceDynamicInfo(tIDevice);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int setIDListValue(TIDevice tIDevice, byte[] arrby) {
        if (tIDevice != null) {
            TIStatus tIStatus = ConnectServerFactory.getCommManager().setIDListValue(tIDevice, arrby);
            return tIStatus.getStatusCode();
        }
        return -6;
    }

    @Override
    public int getVariable(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
            arrayList.add(tIVar);
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().getVar(tIDevice, arrayList, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int getANSVariable(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().getANSVar(tIDevice, tIVar, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int sendVariable(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
            arrayList.add(tIVar);
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().sendVar(tIDevice, arrayList, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int sendOS(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().sendOSUpdate(tIDevice, tIVar, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int deleteVariable(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            ArrayList<TIVar> arrayList = new ArrayList<TIVar>();
            arrayList.add(tIVar);
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().deleteVar(tIDevice, arrayList, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public int executeVariable(String string, TIVar tIVar) {
        TIDevice tIDevice = ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
        if (tIDevice != null) {
            SimpleErrorListener simpleErrorListener = new SimpleErrorListener();
            ConnectServerFactory.getCommManager().executeVar(tIDevice, tIVar, simpleErrorListener);
            return simpleErrorListener.getErrorCode();
        }
        return -6;
    }

    @Override
    public void useSimulator() {
    }

    @Override
    public TIDevice getTIDevice(String string) {
        return ConnectServerFactory.getCommManager().getTIDeviceForConnectionID(string);
    }

    @Override
    public TIVar createTIVar(String string) {
        List list = new ArrayList();
        list = ConnectServerFactory.getCommManager().createTIVar(string);
        if (list != null && list.size() > 0) {
            return (TIVar)list.get(0);
        }
        return null;
    }
}

