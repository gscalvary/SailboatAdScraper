package sailboatAdScraper.domain.services

import org.jsoup.Jsoup
import sailboatAdScraper.domain.factories.SailboatAdFactory
import spock.lang.Specification

class SailboatAdFactorySpec extends Specification {

    def sailboatAdFactory

    def setup () {

        sailboatAdFactory = new SailboatAdFactory()
    }

    def 'A sailboat ad without a sailboat make and model should not be included in output' () {

        given:
        def html = "<html lang=\"en-US\" xmlns:fb=\"\"><head></head><body><div class=\"information\"><div class=\"price\">US\$126,185 <span class=\"currNote\">*</span></div><div class=\"location\">Newport, RI</div><div class=\"broker\">Broker</div></div><div class=\"description-container\"><span class=\"all-description\"><div class=\"description\">Description</div></span></div></body></html>"
        def document = getDocument(html)

        when:
        def sailboatAds = sailboatAdFactory.getSailboatAds(document)

        then:
        sailboatAds.isEmpty()
    }

    def 'A sailboat ad without a sailboat price should not be included in output' () {

        given:
        def html = "<html lang=\"en-US\" xmlns:fb=\"\"><head></head><body><div class=\"information\"><div class=\"make-model\"><a href=\"\" name=\"\"><span class=\"length feet\">31&nbsp;ft</span> 2018 Make Model</a></div><div class=\"location\">Newport, RI</div><div class=\"broker\">Broker</div></div><div class=\"description-container\"><span class=\"all-description\"><div class=\"description\">Description</div></span></div></body></html>"
        def document = getDocument(html)

        when:
        def sailboatAds = sailboatAdFactory.getSailboatAds(document)

        then:
        sailboatAds.isEmpty()
    }

    def 'A sailboat ad with a sailboat price of zero should not be included in output' () {

        given:
        def html = "<html lang=\"en-US\" xmlns:fb=\"\"><head></head><body><div class=\"information\"><div class=\"make-model\"><a href=\"\" name=\"\"><span class=\"length feet\">31&nbsp;ft</span> 2018 Make Model</a></div><div class=\"price\">US\$0 <span class=\"currNote\">*</span></div><div class=\"location\">Newport, RI</div><div class=\"broker\">Broker</div></div><div class=\"description-container\"><span class=\"all-description\"><div class=\"description\">Description</div></span></div></body></html>"
        def document = getDocument(html)

        when:
        def sailboatAds = sailboatAdFactory.getSailboatAds(document)

        then:
        sailboatAds.isEmpty()
    }

    def 'A sailboat ad without a sailboat location not be included in output' () {

        given:
        def html = "<html lang=\"en-US\" xmlns:fb=\"\"><head></head><body><div class=\"information\"><div class=\"make-model\"><a href=\"\" name=\"\"><span class=\"length feet\">31&nbsp;ft</span> 2018 Make Model</a></div><div class=\"price\">US\$126,185 <span class=\"currNote\">*</span></div><div class=\"broker\">Broker</div></div><div class=\"description-container\"><span class=\"all-description\"><div class=\"description\">Description</div></span></div></body></html>"
        def document = getDocument(html)

        when:
        def sailboatAds = sailboatAdFactory.getSailboatAds(document)

        then:
        sailboatAds.isEmpty()
    }

    def 'A sailboat ad with make and model, price, and location should not be included in output' () {

        given:
        def html = "<html lang=\"en-US\" xmlns:fb=\"\"><head></head><body><div class=\"information\"><div class=\"make-model\"><a href=\"\" name=\"\"><span class=\"length feet\">31&nbsp;ft</span> 2018 Make Model</a></div><div class=\"price\">US\$126,185 <span class=\"currNote\">*</span></div><div class=\"location\">Newport, RI</div><div class=\"broker\">Broker</div></div><div class=\"description-container\"><span class=\"all-description\"><div class=\"description\">Description</div></span></div></body></html>"
        def document = getDocument(html)

        when:
        def sailboatAds = sailboatAdFactory.getSailboatAds(document)

        then:
        !sailboatAds.isEmpty()
    }

    def getDocument(String html)
    {
        return Jsoup.parse(html)
    }
}
