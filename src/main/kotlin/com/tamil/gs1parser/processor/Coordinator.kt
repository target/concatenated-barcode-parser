package com.tamil.gs1parser.processor

import com.tamil.gs1parser.exception.IdentifierNotMatchedException
import com.tamil.gs1parser.exception.LessThanSufficientPossibilitiesException
import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutput
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.pojo.PossibleSpecification
import com.tamil.gs1parser.util.PossibilitiesFetcher

/**
 *  Processor class to coordinate the barcode parsing.
 */
class Coordinator {

    /**
     *  This function coordinates with other required functions to parse the provided barcode.
     *  @param barcode String Type
     *  @param listOf[ApplicationIdentifier]
     *  @see [JsonLoader]
     *  @return listOf[ParsedOutput]
     *  @exception IdentifierNotMatchedException is handled when the possible AI specs in provided barcode doesn't match with any of GS1-128 AI
     *  @exception LessThanSufficientPossibilitiesException is handled when [PossibilitiesFetcher] throws the exception
     */
    fun coordinate(barcode: String, applicationIdentifiers: List<ApplicationIdentifier>): List<ParsedOutput> {
        var parsedOutputHolder: ParsedOutputHolder
        var barcodeHolder: String = barcode
        val parsedOutputs: ArrayList<ParsedOutput> = ArrayList()
        val matcher = SpecificationMatcher()
        val fetcher = PossibilitiesFetcher()

        lateinit var possibleSpecifications: List<PossibleSpecification>
        lateinit var applicationIdentifier: ApplicationIdentifier

        while (barcodeHolder.isNotEmpty()) {

            try {
                possibleSpecifications = fetcher.getPossibilities(barcodeHolder)

                applicationIdentifier =
                    matcher.filterForApplicationIdentifier(possibleSpecifications, applicationIdentifiers)

                parsedOutputHolder = matcher.parseBarcodeValue(
                    applicationIdentifier,
                    matcher.getInputToParseBasedOnAppIdentfierMatched(applicationIdentifier, possibleSpecifications)
                )

            } catch (nex: IdentifierNotMatchedException) {
                parsedOutputHolder = setUncategorizedOutput(barcodeHolder)
            } catch (lex: LessThanSufficientPossibilitiesException) {
                parsedOutputHolder = setUncategorizedOutput(barcodeHolder)
            } catch (ex: Exception) {
                parsedOutputHolder = setUncategorizedOutput(barcodeHolder)
            }

            barcodeHolder = parsedOutputHolder.newInput.toString()
            parsedOutputHolder.parsedOutput?.let { parsedOutputs.add(it) }
        }
        return parsedOutputs
    }

    /**
     *  This function returns a hard coded response when parsing logic sees a value that couldn't be parsed.
     *  @param barcode String Type
     *  @return [ParsedOutputHolder]
     *  */
    private fun setUncategorizedOutput(barcode: String): ParsedOutputHolder {

        val parsedOutputHolder = ParsedOutputHolder()
        val parsedOutput = ParsedOutput()

        parsedOutput.value = barcode
        parsedOutput.specification = "uncategorized"
        parsedOutput.description = "uncategorized"
        parsedOutput.error = "Barcode couldn't be parsed"

        parsedOutputHolder.newInput = ""
        parsedOutputHolder.parsedOutput = parsedOutput

        return parsedOutputHolder
    }

}