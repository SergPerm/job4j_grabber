package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers";
        for (int i = 1; i <= 5; i++) {
            String urlWithPage = url + "/" + i;
            System.out.println(urlWithPage);
            Document doc = Jsoup.connect(urlWithPage).get();
            Elements row1 = doc.select(".postslisttopic");

            for (Element td : row1) {
                Element href = td.child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                Element parent = td.parent();
                System.out.println(parent.child(5).text());
            }
        }
    }
}