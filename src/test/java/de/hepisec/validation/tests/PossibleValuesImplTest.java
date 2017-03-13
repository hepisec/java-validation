package de.hepisec.validation.tests;

import de.hepisec.validation.PossibleValuesImpl;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.PossibleValues;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class PossibleValuesImplTest {
    
    private String field;
    
    @PossibleValues(value = { "hello", "world" })
    private String pv;
       
    public PossibleValuesImplTest() {
    }
   
    @Test
    public void testValidate() throws Exception {
        Validator validator = new PossibleValuesImpl();
        validator.validate(getClass().getDeclaredField("field"), "text");
        validator.validate(getClass().getDeclaredField("pv"), null);
    }    
}
