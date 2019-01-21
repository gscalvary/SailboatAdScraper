package sailboatAdScraper.controllers;

import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatAdScraper.configuration.YachtWorldProperties;
import sailboatAdScraper.domain.factories.SailboatAdFactory;
import sailboatAdScraper.domain.services.SiteScrapingService;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.util.List;

@RestController
public class IndexController {

    private final SiteScrapingService siteScrapingService;
    private final SailboatAdFactory sailboatAdFactory;
    private final YachtWorldProperties yachtWorldProperties;

    public IndexController(SiteScrapingService siteScrapingService,
                           SailboatAdFactory sailboatAdFactory,
                           YachtWorldProperties yachtWorldProperties) {
        this.siteScrapingService = siteScrapingService;
        this.sailboatAdFactory = sailboatAdFactory;
        this.yachtWorldProperties = yachtWorldProperties;
    }

    @GetMapping("/")
    public List<SailboatAd> getSailboatAds() {

        Document document = siteScrapingService.scrapeSite(
                yachtWorldProperties.getUrl(),
                yachtWorldProperties.getRequestParameters());

        return sailboatAdFactory.getSailboatAds(document);
    }
}
