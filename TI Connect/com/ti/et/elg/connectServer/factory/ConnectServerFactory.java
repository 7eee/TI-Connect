/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.connectServer.factory;

import com.ti.et.elg.connectServer.autoDetect.AutoDetect;
import com.ti.et.elg.connectServer.commManager.CommManager;
import com.ti.et.elg.connectServer.commManager.TestAutomationAPI;
import com.ti.et.elg.connectServer.deviceComm.DeviceComm;
import com.ti.et.elg.connectServer.exports.AutoDetectInterface;
import com.ti.et.elg.connectServer.exports.CommManagerInterface;
import com.ti.et.elg.connectServer.exports.DeviceCommInterface;
import com.ti.et.elg.connectServer.exports.TestAutomationInterface;
import com.ti.et.elg.connectServer.exports.TranslatorInterface;
import com.ti.et.elg.connectServer.translator.Translator;

public class ConnectServerFactory {
    private static CommManagerInterface commManager;
    private static DeviceCommInterface deviceComm;
    private static TranslatorInterface translator;
    private static AutoDetectInterface autoDetect;
    private static TestAutomationInterface testAutomationAPI;

    private ConnectServerFactory() {
    }

    public static void configureFactory() {
        ConnectServerFactory.configureFactory(new CommManager(), new DeviceComm(), Translator.getInstance(), AutoDetect.getInstance(), new TestAutomationAPI());
    }

    public static void configureFactory(CommManagerInterface commManagerInterface, DeviceCommInterface deviceCommInterface, TranslatorInterface translatorInterface, AutoDetectInterface autoDetectInterface) {
        ConnectServerFactory.configureFactory(commManagerInterface, deviceCommInterface, translatorInterface, autoDetectInterface, null);
    }

    public static void configureFactory(CommManagerInterface commManagerInterface, DeviceCommInterface deviceCommInterface, TranslatorInterface translatorInterface, AutoDetectInterface autoDetectInterface, TestAutomationInterface testAutomationInterface) {
        commManager = commManagerInterface;
        deviceComm = deviceCommInterface;
        translator = translatorInterface;
        autoDetect = autoDetectInterface;
        testAutomationAPI = testAutomationInterface;
    }

    public static CommManagerInterface getCommManager() {
        return commManager;
    }

    public static DeviceCommInterface getDeviceComm() {
        return deviceComm;
    }

    public static TranslatorInterface getTranslator() {
        return translator;
    }

    public static AutoDetectInterface getAutoDetect() {
        return autoDetect;
    }

    public static TestAutomationInterface getTestAutomationAPI() {
        return testAutomationAPI;
    }
}

