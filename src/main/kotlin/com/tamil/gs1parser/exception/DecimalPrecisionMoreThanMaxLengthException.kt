package com.tamil.gs1parser.exception

import java.lang.Exception

/**
 *  Exception thrown when decimal precision requested is out of bound
 */
class DecimalPrecisionMoreThanMaxLengthException(message: String): Exception(message) {
}