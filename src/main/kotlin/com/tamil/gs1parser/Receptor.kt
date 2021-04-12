package com.tamil.gs1parser


import com.tamil.gs1parser.constants.AppConstants
import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.processor.Coordinator
import com.tamil.gs1parser.util.JsonLoader
import com.tamil.gs1parser.util.VariableLengthCheck


class Receptor {

    private val applicationIdentifier: List<ApplicationIdentifier>
            = JsonLoader.getFileFromResouce().specs!!

    fun parseGS1Barcode(barcode: String): ArrayList<ParsedOutput> {
        val parsedBarcode: ArrayList<ParsedOutput> = ArrayList()

        if (VariableLengthCheck.hasGSOne128FunctionCodeSpecifier(barcode)) {
            val parsedASCIIBarcode = VariableLengthCheck.setParsableASCII(barcode);

            for (barcodeSplit in parsedASCIIBarcode.split(AppConstants.SPLIT_ASCII_SEQUENCE)) {

                parsedBarcode += Coordinator()
                    .coordinate(barcodeSplit, applicationIdentifier)
            }

        } else {
            parsedBarcode += Coordinator()
                .coordinate(barcode, applicationIdentifier)
        }
        return parsedBarcode
    }
}