package com.tamil.gs1parser.util

import com.tamil.gs1parser.exception.DecimalPrecisionMoreThanMaxLengthException

/**
 *  Utility class to set decimal precision.
 */
class DecimalPrecisionSetter {

    /**
     *  This function gets a string of 6 characters.
     *  Input will be parsed to set a decimal precision based on the 4th character of the spec (AI - 310n till 394n except 37)
     *  sample : if AI spec matched is 3102, decimal precision required is 2 and if the value to parse is '000139',
     *  output will be 1.39.
     *  @param barcodeInput String Type
     *  @return String with a decimal precision
     *  @throws [DecimalPrecisionMoreThanMaxLengthException] when provided precision is not processable or more than the value provided to parse
     */
    fun setDecimalPrecision(barcodeInput:String, maxLength:Int):String
    {
        val parsedValue: String

        val precisionMarker: Int = barcodeInput.substring(0, 1).toInt()
        if (precisionMarker <= maxLength) {
            val tmpValue: Int = barcodeInput.substring(1, maxLength + 1).toInt()
            val divisor = Math.pow(10.0, precisionMarker.toDouble())
            parsedValue = String.format("%."+precisionMarker+"f",tmpValue / divisor)
        } else {
            throw DecimalPrecisionMoreThanMaxLengthException("Precision requested is $precisionMarker, which is more than the actual value")
        }
        return parsedValue
    }
}