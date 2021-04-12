package com.tamil.gs1parser.util

import com.tamil.gs1parser.constants.AppConstants
import com.tamil.gs1parser.constants.RegexPatterns

/**
 *  Utility class to check if the barcode has a FCN1 character.
 */
class VariableLengthCheck {

    companion object {

        /**
         *  This function has checks to determine if the barcode has variable length fields
         *  determined by FCN1 character in the barcode.
         *  Info: Per GS1 standards variable length values will be
         *  stacked at the end on concatenated barcode. If more than one variable length fields are available
         *  FCN1 character (□) denotes the end of the previous value
         *  @param barcodeInput String Type
         *  @return Boolean
         *      - return True when the barcode contains FCN1 character
         *      - return False otherwise
         */

        fun hasGSOne128FunctionCodeSpecifier(barcodeInput: String): Boolean {

            if (RegexPatterns.NON_PRINTABLE_ASCII.matcher(
                    barcodeInput
                ).find()
            )
                return true
            return false
        }

        /**
         *  This function replaces FCN1 character in the barcode with "@:@". This printable
         *  ASCII sequence is custom created and is used in splitting the barcode based on this
         *  sequence for the ease of parsing. see [Receptor] for the splitting significance.
         *  @param input String
         *  @return String
         */
        fun setParsableASCII(input: String): String {
            return if (RegexPatterns.NON_PRINTABLE_ASCII.matcher(input).find())
                RegexPatterns.NON_PRINTABLE_ASCII.matcher(
                    input
                ).replaceAll(AppConstants.SPLIT_ASCII_SEQUENCE)
            else {
                return input
            }
        }
    }

}
