/*
 * Decompiled with CFR 0_115.
 */
package com.ti.et.elg.notifications;

public enum JsonWebServiceIds {
    REQ_SHORT_NAME("shortName"),
    REQ_CURRENT_VERSION("currentVersion"),
    REQ_LANG_CODE("languageCode"),
    RES_PROD_NAME("productName"),
    RES_PROD_VERSION("productVersion"),
    RES_SHORT_NAME("shortName"),
    RES_UP_TO_DATE("upToDate"),
    RES_INFO_URL("infoUrl"),
    RES_DOWNLOAD_URL("downloadUrl"),
    RES_ERROR_NUM("errorNumber"),
    RES_ERROR_DESCRIP("errorDescription");
    
    private String id;

    private JsonWebServiceIds(String string2) {
        this.id = string2;
    }

    public String getId() {
        return this.id;
    }
}

