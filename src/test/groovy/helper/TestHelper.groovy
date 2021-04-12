package helper

import com.tamil.gs1parser.pojo.ApplicationIdentifier


class TestHelper {


    static ApplicationIdentifier applicationIdentifer = new ApplicationIdentifier()

    static ApplicationIdentifier getAppIdentifierForDateField(){
        applicationIdentifer.setSpecialHandlingRequired(false)
        applicationIdentifer.maxLength = 6
        applicationIdentifer.minLength = 6
        applicationIdentifer.setDateField(true)
        applicationIdentifer.setDecimalPrecisionSpecified(false)
        applicationIdentifer.description = "Expiry Date"
        applicationIdentifer.setFixedLength(true)
        applicationIdentifer.specifier = "11"

        return applicationIdentifer;
    }

    static ApplicationIdentifier getAppIdentifierForDecimalSpecifier()
    {
        applicationIdentifer.setSpecialHandlingRequired(false)
        applicationIdentifer.maxLength = 6
        applicationIdentifer.minLength = 6
        applicationIdentifer.setDateField(false)
        applicationIdentifer.setDecimalPrecisionSpecified(true)
        applicationIdentifer.description = "Weight"
        applicationIdentifer.setFixedLength(true)
        applicationIdentifer.specifier = "320"

        return applicationIdentifer;
    }

    static ApplicationIdentifier getAppIdentifierForaNumeric(){
        applicationIdentifer.setSpecialHandlingRequired(false)
        applicationIdentifer.maxLength = 7
        applicationIdentifer.minLength = 7
        applicationIdentifer.setDateField(false)
        applicationIdentifer.setDecimalPrecisionSpecified(false)
        applicationIdentifer.description = "Test"
        applicationIdentifer.setFixedLength(true)
        applicationIdentifer.specifier = "20"

        return applicationIdentifer;
    }

    static ApplicationIdentifier getAppIdentifierForVariableLength(){
        applicationIdentifer.setSpecialHandlingRequired(false)
        applicationIdentifer.maxLength = 8
        applicationIdentifer.minLength = 1
        applicationIdentifer.setDateField(false)
        applicationIdentifer.setDecimalPrecisionSpecified(false)
        applicationIdentifer.description = "Test"
        applicationIdentifer.setFixedLength(false)
        applicationIdentifer.specifier = "20"

        return applicationIdentifer;
    }

    static ApplicationIdentifier getAppIdentifierForVariableLengthwithSpecialHandle(){
        applicationIdentifer.setSpecialHandlingRequired(true)
        applicationIdentifer.maxLength = 12
        applicationIdentifer.minLength = 3
        applicationIdentifer.setDateField(false)
        applicationIdentifer.setDecimalPrecisionSpecified(false)
        applicationIdentifer.description = "TestForSpecialHandleError"
        applicationIdentifer.setFixedLength(false)
        applicationIdentifer.specifier = "80"

        return applicationIdentifer;
    }

    static ApplicationIdentifier getAppIdentifierForFixedLength(){
        applicationIdentifer.setSpecialHandlingRequired(false)
        applicationIdentifer.maxLength = 18
        applicationIdentifer.minLength = 18
        applicationIdentifer.setDateField(false)
        applicationIdentifer.setDecimalPrecisionSpecified(false)
        applicationIdentifer.description = "TestForsetFixedLengthLessThanExpected"
        applicationIdentifer.setFixedLength(true)
        applicationIdentifer.specifier = "00"

        return applicationIdentifer;
    }

}