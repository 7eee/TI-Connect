/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.event.EventTarget
 *  javafx.event.EventType
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Menu
 *  javafx.scene.control.MenuBar
 *  javafx.scene.control.MenuButton
 *  javafx.scene.input.KeyCode
 *  javafx.scene.input.KeyEvent
 *  javafx.scene.input.MouseButton
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.input.ScrollEvent
 */
package com.ti.et.elg.commonUISupport.UINavigation;

import com.ti.et.utils.platformUtils.PlatformManager;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.WeakHashMap;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class SceneDecorator {
    private static final WeakHashMap<Scene, Object[]> cache = new WeakHashMap();
    private static boolean onlyAltDown = false;
    private static boolean mouseKeyDown = false;

    public static void SD_directMainMenuNavigationHook(MenuBar menuBar) {
        Scene scene = menuBar.getScene();
        assert (scene != null);
        SceneDecorator.SD_directMainMenuNavigationHook(scene, menuBar);
    }

    public static void SD_directMainMenuNavigationHook(Scene scene, MenuBar menuBar) {
        if (!PlatformManager.isWindows()) {
            return;
        }
        final WeakReference<MenuBar> weakReference = new WeakReference<MenuBar>(menuBar);
        Object[] arrobject = cache.get((Object)scene);
        if (arrobject == null) {
            arrobject = new Object[]{null, null, null, null};
            cache.put(scene, arrobject);
        } else {
            scene.removeEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)arrobject[0]);
            scene.removeEventFilter(KeyEvent.KEY_RELEASED, (EventHandler)arrobject[1]);
            scene.removeEventFilter(MouseEvent.ANY, (EventHandler)arrobject[2]);
            scene.removeEventFilter(ScrollEvent.ANY, (EventHandler)arrobject[3]);
        }
        arrobject[0] = new EventHandler<KeyEvent>(){

            public void handle(KeyEvent keyEvent) {
                onlyAltDown = !mouseKeyDown && !keyEvent.isConsumed() && !keyEvent.isControlDown() && !keyEvent.isMetaDown() && !keyEvent.isShiftDown() && !keyEvent.isShortcutDown() && SceneDecorator.isAltKey(keyEvent);
            }
        };
        arrobject[1] = new EventHandler<KeyEvent>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public void handle(KeyEvent keyEvent) {
                boolean bl;
                block8 : {
                    Menu menu2;
                    if (!onlyAltDown || keyEvent.isConsumed() || keyEvent.isControlDown() || keyEvent.isMetaDown() || keyEvent.isShiftDown() || keyEvent.isShortcutDown() || !SceneDecorator.isAltKey(keyEvent)) {
                        return;
                    }
                    onlyAltDown = false;
                    MenuBar menuBar = (MenuBar)weakReference.get();
                    if (menuBar == null) {
                        return;
                    }
                    ObservableList observableList = menuBar.getMenus();
                    if (observableList.isEmpty()) {
                        return;
                    }
                    bl = true;
                    for (Menu menu2 : observableList) {
                        if (!menu2.isShowing()) continue;
                        menu2.hide();
                        break block8;
                    }
                    Iterator iterator = menuBar;
                    try {
                        while (!(iterator instanceof MenuButton) && iterator instanceof Parent) {
                            iterator = (Node)((Parent)iterator).getChildrenUnmodifiable().get(0);
                        }
                    }
                    catch (IndexOutOfBoundsException var6_7) {
                        return;
                    }
                    if (!(iterator instanceof MenuButton)) return;
                    menu2 = iterator.getOnMouseEntered();
                    if (menu2 != null) {
                        menu2.handle(null);
                    }
                    Event.fireEvent((EventTarget)iterator, (Event)MouseEvent.impl_mouseEvent((double)0.0, (double)0.0, (double)0.0, (double)0.0, (MouseButton)MouseButton.PRIMARY, (int)1, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)true, (boolean)false, (boolean)false, (boolean)false, (EventType)MouseEvent.MOUSE_PRESSED));
                    Event.fireEvent((EventTarget)iterator, (Event)MouseEvent.impl_mouseEvent((double)0.0, (double)0.0, (double)0.0, (double)0.0, (MouseButton)MouseButton.PRIMARY, (int)0, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (boolean)false, (EventType)MouseEvent.MOUSE_RELEASED));
                }
                if (!bl) return;
                keyEvent.consume();
            }
        };
        arrobject[2] = new EventHandler<MouseEvent>(){

            public void handle(MouseEvent mouseEvent) {
                if (mouseKeyDown = mouseEvent.isMiddleButtonDown() || mouseEvent.isPrimaryButtonDown() || mouseEvent.isSecondaryButtonDown()) {
                    onlyAltDown = false;
                }
            }
        };
        arrobject[3] = new EventHandler<ScrollEvent>(){

            public void handle(ScrollEvent scrollEvent) {
                onlyAltDown = false;
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (EventHandler)arrobject[0]);
        scene.addEventFilter(KeyEvent.KEY_RELEASED, (EventHandler)arrobject[1]);
        scene.addEventFilter(MouseEvent.ANY, (EventHandler)arrobject[2]);
        scene.addEventFilter(ScrollEvent.ANY, (EventHandler)arrobject[3]);
    }

    private static boolean isAltKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        boolean bl = keyCode == KeyCode.ALT || keyCode == KeyCode.ALT_GRAPH;
        return bl;
    }

}

