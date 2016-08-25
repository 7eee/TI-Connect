/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.exports;

public interface TINode {
    public static final int NODE_TYPE_MOCK = 0;
    public static final int NODE_TYPE_CARS = 1;
    public static final int NODE_TYPE_DBUS = 2;

    public String getNodeId();

    public void setNodeParams(String var1, String var2);

    public String getDeviceName();

    public int getNodeType();
}

