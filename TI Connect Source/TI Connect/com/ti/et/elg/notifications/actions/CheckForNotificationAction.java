/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  org.json.JSONException
 */
package com.ti.et.elg.notifications.actions;

import com.ti.et.elg.commonUISupport.action.TIAction;
import com.ti.et.elg.notifications.JsonWebServiceIds;
import com.ti.et.elg.notifications.connections.JsonSoftwareUpdateConnection;
import com.ti.et.elg.notifications.dialogs.AutomaticNotificationDialog;
import com.ti.et.elg.notifications.requests.JsonSoftwareUpdateRequest;
import com.ti.et.elg.notifications.responses.JsonSoftwareUpdateResponse;
import com.ti.et.utils.platformUtils.PlatformManager;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import org.json.JSONException;

public class CheckForNotificationAction
extends TIAction {
    private static final CheckForNotificationAction INSTANCE = new CheckForNotificationAction();
    private static final Logger LOG = Logger.getLogger(JsonSoftwareUpdateRequest.class.getName());
    private boolean isManualCheck = false;

    private CheckForNotificationAction() {
        this.setDisabled(false);
        this.setName(CheckForNotificationAction.class.getSimpleName());
        this.setEventHandler(new EventHandler<ActionEvent>(){

            public void handle(ActionEvent actionEvent) {
                CheckForNotificationAction.this.isManualCheck = true;
                CheckForNotificationAction.this.checkForNotifications();
            }
        });
    }

    public static CheckForNotificationAction getInstance() {
        return INSTANCE;
    }

    public void checkForNotifications() {
        String string = PlatformManager.getProductVersion();
        String string2 = PlatformManager.getUpdateName();
        String string3 = System.getProperty("user.language").toUpperCase();
        LOG.log(Level.INFO, "productVersion: " + string);
        LOG.log(Level.INFO, "shortName: " + string2);
        LOG.log(Level.INFO, "language: " + string3);
        final JsonSoftwareUpdateRequest jsonSoftwareUpdateRequest = new JsonSoftwareUpdateRequest(string2, string, string3);
        Thread thread = new Thread(null, new Runnable(){
            Map<String, String> jsonResponseValues;

            @Override
            public void run() {
                try {
                    JsonSoftwareUpdateResponse jsonSoftwareUpdateResponse = new JsonSoftwareUpdateResponse(JsonSoftwareUpdateConnection.getInstance().askForUpdateInformation(jsonSoftwareUpdateRequest));
                    this.jsonResponseValues = jsonSoftwareUpdateResponse.getResponseValuesParsed();
                }
                catch (JSONException var1_2) {
                    LOG.log(Level.SEVERE, "Error while connecting to JSON Web Service");
                    if (CheckForNotificationAction.this.isManualCheck) {
                        Platform.runLater((Runnable)new Runnable(){

                            @Override
                            public void run() {
                                new AutomaticNotificationDialog(AutomaticNotificationDialog.DialogIds.CONNECTION_ERROR).show();
                            }
                        });
                    }
                    return;
                }
                boolean bl = false;
                if (this.jsonResponseValues != null && this.jsonResponseValues.size() > 0) {
                    for (Map.Entry<String, String> entry : this.jsonResponseValues.entrySet()) {
                        if (entry.getKey().equalsIgnoreCase(JsonWebServiceIds.RES_ERROR_NUM.getId())) {
                            LOG.info("An error response received from Web Service. No software update!");
                            LOG.info("Error: " + entry.getValue());
                            LOG.info("Description: " + this.jsonResponseValues.get(JsonWebServiceIds.RES_ERROR_DESCRIP.getId()));
                            break;
                        }
                        if (!JsonWebServiceIds.RES_UP_TO_DATE.getId().equalsIgnoreCase(entry.getKey())) continue;
                        if (Boolean.parseBoolean(entry.getValue())) {
                            LOG.info("Software is up to date, no upgrade needed.");
                            break;
                        }
                        if (this.jsonResponseValues.containsKey(JsonWebServiceIds.RES_INFO_URL.getId()) && this.jsonResponseValues.containsKey(JsonWebServiceIds.RES_DOWNLOAD_URL.getId())) {
                            LOG.info("Software upgrade is available, showing upgrade dialog.");
                            bl = true;
                            break;
                        }
                        LOG.info("Software upgrade is available, but not enough paramaters on JSON response");
                        break;
                    }
                }
                if (bl) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            AutomaticNotificationDialog automaticNotificationDialog = new AutomaticNotificationDialog(2.this.jsonResponseValues.get(JsonWebServiceIds.RES_INFO_URL.getId()), 2.this.jsonResponseValues.get(JsonWebServiceIds.RES_DOWNLOAD_URL.getId()));
                            automaticNotificationDialog.show();
                        }
                    });
                } else if (CheckForNotificationAction.this.isManualCheck) {
                    Platform.runLater((Runnable)new Runnable(){

                        @Override
                        public void run() {
                            new AutomaticNotificationDialog(AutomaticNotificationDialog.DialogIds.NO_NOTIFICATION).show();
                        }
                    });
                }
            }

        }, "JSON Web Service connection thread", 262144);
        thread.setDaemon(true);
        thread.start();
    }

}

