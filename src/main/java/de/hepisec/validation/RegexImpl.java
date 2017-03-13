package de.hepisec.validation;

import de.hepisec.validation.annotations.Regex;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hendrik Pilz
 */
public class RegexImpl implements Validator {

    public RegexImpl() {
    }

    @Override
    public void validate(Field field, Object value) throws ValidationException {
        if (value == null) {
            return;
        }

        Annotation[] annotations = field.getAnnotations();

        if (0 == annotations.length || !field.isAnnotationPresent(Regex.class)) {
            return;
        }

        for (Annotation annotation : annotations) {
            if (!Regex.class.equals(annotation.annotationType())) {
                continue;
            }

            Regex regex = (Regex) annotation;

            Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Regex on {0}", field.getName());
            
            if (isValid(value, regex)) {
                return;
            }
        }

        throw new ValidationException("Regex Validation failed on " + field.getName());
    }

    protected static boolean isValid(Object value, Regex regex) {
        Pattern pattern = Pattern.compile(regex.pattern(), regex.flags());
        Matcher matcher = pattern.matcher(value.toString());

        if ((!regex.dontMatch() && matcher.matches()) || (regex.dontMatch() && !matcher.matches())) {
            return true;
        }

        return false;
    }
}
