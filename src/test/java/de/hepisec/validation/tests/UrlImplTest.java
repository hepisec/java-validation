package de.hepisec.validation.tests;

import de.hepisec.validation.UrlImpl;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.Url;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class UrlImplTest {
    
    private String field;
    
    @Url(allowedProtocols = { "http" })
    private String url;
       
    public UrlImplTest() {
    }
   
    @Test
    public void testValidate() throws Exception {
        Validator validator = new UrlImpl();
        validator.validate(getClass().getDeclaredField("field"), "text");
        validator.validate(getClass().getDeclaredField("url"), null);
    }    
}
