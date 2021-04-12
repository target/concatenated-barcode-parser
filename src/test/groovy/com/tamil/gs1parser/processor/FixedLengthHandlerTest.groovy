package com.tamil.gs1parser.processor

import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import helper.TestHelper
import org.junit.After
import org.junit.Test
import spock.lang.Specification

class FixedLengthHandlerTest extends Specification {


    FixedLengthHandler fixedLengthHandler = new FixedLengthHandler()
    def helper = new TestHelper()
    ParsedOutputHolder outputHolder

    @Test
    def "FixedLengthParserHandlesDateValue"() {
        given: "provided a string to parse and an identifier with date_field set to true"

        String dateStringToParse = "190828"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForDateField


        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if Date block is executed"
        assert outputHolder.parsedOutput.error == null
        assert  outputHolder.parsedOutput.value == "2019-08-28"

    }

    @Test
    def "FixedLengthParserHandlesDateValueThrowsDateParseException"() {
        given: "provided a string to parse and an identifier with date_field set to true"

        String dateStringToParse = "111908"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForDateField

        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if Date value is not parsed and exception added to object"
        assert outputHolder.parsedOutput.error != null
        assert outputHolder.parsedOutput.value == "111908"

    }

    @Test
    def "FixedLengthParserHandlesDecimalValue"() {
        given: "provided a string to parse and an identifier with decimal specifier set to true"

        String dateStringToParse = "10009082345"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForDecimalSpecifier

        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if decimal precision is set"
        assert outputHolder.parsedOutput.error == null
        assert  outputHolder.parsedOutput.value == "90.8"
    }

    @Test
    def "FixedLengthParserHandlesDecimalValueHasParsingException"() {
        given: "provided a string to parse and an identifier with decimal specifier set to true"

        String dateStringToParse = "70009082345"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForDecimalSpecifier

        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if decimal precision is not set and error is added to response"
        assert  outputHolder.parsedOutput.error != null
        assert  outputHolder.parsedOutput.value == "000908"
    }

    @Test
    def "FixedLengthParserHandlesNonDateAndDecimalValue"() {
        given: "provided a string to parse and an identifier with date_field and decimal specifier set to false"

        String dateStringToParse = "70009082345"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForaNumeric

        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if Date block is executed"
        assert  outputHolder.parsedOutput.error == null
        assert  outputHolder.parsedOutput.value == "7000908"
        assert  outputHolder.newInput == "2345"
    }

    @Test
    def "FixedLengthParserHandlesLessThanMaxLength"() {
        given: "provided a string to parse where SSCC value is less than 18 chars numeric"

        String stringToParse = "10000456"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForFixedLength

        when: "Parser handles date value"

        outputHolder = fixedLengthHandler.fixedLengthParser(applicationIdentifier, stringToParse)

        then: "validate if Date block is executed"
        assert  outputHolder.parsedOutput.error != null
        assert  outputHolder.parsedOutput.value == "10000456"
        assert  outputHolder.newInput == ""
    }

    @After
    tearDown()
    {
        helper = null
        fixedLengthHandler = null
    }


}
