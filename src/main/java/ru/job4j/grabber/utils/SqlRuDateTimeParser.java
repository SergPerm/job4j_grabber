package ru.job4j.grabber.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "JANUARY"),
            Map.entry("фев", "FEBRUARY"),
            Map.entry("мар", "MARCH"),
            Map.entry("апр", "APRIL"),
            Map.entry("май", "MAY"),
            Map.entry("июн", "JUNE"),
            Map.entry("июл", "JULY"),
            Map.entry("авг", "AUGUST"),
            Map.entry("сен", "SEPTEMBER"),
            Map.entry("окт", "OCTOBER"),
            Map.entry("ноя", "NOVEMBER"),
            Map.entry("дек", "DECEMBER"));

    private LocalDate date;

    public SqlRuDateTimeParser() {
        this.date = LocalDateTime.now().toLocalDate();
    }

    public SqlRuDateTimeParser(LocalDate date) {
        this.date = date;
    }

    @Override
    public LocalDateTime parse(String parse) {
        String[] tmp = parse.split(", ");
        String[] tmpDate = tmp[0].split(" ");
        String[] tmpTime = tmp[1].split(":");
        if (tmpDate.length == 1) {
            if ("вчера".equals(tmpDate[0])) {
                date = date.minusDays(1);
            }
        } else {
            int dayOfMonth = Integer.parseInt(tmpDate[0]);
            int year = 2000 + Integer.parseInt(tmpDate[2]);
            Month month = Month.valueOf(MONTHS.get(tmpDate[1]));
            date = LocalDate.of(year, month, dayOfMonth);
        }
        int hour = Integer.parseInt(tmpTime[0]);
        int minute = Integer.parseInt(tmpTime[1]);
        LocalTime time = LocalTime.of(hour, minute);

        return LocalDateTime.of(date, time);
    }
}
