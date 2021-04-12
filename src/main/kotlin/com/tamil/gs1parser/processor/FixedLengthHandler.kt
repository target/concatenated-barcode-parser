package com.tamil.gs1parser.processor

import com.tamil.gs1parser.exception.DecimalPrecisionMoreThanMaxLengthException
import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.util.DateFormat
import com.tamil.gs1parser.util.DecimalPrecisionSetter

/**
 *  Processor class to parse fixed length values from the barcode.
 */
class FixedLengthHandler {

    /**
     *  This function parses fixed length value. Handles Date values, Decimal values, Strings and Numeric.
     *  @param barcodeToParse, [ApplicationIdentifier]
     *  @see [ApplicationIdentifier]
     *  @return [ParsedOutputHolder]
     *  @exception DecimalPrecisionMoreThanMaxLengthException is handled when decimal precision expected os out of bound
     */
    fun fixedLengthParser(applicationIdentifier: ApplicationIdentifier, barcodeToParse: String): ParsedOutputHolder {
        val parsedOutputHolder = ParsedOutputHolder()
        val parsedOutput = ParsedOutput()
        val maxLength: Int? = applicationIdentifier.maxLength
        val specification = applicationIdentifier.specifier

        if(applicationIdentifier.isFixedLength == true && barcodeToParse.length >= maxLength!!) {
            if (applicationIdentifier.isDateField == true) {
                try {
                    parsedOutput.value = DateFormat().convertToDateFormat(barcodeToParse.substring(0, maxLength))
                } catch (ex: Exception) {
                    parsedOutput.value = barcodeToParse.substring(0, maxLength)
                    parsedOutput.error = ex.message
                }

                parsedOutput.specification = specification
                parsedOutputHolder.newInput = barcodeToParse.substring(maxLength)
            } else if (applicationIdentifier.isDecimalPrecisionSpecified == true) {
                //call decimal precision setter unit
                var value: String
                try {
                    value = DecimalPrecisionSetter().setDecimalPrecision(barcodeToParse, maxLength)
                } catch (dex: DecimalPrecisionMoreThanMaxLengthException) {
                    value = barcodeToParse.substring(1, maxLength + 1)
                    parsedOutput.error = dex.message
                }
                parsedOutput.value = value
                //for the specifications that has last character as decimal specifier, spec json has this as
                //3 character AI and programmatically it is handled when spec is sent in response
                //this logic is followed as there are handful of AI specs like this and this logic will reduce the
                //size of the json file
                parsedOutput.specification = specification + barcodeToParse.substring(0, 1)
                parsedOutputHolder.newInput = barcodeToParse.substring(maxLength + 1)
            } else {
                parsedOutput.specification = specification
                parsedOutput.value = barcodeToParse.substring(0, maxLength)
                parsedOutputHolder.newInput = barcodeToParse.substring(maxLength)
            }
        }
        else
        {
            //if the expected value is of fixed length but if the provided value is less than the max length,
            // mark it with an error message
            parsedOutput.specification  = specification
            parsedOutput.value = barcodeToParse
            parsedOutput.error = "Expected $maxLength characters, but received less than the expected length."
            parsedOutputHolder.newInput = ""
        }
        parsedOutputHolder.parsedOutput = parsedOutput
        return parsedOutputHolder
    }
}