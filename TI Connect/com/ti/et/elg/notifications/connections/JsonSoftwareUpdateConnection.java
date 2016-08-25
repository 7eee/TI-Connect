/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 */
package com.ti.et.elg.notifications.connections;

import com.ti.et.elg.notifications.AutomaticNotificationResourceBundle;
import com.ti.et.elg.notifications.requests.JsonSoftwareUpdateRequest;
import com.ti.et.elg.notifications.util.WebServer;
import com.ti.et.utils.resourceManager.TIResourceBundle;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class JsonSoftwareUpdateConnection {
    private static final Logger LOG = Logger.getLogger(JsonSoftwareUpdateConnection.class.getName());
    private static String webServiceURL;
    private static JsonSoftwareUpdateConnection instance;
    private HttpURLConnection urlConnection;

    private JsonSoftwareUpdateConnection() {
    }

    public static synchronized JsonSoftwareUpdateConnection getInstance() {
        if (instance == null) {
            instance = new JsonSoftwareUpdateConnection();
        }
        return instance;
    }

    public String askForUpdateInformation(JsonSoftwareUpdateRequest jsonSoftwareUpdateRequest) throws JSONException {
        StringBuffer stringBuffer;
        block4 : {
            stringBuffer = new StringBuffer();
            try {
                this.initUrlConnection();
                this.urlConnection.connect();
                DataOutputStream dataOutputStream = new DataOutputStream(this.urlConnection.getOutputStream());
                dataOutputStream.writeBytes(jsonSoftwareUpdateRequest.getRequest());
                dataOutputStream.flush();
                dataOutputStream.close();
                int n = this.urlConnection.getResponseCode();
                if (n == 200) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.urlConnection.getInputStream(), "utf-8"));
                    String string = null;
                    while ((string = bufferedReader.readLine()) != null) {
                        stringBuffer.append(string + "\n");
                    }
                    bufferedReader.close();
                    LOG.info("JSON Response from server: " + stringBuffer);
                    break block4;
                }
                throw new JSONException("Server reply was not the expected. Actual reply " + n);
            }
            catch (Exception var3_4) {
                LOG.log(Level.SEVERE, "Error connecting to JSON Web Service!");
                throw new JSONException((Throwable)var3_4);
            }
        }
        return stringBuffer.insert(0, "[").append("]").toString();
    }

    private void initUrlConnection() throws IOException {
        this.urlConnection = (HttpURLConnection)new URL(webServiceURL).openConnection();
        this.urlConnection.setRequestMethod("POST");
        this.urlConnection.setDoInput(true);
        this.urlConnection.setDoOutput(true);
        this.urlConnection.setUseCaches(false);
        this.urlConnection.setConnectTimeout(10000);
        this.urlConnection.setReadTimeout(10000);
        this.urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        this.urlConnection.setRequestProperty("Accept", "*/*");
    }

    private static void setProxy() {
        String string = System.getenv("TI.HttpProxy");
        if (string == null || string.isEmpty()) {
            string = System.getenv("TI.HttpsProxy");
        }
        String string2 = "";
        String string3 = "";
        if (null != string && !string.isEmpty()) {
            String[] arrstring = string.split(":");
            if (arrstring.length == 2) {
                try {
                    string2 = arrstring[0];
                    string3 = arrstring[1];
                    if (string2 != null && string3 != null) {
                        Properties properties = System.getProperties();
                        properties.put("proxySet", "true");
                        properties.put("http.proxyHost", string2);
                        properties.put("http.proxyPort", string3);
                        LOG.info(properties.getProperty("proxySet"));
                        LOG.info(properties.getProperty("http.proxyHost"));
                        LOG.info(properties.getProperty("http.proxyPort"));
                    }
                    LOG.info("Proxy set succesfully");
                }
                catch (Exception var4_5) {
                    LOG.log(Level.SEVERE, var4_5.getMessage(), var4_5);
                    string2 = null;
                    string3 = null;
                }
            } else {
                LOG.info("Invalid proxy var format..., expecting proxyServer:portNumber");
            }
        } else {
            LOG.info("No proxy info set...");
        }
    }

    static {
        JsonSoftwareUpdateConnection.setProxy();
        WebServer webServer = WebServer.PRODUCTION;
        String string = System.getenv("webUpdateServer");
        if (null != string) {
            if (string.equalsIgnoreCase("test")) {
                webServer = WebServer.TEST;
            } else if (string.equalsIgnoreCase("dev")) {
                webServer = WebServer.DEVELOPMENT;
            } else if (string.equalsIgnoreCase("preprod")) {
                webServer = WebServer.PRE_PRODUCTION;
            } else if (string.equalsIgnoreCase("prod")) {
                webServer = WebServer.PRODUCTION;
            } else if (string.startsWith("custom:")) {
                try {
                    String string2 = string.substring(string.indexOf(":") + 1).trim();
                    if (null != string2 && string2.length() > 0) {
                        webServer = WebServer.CUSTOM;
                        webServer.setServerURL(string2);
                    }
                }
                catch (Exception var2_3) {
                    LOG.log(Level.SEVERE, "exception while setting custom server : " + var2_3.getMessage());
                }
            }
        }
        webServiceURL = webServer.getServerURL() + AutomaticNotificationResourceBundle.BUNDLE.getString("JsonWebUpdate.queryPath");
        LOG.fine("Found server env var set to [" + string + "]");
        LOG.info("Connecting to " + webServiceURL);
    }
}

