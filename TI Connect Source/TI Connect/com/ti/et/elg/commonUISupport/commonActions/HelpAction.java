/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.HostServices
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 */
package com.ti.et.elg.commonUISupport.commonActions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.commonUISupport.commonConstants.CommonConstants;
import com.ti.et.utils.logger.TILogger;
import com.ti.et.utils.platformUtils.PlatformManager;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class HelpAction
extends TIAction {
    private static final HelpAction INSTANCE = new HelpAction();

    private HelpAction() {
        this.setName(HelpAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                TILogger.logInfo("HelpAction", "Executing HelpAction");
                String string = HelpAction.this.getLocaleSuffix();
                String string2 = "TIConnectHelp" + string + ".pdf";
                String string3 = PlatformManager.getAppHome() + "help/" + string2;
                File file = new File(string3);
                if (!file.canRead()) {
                    TILogger.logError("HelpAction", "error, Help file not found");
                    return;
                }
                HelpAction.this.launch(file);
            }
        });
    }

    public static HelpAction getInstance() {
        return INSTANCE;
    }

    protected String getLocaleSuffix() {
        Locale locale = Locale.getDefault();
        if (locale.getLanguage().equals(new Locale("de").getLanguage())) {
            return "_DE";
        }
        if (locale.getLanguage().equals(new Locale("es").getLanguage())) {
            return "_ES";
        }
        if (locale.getLanguage().equals(new Locale("fr").getLanguage())) {
            return "_FR";
        }
        if (locale.getLanguage().equals(new Locale("nl").getLanguage())) {
            return "_NL";
        }
        if (locale.getLanguage().equals(new Locale("pt").getLanguage())) {
            return "_PT";
        }
        if (locale.getLanguage().equals(new Locale("sv").getLanguage())) {
            return "_SV";
        }
        if (locale.getLanguage().equals(new Locale("en", "GB").getLanguage()) && locale.getCountry().equals(new Locale("en", "GB").getCountry())) {
            return "_EN_GB";
        }
        return "_EN";
    }

    private void launch(final File file) {
        if (PlatformManager.isMac()) {
            new Thread(){

                @Override
                public void run() {
                    try {
                        TILogger.logInfo("HelpAction", "opening " + file.getName() + " " + file.getParentFile());
                        Runtime runtime = Runtime.getRuntime();
                        runtime.exec("open " + file.getName(), null, file.getParentFile());
                    }
                    catch (IOException var1_2) {
                        TILogger.logError("HelpAction", "Could not open help file", var1_2);
                    }
                }
            }.start();
        } else {
            new Thread(){

                @Override
                public void run() {
                    CommonConstants.hostServices.showDocument(file.getAbsolutePath());
                }
            }.start();
        }
    }

}

