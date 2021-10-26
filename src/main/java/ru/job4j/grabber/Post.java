package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.TextNode;
import ru.job4j.grabber.utils.SqlRuDateTimeParser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id
                && title.equals(post.title)
                && link.equals(post.link)
                && created.equals(post.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link, created);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", link='" + link + '\''
                + ", description='" + description + '\''
                + ", created=" + created
                + '}';
    }

    private void getDescriptionFromUrl(String url) throws IOException {
        int numberOfMsg = 0;

        Document doc = Jsoup.connect(url).get();

        List<TextNode> textMsgDescription = doc.select(".msgTable")
                .get(numberOfMsg).select(".msgBody")
                .get(1).textNodes();
        StringBuilder sb = new StringBuilder();
        for (TextNode tn : textMsgDescription) {
            sb.append(tn.getWholeText());
            sb.append(System.lineSeparator());
        }
        description = sb.toString().trim();

        String wholeText = doc.select(".msgTable")
                .get(numberOfMsg).select(".msgFooter")
                .get(0).textNodes()
                .get(0).getWholeText();
        String data = wholeText.substring(0, wholeText.length() - 5).trim();
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        created = sqlRuDateTimeParser.parse(data);
    }

    public static void main(String[] args) throws IOException {
        Post post = new Post();
        String url = "https://www.sql.ru/forum/"
                + "1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        post.getDescriptionFromUrl(url);
        System.out.println(post);
    }
}
