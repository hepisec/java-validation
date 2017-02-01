package de.hepisec.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to specify a regular expression for the value
 * 
 * Multiple occurrences on the same property are allowed
 * 
 * @author Hendrik Pilz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Regex {
    /**
     * Regular expression to match
     *
     * @return the regular expression
     */
    String pattern();
    
    /**
     * The flags to be used with the pattern, as defined in java.util.regex.Pattern
     * 
     * @return the flags
     */
    int flags() default 0;
    
    /**
     * Set dontMatch to true, if you want the value not to be matched by the regular expression
     * 
     * @return true, if the regex must not match, otherwise false
     */
    boolean dontMatch() default false;
}
