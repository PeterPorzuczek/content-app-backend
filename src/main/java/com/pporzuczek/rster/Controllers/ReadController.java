package com.pporzuczek.rster.Controllers;

import com.pporzuczek.rster.Models.ReadResult;
import com.pporzuczek.rster.Services.ReadabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ReadController {
    @Autowired
    private ReadabilityService readabilityService;

    @RequestMapping(value = "/read/html/", method = RequestMethod.GET)
    @ResponseBody
    public String getSearchResultsAsHtml(
            @RequestParam("url") String url) throws IOException {
        return readabilityService.readAsHtml(url);
    }

    @RequestMapping(value = "/read/", method = RequestMethod.GET)
    @ResponseBody
    public ReadResult getSearchResults(
            @RequestParam("url") String url) throws IOException {
        return readabilityService.read(url);
    }
}
