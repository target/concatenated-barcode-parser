package com.tamil.gs1parser.pojo

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

/**
 * Schema of Parsed output
 * @property specification - GS1-128 Application Identifier
 * @property description - description of what the Application Identifier value is
 * @property value - value parsed for the Application Identifier
 * @property error - Error if any while parsing. Usually null if value is parsed without issues
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
class ParsedOutput {
    private var _specification: String? = null
    var specification: String?
        get() = _specification
        set(value){
            _specification = value
        }

    private var _description: String? = null
    var description: String?
        get() = _description
        set(value){
            _description = value
        }

    private var _value: String? = null
    var value: String?
        get() = _value
        set(value){
            _value = value
        }

    private var _error: String? = null
    var error: String?
        get() = _error
        set(value){
            _error = value
        }
}