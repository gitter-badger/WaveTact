package com.techcavern.wavetact.utils.objects;


public class UTime {
    private final String Hostmask;
    private final String networkName;
    private final String channelName;
    private final String type;
    private final long init;
    private long time;

    public UTime(String Hostmask, String networkName, String type, String channelName, long time, long init) {
        this.time = time;
        this.networkName = networkName;
        this.channelName = channelName;
        this.type = type;
        this.Hostmask = Hostmask;
        this.init = init;
    }

    public String getHostmask() {
        return this.Hostmask;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long x) {
        this.time = x;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getNetworkName() {
        return this.networkName;
    }

    public String getType() {
        return this.type;
    }

    public long getInit() {
        return this.init;
    }
}
