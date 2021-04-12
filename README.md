# GS1-128 Barcode Parser 

This library has logic to parse GS1-128 (Global Standard 1) concatenated barcode and return a list of parsed objects. High level information on what is GS1-128 is here on wiki : https://en.wikipedia.org/wiki/GS1-128. For detailed information, please visit GS1 official website : https://www.gs1-128.info

##### Program language: Kotlin v.2.8.8
##### java compatability: 1.8
##### Documentation Engine: Dokka

###### How to  use this library

in terminal/cmd run `./gradlew build` or execute build task in gradle.

When build successful, jar file should be created at `build/libs/concatenated-barcode-parser.jar`

add this jar into your application's resource folder in libs directory, and add the dependency in build.gradle as below,

`compile fileTree(dir: 'libs', include: '*.jar')` to dependencies
 
Any application that uses this library should also have the `spring-core` dependency to fetch the Application Identifier Json file placed in library's jar class path.

###### sample spring-core dependency:
###### Gradle:
```
  // maven { url "https://mvnrepository.com/artifact/org.springframework/spring-core" }
  compile group: 'org.springframework', name: 'spring-core', version: '5.2.8.RELEASE'
```

###### Example block to call library(kotlin)
call function `parseGS1Barcode(String)` instantiating class `Receptor` with a string barcode input
```
  inside processing function
 {
  ...
  //Declare a ParsedOutput ArrayList
  var parsedOutput: ArrayList<ParsedOutput>
  //call function in library to parse
  parsedOutput =  Receptor().parseGS1Barcode(<input barcode - String type>)
  .. code block
 }
```
 
 ### Library Architecture
 
Parsing is purely string manipulations based on instructions stuffed in Application identifier. unit level flow can be found in file `parser_library.jpeg`
 
GS1 standards are captured as json, placed in class path resource.

sample json structure,
```
{
      "AI_specifier": "00",
      "minimum_length": 18,
      "maximum_length": 18,
      "numeric_expected": true,
      "description": "Serial Shipping Container Code(SSCC-18)",
      "fixed_length": true,
      "decimal_specifier": false,
      "date_field": false,
      "needs_special_handle": false
    }
```
GS1-128 standard (as provided in document section 3.3.1),
```
3.3.1 Identification of a logistic unit (SSCC): AI (00)
The GS1 Application Identifier (00) indicates that the GS1 Application Identifier data field contains an SSCC (Serial Shipping Container Code). The SSCC is used to identify logistic units (see section 2.2).
The extension digit is used to increase the capacity of the serial reference within the SSCC. It is assigned by the company that constructs the SSCC. The extension digit ranges from 0-9.
The GS1 Company Prefix is allocated by GS1 Member Organisations to the company that allocates the SSCC – here the physical builder or the brand owner of the logistic unit (see section 1.4.4). It makes the SSCC unique worldwide but does not identify the origin of the unit.
The structure and content of the serial reference is at the discretion of owner of the GS1 Company Prefix to uniquely identify each logistic unit.
The check digit is explained in section 7.9. Its verification, which must be carried out in the application software, ensures that the number is correctly composed.
Figure 3.3.1-1. Format of the element string
The data transmitted from the barcode reader means that the element string denoting the SSCC of a logistic unit has been captured. When indicating this element string in the non-HRI text section of a barcode label, the following data title SHOULD be used: SSCC
```

SSCC (Serial Shipping Container Code)
--------------
GS1 Application Identifier  | Extension digit | GS1 Company Prefix Serial reference | Check digit
------------- | ------------- | ------------- | -------------
00 | N1 | N2 N3 N4 N5 N6 N7 N8 N9 N10 N11 N12 N13 N14 N15 N16 N17 | N18


Gs1-128 specification match to json should be done as follows,

Parser json | GS1 standard
------------- | -------------
AI_specifier | GS1 Application Identifier 
minimum_length | to denote minimum length the spec should have (18 here since this is fixed length)
maximum_length | to denote maximnum length to expect as value (in this example 18)
numeric_expected | flag to instruct the expected value is all digits
description | SSCC (Serial Shipping Container Code)
fixed_length | flag to instruct if the expected value is fixed length
decimal_specifier |  flag to instruct if the expected value needs a decimal precision
date_field |  flag to instruct if the expected value needs to be a date value
needs_special_handle | flag to instruct if the minumum expected value should be digits







