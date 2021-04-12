package com.tamil.gs1parser.util

import com.tamil.gs1parser.constants.AppConstants
import com.tamil.gs1parser.exception.LessThanSufficientPossibilitiesException
import com.tamil.gs1parser.pojo.PossibleSpecification

/**
 *  Utility class to generate a set of possible AI specs.
 */
class PossibilitiesFetcher {

    /**
     *  This function gets a variable length string as input.
     *  AI spec can be of lengths 2,3 and 4 characters.
     *  Input will be parsed to get first 2,3 and 4 characters paired with residue after slicing the starting characters
     *  in each case.
     *  @param barcode String Type
     *  @return listOf[PossibleSpecification] of size 3
     *  @throws [LessThanSufficientPossibilitiesException] when the provided input has less than 2 characters
     */
    fun getPossibilities(barcode: String): List<PossibleSpecification> {
        val possibleSpecifications: MutableList<PossibleSpecification> = ArrayList()
        var i = 0
        if(barcode.length > 4){
            do {
                val possibleSpec = PossibleSpecification()
                possibleSpec.possibleSpecification = barcode.substring(0, i + 2)
                possibleSpec.possibleInput = barcode.substring(i + 2)
                possibleSpecifications.add(possibleSpec)
                i++
            } while (i < AppConstants.NUMBER_OF_POSSIBILITIES)
        }
        else
        {
            throw LessThanSufficientPossibilitiesException("Less than two characters left to parse")
        }
        return possibleSpecifications
    }
}