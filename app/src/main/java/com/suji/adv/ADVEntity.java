package com.suji.adv;

public class ADVEntity {
    private long id;
    private String platform;
    private String time;
    private float cpm;

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getCpm() {
        return cpm;
    }

    public void setCpm(float cpm) {
        this.cpm = cpm;
    }
}
