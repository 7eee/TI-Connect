/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 */
package com.ti.et.elg.commonUISupport.UINavigation;

import com.ti.et.utils.platformUtils.PlatformManager;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class FXKeysetDefinitions {
    public static boolean shiftable_isSimpleKeyEvent(KeyEvent keyEvent) {
        boolean bl = !keyEvent.isConsumed() && !keyEvent.isControlDown() && !keyEvent.isMetaDown() && !keyEvent.isAltDown() && !keyEvent.isShortcutDown();
        return bl;
    }

    public static boolean isMacOnlyNavKeyEvent(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        boolean bl = PlatformManager.isMac() && (KeyCode.UP == keyCode || KeyCode.DOWN == keyCode || KeyCode.LEFT == keyCode || KeyCode.RIGHT == keyCode) && !keyEvent.isConsumed() && !keyEvent.isControlDown() && keyEvent.isMetaDown() && !keyEvent.isAltDown() && !keyEvent.isShortcutDown();
        return bl;
    }
}

