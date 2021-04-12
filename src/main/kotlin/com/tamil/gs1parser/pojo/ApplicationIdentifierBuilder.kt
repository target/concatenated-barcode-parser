package com.tamil.gs1parser.pojo

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.*

/**
 * class loaded from json
 * @property barcode_type - Type of barcode specification
 * @property specs - listOf - [ApplicationIdentifier]
 * */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class ApplicationIdentifierBuilder {

    private var _barcodeType: String? = null

    @get:JsonProperty("barcode_type")
    var barcodeType: String?
        get() = _barcodeType
        set(value) {
            _barcodeType = value
        }


    private var _specs: List<ApplicationIdentifier>? = null

    @get:JsonProperty("specs")
    var specs: List<ApplicationIdentifier>?
        get() = _specs
        set(value) {
            _specs = value
        }
}