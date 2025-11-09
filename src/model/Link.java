package model;

public class Link {

    private final String key;
    private final String url;
    private final String owner;
    private final long timestamp;
    private final int timeLimit;

    private int viewLimit;

    public Link(String key, String value, String owner, long timestamp, int timeLimit, int viewLimit) {
        this.key = key;
        this.url = value;
        this.owner = owner;
        this.timestamp = timestamp;
        this.timeLimit = timeLimit;
        this.viewLimit = viewLimit;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

    public String getOwner() {
        return owner;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getViewLimit() {
        return viewLimit;
    }

    public void setViewLimit(int viewLimit) {
        this.viewLimit = viewLimit;
    }
}
