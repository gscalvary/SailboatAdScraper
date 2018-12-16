package sailboatScraper.services.implementations;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sailboatScraper.entities.Sailboat;
import sailboatScraper.factories.SailboatFactory;
import sailboatScraper.services.ScrappingService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SailboatScrappingServiceImpl implements ScrappingService {

    private final RestTemplate restTemplate;
    private final SailboatFactory sailboatFactory;

    public SailboatScrappingServiceImpl(RestTemplate restTemplate, SailboatFactory sailboatFactory) {

        this.restTemplate = restTemplate;
        this.sailboatFactory = sailboatFactory;
    }

    @Override
    public List<Sailboat> scrapeSite() {

        List<Sailboat> sailboats = new ArrayList<>();

        String html = restTemplate.getForObject("https://www.yachtworld.com/core/listing/cache/searchResults.jsp?cit=true&slim=quick&ybw=&sm=3&searchtype=advancedsearch&Ntk=boatsEN&Ntt=&is=false&man=&hmid=0&ftid=0&enid=0&type=%28Sail%29&fromLength=38&toLength=50&fromYear=2013&toYear=2019&fromPrice=100000&toPrice=300000&luom=126&currencyid=100&city=&pbsint=&boatsAddedSelected=-1No=0&ps=10", String.class);

        if (html == null) {
            return sailboats;
        }

        Document document = Jsoup.parse(html);

        sailboats.add(
                new Sailboat(
                        6241053L,
                        48,
                        2009,
                        "Jeanneau",
                        "Sun Odyssey 50 DS",
                        202413,
                        html,
                        LocalDateTime.now()));
        sailboats.add(
                new Sailboat(
                        6744711L,
                        48,
                        2008,
                        "Jeanneau",
                        "Sun Odyssey 50 DS",
                        220506,
                        "Barcelona, Spain",
                        LocalDateTime.now()));

        return sailboats;
    }
}
