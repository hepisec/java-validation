package de.hepisec.validation.tests;

import de.hepisec.validation.RangesImpl;
import de.hepisec.validation.Validator;
import de.hepisec.validation.annotations.Range;
import de.hepisec.validation.annotations.Ranges;
import org.junit.Test;

/**
 *
 * @author Hendrik Pilz
 */
public class RangesImplTest {

    private String field;

    @Ranges(@Range(min = 1, max = 10))
    private String range;

    public RangesImplTest() {
    }

    @Test
    public void testValidate() throws Exception {
        Validator validator = new RangesImpl();
        validator.validate(getClass().getDeclaredField("field"), "text");
        validator.validate(getClass().getDeclaredField("range"), null);
    }
}
