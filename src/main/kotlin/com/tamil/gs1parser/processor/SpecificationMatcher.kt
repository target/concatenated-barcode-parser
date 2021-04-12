package com.tamil.gs1parser.processor

import com.tamil.gs1parser.exception.IdentifierNotMatchedException
import com.tamil.gs1parser.pojo.ApplicationIdentifier
import com.tamil.gs1parser.pojo.ParsedOutputHolder
import com.tamil.gs1parser.pojo.PossibleSpecification
import com.tamil.gs1parser.util.TypeCheck

/**
 *  Processor class to compare GS1 specs with the barcode.
 */
class SpecificationMatcher {


    /**
     *  This function compares possible application identifiers fetched from the supplied barcode with the standard GS1-128 Application Identifiers(AI).
     *  @param listOf[PossibleSpecification]
     *  @param listOf[ApplicationIdentifier]
     *  @return
     *  [ApplicationIdentifier] if a match is found
     *  else throws [IdentifierNotMatchedException] exception
     *  @throws IdentifierNotMatchedException when possible specs derived from input did not match and GS1-128 AI spec
     */
    fun filterForApplicationIdentifier(
        possibleSpecs: List<PossibleSpecification>,
        appIdentifiers: List<ApplicationIdentifier>
    ): ApplicationIdentifier {

        val applicationIdentifier: ApplicationIdentifier
        try {

            applicationIdentifier = appIdentifiers.asSequence()
                .filter { p -> possibleSpecs.any { q -> q.possibleSpecification.equals(p.specifier) } }
                .take(1).first()
        } catch (iex: NoSuchElementException) {
            //add metrics here
            throw IdentifierNotMatchedException("Input specifier from barcode did not match with any standard Application Specifiers.")
        }

        return applicationIdentifier
    }

     /**
     *  This function uses the matched application identifier and compares it against possible application identifiers
     *  fetched from the supplied barcode to get the string to parse excluding the identified
     *  AI spec
     *  @param ApplicationIdentifier
     *  @param listOf[PossibleSpecification]
     *  @return String - barcode value to parsed based on the identified AI
     *  @throws Exception unhandled exception
     */
    @Throws(Exception::class)
    fun getInputToParseBasedOnAppIdentfierMatched(
        applicationIdentifier: ApplicationIdentifier,
        possibleSpecs: List<PossibleSpecification>
    ): String {

        return possibleSpecs
            .filter { p -> p.possibleSpecification.equals(applicationIdentifier.specifier) }.first()
            .possibleInput.toString()
    }

    /**
     *  This function parses the barcode value fetched excluding the AI characters in the beginning
     *  of the string and used Application Identifier to parse it
     *  @param ApplicationIdentifier
     *  @param barcodeToParse String Type
     *  @return [ParsedOutputHolder]
     *  @throws Exception unhandled exception
     */
    @Throws(Exception::class)
    fun parseBarcodeValue(applicationIdentifier: ApplicationIdentifier, barcodeToParse: String): ParsedOutputHolder {
        val parsedOutputHolder: ParsedOutputHolder

        if (applicationIdentifier.isFixedLength == true) {
            parsedOutputHolder = FixedLengthHandler().fixedLengthParser(applicationIdentifier, barcodeToParse)
        } else {
            parsedOutputHolder = VariableLengthHandler().variableLengthParser(applicationIdentifier, barcodeToParse)
        }

        if (applicationIdentifier.isNumeric == true && !applicationIdentifier.isDateField!! && !applicationIdentifier.isDecimalPrecisionSpecified!!
            && !TypeCheck.checkForNumericType(parsedOutputHolder.parsedOutput?.value.toString())
        ) {
            parsedOutputHolder.parsedOutput?.error = "Value expected is numeric but received an alphanumeric"

        } else if (applicationIdentifier.isSpecialHandlingRequired == true
            && !TypeCheck.checkForNumericType(
                parsedOutputHolder.parsedOutput?.value.toString().substring(
                    0,
                    applicationIdentifier.maxLength!!
                )
            )
        ) {
            parsedOutputHolder.parsedOutput?.error =
                "Initial ${applicationIdentifier.minLength} characters are expected to be numeric, but received an alphanumeric"
        } else {
            /** No action needed as the conditions may grow as handles increases and this conditional block validates the parsed
             ** value to add errors if any based on the handle type - This empty block is added to comply code standards suggested by Sonarqube**/
        }

        parsedOutputHolder.parsedOutput?.description = applicationIdentifier.description

        return parsedOutputHolder

    }
}