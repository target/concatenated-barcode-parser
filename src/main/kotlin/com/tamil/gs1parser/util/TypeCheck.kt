package com.tamil.gs1parser.util

import com.tamil.gs1parser.constants.RegexPatterns

/**
 * Utility class to check on data types
 */
class TypeCheck {
    companion object {
        /**
         *  This function check the data type to be numeric
         *  @param barcodeInput String Type
         *  @return Boolean
         *      - Return true if the given input matches numeric type
         *      - Return false, otherwise
         */
        fun checkForNumericType(barcodeInput: String): Boolean {
            //check for numeric type - containing one or more digits
            if (barcodeInput.matches(RegexPatterns.DIGIT.toRegex())) {
                return true
            }
            return false
        }
    }
}