package com.company.llp.spediteur.common.model;

public class SettingBean {
    private long id;
    private int appId; // velo-de-piste =1
    private String forwardURL;
    private boolean isEnabled;
    private String extractionKey;

    public long getId() {
        return id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getForwardURL() {
        return forwardURL;
    }

    public void setForwardURL(String forwardURL) {
        this.forwardURL = forwardURL;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getExtractionKey() {
        return extractionKey;
    }

    public void setExtractionKey(String extractionKey) {
        this.extractionKey = extractionKey;
    }

    @Override
    public String toString() {
        return "SettingBean{" +
                "id=" + id +
                ", appId=" + appId +
                ", forwardURL='" + forwardURL + '\'' +
                ", isEnabled=" + isEnabled +
                ", extractionKey='" + extractionKey + '\'' +
                '}';
    }
}
