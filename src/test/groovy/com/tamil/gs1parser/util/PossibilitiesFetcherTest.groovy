package com.tamil.gs1parser.util

import com.tamil.gs1parser.constants.AppConstants
import com.tamil.gs1parser.pojo.PossibleSpecification
import org.junit.Test
import spock.lang.Specification

class PossibilitiesFetcherTest extends Specification {


    PossibilitiesFetcher fetcher =  new PossibilitiesFetcher();

    @Test
    def "GetPossibilities"() {
        given: "A barcode input"
        String barcode = "01076123456789001710050310AC3453G3:21455777"

        when: "possibilities are tried"
        List<PossibleSpecification> list = fetcher.getPossibilities(barcode)

        then: "return should have 3 possibilities"
        assert list.size()== AppConstants.NUMBER_OF_POSSIBILITIES
    }
}
