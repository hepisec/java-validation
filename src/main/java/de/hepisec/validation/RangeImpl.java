package de.hepisec.validation;

import de.hepisec.validation.annotations.Range;
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
        Range range = field.getAnnotation(Range.class);

        if (range == null || value == null) {
            return;
        }

        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Range on {0}", field.getName());

        Long val = Long.parseLong(value.toString());

        if (val < range.min() || val > range.max()) {
            throw new ValidationException(val + " is outside the allowed range.");
        }
    }
}
