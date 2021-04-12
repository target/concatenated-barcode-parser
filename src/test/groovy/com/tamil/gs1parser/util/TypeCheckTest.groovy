package com.tamil.gs1parser.util

import org.junit.Test
import spock.lang.Specification

class TypeCheckTest extends Specification {
    def dataTypeChecker = new TypeCheck.Companion()

    @Test
    def "checkForNumericType" () {

        when: "A string is given input with a certain type"
        String barcode_input = "123456"

        then: "The input should match the required type"
        assert dataTypeChecker.checkForNumericType(barcode_input)
    }

    @Test
    def "checkForNumericTypeReturningFalseforAlphanumeric" () {

        when: "A string is given input with a certain type"
        String barcode_input = "123BG456"

        then: "The input should match the required type"
        assert !dataTypeChecker.checkForNumericType(barcode_input)
    }
}
