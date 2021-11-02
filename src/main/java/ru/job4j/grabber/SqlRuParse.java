package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DateTimeParser dateTimeParser;

    public SqlRuParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Post> list(String link) {

        List<Post> posts = new ArrayList<>();

        int amountOfPage = 1;
        for (int i = 1; i <= amountOfPage; i++) {
            String urlWithPage = link + "/" + i;
            Document doc = null;
            try {
                doc = Jsoup.connect(urlWithPage).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements row1 = null;
            if (doc != null) {
                row1 = doc.select(".postslisttopic");
            }

            if (row1 != null) {
                for (Element td : row1) {
                    Element href = td.child(0);
                    String ref = href.attr("href");
                    Post tmp = this.detail(ref);
                    posts.add(tmp);
                }
            }
        }
        return posts;
    }

    @Override
    public Post detail(String link) {

        int numberOfMsg = 0;
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element msgTable = null;
        if (doc != null) {
            msgTable = doc.select(".msgTable").get(numberOfMsg);
        }

        String title = null;
        if (msgTable != null) {
            title = msgTable.selectFirst(".messageHeader").text();
        }

        String description = getDescriptionWithLineSeparator(msgTable);

        LocalDateTime created = getDateTimeCreated(msgTable);

        Post postTmp = new Post();
        postTmp.setLink(link);
        postTmp.setTitle(title);
        postTmp.setDescription(description);
        postTmp.setCreated(created);

        return postTmp;
    }

    /**
     * метод getDateTimeCreated(Element msgTable) извлекает
     * из Element msgTable время и дату создания поста в текстовом виде и преобразует
     * в LocalDateTime с помощью dateTimeParser.parse(data)
     * @param msgTable - элемент для разбора
     * @return created or null - LocalDateTime created.
     */
    private LocalDateTime getDateTimeCreated(Element msgTable) {
        String wholeText = null;
        if (msgTable != null) {
            wholeText = msgTable.select(".msgFooter")
                    .get(0).textNodes()
                    .get(0).getWholeText();
        }
        String data = null;
        if (wholeText != null) {
            data = wholeText.substring(0, wholeText.length() - 5).trim();
        }
        LocalDateTime created = null;
        if (data != null) {
            created = dateTimeParser.parse(data);
        }
        return created;
    }

    /**
     * метод getDescriptionWithLineSeparator(Element msgTable) разбирает Element msgTable
     * на составляющие части, выделяет из них текст и собирает текст в единую строку
     * с помощью StringBuilder и System.lineSeparator для придания удобочитаемого вида
     * @param msgTable - элемент для разбора
     * @return description - строка без пробелов до и после текста.
     */
     private String getDescriptionWithLineSeparator(Element msgTable) {
        List<TextNode> textMsgDescription = null;
        if (msgTable != null) {
            textMsgDescription = msgTable.select(".msgBody")
                    .get(1).textNodes();
        }
        StringBuilder sb = new StringBuilder();
        if (textMsgDescription != null) {
            for (TextNode tn : textMsgDescription) {
                sb.append(tn.getWholeText());
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString().trim();
    }

    public static void main(String[] args) {
        String url = "https://www.sql.ru/forum/job-offers";
        DateTimeParser dtp = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse1 = new SqlRuParse(dtp);
        List<Post> posts = sqlRuParse1.list(url);
        for (Post p : posts) {
            System.out.println(p);
        }
    }
}