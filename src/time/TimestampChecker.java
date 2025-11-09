package time;

import model.Link;

public interface TimestampChecker {

    long now();

    boolean active(long timestamp, int timeLimit);

    default boolean active(Link link) {
        return active(link.getTimestamp(), link.getTimeLimit());
    }
}
