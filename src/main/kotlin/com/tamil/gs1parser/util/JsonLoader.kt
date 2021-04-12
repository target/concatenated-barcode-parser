package com.tamil.gs1parser.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.tamil.gs1parser.pojo.ApplicationIdentifierBuilder
import org.springframework.core.io.ClassPathResource
import java.io.BufferedReader

/**
 *  Utility class to load application identifiers from json file.
 */
class JsonLoader {
    companion object {
        /**
         *  This function loads json from resource path of the calling application and loads it to an object.
         *  @return [ApplicationIdentifierBuilder]
         */
        fun getFileFromResouce(): ApplicationIdentifierBuilder {
            val input =
                ClassPathResource("Gs1spec/GS1Specs.json").inputStream
            val bufferedReader: BufferedReader =
               input.bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            val mapper = jacksonObjectMapper()
            return mapper.readValue(inputString)
        }

    }
}