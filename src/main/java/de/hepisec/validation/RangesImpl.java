package de.hepisec.validation;

import de.hepisec.validation.annotations.Range;
import de.hepisec.validation.annotations.Ranges;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class RangesImpl implements Validator {

    public RangesImpl() {
    }

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        Ranges ranges = field.getAnnotation(Ranges.class);
        
        if (ranges == null || value == null) {
            return;
        }

        for (Range range : ranges.value()) {            
            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Range on {0}", field.getName());
            
            if (RangeImpl.isValid(value, range)) {
                return;
            }            
        }
        
        throw new ValidationException(value.toString() + " is outside the allowed range.");        
    }
}