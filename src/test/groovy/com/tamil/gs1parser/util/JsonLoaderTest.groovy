package com.tamil.gs1parser.util

import com.tamil.gs1parser.pojo.ApplicationIdentifierBuilder
import org.junit.Test
import spock.lang.Specification

class JsonLoaderTest extends Specification {

    @Test
    def "GetFileFromResouce"() {

        ApplicationIdentifierBuilder applicationIdentifier
        def jsonLoader = new JsonLoader.Companion()

        given: "A json file is stored in resource folder"

        when: "Method is called"
        applicationIdentifier = jsonLoader.getFileFromResouce()

        then: "Validate is object has all specs"

        applicationIdentifier.specs.size() == 165

    }

}
