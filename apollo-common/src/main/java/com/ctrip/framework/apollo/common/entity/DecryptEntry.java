package com.ctrip.framework.apollo.common.entity;

public class DecryptEntry {
    private String decrypted;
    private String mac;

    public DecryptEntry(String decrypted, String mac) {
        super();
        this.decrypted = decrypted;
        this.mac = mac;
    }

    public String getDecrypted() {
        return decrypted;
    }

    public void setDecrypted(String decrypted) {
        this.decrypted = decrypted;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("DecryptEntry [decrypted=");
        builder.append(decrypted);
        builder.append(", mac=");
        builder.append(mac);
        builder.append("]");
        return builder.toString();
    }

}