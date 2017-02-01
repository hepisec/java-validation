package de.hepisec.validation;

import de.hepisec.validation.annotations.NotNullOrEmpty;
import de.hepisec.validation.annotations.PossibleValues;
import de.hepisec.validation.annotations.Range;
import de.hepisec.validation.annotations.Ranges;
import de.hepisec.validation.annotations.Regex;
import de.hepisec.validation.annotations.Regexes;
import de.hepisec.validation.annotations.Url;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class Validation {

    private final Map<Class<? extends Annotation>, Validator> validatorMap;

    public Validation() {
        /**
         * all validators must be added to the validationMap
         *
         * this could be replaced with a ClassLoader, which loads Validator
         * implementations and Annotations automatically
         */
        validatorMap = new HashMap<>();
        validatorMap.put(NotNullOrEmpty.class, new NotNullOrEmptyImpl());
        validatorMap.put(PossibleValues.class, new PossibleValuesImpl());
        validatorMap.put(Url.class, new UrlImpl());
        validatorMap.put(Range.class, new RangeImpl());
        validatorMap.put(Ranges.class, new RangesImpl());
        validatorMap.put(Regex.class, new RegexImpl());
        validatorMap.put(Regexes.class, new RegexesImpl());
    }

    /**
     * Validate an object with Validation annotations
     *
     * @param object whose properties have to be validated
     * @throws ValidationException if the validation of a property has failed
     */
    public void validate(Object object) throws ValidationException {
        Logger.getLogger(Validation.class.getName()).fine("Validating entity!");
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            validate(object, field);
        }
    }

    /**
     * Validate a specific field
     *
     * @param object whose properties have to be validated
     * @param field of the object to validate, all other fields are ignored
     * @throws ValidationException if the validation of the property has failed
     */
    public void validate(Object object, Field field) throws ValidationException {
        Annotation[] annotations = field.getAnnotations();

        if (0 == annotations.length) {
            return;
        }

        Method getMethod = getGetter(object, field);

        if (getMethod == null) {
            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Field {0} has no associated method {1}", new Object[]{field.getName(), getGetter(object, field)});
            return;
        }

        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating field {0}", field.getName());

        try {
            Object value = getMethod.invoke(object);
            /**
             * We iterate over all annotations and remember each executed validator to run each validator only once
             * If a validator supports multiple annotations of the same type, it must iterate the annotations itself
             */
            Set<Validator> executedValidators = new HashSet<>();

            for (Annotation annotation : annotations) {
                if (!validatorMap.containsKey(annotation.annotationType())) {
                    continue;
                }
                
                Validator validator = validatorMap.get(annotation.annotationType());
                
                if (!executedValidators.contains(validator)) {                
                    validator.validate(field, value);
                    executedValidators.add(validator);
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Helper method to find the get method for a given field
     *
     * @param field to find the get method for
     * @return the get method or null if no get method is found
     */
    private Method getGetter(Object object, Field field) {
        String name = field.getName();
        String methodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Check for method {0}", methodName);

        try {
            Method getMethod = object.getClass().getDeclaredMethod(methodName);
            return getMethod;
        } catch (NoSuchMethodException | SecurityException ex) {
            return null;
        }
    }
}
