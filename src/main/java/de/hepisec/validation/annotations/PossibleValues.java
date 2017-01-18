package de.hepisec.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to specify the possible values for String fields
 * 
 * @author Hendrik Pilz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PossibleValues {
    /**
     * List of allowed Strings for the annotated field
     *
     * @return array with allowed values
     */
    String[] value();
}
