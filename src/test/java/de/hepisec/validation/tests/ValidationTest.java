package de.hepisec.validation.tests;

import de.hepisec.validation.Validation;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.annotations.NotNullOrEmpty;
import de.hepisec.validation.annotations.PossibleValues;
import de.hepisec.validation.annotations.Url;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hendrik Pilz
 */
public class ValidationTest {
    
    @Deprecated
    private String field;
    
    @Deprecated
    private String text;
    
    @NotNullOrEmpty
    private String privateField;    
    
    public ValidationTest() {
    }
    
    @Test
    public void testNotNullOrEmptyString() {
        ClassWithValidation object = new ClassWithValidation();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("NotNullOrEmpty on String");
        }
        
        object.setNotNullOrEmpty(null);
        
        try {
            validation.validate(object);
            fail("NotNullOrEmpty on null");
        } catch (ValidationException ex) {
        }

        object.setNotNullOrEmpty("");
        
        try {
            validation.validate(object);
            fail("NotNullOrEmpty on empty String");
        } catch (ValidationException ex) {
        }
    }
    
    @Test
    public void testNotNullOrEmptyCollection() {
        ClassWithValidation object = new ClassWithValidation();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("NotNullOrEmpty on List");
        }        
        
        object.getNotNullOrEmptyCollection().clear();

        try {
            validation.validate(object);
            fail("NotNullOrEmpty on empty List");
        } catch (ValidationException ex) {
        }        
        
        object.setNotNullOrEmptyCollection(null);
        
        try {
            validation.validate(object);
            fail("NotNullOrEmpty on null");
        } catch (ValidationException ex) {
        }        
    }
    
    @Test
    public void testPossibleValues() {
        ClassWithValidation object = new ClassWithValidation();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("PossibleValues on correct value");
        }        
        
        object.setPossibleValues("fail");
        
        try {
            validation.validate(object);
            fail("PossibleValues on incorrect value");
        } catch (ValidationException ex) {
        }                
    }
    
    @Test
    public void testPossibleValuesCollection() {
        ClassWithValidation object = new ClassWithValidation();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("PossibleValuesCollection on correct values");
        }        
        
        object.getPossibleValuesCollection().add("fail");
        
        try {
            validation.validate(object);
            fail("PossibleValuesCollection on incorrect value");
        } catch (ValidationException ex) {
        }          
    }
    
    @Test
    public void testUrl() {
        ClassWithUrl object = new ClassWithUrl();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("Url on valid URL");
        } 
        
        object.setUrl("invalid");
        
        try {
            validation.validate(object);
            fail("Url on invalid value");
        } catch (ValidationException ex) {
        }         
        
        object.setUrl("ftp://www.example.com");
        
        try {
            validation.validate(object);
            fail("Url on wrong protocol");
        } catch (ValidationException ex) {
        }
        
        object.setUrl("http://www.example.com/404");
        
        try {
            validation.validate(object);
            fail("Url on wrong status");
        } catch (ValidationException ex) {
        }        
    }
        
    @Test
    public void testValidateNoGetter() throws Exception {
        Validation validation = new Validation();
        validation.validate(this);
    }
 
    public String getText() {
        return text;
    }
    
    private String getPrivateField() {
        return privateField;
    }    
    
    public class ClassWithValidation {
        @NotNullOrEmpty
        private String notNullOrEmpty = "notNullOrEmpty";
        
        @NotNullOrEmpty
        private List<String> notNullOrEmptyCollection = new ArrayList<String>(Arrays.asList("Hello"));
        
        @PossibleValues(value = { "hello", "world" })
        private String possibleValues = "hello";
        
        @PossibleValues(value = { "hello", "world" })
        private List<String> possibleValuesCollection = new ArrayList<String>(Arrays.asList("hello", "world"));
        
        public Object getNotNullOrEmpty() {
            return notNullOrEmpty;
        }

        public void setNotNullOrEmpty(String notNullOrEmpty) {
            this.notNullOrEmpty = notNullOrEmpty;
        }

        public List<String> getNotNullOrEmptyCollection() {
            return notNullOrEmptyCollection;
        }

        public void setNotNullOrEmptyCollection(List<String> notNullOrEmptyCollection) {
            this.notNullOrEmptyCollection = notNullOrEmptyCollection;
        }

        public String getPossibleValues() {
            return possibleValues;
        }

        public void setPossibleValues(String possibleValues) {
            this.possibleValues = possibleValues;
        }        

        public List<String> getPossibleValuesCollection() {
            return possibleValuesCollection;
        }

        public void setPossibleValuesCollection(List<String> possibleValuesCollection) {
            this.possibleValuesCollection = possibleValuesCollection;
        }
    }
    
    public class ClassWithUrl {
        @Url(allowedProtocols = { "http", "https" }, checkHttpStatus = { 200 })
        private String url = "http://www.example.com";

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
