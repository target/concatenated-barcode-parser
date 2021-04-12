package com.tamil.gs1parser.processor

import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import helper.TestHelper
import org.junit.After
import org.junit.Test
import spock.lang.Specification

class VariableLengthHandlerTest extends Specification {

    TestHelper helper = new TestHelper()

    VariableLengthHandler handler = new VariableLengthHandler()

    ParsedOutputHolder outputHolder

    @Test
    def "VariableLengthParserGetsVariableLengthValue"() {

        given: "provided a string to parse that has variable identifier and value less than maximum length"

        String dateStringToParse = "700090"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForVariableLength

        when: "Parser handles date value"

        outputHolder = handler.variableLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if Date block is executed"
        assert  outputHolder.parsedOutput.error == null
        assert  outputHolder.parsedOutput.value == "700090"
        assert  outputHolder.newInput == ""
    }

    @Test
    def "VariableLengthParserGetsVariableLengthValuewithMaximumLength"() {

        given: "provided a string to parse that has variable identifier and value equal to maximum length"

        String dateStringToParse = "70009082adbch59786"
        ApplicationIdentifier applicationIdentifier = helper.appIdentifierForVariableLength

        when: "Parser handles date value"

        outputHolder = handler.variableLengthParser(applicationIdentifier, dateStringToParse)

        then: "validate if Date block is executed"
        assert  outputHolder.parsedOutput.error == null
        assert  outputHolder.parsedOutput.value == "70009082"
        assert  outputHolder.newInput == "adbch59786"
    }

    @After
    tearDown()
    {
        helper = null
        handler = null
    }
}
