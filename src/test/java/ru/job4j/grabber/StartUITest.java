package ru.job4j.grabber;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StartUITest {

    @Test
    public void testPrinting() {
        StartUI startUI = new StartUI();
        String str = startUI.printing();
        assertThat(str, is("hello world"));
    }
}