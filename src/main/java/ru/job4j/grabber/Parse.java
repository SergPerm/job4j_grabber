package ru.job4j.grabber;

import java.util.List;

public interface Parse {

    /**
     * list(link) - этот метод загружает список объявлений по ссылке типа
     * - https://www.sql.ru/forum/job-offers/1
     *
     * detail(link) - этот метод загружает детали объявления по ссылке типа
     * - https://www.sql.ru/forum/1323839/razrabotchik-java-g-kazan
    */

    List<Post> list(String link);

    Post detail(String link);
}