package com.tamil.gs1parser.util

import com.tamil.gs1parser.constants.RegexPatterns
import com.tamil.gs1parser.exception.DateParseException
import org.junit.Test
import spock.lang.Specification

class DateFormatTest extends Specification {


    def dateFormatter = new DateFormat()

    @Test
    def "GetFormattedDateAndTime"() {

        given: "A string with date format YYMMDDHHmm"
        String date_input = "1906281520"

        when: "date formatting is done"
        String dateOutput = dateFormatter.convertToDateFormat(date_input)

        then: "A string with date format YYYY-MM-DD HH:MM should be returned"
        assert (dateOutput).matches(RegexPatterns.OUTPUT_DATE_FORMAT_WITH_TIME)

    }

    @Test
    def "GetFormattedDate"() {

        given: "A string with date format YYMMDD"
        String date_input = "190600"

        when: "date formatting is done"
        String dateOutput = dateFormatter.convertToDateFormat(date_input)

        then: "A string with date format YYYY-MM-DD should be returned"
        assert (dateOutput).matches(RegexPatterns.OUTPUT_DATE_FORMAT)

    }

    @Test
    def "GetFormattedDateGetsLessThan4chars"() {

        given: "A string with date format yymmdd"
        String date_input = "001"

        when: "date formatting is done"
        dateFormatter.convertToDateFormat(date_input)

        then: "An exception is thrown"
        thrown(DateParseException)

    }

    @Test
    def "GetFormattedDateGetsLessThan6charsButHas4Chars"() {

        given: "A string with date format yymmdd"
        String date_input = "0012"

        when: "date formatting is done"
        String dateOutput = dateFormatter.convertToDateFormat(date_input)

        then: "A string with date format YYYY-MM-DD should be returned"
        assert (dateOutput).matches(RegexPatterns.OUTPUT_DATE_FORMAT)

    }

    @Test
    def "GetFormattedDateGetsWrongDate"() {

        given: "A string with date format yymmdd"
        String date_input = "002120"

        when: "date formatting is done"
        dateFormatter.convertToDateFormat(date_input)

        then: "An exception is thrown"
        thrown(DateParseException)

    }

    @Test
    def "ExpirtDateTimehasOnltDate"() {

        given: "A string with date format yymmdd"
        String date_input = "00012000"

        when: "date formatting is done"
        String dateOutput = dateFormatter.convertToDateFormat(date_input)

        then: "An exception is thrown"
        assert (dateOutput).matches(RegexPatterns.OUTPUT_DATE_FORMAT_WITH_TIME)

    }
}
