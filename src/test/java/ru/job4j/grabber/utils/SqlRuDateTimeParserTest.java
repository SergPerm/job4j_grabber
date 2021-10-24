package ru.job4j.grabber.utils;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SqlRuDateTimeParserTest {

    @Test
    public void whenDataIsNumberThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser();
        LocalDateTime ldt = LocalDateTime.of(2021, 9, 20, 15, 26);
        Assert.assertEquals(ldt, parser.parse("20 сен 21, 15:26"));
    }

    @Test
    public void whenDataIsWorldYesterdayThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser(LocalDate.of(2021, 10, 24));
        LocalDateTime ldt = LocalDateTime.of(2021, 10, 23, 23, 6);
        Assert.assertEquals(ldt, parser.parse("вчера, 23:06"));
    }

    @Test
    public void whenDataIsWorldTodayThenOk() {
        SqlRuDateTimeParser parser = new SqlRuDateTimeParser(LocalDate.of(2021, 10, 24));
        LocalDateTime ldt = LocalDateTime.of(2021, 10, 24, 11, 27);
        Assert.assertEquals(ldt, parser.parse("сегодня, 11:27"));
    }
}