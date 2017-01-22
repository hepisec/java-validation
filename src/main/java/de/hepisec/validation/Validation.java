package de.hepisec.validation;

import de.hepisec.validation.annotations.NotNullOrEmpty;
import de.hepisec.validation.annotations.PossibleValues;
import de.hepisec.validation.annotations.Range;
import de.hepisec.validation.annotations.Url;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class Validation {
    
    private final Map<Class, Class> validationMap;
    
    public Validation() {
        /**
         * all validators must be added to the validationMap
         * 
         * this could be replaced with a ClassLoader, which loads Validator implementations and Annotations automatically
         */
        validationMap = new HashMap<>();
        validationMap.put(NotNullOrEmpty.class, NotNullOrEmptyImpl.class);
        validationMap.put(PossibleValues.class, PossibleValuesImpl.class);
        validationMap.put(Url.class, UrlImpl.class);
        validationMap.put(Range.class, RangeImpl.class);
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
        if (!requiresValidation(field)) {
            return;
        }
        
        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating field {0}", field.getName());
        Method getMethod = getGetter(object, field);

        if (getMethod == null) {
            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Field {0} has no associated method {1}", new Object[]{field.getName(), getGetter(object, field)});
            return;
        }

        try {
            Object value = getMethod.invoke(object);
            
            for (Map.Entry<Class, Class> entry : validationMap.entrySet()) {
                try {
                    Class validatorClass = entry.getValue();
                    Object validator = validatorClass.getConstructor().newInstance();
                    Method validateMethod = validatorClass.getDeclaredMethod("validate", Field.class, Object.class);
                    validateMethod.invoke(validator, field, value);
                } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
                    Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);                    
                } catch (InvocationTargetException ex) {
                    Throwable cause = ex.getCause();
                    
                    if (cause != null) {
                        throw new ValidationException(cause.getMessage());
                    }                    
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Validation.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);           
        }
    }
    
    /**
     * Helper method to determine, whether the field requires validation
     * 
     * @param field to check
     */
    private boolean requiresValidation(Field field) {        
        for (Class validationAnnotation : validationMap.keySet()) {
            if (field.isAnnotationPresent(validationAnnotation)) {
                return true;
            }
        }
        
        return false;
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
