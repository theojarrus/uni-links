package time;

import java.time.LocalDate;

public class TimestampCheckerImpl implements TimestampChecker {

    @Override
    public long now() {
        return nowDate().toEpochDay();
    }

    @Override
    public boolean active(long timestamp, int timeLimit) {
        return LocalDate.ofEpochDay(timestamp).plusDays(timeLimit).isAfter(nowDate());
    }

    private LocalDate nowDate() {
        return LocalDate.now();
    }
}
