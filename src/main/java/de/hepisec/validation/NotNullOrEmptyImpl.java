package de.hepisec.validation;

import de.hepisec.validation.annotations.NotNullOrEmpty;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The validated value must not be null or an empty String
 *
 * @author Hendrik Pilz
 */
public class NotNullOrEmptyImpl implements Validator {
    public NotNullOrEmptyImpl() {
    }
    
    @Override
    public void validate(Field field, Object value) throws ValidationException {
        if (!field.isAnnotationPresent(NotNullOrEmpty.class)) {
            return;
        }
        
        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating NotNullOrEmpty on {0}", field.getName());

        if (value == null) {
            throw new ValidationException(field.getName() + " must not be null!");
        }

        if (field.getType().equals(String.class)) {
            String sValue = (String) value;

            if (sValue.isEmpty()) {
                throw new ValidationException(field.getName() + " must not be empty!");
            }
        } else if (Collection.class.isAssignableFrom(field.getType())) {
            Collection cValue = (Collection) value;

            if (cValue.isEmpty()) {
                throw new ValidationException(field.getName() + " must not be empty!");
            }
        }     
    }    
}
