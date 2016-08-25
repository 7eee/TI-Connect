/*
 * Decompiled with CFR 0_115.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.ti.et.elg.notifications.responses;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonSoftwareUpdateResponse {
    private String response;

    public JsonSoftwareUpdateResponse(String string) {
        if (!this.isValidParameter(string)) {
            throw new IllegalArgumentException("Incomplete parameters for a JSON response!");
        }
        this.response = string;
    }

    public void setResponse(String string) {
        if (!this.isValidParameter(string)) {
            throw new IllegalArgumentException("Response can not be null or empty!");
        }
        this.response = string;
    }

    public Map<String, String> getResponseValuesParsed() throws JSONException {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONArray jSONArray = new JSONArray(this.response);
        JSONObject jSONObject = jSONArray.getJSONObject(0);
        for (String string : JSONObject.getNames((JSONObject)jSONObject)) {
            hashMap.put(string, jSONObject.get(string).toString());
        }
        return hashMap;
    }

    private boolean isValidParameter(String string) {
        return string != null && !string.trim().isEmpty();
    }
}

