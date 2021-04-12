package processor

import com.tamil.gs1parser.Receptor
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.util.JsonLoader
import org.junit.After
import org.junit.Test
import spock.lang.Specification

class ReceptorTest extends Specification {

    Receptor receptor = new Receptor()

    def jsonLoader = new JsonLoader.Companion()


    @Test
    def "ParseGS1BarcodeGetsFixedLengthBarcode"() {

        given: "receptor is called with a barcode and an AI spec map"
        String barcode = "01076A23456789001710050310AC3453G3"

        when: "Coordinator is called to parse barcode"
        List<ParsedOutput> parsedOutput = receptor.parseGS1Barcode(barcode) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 3
        assert parsedOutput.get(2).description == "Batch/Lot Number"
        assert parsedOutput.get(0).error != null
        assert parsedOutput.get(0).error == "Value expected is numeric but received an alphanumeric"
    }

    @Test
    def "ParseGS1BarcodeGetsVariableLengthBarcode"() {

        given: "receptor is called with a barcode and an AI spec map"
        String barcode = "01076A23456789001710050310AC3453G3□21192837"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = receptor.parseGS1Barcode(barcode) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 4
        assert parsedOutput.get(2).description == "Batch/Lot Number"
        assert parsedOutput.get(0).error != null
        assert parsedOutput.get(0).error == "Value expected is numeric but received an alphanumeric"
    }

    @Test
    def "ParseGS1BarcodeWithExpirationDateAndTime"() {

        given: "receptor is called with a barcode and an AI spec map"
        String barcode = "011234567890123470031203231420"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = receptor.parseGS1Barcode(barcode) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 2
        assert parsedOutput.get(1).description == "Expiration date and time"
        assert parsedOutput.get(1).error == null
    }

    @After
    public void tearDown()
    {
        receptor = null
    }
}
