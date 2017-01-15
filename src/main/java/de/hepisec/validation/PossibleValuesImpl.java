package de.hepisec.validation;

import de.hepisec.validation.annotations.PossibleValues;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The validated value must be included in the allowed list of values
 * 
 * @author Hendrik Pilz
 */
public class PossibleValuesImpl implements Validator {
    public PossibleValuesImpl() {
    }
    
    @Override
    public void validate(Field field, Object value) throws ValidationException {
        PossibleValues possibleValues = field.getAnnotation(PossibleValues.class);

        if (possibleValues == null || value == null) {
            return;
        }

        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating PossibleValues on {0}", field.getName());
        String[] values = possibleValues.value();

        if (Collection.class.isAssignableFrom(field.getType())) {
            Collection cValue = (Collection) value;
           
            itemLoop:
            for (Object item : cValue) {
                for (String v : values) {
                    if (v.equals(item)) {
                        continue itemLoop;
                    }
                }
                
                throw new ValidationException("The value " + item.toString() + " is not allowed for " + field.getName());
            }
        } else {
            for (String v : values) {
                if (v.equals(value)) {
                    return;
                }
            }
            
            throw new ValidationException("The value " + value.toString() + " is not allowed for " + field.getName());            
        }        
    }
}
