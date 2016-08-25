/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.image.Image
 */
package com.ti.et.elg.commonUISupport.imageUtils;

import com.ti.et.elg.commonUISupport.imageUtils.ImageManipulationUtilities;
import com.ti.et.elg.commonUISupport.imageUtils.TIVarMultiplexer;
import com.ti.et.elg.connectServer.exports.TIDevice;
import com.ti.et.elg.connectServer.exports.TIVar;
import com.ti.et.elg.connectServer.factory.ConnectServerFactory;
import com.ti.et.utils.fileUtils.FileUtils;
import java.io.File;
import javafx.scene.image.Image;

public class TIConvertedImageVarMultiplexer
extends TIVarMultiplexer {
    public static final String PIC_VAR_PREFIX = "Pic";
    public static final String IMAGE_VAR_PREFIX = "Image";
    private Image originalImage;
    private TIVar initialTempVar;
    private TIVar picVar;
    private TIVar imageVar;
    private boolean cropImage = false;
    private ImageKind imageKind = ImageKind.FROM_USER_IMAGE_FILE;

    public TIConvertedImageVarMultiplexer(ImageKind imageKind, File file, int n) {
        super(null);
        this.initialTempVar = ConnectServerFactory.getCommManager().createTIVar(null, "Image" + n, null, 0, 10, 0, 26, true, 15, 115, false);
        this.initialTempVar.setHostFile(FileUtils.fixFileIfInURLFormat(file));
        this.imageKind = imageKind;
    }

    public TIConvertedImageVarMultiplexer(ImageKind imageKind, File file, int n, int n2) {
        super(null);
        String string = "Image";
        int n3 = 15;
        int n4 = 10;
        int n5 = 26;
        boolean bl = true;
        if (n2 <= 4) {
            string = "Pic";
            n3 = 10;
            n4 = 0;
            n5 = 7;
            bl = false;
        }
        this.initialTempVar = ConnectServerFactory.getCommManager().createTIVar(null, string + n, null, 0, n4, 0, n5, bl, n3, 15, false);
        this.initialTempVar.setHostFile(FileUtils.fixFileIfInURLFormat(file));
        this.imageKind = imageKind;
    }

    public ImageKind getImageKind() {
        return this.imageKind;
    }

    public boolean setImage(Image image) {
        return this.setImage(image, false);
    }

    public boolean setImage(Image image, boolean bl) {
        if (ImageManipulationUtilities.isValidImage(image)) {
            this.originalImage = image;
            this.cropImage = bl;
            return true;
        }
        return false;
    }

    protected TIVar getPicVar() {
        if (this.picVar == null && this.originalImage != null) {
            int n = TIConvertedImageVarMultiplexer.getImageNumberFromVarName(this.initialTempVar.getDeviceFileName());
            this.picVar = TIConvertedImageVarMultiplexer.createPicVar(this.initialTempVar.getHostFile(), this.originalImage, n, this.cropImage);
        }
        return this.picVar;
    }

    protected TIVar getImageVar() {
        if (this.imageVar == null && this.originalImage != null) {
            int n = TIConvertedImageVarMultiplexer.getImageNumberFromVarName(this.initialTempVar.getDeviceFileName());
            this.imageVar = TIConvertedImageVarMultiplexer.createImageVar(this.initialTempVar.getHostFile(), this.originalImage, n, this.cropImage);
        }
        return this.imageVar;
    }

    protected static int getImageNumberFromVarName(String string) {
        return Integer.parseInt(string.substring(string.length() - 1, string.length()));
    }

    protected static TIVar createPicVar(File file, Image image, int n, boolean bl) {
        TIVar tIVar = null;
        if (image != null) {
            if (bl || ImageManipulationUtilities.isABnWScreenCapture(image)) {
                image = ImageManipulationUtilities.cropGraphAreaFromImage(image);
            }
            tIVar = ImageManipulationUtilities.fromImageToPicBWVar(image, "Pic" + n);
            tIVar.setHostFile(file);
        }
        return tIVar;
    }

    protected static TIVar createImageVar(File file, Image image, int n, boolean bl) {
        TIVar tIVar = null;
        if (image != null) {
            if (bl || ImageManipulationUtilities.isABnWScreenCapture(image)) {
                image = ImageManipulationUtilities.cropGraphAreaFromImage(image);
            }
            tIVar = ImageManipulationUtilities.fromImageToImageVar(image, "Image" + n);
            tIVar.setHostFile(file);
        }
        return tIVar;
    }

    @Override
    public TIVar getTIVar() {
        return this.initialTempVar;
    }

    @Override
    public TIVar getTIVarForDevice(TIDevice tIDevice) {
        if (tIDevice != null) {
            return tIDevice.isColorScreen() ? this.getImageVar() : this.getPicVar();
        }
        return null;
    }

    public static enum ImageKind {
        FROM_USER_IMAGE_FILE,
        FROM_COLOR_SCREEN_SHOT,
        FROM_BLACK_AND_WHITE_SCREEN_SHOT,
        FROM_WHAT_THE_HECK;
        

        private ImageKind() {
        }
    }

}

