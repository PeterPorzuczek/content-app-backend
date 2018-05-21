package com.pporzuczek.rster.Services;

import com.chimbori.crux.articles.Article;
import com.chimbori.crux.articles.ArticleExtractor;
import com.pporzuczek.rster.Models.ReadResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReadabilityService {

    public String getHtmlPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        return doc.html();
    }

    public Article extractArticleFromHtml(String url, String html) {
        return ArticleExtractor.with(url, html)
                .extractMetadata()
                .extractContent()
                .article();
    }

    public String readAsHtml(String url) throws IOException {
        String html = getHtmlPage(url);
        Article article = extractArticleFromHtml(url, html);

        if (article.document.toString().split(" ").length > 150) {
            return "<h2>" + article.title + "</h2>" + article.document.toString();
        } else {
            return "error";
        }
    }

    public ReadResult read(String url) throws IOException {
        String html = getHtmlPage(url);
        Article article = extractArticleFromHtml(url, html);

        ReadResult readResult = new ReadResult();
        int words = article.document.toString().split(" ").length;
        if (words > 150) {
            readResult.setWords(words);
            readResult.setHtml("<h2>" + article.title + "</h2>" + article.document.toString());
        } else {
            readResult.setWords(0);
            readResult.setHtml("");
        }
        return readResult;
    }
}
