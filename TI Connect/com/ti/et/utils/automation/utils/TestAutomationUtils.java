/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.application.Platform
 *  javafx.geometry.Point2D
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.stage.Stage
 *  javafx.stage.Window
 *  org.jemmy.Point
 *  org.jemmy.control.Wrap
 *  org.jemmy.fx.AppExecutor
 *  org.jemmy.fx.ByID
 *  org.jemmy.fx.ByWindowType
 *  org.jemmy.fx.SceneDock
 *  org.jemmy.fx.SceneWrap
 *  org.jemmy.interfaces.Mouse
 *  org.jemmy.interfaces.Parent
 *  org.jemmy.lookup.Lookup
 *  org.jemmy.lookup.LookupCriteria
 */
package com.ti.et.utils.automation.utils;

import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import java.awt.Toolkit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.jemmy.Point;
import org.jemmy.control.Wrap;
import org.jemmy.fx.AppExecutor;
import org.jemmy.fx.ByID;
import org.jemmy.fx.ByWindowType;
import org.jemmy.fx.SceneDock;
import org.jemmy.fx.SceneWrap;
import org.jemmy.interfaces.Mouse;
import org.jemmy.interfaces.Parent;
import org.jemmy.lookup.Lookup;
import org.jemmy.lookup.LookupCriteria;

public class TestAutomationUtils {
    private static final String LOG_TAG = TestAutomationUtils.class.getSimpleName();
    public static final int APP_STARTUP_WAIT_TIME = 5000;
    public static final int STANDARD_CLICK_WAIT_TIME = 300;

    public static void init(Class<? extends Application> class_) {
        if (PlatformManager.isMac()) {
            System.setProperty("javafx.macosx.embedded", "true");
            Toolkit.getDefaultToolkit();
        }
        AppExecutor.executeNoBlock(class_, (String[])new String[0]);
    }

    public static void delay(long l) {
        try {
            Thread.sleep(l);
        }
        catch (IllegalArgumentException | InterruptedException var2_1) {
            TILogger.logError(LOG_TAG, var2_1.getMessage());
        }
    }

    public static SceneDock getCurrentSceneDock() {
        return new SceneDock(new LookupCriteria[]{new ByWindowType<Scene>((Class)Stage.class){

            public boolean check(Scene scene) {
                return true;
            }
        }});
    }

    public static Stage getStageFromSceneDock(SceneDock sceneDock) {
        return (Stage)sceneDock.control().getWindow();
    }

    public static Wrap<? extends Node> getWrapNode(SceneDock sceneDock, final String string) {
        return sceneDock.asParent().lookup((Class)Node.class, (LookupCriteria)new ByID<Node>(string){

            public String toString() {
                return "ID='" + string + "'";
            }
        }).wrap();
    }

    public static String getRootIDFromSceneDock(SceneDock sceneDock) {
        Scene scene = sceneDock.control();
        javafx.scene.Parent parent = scene.getRoot();
        return parent.getId();
    }

    public static void bringStageToFront(final Stage stage) {
        if (Platform.isFxApplicationThread()) {
            stage.toFront();
        } else {
            Platform.runLater((Runnable)new Runnable(){

                @Override
                public void run() {
                    stage.toFront();
                }
            });
        }
    }

    public static void clickComponentByID(SceneDock sceneDock, String string) {
        TestAutomationUtils.clickComponentByID(sceneDock, string, 1);
    }

    public static void clickComponentByID(SceneDock sceneDock, String string, int n) {
        Wrap<? extends Node> wrap = TestAutomationUtils.getWrapNode(sceneDock, string);
        TestAutomationUtils.clickComponent(wrap, n);
    }

    public static void clickComponent(Wrap<? extends Node> wrap) {
        TestAutomationUtils.clickComponent(wrap, 1);
    }

    public static void clickComponent(Wrap<? extends Node> wrap, int n) {
        for (int i = 0; i < n; ++i) {
            wrap.mouse().click();
            TestAutomationUtils.delay(300);
        }
    }

    public static void pressComponent(Wrap<? extends Node> wrap, int n) {
        wrap.mouse().press();
        TestAutomationUtils.delay(n);
        wrap.mouse().release();
    }

    public static boolean checkFocusForNode(Wrap<? extends Node> wrap) {
        TILogger.logInfo(LOG_TAG, "Checking focus for node " + ((Node)wrap.getControl()).getId());
        Node node = TestAutomationUtils.getCurrentlyFocusedNodeinCurrentScene();
        String string = node.getId();
        boolean bl = string.equals(((Node)wrap.getControl()).getId());
        TILogger.logInfo(LOG_TAG, "Node " + wrap.toString());
        String string2 = bl ? " has focus.\n" : " doesn't have focus, " + string + " has focus instead.\n";
        TILogger.logInfo(LOG_TAG, string2);
        return bl;
    }

    public static Node getCurrentlyFocusedNodeinCurrentScene() {
        TILogger.logInfo(LOG_TAG, "Checking focused node");
        SceneDock sceneDock = TestAutomationUtils.getCurrentSceneDock();
        TILogger.logDetail(LOG_TAG, "Got Scene");
        SceneWrap sceneWrap = sceneDock.wrap();
        TILogger.logDetail(LOG_TAG, "Got Scene Wrap");
        Node node = ((Scene)sceneWrap.getControl()).getFocusOwner();
        if (node != null) {
            TILogger.logInfo(LOG_TAG, "Got focus owner " + node.toString());
            ByID byID = new ByID(node.getId());
            Lookup lookup = sceneDock.asParent().lookup((Class)Node.class, (LookupCriteria)byID);
            Wrap wrap = lookup.wrap();
            return (Node)wrap.getControl();
        }
        return null;
    }

    public static Point convertPointToScenePoint(Wrap<? extends Node> wrap, Point point) {
        Point2D point2D = ((Node)wrap.getControl()).localToScene(new Point2D((double)point.getX(), (double)point.getY()));
        Point point2 = new Point(point2D.getX(), point2D.getY());
        return point2;
    }

}

