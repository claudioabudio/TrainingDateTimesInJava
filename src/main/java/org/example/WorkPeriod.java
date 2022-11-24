package org.example;

import java.time.LocalDateTime;

public class WorkPeriod {
    private LocalDateTime start;
    private LocalDateTime end;

    private WorkPeriod(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "WorkPeriod{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }

    public static WorkPeriod of(LocalDateTime start, LocalDateTime end) {
        return new WorkPeriod(start, end);
    }
}
