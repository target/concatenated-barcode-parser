package com.tamil.gs1parser.exception

import java.lang.Exception

/**
 *  Exception thrown when parsable barcode string is less than minimum possible GS1-128 application identifier
 */
class LessThanSufficientPossibilitiesException(message: String): Exception(message) {
}