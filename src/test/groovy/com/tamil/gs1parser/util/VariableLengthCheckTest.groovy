package com.tamil.gs1parser.util

import org.junit.Test
import spock.lang.Specification

class VariableLengthCheckTest extends Specification {


    def variableLengthChecker =  new VariableLengthCheck.Companion()

    @Test
    def "checkBarcodeForVariableLengthFieldTypeReturnsFalseForAFixedLengthBarcode" () {
        when: "A string of barcode without variable length is given as input"
        String barcode_input = "011001186311870917170625320200039810038425642546"

        then: "Variable field check is done on the input barcode"
        assert  !variableLengthChecker.hasGSOne128FunctionCodeSpecifier(barcode_input)
    }


    @Test
    def "checkBarcodeForVariableLengthFieldReturnsFalseForVaiableLengthBarcode" () {
        when: "A string of barcode with variable length is given as input"
        String barcode_input = "0110011863118709171706253202000398:10038425642546"

        then: "Variable field check is done on the input barcode"
        assert  !variableLengthChecker.hasGSOne128FunctionCodeSpecifier(barcode_input)
    }

    @Test
    def "checkBarcodeForVariableLengthFieldReturnsTrueForFCN1Barcode" () {
        when: "A string of barcode with variable length is given as input"
        String barcode_input = "01195060001178431714112010NYFUL01□21192837"

        then: "Variable field check is done on the input barcode"
        assert  variableLengthChecker.hasGSOne128FunctionCodeSpecifier(barcode_input)
    }

    @Test
    def "checkBarcodeForVariableLengthFieldReturnsFalseForanyPrintableASCIIinBarcode" () {
        when: "A string of barcode with variable length is given as input"
        String barcode_input = "01195060001178431714,112010NYFUL01!21192837"

        then: "Variable field check is done on the input barcode"
        assert !variableLengthChecker.hasGSOne128FunctionCodeSpecifier(barcode_input)
    }

    @Test
    def "setParsableASCIIhandlesFixedLengthBarcode" () {

        given: "A string of barcode with variable length is given as input"
        String barcode_input = "01195060001178431714112010NYFUL0121192837"

        when: "parser get the input to process"
        String actual = variableLengthChecker.setParsableASCII(barcode_input)

        then: "Fixed length barcode is returned as such"
        assert actual == barcode_input
    }

    @Test
    def "setParsableASCIIhandlesFCN1" () {

        given: "A string of barcode with variable length is given as input"
        String barcode_input = "01195060001178431714112010NYFUL01□21192837"

        when: "parser get the input to process"
        String actual = variableLengthChecker.setParsableASCII(barcode_input)

        then: "Fixed length barcode is returned as such"
        assert actual == "01195060001178431714112010NYFUL01@:@21192837"
    }

    @Test
    def "setParsableASCIIhandlesNonPrintableASCII" () {

        given: "A string of barcode with variable length is given as input"
        String barcode_input = "01195060001178!431714112010NYFUL01#21192837"

        when: "parser get the input to process"
        String actual = variableLengthChecker.setParsableASCII(barcode_input)

        then: "Fixed length barcode is returned as such"
        assert actual == "01195060001178!431714112010NYFUL01#21192837"
    }

}
