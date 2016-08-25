/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.collections.ObservableMap
 *  javafx.geometry.Point2D
 *  javafx.scene.Node
 *  javafx.scene.Parent
 */
package com.ti.et.elg.commonUISupport.utils;

import com.ti.et.elg.commonUISupport.UINavigation.ChildFocusLostListener;
import com.ti.et.utils.logger.TILogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;

public class FXNodeUtils {
    private static String listenerDecorationKeyStr = String.format("%s - %s", FXNodeUtils.class.getSimpleName(), "ListenerDecoration");

    private static String getListenerDecorationKeyStr() {
        return listenerDecorationKeyStr;
    }

    private static boolean _childHasFocus(Parent parent) {
        boolean bl = false;
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node == null || node == parent) continue;
            if (node.getParent() != parent) {
                TILogger.logDetail(FXNodeUtils.class.getSimpleName(), String.format("Found a child node with an bad parent!\nCHILD=%s\nBAD_PARENT=%s\nREAL_PARENT=%s", node.toString(), node.getParent().toString(), parent.toString()));
                continue;
            }
            if (!node.isFocused() && (!(node instanceof Parent) || !FXNodeUtils._childHasFocus((Parent)node))) continue;
            bl = true;
            break;
        }
        return bl;
    }

    public static boolean ancestorOf(Node node, Node node2) {
        boolean bl = false;
        if (node != null && node2 != null && node2 != node && node instanceof Parent) {
            while ((node2 = node2.getParent()) != null) {
                if (node2 != node) continue;
                bl = true;
                break;
            }
        }
        return bl;
    }

    public static boolean childHasFocus(Node node) {
        boolean bl = false;
        if (node != null && node instanceof Parent) {
            bl = FXNodeUtils._childHasFocus((Parent)node);
        }
        return bl;
    }

    public static Point2D originToParent(Node node) {
        Point2D point2D = node.localToParent(0.0, 0.0);
        return point2D;
    }

    public static Object[] getImmediateChildFocusLostAncestorListeners(Node[] arrnode, List<Node> list, Node node) {
        Parent parent;
        Object[] arrobject = null;
        while ((parent = node.getParent()) != null && parent != node) {
            if ((parent instanceof ChildFocusLostListener || FXNodeUtils.hasListenerDecoration((Node)parent, ChildFocusLostListener.class)) && list.contains((Object)parent)) {
                arrobject = FXNodeUtils.getListenerDecoration((Node)parent, ChildFocusLostListener.class);
                if (parent instanceof ChildFocusLostListener) {
                    if (arrobject == null) {
                        arrobject = new Object[]{parent};
                    } else if (arrobject != null && !Arrays.asList(arrobject).contains((Object)parent)) {
                        Object[] arrobject2 = new Object[arrobject.length + 1];
                        arrobject2[0] = parent;
                        System.arraycopy(arrobject, 0, arrobject2, 1, arrobject.length);
                        arrobject = arrobject2;
                    }
                }
                if (arrobject != null) {
                    if (arrnode == null || arrnode.length <= 0) break;
                    arrnode[0] = parent;
                    break;
                }
            }
            node = parent;
        }
        return arrobject;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void addListenerDecoration(Node node, Class<?> class_, Object object) {
        String string = FXNodeUtils.getListenerDecorationKeyStr();
        ObservableMap observableMap = node.getProperties();
        ConcurrentHashMap concurrentHashMap = null;
        List<Object> list = null;
        ObservableMap observableMap2 = observableMap;
        synchronized (observableMap2) {
            concurrentHashMap = (ConcurrentHashMap)observableMap.get((Object)string);
            if (concurrentHashMap == null) {
                concurrentHashMap = new ConcurrentHashMap(1);
                observableMap.put((Object)string, concurrentHashMap);
            } else {
                list = (List<Object>)concurrentHashMap.get(class_);
            }
            if (list == null) {
                list = Collections.synchronizedList(new ArrayList(1));
                concurrentHashMap.put(class_, list);
            }
            if (!list.contains(object)) {
                list.add(object);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean hasListenerDecoration(Node node, Class<?> class_) {
        String string = FXNodeUtils.getListenerDecorationKeyStr();
        ObservableMap observableMap = node.getProperties();
        boolean bl = false;
        ObservableMap observableMap2 = observableMap;
        synchronized (observableMap2) {
            List list;
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)observableMap.get((Object)string);
            if (concurrentHashMap != null && (list = (List)concurrentHashMap.get(class_)) != null) {
                bl = !list.isEmpty();
            }
        }
        return bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object[] getListenerDecoration(Node node, Class<?> class_) {
        String string = FXNodeUtils.getListenerDecorationKeyStr();
        ObservableMap observableMap = node.getProperties();
        Object[] arrobject = null;
        ObservableMap observableMap2 = observableMap;
        synchronized (observableMap2) {
            List list;
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)observableMap.get((Object)string);
            if (concurrentHashMap != null && (list = (List)concurrentHashMap.get(class_)) != null) {
                arrobject = list.toArray();
            }
        }
        return arrobject;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void removeListenerDecoration(Node node, Class<?> class_, Object object) {
        ObservableMap observableMap;
        String string = FXNodeUtils.getListenerDecorationKeyStr();
        ObservableMap observableMap2 = observableMap = node.getProperties();
        synchronized (observableMap2) {
            List list;
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap)observableMap.get((Object)string);
            if (concurrentHashMap != null && (list = (List)concurrentHashMap.get(class_)) != null && list.remove(object) && list.isEmpty()) {
                concurrentHashMap.remove(class_);
                if (concurrentHashMap.isEmpty()) {
                    observableMap.put((Object)string, (Object)null);
                }
            }
        }
    }
}

