package sailboatAdScraper.domain.services;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SailboatAdScrapingService {

    @Cacheable("sailboatAds")
    public List<SailboatAd> scrapeSite() {

        List<SailboatAd> sailboatAds = new ArrayList<>();
        Document document = getDocument();

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

    private Document getDocument() {

        Document document;
        Map<String, String> requestParameters = new HashMap<>();

        String uri = "https://www.yachtworld.com/core/listing/cache/searchResults.jsp";

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

        try {
            document = Jsoup.connect(uri)
                    .data(requestParameters)
                    .timeout(60000)
                    .maxBodySize(0)
                    .get();
        } catch (IOException ioe) {
            document  = new Document(uri);
        }

        return document;
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
