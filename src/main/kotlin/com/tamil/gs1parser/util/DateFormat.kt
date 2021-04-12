package com.tamil.gs1parser.util


import com.tamil.gs1parser.constants.AppConstants
import com.tamil.gs1parser.exception.DateParseException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

/**
 *  Utility class to parse Date from String input.
 */
class DateFormat {

    private val ERRORMESSAGE =  "Error while parsing date value"
    private lateinit var outputDate : String

    /**
     *  This function gets a string upto 10 characters.
     *  If length equals 6 (AI - 11 till 17) , considered input format is "yyMMdd" and date is formatted to
     *  "yyyy-MM-dd".
     *  If the length is greater than 6 (AI - 7003), considered input format is "yyMMddHHmm" and date is formatted to
     *  "yyyy-MM-dd HH:mm:SS"
     *  @param dateInput String type input to format
     *  @return Formatted String based on the input
     *  @throws DateParseException when provided input string is not Date format-table
     */
    fun convertToDateFormat(dateInput: String): String {

        return if(dateInput.length <= 6)
                convertDate(dateInput)
        else
            convertDateWithTime(dateInput)
    }

    /**
     *  This function parses a string of 6 characters to "yyyy-MM-dd" date format.
     *  Special condition handled as per AI spec of GS1 128 -
     *  "If only year and month are available, DD must be filled with two zeroes."
     *  @param dateInput String Type
     *  @return String of pattern "yyyy-MM-dd"
     *  @throws [DateParseException] when provided input string is not Date format-table
     */
    private fun convertDate(dateInput: String): String
    {
        val yearMonthDateFormatter = DateTimeFormatter.ofPattern(AppConstants.INPUT_DATE_FORMAT_YYMMDD, Locale.US)
        try {
            if (dateInput.length == 6 &&  dateInput.substring(4) != AppConstants.ZERO_PAIR)
                    outputDate = LocalDate.parse(dateInput, yearMonthDateFormatter).toString()

            else if (dateInput.length == 6  && dateInput.substring(4) == AppConstants.ZERO_PAIR )
                    outputDate = convertYearAndMonthOnly(dateInput.substring(0,4))
            else if (dateInput.length == 4)
                    outputDate = convertYearAndMonthOnly(dateInput)
            else if (dateInput.length < 4)
                throw DateParseException(ERRORMESSAGE)

        } catch (e: DateTimeParseException) {
            throw DateParseException(ERRORMESSAGE)
        }
        return outputDate
    }

    /**
     *  This function parses a string of 10 characters to "yyyy-MM-dd HH:mm:SS" date format.
     *  @param dateInput String Type
     *  @return String of pattern "yyyy-MM-dd HH:mm:SS"
     *  @throws DateParseException when provided input string is not Date format-table
     */
    private fun convertDateWithTime(dateInput: String): String
    {
        var tweakedInput = dateInput;
        try {
            if(dateInput.length < 10)
                tweakedInput = String.format("%-10s", dateInput ).replace(' ', '0')
            outputDate = convertDate(tweakedInput.substring(0,6)) + " " + tweakedInput.substring(6,8) + ":" + tweakedInput.substring(8) + ":00"
        } catch (e: DateTimeParseException) {
            throw DateParseException(ERRORMESSAGE)
        }
        return outputDate
    }

    /**
     *  This function parses a string of 4 characters to "yyyy-MM-dd" date format with dd as 00.
     *  @param dateInput String Type
     *  @return String of pattern "yyyy-MM-dd"
     *  @throws DateParseException when provided input string is not Date format-table
     */
    private fun convertYearAndMonthOnly(dateInput: String): String
    {
        //adding a zero DD to convert the date to valid format and 01 will be replaced with 00
        val tweakedInput = dateInput + "01"
        val yearMonthFormatter = DateTimeFormatter.ofPattern(AppConstants.INPUT_DATE_FORMAT_YYMMDD, Locale.US)
        try{
            outputDate = LocalDate.parse(tweakedInput, yearMonthFormatter).toString().substring(0,8) + "00"
        }catch (e: DateTimeParseException) {
            throw DateParseException(ERRORMESSAGE)
        }
        return outputDate

    }


}