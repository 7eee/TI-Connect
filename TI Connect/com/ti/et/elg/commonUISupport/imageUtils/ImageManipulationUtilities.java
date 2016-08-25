/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.embed.swing.SwingFXUtils
 *  javafx.scene.image.Image
 *  javafx.scene.image.PixelReader
 *  javafx.scene.image.PixelWriter
 *  javafx.scene.image.WritableImage
 *  javafx.scene.paint.Color
 *  javax.media.jai.JAI
 */
package com.ti.et.elg.commonUISupport.imageUtils;

import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.logger.TILogger;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.media.jai.JAI;

public class ImageManipulationUtilities {
    private static final int DEFAULT_BW_GRAPH_AREA_ORIGIN = 0;
    private static final int DEFAULT_BW_GRAPH_AREA_WIDTH = 190;
    private static final int DEFAULT_BW_GRAPH_AREA_HEIGHT = 126;
    private static final int DEFAULT_GRAPH_AREA_WIDTH = 265;
    private static final int DEFAULT_GRAPH_AREA_HEIGHT = 165;
    private static final int DEFAULT_GRAPH_AREA_POS_X = 27;
    private static final int DEFAULT_GRAPH_AREA_POS_Y = 46;
    private static final int DEVICE_IMAGE_WIDTH = 134;
    private static final int DEVICE_IMAGE_HEIGHT = 83;
    private static final int DEVICE_PIC_BW_WIDTH = 96;
    private static final int DEVICE_PIC_BW_HEIGHT = 63;
    private static javafx.scene.image.Image resultImg = null;
    private static final String SPACE_CHAR_SEQUENCE = " ";
    private static final String FILE_PROTOCOL = "file:";
    private static final String TIFF_IMAGE_EXTENSION_0 = ".tif";
    private static final String TIFF_IMAGE_EXTENSION_1 = ".tiff";
    private static final String TAG = ImageManipulationUtilities.class.getSimpleName();
    private static boolean resultWasSet = false;
    private static final int IMG_SIZE_THRESHOLD = 800;

    public static javafx.scene.image.Image cropGraphAreaFromImage(javafx.scene.image.Image image) {
        if (ImageManipulationUtilities.isAColorScreenCapture(image)) {
            return ImageManipulationUtilities.cropAreaFromImage(image, 27, 46, 265, 165);
        }
        if (ImageManipulationUtilities.isABnWScreenCapture(image)) {
            return ImageManipulationUtilities.cropAreaFromImage(image, 0, 0, 190, 126);
        }
        TILogger.logError(TAG, "Attempting to crop a Screen Capture of an unexpected size!! Not the desired behaviour");
        return image;
    }

    public static javafx.scene.image.Image cropAreaFromImage(javafx.scene.image.Image image, int n, int n2, int n3, int n4) {
        WritableImage writableImage = new WritableImage(n3, n4);
        writableImage.getPixelWriter().setPixels(0, 0, n3, n4, image.getPixelReader(), n, n2);
        return writableImage;
    }

    private static javafx.scene.image.Image scaleImageDownToDeviceSize(javafx.scene.image.Image image, boolean bl) {
        double d;
        double d2;
        int n;
        double d3;
        int n2 = bl ? 134 : 96;
        double d4 = 100.0 * ((double)n2 / (d2 = image.getWidth()));
        javafx.scene.image.Image image2 = ImageManipulationUtilities.scaleImageTransform(image, Math.min(d4, d = 100.0 * ((double)(n = bl ? 83 : 63) / (d3 = image.getHeight()))));
        if (image2 == null) {
            return null;
        }
        WritableImage writableImage = new WritableImage(n2, n);
        int n3 = (int)(((double)n2 - image2.getWidth()) / 2.0);
        int n4 = (int)(((double)n - image2.getHeight()) / 2.0);
        int n5 = (int)image2.getWidth();
        int n6 = (int)image2.getHeight();
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                writableImage.getPixelWriter().setColor(i, j, Color.WHITE);
            }
        }
        writableImage.getPixelWriter().setPixels(n3, n4, n5, n6, image2.getPixelReader(), 0, 0);
        return writableImage;
    }

    public static javafx.scene.image.Image scaleImageDownToDeviceSizeNoKeepRatio(javafx.scene.image.Image image) {
        double d = image.getWidth();
        double d2 = image.getHeight();
        double d3 = 100.0 * (134.0 / d);
        double d4 = 100.0 * (83.0 / d2);
        javafx.scene.image.Image image2 = ImageManipulationUtilities.scaleImageNoKeepRatioTransform(image, d3, d4);
        WritableImage writableImage = new WritableImage(134, 83);
        int n = (int)image2.getWidth();
        int n2 = (int)image2.getHeight();
        writableImage.getPixelWriter().setPixels(0, 0, n, n2, image2.getPixelReader(), 0, 0);
        return writableImage;
    }

    public static javafx.scene.image.Image scaleImageNoKeepRatioTransform(javafx.scene.image.Image image, double d, double d2) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((javafx.scene.image.Image)image, (BufferedImage)null);
        AffineTransform affineTransform = AffineTransform.getScaleInstance(d / 100.0, d2 / 100.0);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 3);
        bufferedImage = affineTransformOp.filter(bufferedImage, null);
        WritableImage writableImage = SwingFXUtils.toFXImage((BufferedImage)bufferedImage, (WritableImage)null);
        return writableImage;
    }

    public static javafx.scene.image.Image scaleImageTransform(javafx.scene.image.Image image, double d) {
        WritableImage writableImage = null;
        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage((javafx.scene.image.Image)image, (BufferedImage)null);
            if (bufferedImage == null) {
                return null;
            }
            int n = bufferedImage.getWidth();
            int n2 = bufferedImage.getHeight();
            double d2 = (double)n * d / 100.0;
            double d3 = (double)n2 * d / 100.0;
            BufferedImage bufferedImage2 = new BufferedImage((int)d2, (int)d3, 1);
            Graphics2D graphics2D = bufferedImage2.createGraphics();
            AffineTransform affineTransform = AffineTransform.getScaleInstance(d2 / (double)n, d3 / (double)n2);
            graphics2D.drawRenderedImage(bufferedImage, affineTransform);
            writableImage = SwingFXUtils.toFXImage((BufferedImage)bufferedImage2, (WritableImage)null);
        }
        catch (Error var4_4) {
            TILogger.logError(TAG, var4_4.getMessage(), var4_4);
            writableImage = null;
        }
        catch (Exception var4_5) {
            TILogger.logError(TAG, var4_5.getMessage(), var4_5);
            writableImage = null;
        }
        return writableImage;
    }

    public static javafx.scene.image.Image scaleUpImagePixelPerPixelNoKeepRatio(javafx.scene.image.Image image, int n, int n2) {
        WritableImage writableImage = null;
        if (null != image) {
            writableImage = new WritableImage(n * (int)image.getWidth(), n2 * (int)image.getHeight());
            for (int i = 0; i < (int)image.getWidth(); ++i) {
                for (int j = 0; j < (int)image.getHeight(); ++j) {
                    for (int k = 0; k < n; ++k) {
                        for (int i2 = 0; i2 < n2; ++i2) {
                            writableImage.getPixelWriter().setPixels(k + i * n, i2 + j * n2, 1, 1, image.getPixelReader(), i, j);
                        }
                    }
                }
            }
        }
        return writableImage;
    }

    public static BufferedImage scaleUpBImagePixelPerPixelNoKeepRatio(BufferedImage bufferedImage, int n, int n2) {
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

    private static int computePICBnWThreshold(int[] arrn) {
        int n;
        int n2 = arrn.length;
        int[] arrn2 = new int[16];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = arrn[i] & 255;
            int n5 = arrn[i] >> 8 & 255;
            int n6 = arrn[i] >> 16 & 255;
            int n7 = (n6 + n5 + n4) / 3 >> 4 & 15;
            int[] arrn3 = arrn2;
            int n8 = n7;
            arrn3[n8] = arrn3[n8] + 1;
        }
        double d = 0.0;
        double d2 = 0.0;
        TILogger.logDetail(TAG, "totalPixelCount=" + n2);
        for (n = 0; n < 16; ++n) {
            TILogger.logDetail(TAG, "colorTable[" + n + "]=" + arrn2[n]);
            d = (double)arrn2[n] / (double)n2;
            if ((d2 += d) >= 0.42) break;
        }
        n3 = n * 16 + 8;
        TILogger.logDetail(TAG, "averageToReturn" + n3);
        return n3;
    }

    public static TIVar fromImageToPicBWVar(javafx.scene.image.Image image, String string) {
        if ((image = ImageManipulationUtilities.scaleImageDownToDeviceSize(image, false)) == null) {
            return null;
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((javafx.scene.image.Image)image, (BufferedImage)null);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        int[] arrn = ((DataBufferInt)dataBuffer).getData();
        int n = dataBuffer.getSize();
        int n2 = 0;
        byte[] arrby = new byte[n / 8 + 2];
        int n3 = 2;
        int n4 = ImageManipulationUtilities.computePICBnWThreshold(arrn);
        for (int i = 0; i < n; ++i) {
            int n5 = arrn[i] & 255;
            int n6 = arrn[i] >> 8 & 255;
            int n7 = arrn[i] >> 16 & 255;
            int n8 = (n7 + n6 + n5) / 3;
            n2 = (short)(n2 << 1);
            if (n8 < n4) {
                n2 = (short)(n2 | 1);
            }
            if ((i + 1) % 8 != 0) continue;
            arrby[n3++] = (byte)(n2 & 255);
            n2 = 0;
        }
        arrby[0] = -12;
        arrby[1] = 2;
        TIVar tIVar = ConnectServerFactory.getCommManager().createTIVar(null, string, null, 0, 0, 0, 7, false, 10, 115, false);
        tIVar.setData(arrby);
        return tIVar;
    }

    public static boolean isValidImage(javafx.scene.image.Image image) {
        if ((image = ImageManipulationUtilities.scaleImageDownToDeviceSize(image, true)) == null) {
            return false;
        }
        return true;
    }

    public static TIVar fromImageToImageVar(javafx.scene.image.Image image, String string) {
        if ((image = ImageManipulationUtilities.scaleImageDownToDeviceSize(image, true)) == null) {
            return null;
        }
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((javafx.scene.image.Image)image, (BufferedImage)null);
        AffineTransform affineTransform = AffineTransform.getScaleInstance(1.0, -1.0);
        affineTransform.translate(0.0, - bufferedImage.getHeight(null));
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 3);
        bufferedImage = affineTransformOp.filter(bufferedImage, null);
        DataBuffer dataBuffer = bufferedImage.getRaster().getDataBuffer();
        int[] arrn = ((DataBufferInt)dataBuffer).getData();
        int n = 0;
        n = dataBuffer.getSize();
        int n2 = n * 2;
        byte[] arrby = new byte[n2 + 3];
        int n3 = 3;
        for (int i = 0; i < n; ++i) {
            int n4 = arrn[i] & 255;
            int n5 = arrn[i] >> 8 & 255;
            int n6 = arrn[i] >> 16 & 255;
            int n7 = ImageManipulationUtilities.mode_565_RGB16BIT(n6, n5, n4);
            arrby[n3++] = (byte)(n7 & 255);
            arrby[n3++] = (byte)(n7 >> 8 & 255);
        }
        arrby[0] = (byte)(n2 + 1 & 255);
        arrby[1] = (byte)(n2 >> 8 & 255);
        arrby[2] = -127;
        TIVar tIVar = ConnectServerFactory.getCommManager().createTIVar(null, string, null, 0, 10, 0, 26, true, 15, 115, false);
        tIVar.setData(arrby);
        return tIVar;
    }

    public static int mode_565_RGB16BIT(int n, int n2, int n3) {
        int n4;
        int n5 = 0;
        short s = (short)(n / 8 & 31);
        short s2 = (short)(n2 / 4 & 63);
        n5 = n4 = (int)(n3 / 8 & 31);
        n5 += s2 << 5;
        return n5 += s << 11;
    }

    public static javafx.scene.image.Image getTiffImage(String string) {
        WritableImage writableImage = null;
        if (null != string && !string.trim().equals("")) {
            TILogger.logDetail(TAG, "About to create Tiff");
            if (string.substring(string.length() - ".tif".length()).equalsIgnoreCase(".tif") || string.substring(string.length() - ".tiff".length()).equalsIgnoreCase(".tiff")) {
                try {
                    TILogger.logDetail(TAG, "About to create URL");
                    URL uRL = new URL(string);
                    writableImage = SwingFXUtils.toFXImage((BufferedImage)JAI.create((String)"URL", (Object)uRL).getAsBufferedImage(), (WritableImage)null);
                }
                catch (Exception var2_3) {
                    TILogger.logError(TAG, var2_3.getMessage(), var2_3);
                    writableImage = null;
                }
                catch (Error var2_4) {
                    TILogger.logError(TAG, var2_4.getMessage(), var2_4);
                    writableImage = null;
                }
                TILogger.logDetail(TAG, "image processing finished");
            }
        }
        if (null != writableImage) {
            TILogger.logDetail(TAG, "about to exit img is not null");
        } else {
            TILogger.logDetail(TAG, "about to exit img is null");
        }
        return writableImage;
    }

    public static javafx.scene.image.Image getImage(String string) {
        WritableImage writableImage = null;
        if (null != string && !string.trim().equals("")) {
            TILogger.logDetail(TAG, "About to create Image");
            try {
                TILogger.logDetail(TAG, "About to create URL");
                URL uRL = new URL(string);
                BufferedImage bufferedImage = JAI.create((String)"URL", (Object)uRL).getAsBufferedImage();
                if (bufferedImage != null && (bufferedImage.getWidth() > 800 || bufferedImage.getHeight() > 800)) {
                    bufferedImage = ImageManipulationUtilities.scaleBufferedImage(bufferedImage, 800);
                }
                if (bufferedImage != null) {
                    writableImage = SwingFXUtils.toFXImage((BufferedImage)bufferedImage, (WritableImage)null);
                }
            }
            catch (Exception var2_3) {
                TILogger.logError(TAG, var2_3.getMessage() + " - image not created");
                writableImage = null;
            }
            catch (Error var2_4) {
                TILogger.logError(TAG, var2_4.getMessage() + " - image not created");
                writableImage = null;
            }
            TILogger.logDetail(TAG, "image processing finished");
        }
        if (null != writableImage) {
            TILogger.logDetail(TAG, "about to exit img is not null");
        } else {
            TILogger.logDetail(TAG, "about to exit img is null");
        }
        return writableImage;
    }

    public static BufferedImage scaleBufferedImage(BufferedImage bufferedImage, int n) {
        Object object;
        if (bufferedImage == null || n <= 0) {
            return null;
        }
        int n2 = bufferedImage.getWidth();
        int n3 = bufferedImage.getHeight();
        int n4 = n2;
        int n5 = n3;
        if (n3 > n2) {
            object = ImageManipulationUtilities.getFinalSize(n2, n3, n);
            n4 = object[0];
            n5 = object[1];
        } else {
            object = ImageManipulationUtilities.getFinalSize(n3, n2, n);
            n5 = object[0];
            n4 = object[1];
        }
        object = new BufferedImage(n4, n5, 2);
        Graphics2D graphics2D = object.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(bufferedImage, 0, 0, n4, n5, null);
        return object;
    }

    private static int[] getFinalSize(int n, int n2, int n3) {
        int[] arrn = new int[2];
        if (n2 > n3) {
            arrn[1] = n3;
            arrn[0] = arrn[1] * n / n2;
        }
        return arrn;
    }

    public static javafx.scene.image.Image isImageFile(File file) {
        TILogger.logDetail(TAG, "Image file " + file.getName() + " is of size: " + file.length());
        resultImg = null;
        try {
            String string = "";
            string = file.getCanonicalPath().contains(" ") ? file.toURI().toString() : file.getCanonicalPath();
            if (!string.startsWith("file:")) {
                string = "file:" + string;
            }
            final String string2 = string;
            final Thread thread = Thread.currentThread();
            Thread thread2 = new Thread(new Runnable(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void run() {
                    try {
                        javafx.scene.image.Image image = null;
                        image = ImageManipulationUtilities.getImage(string2);
                        ImageManipulationUtilities.setResultImage(image);
                        Thread thread2 = thread;
                        synchronized (thread2) {
                            thread.notify();
                        }
                        ImageManipulationUtilities.setResultWasSet(true);
                    }
                    catch (Exception var1_2) {
                        TILogger.logError(TAG, "Error while getting Image.", var1_2);
                    }
                }
            }, "Is Image Background Daemon Thread");
            thread2.setDaemon(true);
            thread2.start();
            while (!ImageManipulationUtilities.resultWasSet()) {
                try {
                    if (Thread.interrupted()) continue;
                    Thread.sleep(100);
                }
                catch (InterruptedException var5_7) {
                    TILogger.logDetail(TAG, "Thread woken up to continue");
                }
                catch (Exception var5_8) {
                    TILogger.logError(TAG, "Error while sleeping thread " + thread.getName(), var5_8);
                }
            }
            thread2 = null;
        }
        catch (IOException var1_2) {
            TILogger.logError(TAG, var1_2.getMessage(), var1_2);
        }
        ImageManipulationUtilities.setResultWasSet(false);
        return resultImg;
    }

    public static boolean isABnWScreenCapture(javafx.scene.image.Image image) {
        return image.getWidth() == 192.0 && image.getHeight() == 128.0;
    }

    public static boolean isAColorScreenCapture(javafx.scene.image.Image image) {
        return image.getWidth() == 320.0 && image.getHeight() == 240.0;
    }

    public static boolean writeBufferToFile(javafx.scene.image.Image image, String string) {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage((javafx.scene.image.Image)image, (BufferedImage)null);
        File file = new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "imagesFolder");
        if (file != null && file.exists()) {
            file.delete();
        }
        file.mkdir();
        file.deleteOnExit();
        File file2 = new File(file, string + ".png");
        boolean bl = false;
        try {
            bl = ImageIO.write((RenderedImage)bufferedImage, "png", file2);
        }
        catch (IOException var6_6) {
            TILogger.logError(TAG, var6_6.getMessage(), var6_6);
        }
        return bl;
    }

    private static void setResultImage(javafx.scene.image.Image image) {
        resultImg = image;
    }

    private static void setResultWasSet(boolean bl) {
        resultWasSet = bl;
    }

    private static boolean resultWasSet() {
        return resultWasSet;
    }

}

