package com.tamil.gs1parser.processor

import com.tamil.gs1parser.exception.IdentifierNotMatchedException
import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.pojo.PossibleSpecification
import com.tamil.gs1parser.util.JsonLoader
import com.tamil.gs1parser.util.PossibilitiesFetcher
import helper.TestHelper
import org.junit.After
import org.junit.Test
import spock.lang.Specification

class SpecificationMatcherTest extends Specification {

    SpecificationMatcher matcher = new SpecificationMatcher()

    def jsonLoader = new JsonLoader.Companion()

    PossibilitiesFetcher fetcher = new PossibilitiesFetcher()

    @Test
    def "FilterForApplicationIdentifierFindsTheIdentifier"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("01076123456789001710050310AC3453G3")

        when: "Application filters the list of possibilities to match with standard specifications"

        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)

        then: "Validate if the expected identifier is matched"

        applicationIdentifier.specifier == "01"

    }

    @Test
    def "FilterForApplicationIdentifierFindsNoMatchingIdentifier"() {
        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("04076123456789001710050310AC3453G3")

        when: "Application filters the list of possibilities to match with standard specifications"

        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)

        then: "Validate if the expected identifier is matched"

        thrown(IdentifierNotMatchedException)
    }

    @Test
    def "GetInputToParseBasedOnAppIdentfierMatched"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("01076123456789001710050310AC3453G3")
        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)

        when: "Application fetches the possible input based on the AI matched"

        String inputToParse = matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)

        then: "validate if expected barcode value is returned"

        inputToParse == "076123456789001710050310AC3453G3"
    }

    @Test
    def "parseBarcodeValueParsedFixedLengthNumeric"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("01076123456789001710050310AC3453G3")
        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)
        String inputToParse = matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)

        when: "barcode is porsed to slice off the value for the identifed specification"

        ParsedOutputHolder outputHolder = matcher.parseBarcodeValue(applicationIdentifier, inputToParse)

        then: "validate if the value parsed is correct"

        outputHolder.parsedOutput.value == "07612345678900"
        outputHolder.parsedOutput.description == "GTIN-14"
        outputHolder.parsedOutput.specification == "01"
        outputHolder.newInput == "1710050310AC3453G3"

    }

    @Test
    def "parseBarcodeValueParsedVaryingLengthNumericWithMaxLengthData"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("10076123456789001710050310AC3453G3")
        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)
        String inputToParse = matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)

        when: "barcode is porsed to slice off the value for the identifed specification"

        ParsedOutputHolder outputHolder = matcher.parseBarcodeValue(applicationIdentifier, inputToParse)

        then: "validate if the value parsed is correct"

        outputHolder.parsedOutput.value == "07612345678900171005"
        outputHolder.parsedOutput.description == "Batch/Lot Number"
        outputHolder.parsedOutput.specification == "10"
        outputHolder.newInput == "0310AC3453G3"

    }

    @Test
    def "parseBarcodeValueParsedVaryingLengthNumericWithVariableLengthData"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = jsonLoader.getFileFromResouce().specs
        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("100761234567890017")
        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)
        String inputToParse = matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)

        when: "barcode is porsed to slice off the value for the identifed specification"

        ParsedOutputHolder outputHolder = matcher.parseBarcodeValue(applicationIdentifier, inputToParse)

        then: "validate if the value parsed is correct"

        outputHolder.parsedOutput.value == "0761234567890017"
        outputHolder.parsedOutput.description == "Batch/Lot Number"
        outputHolder.parsedOutput.specification == "10"
        outputHolder.newInput == ""

    }

    def "parseBarcodeValueParsedVaryingLengthwithSpecialHandleError"() {

        given: "List of possible specifications from input and list of standard application specifications"

        List<ApplicationIdentifier> applicationIdentifiers = new ArrayList<>()
        applicationIdentifiers.addAll(TestHelper.appIdentifierForVariableLengthwithSpecialHandle)

        List<PossibleSpecification> possibleSpecifications = fetcher.getPossibilities("800A761234567D")
        ApplicationIdentifier applicationIdentifier = matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)
        String inputToParse = matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)

        when: "barcode is porsed to slice off the value for the identifed specification"

        ParsedOutputHolder outputHolder = matcher.parseBarcodeValue(applicationIdentifier, inputToParse)

        then: "validate if the value parsed is correct"

        outputHolder.parsedOutput.value == "0A761234567D"
        outputHolder.parsedOutput.description == "TestForSpecialHandleError"
        outputHolder.parsedOutput.specification == "80"
        outputHolder.parsedOutput.error == "Initial 3 characters are expected to be numeric, but received an alphanumeric"
        outputHolder.newInput == ""

    }

    @After
    tearDown()
    {
        matcher = null
        jsonLoader = null
        fetcher = null
    }

}
