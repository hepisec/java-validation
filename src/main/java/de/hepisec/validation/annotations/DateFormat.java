package de.hepisec.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to specify the input date format for a java.util.Date field
 * 
 * @author Hendrik Pilz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DateFormat {
    /**
     * Date Format as used in java.text.SimpleDateFormat
     *
     * @return the date format
     */
    String format();    
}
