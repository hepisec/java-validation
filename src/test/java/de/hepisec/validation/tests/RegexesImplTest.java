package de.hepisec.validation.tests;

import de.hepisec.validation.RegexesImpl;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.Regex;
import de.hepisec.validation.annotations.Regexes;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class RegexesImplTest {

    private String field;

    @Regexes(@Regex(pattern = "abc"))
    private String regex;

    public RegexesImplTest() {
    }

    @Test
    public void testValidate() throws Exception {
        Validator validator = new RegexesImpl();
        validator.validate(getClass().getDeclaredField("field"), "text");
        validator.validate(getClass().getDeclaredField("regex"), null);
    }
}
