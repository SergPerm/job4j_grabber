package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import ru.job4j.grabber.Parse;
import ru.job4j.grabber.Post;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String link) throws IOException {

        List<Post> posts = new ArrayList<>();

        int amountOfPage = 1;
        for (int i = 1; i <= amountOfPage; i++) {
            String urlWithPage = link + "/" + i;
            Document doc = Jsoup.connect(urlWithPage).get();
            Elements row1 = doc.select(".postslisttopic");

            for (Element td : row1) {
                Element href = td.child(0);
                String ref = href.attr("href");
                String title = href.text();
                Post tmp = this.detail(ref);
                tmp.setTitle(title);
                posts.add(tmp);
            }
        }
        return posts;
    }

    @Override
    public Post detail(String link) throws IOException {

        Document doc = Jsoup.connect(link).get();

        int numberOfMsg = 0;
        List<TextNode> textMsgDescription = doc.select(".msgTable")
                .get(numberOfMsg).select(".msgBody")
                .get(1).textNodes();
        StringBuilder sb = new StringBuilder();
        for (TextNode tn : textMsgDescription) {
            sb.append(tn.getWholeText());
            sb.append(System.lineSeparator());
        }
        String description = sb.toString().trim();

        String wholeText = doc.select(".msgTable")
                .get(numberOfMsg).select(".msgFooter")
                .get(0).textNodes()
                .get(0).getWholeText();
        String data = wholeText.substring(0, wholeText.length() - 5).trim();
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        LocalDateTime created = sqlRuDateTimeParser.parse(data);

        Post postTmp = new Post();
        postTmp.setLink(link);
        postTmp.setDescription(description);
        postTmp.setCreated(created);

        return postTmp;
    }

    public static void main(String[] args) throws Exception {
        String url = "https://www.sql.ru/forum/job-offers";
        SqlRuParse sqlRuParse1 = new SqlRuParse();
        List<Post> posts = sqlRuParse1.list(url);
        for (Post p : posts) {
            System.out.println(p);
        }
    }
}