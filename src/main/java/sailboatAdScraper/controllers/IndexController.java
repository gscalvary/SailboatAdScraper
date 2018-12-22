package sailboatAdScraper.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatAdScraper.domain.services.ScrapingService;

import java.util.List;

@RestController
public class IndexController {

    private final ScrapingService sailboatAdScrapingService;

    public IndexController(ScrapingService sailboatAdScrapingService) {
        this.sailboatAdScrapingService = sailboatAdScrapingService;
    }

    @RequestMapping("/")
    public List<?> getSailboatAds() {
        return sailboatAdScrapingService.scrapeSite();
    }
}
