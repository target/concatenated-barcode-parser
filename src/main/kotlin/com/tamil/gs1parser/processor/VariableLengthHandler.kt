package com.tamil.gs1parser.processor

import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.util.DateFormat

/**
 *  Processor class to handle variable length values from the barcode.
 */
class VariableLengthHandler {

    /**
     *  This function parses variable length value. Handles Date values , Strings and Numeric.
     *  @param barcodeToParse String Type
     *  @param [ApplicationIdentifier]
     *  @see [ApplicationIdentifier]
     *  @return [ParsedOutputHolder]
     *  */
    fun variableLengthParser(applicationIdentifier: ApplicationIdentifier, barcodeToParse: String): ParsedOutputHolder {

        val parsedOutputHolder = ParsedOutputHolder();
        val parsedOutput = ParsedOutput();
        val maxLength: Int? = applicationIdentifier.maxLength

        if (barcodeToParse.length < maxLength!!) {
            parsedOutput.value = barcodeToParse
            parsedOutputHolder.newInput = ""
        } else {
            parsedOutput.value = barcodeToParse.substring(0, maxLength)
            parsedOutputHolder.newInput = barcodeToParse.substring(maxLength)
        }

        if(applicationIdentifier.isDateField == true && parsedOutput.value != null){
            parsedOutput.value = DateFormat().convertToDateFormat(parsedOutput.value!!)
        }

        parsedOutput.specification = applicationIdentifier.specifier
        parsedOutputHolder.parsedOutput = parsedOutput
        return parsedOutputHolder
    }
}