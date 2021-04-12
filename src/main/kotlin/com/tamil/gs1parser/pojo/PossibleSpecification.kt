package com.tamil.gs1parser.pojo

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

/**
 * Schema that hold possible Application Identifiers with possible parsable string
 * @property possible_specification - 2,3 and 4 characters sliced from the provided barcode
 * @property possible_input - available string to parse after slicing the possible  Application Identifier
 * */

class PossibleSpecification {
    private var _possibleSpecification: String? = null
    var possibleSpecification: String?
        get() = _possibleSpecification
        set(value){
            _possibleSpecification = value
        }

    private var _possibleInput: String? = null
    var possibleInput: String?
        get() = _possibleInput
        set(value){
            _possibleInput = value
        }
}