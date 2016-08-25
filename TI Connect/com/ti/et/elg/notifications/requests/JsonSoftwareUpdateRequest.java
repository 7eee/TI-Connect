/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.ti.et.elg.notifications.requests;

import com.ti.et.elg.notifications.JsonWebServiceIds;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSoftwareUpdateRequest {
    private static final Logger LOG = Logger.getLogger(JsonSoftwareUpdateRequest.class.getName());
    private String shortName;
    private String softwareVersion;
    private String languageCode;

    public JsonSoftwareUpdateRequest(String string, String string2, String string3) {
        if (!(this.isValidParameter(string) && this.isValidParameter(string2) && this.isValidParameter(string3))) {
            LOG.severe("Incomplete parameters to build JSON request!");
            throw new IllegalArgumentException("Incomplete parameters to build JSON request!");
        }
        this.shortName = string;
        this.softwareVersion = string2;
        this.languageCode = string3;
    }

    public void setShortName(String string) {
        if (!this.isValidParameter(string)) {
            throw new IllegalArgumentException("Short Name can not be null or empty!");
        }
        this.shortName = string;
    }

    public void setOsVersion(String string) {
        if (!this.isValidParameter(string)) {
            throw new IllegalArgumentException("OS Version can not be null or empty!");
        }
        this.softwareVersion = string;
    }

    public void setLanguageCode(String string) {
        if (!this.isValidParameter(string)) {
            throw new IllegalArgumentException("Language Code can not be null or empty!");
        }
        this.languageCode = string;
    }

    public String getRequest() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(JsonWebServiceIds.REQ_SHORT_NAME.getId(), (Object)this.shortName);
            jSONObject.put(JsonWebServiceIds.REQ_CURRENT_VERSION.getId(), (Object)this.softwareVersion);
            jSONObject.put(JsonWebServiceIds.REQ_LANG_CODE.getId(), (Object)this.languageCode);
        }
        catch (JSONException var2_2) {
            LOG.severe("Error building the JSON Request!");
            throw var2_2;
        }
        LOG.info("JSON request built successfully: " + jSONObject.toString());
        return jSONObject.toString();
    }

    private boolean isValidParameter(String string) {
        return string != null && !string.trim().isEmpty();
    }
}

