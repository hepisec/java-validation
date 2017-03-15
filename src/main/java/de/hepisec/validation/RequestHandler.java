package de.hepisec.validation;

import de.hepisec.validation.annotations.DateFormat;
import de.hepisec.validation.converter.DateConverter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a shorthand class to create an object from request parameters
 *
 * @author Hendrik Pilz
 * @param <T> the type which is returned by the RequestHandler
 */
public class RequestHandler<T> extends Validation {

    public static final String PRIMITIVE_BYTE = "byte";
    public static final String PRIMITIVE_SHORT = "short";
    public static final String PRIMITIVE_INT = "int";
    public static final String PRIMITIVE_LONG = "long";
    public static final String PRIMITIVE_FLOAT = "float";
    public static final String PRIMITIVE_DOUBLE = "double";
    public static final String PRIMITIVE_BOOLEAN = "boolean";
    public static final String PRIMITIVE_CHAR = "char";

    private Class<T> clazz;
    private String parameterPrefix;

    /**
     * Create a new RequestHandler with an empty parameterPrefix
     *
     * @param clazz Class of type T
     */
    public RequestHandler(Class<T> clazz) {
        this(clazz, "");
    }

    /**
     * Create a new RequestHandler with the given parameterPrefix
     *
     * @param clazz Class of type T
     * @param parameterPrefix prefix to find the correct parameters in the
     * parameter map
     */
    public RequestHandler(Class<T> clazz, String parameterPrefix) {
        super();
        this.clazz = clazz;
        this.parameterPrefix = parameterPrefix;
    }

    /**
     * Get the current parameterPrefix
     *
     * @return the current parameterPrefix
     */
    public String getParameterPrefix() {
        return parameterPrefix;
    }

    /**
     * Set the parameterPrefix
     *
     * @param parameterPrefix prefix to find the correct parameters in the
     * parameter map
     */
    public void setParameterPrefix(String parameterPrefix) {
        this.parameterPrefix = parameterPrefix;
    }

    /**
     * Get an validated object from the given parameters
     *
     * @param parameterMap typically obtained from a ServletRequest
     * @return an Object of type T with properties set from the given parameters
     * @throws ValidationException if the validation of the parameters fails
     */
    public T getObject(Map<String, String[]> parameterMap) throws ValidationException {
        return getObject(parameterMap, true);
    }

    /**
     * Get an validated object from the given parameters
     *
     * @param parameterMap typically obtained from a ServletRequest
     * @param validate set to false if the parameters should not get validated
     * @return an Object of type T with properties set from the given parameters
     * @throws ValidationException if the validation of the parameters fails
     */
    public T getObject(Map<String, String[]> parameterMap, boolean validate) throws ValidationException {
        try {
            T object = clazz.newInstance();
            apply(object, parameterMap, validate);
            return object;
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException ex) {
            throw new ValidationException(ex);
        }
    }

    /**
     * Apply and validate the given parameters on the given instance
     *
     * @param instance where the parameters shall be applied
     * @param parameterMap typically obtained from a ServletRequest
     * @throws ValidationException
     */
    public void apply(T instance, Map<String, String[]> parameterMap) throws ValidationException {
        apply(instance, parameterMap, true);
    }

    /**
     * Apply and validate the given parameters on the given instance
     *
     * @param instance where the parameters shall be applied
     * @param parameterMap typically obtained from a ServletRequest
     * @param validate set to false if the parameters should not get validated
     * @throws ValidationException
     */
    public void apply(T instance, Map<String, String[]> parameterMap, boolean validate) throws ValidationException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String paramName = parameterPrefix + field.getName();

            if (parameterMap.containsKey(paramName)) {
                setValue(instance, field, parameterMap.get(paramName));
            }
        }

        if (validate) {
            validate(instance);
        }
    }

    /**
     * Helper method to find the set method for a given field
     *
     * @param field to find the set method for
     * @return the set method or null if no set method is found
     */
    private Method getSetter(Object object, Field field) {
        String name = field.getName();
        String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        Logger.getLogger(RequestHandler.class.getName()).log(Level.FINE, "Check for method {0}", methodName);

        try {
            Method[] methods = object.getClass().getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    return method;
                }
            }
        } catch (SecurityException ex) {
        }

        return null;
    }

    /**
     * Helper method to convert the value to the correct type and invoke the
     * setMethod on object
     *
     * @param setMethod
     * @param object
     * @param type
     * @param get
     */
    private void setValue(T object, Field field, String[] value) throws ValidationException {
        Class<?> type = field.getType();
        
        if (type.isArray() || Collection.class.isAssignableFrom(type)) {
            throw new ValidationException("Arrays and Collections are not supported by RequestHandler yet.");
        } else {
            setValue(object, field, value[0]);
        }
    }

    /**
     * Helper method to convert the value to the correct type and invoke the
     * setMethod on object
     *
     * @param setMethod
     * @param object
     * @param type
     * @param get
     */
    private void setValue(T object, Field field, String value) throws ValidationException {
        Class<?> type = field.getType();
        Method setMethod = getSetter(object, field);
        
        if (null == setMethod) {
            return;
        }        
        
        try {
            if (type.equals(String.class)) {
                setMethod.invoke(object, value);
            } else if (PRIMITIVE_BYTE.equals(type.getName()) || type.equals(Byte.class)) {
                byte v = Byte.parseByte(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_SHORT.equals(type.getName()) || type.equals(Short.class)) {
                short v = Short.parseShort(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_INT.equals(type.getName()) || type.equals(Integer.class)) {
                int v = Integer.parseInt(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_LONG.equals(type.getName()) || type.equals(Long.class)) {
                long v = Long.parseLong(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_FLOAT.equals(type.getName()) || type.equals(Float.class)) {
                float v = Float.parseFloat(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_DOUBLE.equals(type.getName()) || type.equals(Double.class)) {
                double v = Double.parseDouble(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_BOOLEAN.equals(type.getName()) || type.equals(Boolean.class)) {
                boolean v = Boolean.parseBoolean(value);
                setMethod.invoke(object, v);
            } else if (PRIMITIVE_CHAR.equals(type.getName()) || type.equals(Character.class)) {
                if (value.length() > 1) {
                    throw new ValidationException("Illegal char value.");
                } else {
                    char v = value.charAt(0);
                    setMethod.invoke(object, v);
                }
            } else if (type.equals(Date.class)) {
                if (!field.isAnnotationPresent(DateFormat.class)) {
                    throw new ValidationException("Missing DateFormat annotation for field " + field.getName() + ".");
                }
                
                DateFormat df = field.getAnnotation(DateFormat.class);
                DateConverter dc = new DateConverter(df.format());
                Date date = dc.convert(value);
                
                if (null == date) {
                    throw new ValidationException("Couldn't parse date for field " + field.getName() + ".");
                }
                
                setMethod.invoke(object, date);
            } else {
                throw new ValidationException("The type of field " + field.getName() + " is not supported by RequestHandler. Failed type: " + type.getName());
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ValidationException(ex);
        }
    }
}
