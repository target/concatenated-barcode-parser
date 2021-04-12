package com.tamil.gs1parser.processor

import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.util.JsonLoader
import org.junit.Test
import spock.lang.Specification

class CoordinatorTest extends Specification {

    def jsonLoader = new JsonLoader.Companion()
    Coordinator coordinator = new Coordinator()

    @Test
    def "CoordinateForASingleBarcode"() {

        given: "A barcode and application specifiers are provided"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        String barcode = "01076123456789001710050310AC3453G3"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = coordinator.coordinate(barcode,applicationIdentifiers) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 3
        assert parsedOutput.get(2).description == "Batch/Lot Number"
    }

    @Test
    def "CoordinateForASingleBarcodeErrors"() {

        given: "A barcode and application specifiers are provided"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        String barcode = "01076A23456789001710050310AC3453G3"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = coordinator.coordinate(barcode,applicationIdentifiers) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 3
        assert parsedOutput.get(2).description == "Batch/Lot Number"
        assert parsedOutput.get(0).error != null
        assert parsedOutput.get(0).error == "Value expected is numeric but received an alphanumeric"
    }

    @Test
    def "CoordinateForaSplitInput"()
    {
        given: "A barcode with variable length fields"

        String barcode = "01195060001178431714112010NYFUL01:21192837"
        List<String> barcodeSplitBasedOnDelimiter = barcode.split(":")
        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs

        when: "Async method is called to process"

        List<ParsedOutput> parsedOutput = new ArrayList<>()
        for(val in barcodeSplitBasedOnDelimiter)
        {
            parsedOutput.addAll(coordinator.coordinate(val,applicationIdentifiers) as List<ParsedOutput>)
        }

        then: "validate for expected output"
        assert parsedOutput.size() == 4
    }

    @Test
    def "CoordinateForASingleBarcodeWithUnknownSpec"() {

        given: "A barcode and application specifiers are provided"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        String barcode = "04076A23456789001710050310AC3453G3"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = coordinator.coordinate(barcode,applicationIdentifiers) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 1
        assert parsedOutput.get(0).value == barcode
        assert parsedOutput.get(0).description == "uncategorized"
        assert parsedOutput.get(0).error != null
        assert parsedOutput.get(0).error == "Barcode couldn't be parsed"
    }

    @Test
    def "CoordinateForASingleBarcodeverylessPossibilities"() {

        given: "A barcode and application specifiers are provided"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        String barcode = "0"

        when: "Coordinator is called to parse barcode"

        List<ParsedOutput> parsedOutput = coordinator.coordinate(barcode,applicationIdentifiers) as List<ParsedOutput>

        then: "validate if expected values are returned"
        assert parsedOutput.size() == 1
        assert parsedOutput.get(0).value == barcode
        assert parsedOutput.get(0).description == "uncategorized"
        assert parsedOutput.get(0).error != null
        assert parsedOutput.get(0).error == "Barcode couldn't be parsed"
    }

    @Test
    def "setUncategorizedOutput"()
    {
        given: "A barcode with variable length fields"

        String barcode = "01195060001178431714112010NYFUL01"

        when: "setUncategorizedOutput is called "
        ParsedOutputHolder parsedOutput = coordinator.setUncategorizedOutput(barcode)

        then: "validate if barcode is sent as value"

        assert  parsedOutput.parsedOutput.value == barcode
        assert  parsedOutput.newInput == ""
    }
}
