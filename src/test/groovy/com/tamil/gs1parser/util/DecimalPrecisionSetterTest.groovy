package com.tamil.gs1parser.util

import com.tamil.gs1parser.exception.DecimalPrecisionMoreThanMaxLengthException
import org.junit.Test
import spock.lang.Specification

class DecimalPrecisionSetterTest extends Specification {

    DecimalPrecisionSetter decimalPrecisionSetter = new DecimalPrecisionSetter()

    @Test
    def "SetDecimalPrecisionHappyPath"() {

        given: "provided a string to parse"

        String stringToParse = "2000908259786"

        when: "Parser handles decimal value"

         String parsedValue = decimalPrecisionSetter.setDecimalPrecision(stringToParse, 6)

        then: "validate if Date block is executed"
        assert  parsedValue == "9.08"
    }

    @Test
    def "SetDecimalPrecisionThrowsException"() {

        given: "provided a string to parse"

        String stringToParse = "7000908259786"

        when: "Parser handles decimal value"

        String parsedValue = decimalPrecisionSetter.setDecimalPrecision(stringToParse, 6)

        then: "validate if Date block is executed"

        thrown(DecimalPrecisionMoreThanMaxLengthException)
    }

    @Test
    def "SetDecimalPrecisiongivesDecimalsOnly"() {

        given: "provided a string to parse"

        String stringToParse = "6000908259786"

        when: "Parser handles decimal value"

        String parsedValue = decimalPrecisionSetter.setDecimalPrecision(stringToParse, 6)

        then: "validate if Date block is executed"

        assert parsedValue == "0.000908"
    }
}
