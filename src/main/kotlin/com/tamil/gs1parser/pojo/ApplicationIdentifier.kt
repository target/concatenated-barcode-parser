package com.tamil.gs1parser.pojo

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*

/**
 * GS1-128 Application identifier
 * @property specifier - Gs1 128 Application identifier
 * @property min_length - Minimum value a GS1 128 Application identifier can have
 * @property max_length - Maximum value a GS1 128 Application identifier can have
 * @property numeric - Is expected value of GS1 128 Application identifier is numeric
 * @property desc - Description of the value a GS1 128 Application identifier has
 * @property fixed_length - Is the expected value of GS1 128 Application identifier is fixed length
 * @property decimal_precision_specified - Does GS1 128 Application identifier has a decimal specifier
 * @property date_field - Is the expected value of GS1 128 Application identifier is a date
 * @property special_handle_required - Is the expected value of GS1 128 Application identifier has a fixed and variable length value types
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class ApplicationIdentifier {
    /**
     * Gs1 128 Application identifier
     */
    private var _specifier: String? = null

    @get:JsonProperty("AI_specifier")
    var specifier: String?
        get() = _specifier
        set(value) {
            _specifier = value
        }

    /**
     * Minimum value a GS1 128 Application identifier can have
     */

    private var _minLength: Int? = 0

    @get:JsonProperty("minimum_length")
    var minLength: Int?
        get() = _minLength
        set(value) {
            _minLength = value
        }

    /**
     * Maximum value a GS1 128 Application identifier can have
     */

    private var _maxLength: Int? = 0

    @get:JsonProperty("maximum_length")
    var maxLength: Int?
        get() = _maxLength
        set(value) {
            _maxLength = value
        }

    /**
     * Is expected value of GS1 128 Application identifier is numeric
     */

    private
    var _isNumeric: Boolean? = false

    @get:JsonProperty(
        "numeric_expected"
    )
    var isNumeric: Boolean?
        get() = _isNumeric
        set(value) {
            _isNumeric = value
        }

    /**
     * Description of the value a GS1 128 Application identifier has
     */

    private
    var _description: String? = null

    @get:JsonProperty(
        "description"
    )
    var description: String?
        get() = _description
        set(value) {
            _description = value
        }

    /**
     *  Is the expected value of GS1 128 Application identifier is fixed length
     */

    private
    var _isFixedLength: Boolean? = false

    @get:JsonProperty(
        "fixed_length"
    )
    var isFixedLength: Boolean?
        get() = _isFixedLength
        set(value) {
            _isFixedLength = value
        }

    /**
     *  Does GS1 128 Application identifier has a decimal specifier
     */

    private
    var _isDecimalPrecisionSpecified: Boolean? = false

    @get:JsonProperty
        ("decimal_specifier")
    var isDecimalPrecisionSpecified: Boolean?
        get() = _isDecimalPrecisionSpecified
        set(value) {
            _isDecimalPrecisionSpecified = value
        }

    /**
     *  Is the expected value of GS1 128 Application identifier is a date
     */

    private
    var _isDateField: Boolean? = false

    @get:JsonProperty
        ("date_field")
    var isDateField: Boolean?
        get() = _isDateField
        set(value) {
            _isDateField = value
        }

    /**
     *  Is the expected value of GS1 128 Application identifier has a fixed and variable length value types
     */

    private
    var _isSpecialHandlingRequired: Boolean? = false


    @get:JsonProperty(
        "needs_special_handle"
    )
    var isSpecialHandlingRequired: Boolean?
        get() = _isSpecialHandlingRequired
        set(value) {
            _isSpecialHandlingRequired = value
        }


}