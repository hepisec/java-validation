package de.hepisec.validation;

import java.io.IOException;

/**
 *
 * @author Hendrik Pilz
 */
public class ValidationException extends IOException {
    public ValidationException() {
        super();
    }
    
    public ValidationException(String msg) {
        super(msg);
    }
    
    public ValidationException(Throwable cause) {
        super(cause);
    }
    
    public ValidationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
