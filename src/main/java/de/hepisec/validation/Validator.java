package de.hepisec.validation;

import java.lang.reflect.Field;

/**
 *
 * @author Hendrik Pilz
 */
public interface Validator {  
    public void validate(Field field, Object value) throws ValidationException;
}
