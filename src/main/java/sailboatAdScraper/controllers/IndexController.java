package sailboatAdScraper.controllers;

import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatAdScraper.domain.factories.SailboatAdFactory;
import sailboatAdScraper.domain.services.SiteScrapingService;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    private final SiteScrapingService siteScrapingService;
    private final SailboatAdFactory sailboatAdFactory;

    public IndexController(SiteScrapingService siteScrapingService, SailboatAdFactory sailboatAdFactory) {
        this.siteScrapingService = siteScrapingService;
        this.sailboatAdFactory = sailboatAdFactory;
    }

    @GetMapping({"/", "/yachtworld"})
    public List<SailboatAd> getSailboatAds() {

        Document document = siteScrapingService.scrapeSite(
                "https://www.yachtworld.com/core/listing/cache/searchResults.jsp",
                getYachtWorldRequestParameters());

        return sailboatAdFactory.getSailboatAds(document);
    }

    private Map<String, String> getYachtWorldRequestParameters() {

        Map<String, String> requestParameters = new HashMap<>();

        requestParameters.put("cit", "true");
        requestParameters.put("slim", "quick");
        requestParameters.put("sm", "3");
        requestParameters.put("searchtype", "advancedsearch");
        requestParameters.put("Ntk", "boatsEN");
        requestParameters.put("hmid", "0");
        requestParameters.put("ftid", "0");
        requestParameters.put("enid", "0");
        requestParameters.put("type", "Sail");
        requestParameters.put("fromLength", "31");
        requestParameters.put("toLength", "51");
        requestParameters.put("fromYear", "2013");
        requestParameters.put("toYear", "2019");
        requestParameters.put("fromPrice", "50000");
        requestParameters.put("toPrice", "400000");
        requestParameters.put("luom", "126");
        requestParameters.put("currencyid", "100");
        requestParameters.put("boatsAddedSelected", "-1");
        requestParameters.put("fracts", "1");
        requestParameters.put("No", "0");
        requestParameters.put("ps", "2000");

        return requestParameters;
    }
}
