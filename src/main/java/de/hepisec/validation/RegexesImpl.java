package de.hepisec.validation;

import de.hepisec.validation.annotations.Regex;
import de.hepisec.validation.annotations.Regexes;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class RegexesImpl implements Validator {

    public RegexesImpl() {
    }

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        Regexes regexes = field.getAnnotation(Regexes.class);
        
        if (regexes == null || value == null) {
            return;
        }

        for (Regex regex : regexes.value()) {            
            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Regex on {0}", field.getName());
            
            if (RegexImpl.isValid(value, regex)) {
                return;
            }            
        }
        
        throw new ValidationException("Regex Validation failed on " + field.getName());
    }
}