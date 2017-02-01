package de.hepisec.validation.tests;

import de.hepisec.validation.Validation;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.annotations.Regex;
import de.hepisec.validation.annotations.Regexes;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Hendrik pilz
 */
public class RegexTest {
    @Test
    public void testRegex() {
        ClassWithRegex object = new ClassWithRegex();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("Regex on valid values");
        } 

        object.setAbc("d");
        
        try {
            validation.validate(object);
            fail("Regex on invalid value (a+b+c+)");
        } catch (ValidationException ex) {
        } 

        object.setAbc("abc");
        object.setNotAbc("abc");
        
        try {
            validation.validate(object);
            fail("Regex on invalid valid (not a+b+c+)");
        } catch (ValidationException ex) {
        } 

        object.setNotAbc("d");
        object.setAbcdef("dddeeefff");
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("Regex on valid value (a+b+c+ or d+e+f+)");
        }         
        
        object.setAbcdef("g");
        
        try {
            validation.validate(object);
            fail("Regex on invalid value (a+b+c+ or d+e+f+)");
        } catch (ValidationException ex) {
        }        
    }

    public class ClassWithRegex {
        @Regex(pattern = "a+b+c+")
        private String abc = "aaabbbccc";
        
        @Regex(pattern = "a+b+c+", dontMatch = true)
        private String notAbc = "d";
        
        @Regexes({
            @Regex(pattern = "a+b+c+"),
            @Regex(pattern = "d+e+f+")
        })
        private String abcdef = "aaabbbccc";

        public String getAbc() {
            return abc;
        }

        public void setAbc(String abc) {
            this.abc = abc;
        }

        public String getNotAbc() {
            return notAbc;
        }

        public void setNotAbc(String notAbc) {
            this.notAbc = notAbc;
        }

        public String getAbcdef() {
            return abcdef;
        }

        public void setAbcdef(String abcdef) {
            this.abcdef = abcdef;
        }        
    }    
}
