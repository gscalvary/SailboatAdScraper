package sailboatScraper.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sailboatScraper.entities.Sailboat;
import sailboatScraper.services.implementations.SailboatScrappingServiceImpl;

import java.util.List;

@RestController
public class IndexController {

    private final SailboatScrappingServiceImpl sailboatScrappingService;

    public IndexController (SailboatScrappingServiceImpl sailboatScrappingService) {
        this.sailboatScrappingService = sailboatScrappingService;
    }

    @RequestMapping("/")
    public List<Sailboat> getSailboats() {
        return sailboatScrappingService.scrapeSite();
    }
}
