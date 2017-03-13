package de.hepisec.validation;

import de.hepisec.validation.annotations.Range;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class RangeImpl implements Validator {

    public RangeImpl() {
    }

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        if (value == null) {
            return;
        }

        Annotation[] annotations = field.getAnnotations();

        if (0 == annotations.length || !field.isAnnotationPresent(Range.class)) {
            return;
        }

        for (Annotation annotation : annotations) {
            if (!Range.class.equals(annotation.annotationType())) {
                continue;
            }

            Range range = (Range) annotation;

            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Range on {0}", field.getName());
            
            if (isValid(value, range)) {
                return;
            }
        }

        throw new ValidationException(value.toString() + " is outside the allowed range.");
    }

    protected static boolean isValid(Object value, Range range) throws ValidationException {
        try {
            Long val = Long.parseLong(value.toString());

            if (val >= range.min() && val <= range.max()) {
                return true;
            }
        } catch (NumberFormatException ex) {
            throw new ValidationException(value.toString() + " is not a number.");
        }
        
        return false;
    }
}
