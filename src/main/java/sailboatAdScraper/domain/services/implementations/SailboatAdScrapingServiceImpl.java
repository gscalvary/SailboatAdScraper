package sailboatAdScraper.domain.services.implementations;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sailboatAdScraper.domain.valueObjects.SailboatAd;
import sailboatAdScraper.domain.services.ScrapingService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SailboatAdScrapingServiceImpl implements ScrapingService {

    @Override
    @Cacheable("sailboatAds")
    public List<SailboatAd> scrapeSite() {

        List<SailboatAd> sailboatAds = new ArrayList<>();
        Document document;

        try {
            document = Jsoup.connect("https://www.yachtworld.com/core/listing/cache/searchResults.jsp?cit=true&slim=quick&sm=3&searchtype=advancedsearch&Ntk=boatsEN&hmid=0&ftid=0&enid=0&type=Sail&fromLength=38&toLength=45&fromYear=2013&toYear=2019&fromPrice=100000&toPrice=300000&luom=126&currencyid=100&boatsAddedSelected=-1&fracts=1&No=0&ps=1000").get();
        } catch (IOException ioe) {
            return sailboatAds;
        }

        Elements listings = document.getElementsByClass("information");

        for (Element listing : listings) {

            Elements makeModelElements = listing.getElementsByClass("make-model");

            if (makeModelElements.isEmpty()) {
                continue;
            }

            Elements priceElements = listing.getElementsByClass("price");

            if (priceElements.isEmpty()) {
                continue;
            }

            float price = getPrice(priceElements.get(0));

            if (price == 0) {
                continue;
            }

            Elements locationElements = listing.getElementsByClass("location");

            if (locationElements.isEmpty()) {
                continue;
            }

            sailboatAds.add(
                    new SailboatAd(
                            getLength(makeModelElements.get(0)),
                            getYear(makeModelElements.get(0)),
                            getMake(makeModelElements.get(0)),
                            getModel(makeModelElements.get(0)),
                            price,
                            getLocation(locationElements.get(0)),
                            LocalDateTime.now()));
        }

        return sailboatAds;
    }

    private float getLength(Element makeModelElement) {

        String rawLength = makeModelElement.getElementsByClass("length feet").get(0).text();
        String[] rawLengthParts = rawLength.split(" ");

        return Integer.valueOf(rawLengthParts[0]);
    }

    private int getYear(Element makeModelElement) {

        String linkText = makeModelElement.selectFirst("a").text();
        String[] linkTextNodes = linkText.split(" ");

        return Integer.valueOf(linkTextNodes[2]);
    }

    private String getMake(Element makeModelElement) {

        String linkText = makeModelElement.selectFirst("a").text();
        String[] linkTextNodes = linkText.split(" ");

        return linkTextNodes[3];
    }

    private String getModel(Element makeModelElement) {

        String linkText = makeModelElement.selectFirst("a").text();
        int index = StringUtils.ordinalIndexOf(linkText, " ", 4);

        return linkText.substring(index + 1);
    }

    private float getPrice(Element priceElement) {

        String rawPrice = priceElement.text();

        if (rawPrice.indexOf(" ") <= 3) {
            return 0;
        }

        String price = rawPrice.substring(3, rawPrice.indexOf(" ")).replace(",", "");

        return Float.valueOf(price);
    }

    private String getLocation(Element locationElement) {

        return locationElement.text();
    }
}
