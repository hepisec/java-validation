package de.hepisec.validation.tests;

import de.hepisec.validation.RegexImpl;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.NotNullOrEmpty;
import de.hepisec.validation.annotations.Regex;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class RegexImplTest {
    
    private String field;
    
    @NotNullOrEmpty
    private String notext;
    
    @NotNullOrEmpty
    @Regex(pattern = "abc")
    private String text;
    
    public RegexImplTest() {
    }

    @Test
    public void testNoValidation() throws NoSuchFieldException, ValidationException {
        Validator validator = new RegexImpl();
        validator.validate(getClass().getDeclaredField("field"), null);
        validator.validate(getClass().getDeclaredField("notext"), "notext");    
    }
    
    @Test(expected = ValidationException.class)
    public void testValidate() throws Exception {
        Validator validator = new RegexImpl();
        validator.validate(getClass().getDeclaredField("text"), "def");
    }    
}
