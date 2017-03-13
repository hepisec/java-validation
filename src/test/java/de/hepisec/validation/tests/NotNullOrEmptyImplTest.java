package de.hepisec.validation.tests;

import de.hepisec.validation.NotNullOrEmptyImpl;
import de.hepisec.validation.Validator;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class NotNullOrEmptyImplTest {
    
    private String field;
          
    public NotNullOrEmptyImplTest() {
    }
   
    @Test
    public void testValidate() throws Exception {
        Validator validator = new NotNullOrEmptyImpl();
        validator.validate(getClass().getDeclaredField("field"), "text");
    }    
}
