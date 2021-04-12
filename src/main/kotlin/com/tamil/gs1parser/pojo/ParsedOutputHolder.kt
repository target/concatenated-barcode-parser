package com.tamil.gs1parser.pojo

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor


/**
 * Schema of Parsed output with String that still needs parsing
 * @property newInput - String that's left to be parsed after parsing a previous Application Identifier value
 * @property parsedOutput - [parsedOutput]
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ParsedOutputHolder {
    private var _newInput: String? = null
    var newInput: String?
        get() = _newInput
        set(value){
            _newInput = value
        }

    private var _parsedOutput: ParsedOutput? = null
    var parsedOutput: ParsedOutput?
        get() = _parsedOutput
        set(value){
            _parsedOutput = value
        }
}