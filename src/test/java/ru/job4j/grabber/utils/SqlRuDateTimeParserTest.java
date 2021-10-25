package ru.job4j.grabber.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class SqlRuDateTimeParserTest {

    @Test
    public void whenDataIsNumberThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime ldt = LocalDateTime.of(2021, 9, 20, 15, 26);
        Assert.assertEquals(ldt, parser.parse("20 сен 21, 15:26"));
    }

    @Test
    public void whenDataIsWorldYesterdayThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime ldt = LocalDateTime.of(
                LocalDateTime.now().toLocalDate().minusDays(1),
                LocalTime.of(23, 6));
        Assert.assertEquals(ldt, parser.parse("вчера, 23:06"));
    }

    @Test
    public void whenDataIsWorldTodayThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime ldt = LocalDateTime.of(
                LocalDateTime.now().toLocalDate(),
                LocalTime.of(11, 27));
        Assert.assertEquals(ldt, parser.parse("сегодня, 11:27"));
    }
}