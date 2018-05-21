package com.pporzuczek.rster.Services;

import java.net.URLDecoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.pporzuczek.rster.Models.SearchResult;
import org.springframework.stereotype.Service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Service
public class SearchService {

    public List<SearchResult> searchGoogle(String query, int amount, String language, int offset) throws IOException {

        String GOOGLE_SEARCH_URL = "https://www.google.com/search";
        String searchURL = GOOGLE_SEARCH_URL + "?q="+query+"&num="+amount+"&gl="+language+"&start="+offset;

        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("h3.r > a");

        List<SearchResult> googleSearchResults = new ArrayList<>();
        for (Element result : results) {
            SearchResult searchResult = new SearchResult();
            searchResult.setSource(result.attr("href"));
            searchResult.setHref(result.attr("href")
                        .substring(7, result.attr("href").indexOf("&")));
            searchResult.setTitle(result.text());
            googleSearchResults.add(searchResult);
        }

        return googleSearchResults;
    }

    public List<SearchResult> searchDuckDuckGo(String query, String language) throws IOException {

        String DUCKDUCKGO_SEARCH_URL = "https://duckduckgo.com/html/";
        String searchURL = DUCKDUCKGO_SEARCH_URL + "?q="+query+"&kp=-1&kl="+language+"-"+language;

        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        Elements results = doc.getElementById("links").getElementsByClass("results_links");

        List<SearchResult> duckduckgoSearchResults = new ArrayList<>();
        for (Element result : results) {
            if (results.indexOf(result) != 0) {
                SearchResult searchResult = new SearchResult();
                Element title = result.getElementsByClass("links_main").first().getElementsByTag("a").first();
                searchResult.setHref(URLDecoder.decode(title.attr("href").split("uddg=")[1], "UTF-8" ));
                searchResult.setTitle(title.text());
                duckduckgoSearchResults.add(searchResult);
            }
        }

        return duckduckgoSearchResults;
    }

    public List<SearchResult> searchBing(String query, int amount, String language, int offset) throws IOException {

        String BING_SEARCH_URL = "https://www.bing.com/search";
        String searchURL = BING_SEARCH_URL + "?q="+query+"&count="+amount+"&setlang="+language+"&lf=1"+"&first="+offset;

        Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
        Elements results = doc.select("li.b_algo h2 a");

        List<SearchResult> bingSearchResults = new ArrayList<>();
        for (Element result : results) {
            SearchResult searchResult = new SearchResult();
            searchResult.setHref(result.attr("href"));
            searchResult.setTitle(result.text());
            bingSearchResults.add(searchResult);
        }

        return bingSearchResults;
    }

    }