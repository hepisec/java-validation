This is a simple annotation based validation framework.

Annotate your class properties and validate them easily.

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

        // getters and setters ...
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
