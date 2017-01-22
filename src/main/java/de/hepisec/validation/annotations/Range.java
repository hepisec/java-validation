package de.hepisec.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to specify the allowed range for integer values
 * 
 * @author Hendrik Pilz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Range {
    /**
     * Allowed minimum
     *
     * @return minimum allowed value
     */
    long min() default Long.MIN_VALUE;
    
    /**
     * Allowed maximum
     * 
     * @return maximum allowed value
     */
    long max() default Long.MAX_VALUE;
}
