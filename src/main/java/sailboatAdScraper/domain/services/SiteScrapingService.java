package sailboatAdScraper.domain.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class SiteScrapingService {

    @Value("${jsoup.timeout}")
    private int timeout;

    @Value("${jsoup.maxBodySize}")
    private int maxBodySize;

    @Cacheable("document")
    public Document scrapeSite(String url, Map<String, String> requestParameters) {

        Document document;

        try {
            document = Jsoup.connect(url)
                    .data(requestParameters)
                    .timeout(timeout)
                    .maxBodySize(maxBodySize)
                    .get();
        } catch (IOException ioe) {
            document  = new Document(url);
        }

        return document;
    }
}
