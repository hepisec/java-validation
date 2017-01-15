package de.hepisec.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this annotation to check and verify URL fields (URLs stored as String)
 * 
 * @author Hendrik Pilz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Url {
    /**
     * list of allowed protocols for this URL, e.g. {"http", "https"}
     */
    String[] allowedProtocols();
    /**
     * list of allowed HTTP status codes, e.g. {200, 301, 302, 303}
     * 
     * by default HTTP status codes won't be checked
     */
    int[] checkHttpStatus() default {};
}
