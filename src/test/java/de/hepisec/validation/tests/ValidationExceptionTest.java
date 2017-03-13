package de.hepisec.validation.tests;

import de.hepisec.validation.ValidationException;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class ValidationExceptionTest {
    
    public ValidationExceptionTest() {
    }

    @Test(expected = ValidationException.class)
    public void testValidationException1() throws ValidationException {
        throw new ValidationException();
    }

    @Test(expected = ValidationException.class)
    public void testValidationException2() throws ValidationException {
        throw new ValidationException("msg");
    }    
    
    @Test(expected = ValidationException.class)
    public void testValidationException3() throws ValidationException {
        throw new ValidationException(new Exception());
    }    
    
    @Test(expected = ValidationException.class)
    public void testValidationException4() throws ValidationException {
        throw new ValidationException("msg", new Exception());
    }        
}
