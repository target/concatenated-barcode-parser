package com.tamil.gs1parser.constants

import java.util.regex.Pattern

object RegexPatterns {
    //This regex pattern is to find if there is a FCN1 character in the barcode
    val NON_PRINTABLE_ASCII = Pattern.compile("[^ -~]+")

    val DIGIT = "\\p{Digit}+"
    val OUTPUT_DATE_FORMAT =  "[0-9]{4}-[0-9]{2}-[0-9]{2}"
    val OUTPUT_DATE_FORMAT_WITH_TIME = "[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}"
}