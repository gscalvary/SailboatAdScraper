package sailboatAdScraper.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatAdScraper.domain.services.SailboatAdScrapingService;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.util.List;

@RestController
public class IndexController {

    private final SailboatAdScrapingService sailboatAdScrapingService;

    public IndexController(SailboatAdScrapingService sailboatAdScrapingService) {
        this.sailboatAdScrapingService = sailboatAdScrapingService;
    }

    @RequestMapping("/")
    public List<SailboatAd> getSailboatAds() {
        return sailboatAdScrapingService.scrapeSite();
    }
}
