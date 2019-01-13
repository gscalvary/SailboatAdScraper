package sailboatAdScraper.domain.services

import org.jsoup.nodes.Document
import spock.lang.Specification

class SailboatAdScrapingServiceSpec extends Specification {

    def sailboatAdScrapingService

    def setup () {

        sailboatAdScrapingService = new SailboatAdScrapingService()
    }

    def 'A sailboat ad without a sailboat make and model should be discarded from output' () {

        given:
        sailboatAdScrapingService.getDocument() >> getDocumentWithoutMakeAndModel()

        when:
        def sailboatAds = sailboatAdScrapingService.scrapeSite()

        then:
        sailboatAds.isEmpty()
    }

    def 'A sailboat ad without a sailboat price should be discarded from output' () {
        assert true
    }

    def 'A sailboat ad without a sailboat location should be discarded from output' () {
        assert true
    }

    def 'A sailboat ad with make and model, price, and location should be included in output' () {
        assert true
    }

    def getDocumentWithoutMakeAndModel ()
    {

        return new Document("")
    }
}
