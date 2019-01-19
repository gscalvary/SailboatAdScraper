package sailboatAdScraper.domain.factories;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import sailboatAdScraper.domain.valueObjects.SailboatAd;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SailboatAdFactory {

    public List<SailboatAd> getSailboatAds(Document document) {

        List<SailboatAd> sailboatAds = new ArrayList<>();

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
