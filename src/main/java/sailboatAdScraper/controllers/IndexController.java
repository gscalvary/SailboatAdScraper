package sailboatAdScraper.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatAdScraper.domain.services.implementations.SailboatAdScrapingServiceImpl;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.util.List;

@RestController
public class IndexController {

    private final SailboatAdScrapingServiceImpl sailboatAdScrapingService;

    public IndexController(SailboatAdScrapingServiceImpl sailboatAdScrapingService) {
        this.sailboatAdScrapingService = sailboatAdScrapingService;
    }

    @RequestMapping("/")
    public List<SailboatAd> getSailboatAds() {
        return sailboatAdScrapingService.scrapeSite();
    }
}
