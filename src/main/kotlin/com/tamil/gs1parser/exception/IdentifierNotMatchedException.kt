package com.tamil.gs1parser.exception

/**
 *  Exception thrown when barcode is not having a valid GS1-128 Application Identifier
 */
class IdentifierNotMatchedException(message: String) : Exception(message) {
}