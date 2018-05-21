package com.pporzuczek.rster.Controllers;

import com.pporzuczek.rster.Models.SearchResult;
import com.pporzuczek.rster.Services.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search/{language}/{query}/{amount}/{offset}", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResult> getSearchResults(
            @PathVariable("language") String language,
            @PathVariable("query") String query,
            @PathVariable("amount") int amount,
            @PathVariable("offset") int offset) throws IOException {
//      return searchService.searchGoogle(query, amount, language, offset);
//      return searchService.searchDuckDuckGo(query, language);
        return searchService.searchBing(query, amount, language, offset);
    }

    @RequestMapping(value = "/search/batch/{language}/{query}", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchResult> getSearchResultsBatch(
            @PathVariable("language") String language,
            @PathVariable("query") String query) throws IOException {
        List<SearchResult> results = new ArrayList<SearchResult>();
        results.addAll(searchService.searchDuckDuckGo(query, language));
        results.addAll(searchService.searchBing(query, 10, language, 0));
        results.addAll(searchService.searchBing(query, 10, language, 11));
        results.addAll(searchService.searchBing(query, 10, language, 21));
        results.addAll(searchService.searchBing(query, 10, language, 31));
        results.addAll(searchService.searchBing(query, 10, language, 41));
        results.addAll(searchService.searchBing(query, 10, language, 51));

        HashSet<Object> seen=new HashSet<>();
        results.removeIf(e->!seen.add(e.getHref()));

        return results;
    }
}