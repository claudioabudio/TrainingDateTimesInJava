package org.example;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WorkPeriods {
    private static LocalTime AM_START_TIME = LocalTime.of(9, 5);

    private static LocalTime PM_START_TIME = LocalTime.of(13, 5);
    private static Duration WORK_PERIOD_LEN = Duration.ofHours(3).plusMillis(30);

    public static WorkPeriod createMorningWorkPeriod(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, AM_START_TIME);
        LocalDateTime endDateTime = startDateTime.plus(WORK_PERIOD_LEN);
        return WorkPeriod.of(startDateTime, endDateTime);
    }

    public static WorkPeriod createAfternoonWorkPeriod(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, PM_START_TIME);
        LocalDateTime endDateTime = startDateTime.plus(WORK_PERIOD_LEN);
        return WorkPeriod.of(startDateTime, endDateTime);
    }

    public static List<WorkPeriod> generateWorkPeriods(LocalDate startDate, int dayCount) {
        List<LocalDate> workingDays = generateWorkingDays(startDate, dayCount);
        return generateWorkPeriods(workingDays);
    }

    private static List<WorkPeriod> generateWorkPeriods(List<LocalDate> workingDays) {
        return workingDays.stream()
                .flatMap(date -> Stream.of(createMorningWorkPeriod(date), createAfternoonWorkPeriod(date)))
                .collect(Collectors.toList());
    }

    public static List<LocalDate> generateWorkingDays(LocalDate startDate, int dayCount) {
        return Stream.iterate(startDate, date -> date.with(nextWorkingDay))
                .limit(dayCount)
                .collect(Collectors.toList());
    }

    public static TemporalAdjuster nextWorkingDay = d -> DayOfWeek.from(d) != DayOfWeek.FRIDAY
            ? d.plus(1, ChronoUnit.DAYS)
            : d.with(TemporalAdjusters.next(DayOfWeek.MONDAY));

    public static boolean isWorkingDay(LocalDate date) {
        return !date.getDayOfWeek().equals(DayOfWeek.SATURDAY) &&
                !date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }

    public static void main(String... args) {
        List<WorkPeriod> workPeriods = generateWorkPeriods(LocalDate.ofEpochDay(0), 10);
        workPeriods.forEach(System.out::println);
    }
}
