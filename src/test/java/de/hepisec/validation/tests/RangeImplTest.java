package de.hepisec.validation.tests;

import de.hepisec.validation.RangeImpl;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.NotNullOrEmpty;
import de.hepisec.validation.annotations.Range;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class RangeImplTest {
    
    private String field;
    
    @NotNullOrEmpty
    private int number;
    
    @NotNullOrEmpty
    @Range(min = 0, max = 1)
    private String text;
    
    public RangeImplTest() {
    }

    @Test
    public void testNoValidation() throws NoSuchFieldException, ValidationException {
        Validator validator = new RangeImpl();
        validator.validate(getClass().getDeclaredField("field"), null);
        validator.validate(getClass().getDeclaredField("number"), 2);    
    }
    
    @Test(expected = ValidationException.class)
    public void testValidate() throws Exception {
        Validator validator = new RangeImpl();
        validator.validate(getClass().getDeclaredField("text"), "text");
    }    
}
