package org.opengraph.lst.core.beans;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author ravi
 *
 * Stat bean
 */
public class Stat {
    private String id;
    private Type type;
    private String app;
    private String flow;
    private Map<String, String> data;
    private LocalDateTime receivedOn;
    private long timeToCompleteInMillis;

    public Stat() {
        this.receivedOn = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public LocalDateTime getReceivedOn() {
        return receivedOn;
    }

    public void setReceivedOn(LocalDateTime receivedOn) {
        this.receivedOn = receivedOn;
    }

    public long getTimeToCompleteInMillis() {
        return timeToCompleteInMillis;
    }

    public void setTimeToCompleteInMillis(long timeToCompleteInMillis) {
        this.timeToCompleteInMillis = timeToCompleteInMillis;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stat{");
        sb.append("id='").append(id).append('\'');
        sb.append(", type=").append(type);
        sb.append(", app='").append(app).append('\'');
        sb.append(", flow='").append(flow).append('\'');
        sb.append(", data=").append(data);
        sb.append(", receivedOn=").append(receivedOn);
        sb.append(", timeToCompleteInMillis=").append(timeToCompleteInMillis);
        sb.append('}');
        return sb.toString();
    }

    public enum Type {
        START, END, DATA
    }
}
