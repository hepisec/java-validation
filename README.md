This is a simple annotation based validation framework.

Annotate your class properties and validate them easily.

Installation
============

This package is available on Maven Central

    <groupId>de.hepisec.validation</groupId>
    <artifactId>validation</artifactId>
    <version>1.3.1</version>

Changelog
=========

1.3.1 - Added RequestHandler.apply()

1.3.0 - Added @Regex and support for multiple @Range and @Regex annotations

Usage
=====

Annotations for your class properties:


    public class ClassWithValidation {
        @NotNullOrEmpty
        private String notNullOrEmpty;

        @NotNullOrEmpty
        private List<String> notNullOrEmptyCollection;

        @PossibleValues(value = { "hello", "world" })
        private String possibleValues;

        @PossibleValues(value = { "hello", "world" })
        private List<String> possibleValuesCollection;

        @Url(allowedProtocols = { "http", "https" }, checkHttpStatus = { 200 })
        private String url;

        @Range(min = 10, max = 100)
        private long longNumber;

        @Range(min = 10, max = 100)
        private int intNumber;

        // Java 7
        @Ranges({
            @Range(min = 1, max = 10),
            @Range(min = 100, max = 110)
        })
        private int doubleRangeNumber7 = 2;

        // Java 8
        @Range(min = 1, max = 10)
        @Range(min = 100, max = 110)
        private int doubleRangeNumber8 = 2;

        @Regex(pattern = "a+b+c+")
        private String abc = "aaabbbccc";
        
        @Regex(pattern = "a+b+c+", dontMatch = true)
        private String notAbc = "d";
        
        // Java 7
        @Regexes({
            @Regex(pattern = "a+b+c+"),
            @Regex(pattern = "d+e+f+")
        })
        private String abcdef7 = "aaabbbccc";

        // Java 8
        @Regex(pattern = "a+b+c+")
        @Regex(pattern = "d+e+f+")
        private String abcdef8 = "aaabbbccc";

        // getters and setters following the standard naming convention
        // getProperty()
        // setProperty(...)
    }


Validating an Object with such annotations;


    Validation validation = new Validation();

    try {
        validation.validate(object);
    } catch (ValidationException ex) {
        // object validation failed!
    }


You can also use the RequestHandler class to easily receive an Object with properties set from request parameters:


    Map<String, String[]> parameters = httpServletRequest.getParameterMap();

    RequestHandler<ClassWithValidation> requestHandler = new RequestHandler<>(ClassWithValidation.class);
    // object is validated automatically
    ClassWithValidation object = requestHandler.getObject(parameters);

    // unsafeObject is not validated
    ClassWithValidation unsafeObject = requestHandler.getObject(parameters, false);


Or update an existing object


    ClassWithValidation object = ...
    Map<String, String[]> parameters = httpServletRequest.getParameterMap();

    RequestHandler<ClassWithValidation> requestHandler = new RequestHandler<>(ClassWithValidation.class);
    // object is validated automatically
    requestHandler.apply(object, parameters);

    // object is not validated
    requestHandler.apply(object, parameters, false);

