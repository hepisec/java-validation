package de.hepisec.validation.tests;

import de.hepisec.validation.RequestHandler;
import de.hepisec.validation.ValidationException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hendrik Pilz
 */
public class RequestHandlerTest {

    public RequestHandlerTest() {
    }

    @Test
    public void testRequestHandler() throws ValidationException {
        // the map simulates what is usually returned from ServletRequest.getParameterMap()
        Map<String, String[]> parameters = new HashMap<>();
        String[] numericValue = {"1"};
        parameters.put("byteValue", numericValue);
        parameters.put("shortValue", numericValue);
        parameters.put("intValue", numericValue);
        parameters.put("longValue", numericValue);
        String[] floatingValue = {"1.1"};
        parameters.put("floatValue", floatingValue);
        parameters.put("doubleValue", floatingValue);
        parameters.put("booleanValue", new String[]{"true"});
        parameters.put("stringValue", new String[]{"hello world"});
        parameters.put("charValue", new String[]{"a"});
        RequestHandler<ClassToTest> requestHandler = new RequestHandler<>(ClassToTest.class);
        requestHandler.setParameterPrefix(requestHandler.getParameterPrefix()); // to cover the getter and setter ;-)
        ClassToTest object = requestHandler.getObject(parameters);
        assertTrue(object.getByteValue() == 1);
        assertTrue(object.getShortValue() == 1);
        assertTrue(object.getIntValue() == 1);
        assertTrue(object.getLongValue() == 1);
        assertTrue(Math.abs(object.getFloatValue() - 1.1) < 0.0000001);
        assertTrue(Math.abs(object.getDoubleValue() - 1.1) < 0.0000001);
        assertTrue(object.getBooleanValue());
        assertTrue(object.getStringValue().equals("hello world"));
        assertTrue(object.getCharValue() == 'a');
    }

    @Test
    public void testRequestHandlerApply() throws ValidationException {
        // the map simulates what is usually returned from ServletRequest.getParameterMap()
        Map<String, String[]> parameters = new HashMap<>();
        String[] numericValue = {"1"};
        parameters.put("byteValue", numericValue);
        parameters.put("shortValue", numericValue);
        parameters.put("intValue", numericValue);
        parameters.put("longValue", numericValue);
        String[] floatingValue = {"1.1"};
        parameters.put("floatValue", floatingValue);
        parameters.put("doubleValue", floatingValue);
        parameters.put("booleanValue", new String[]{"true"});
        parameters.put("stringValue", new String[]{"hello world"});
        parameters.put("charValue", new String[]{"a"});
        RequestHandler<ClassToTest> requestHandler = new RequestHandler<>(ClassToTest.class);
        ClassToTest object = new ClassToTest();
        requestHandler.apply(object, parameters);
        assertTrue(object.getByteValue() == 1);
        assertTrue(object.getShortValue() == 1);
        assertTrue(object.getIntValue() == 1);
        assertTrue(object.getLongValue() == 1);
        assertTrue(Math.abs(object.getFloatValue() - 1.1) < 0.0000001);
        assertTrue(Math.abs(object.getDoubleValue() - 1.1) < 0.0000001);
        assertTrue(object.getBooleanValue());
        assertTrue(object.getStringValue().equals("hello world"));
        assertTrue(object.getCharValue() == 'a');
    }    
    
    @Test(expected = ValidationException.class)
    public void testRequestHandler2() throws ValidationException {
        // the map simulates what is usually returned from ServletRequest.getParameterMap()
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("charValue", new String[]{"aa"});
        RequestHandler<ClassToTest> requestHandler = new RequestHandler<>(ClassToTest.class);
        ClassToTest object = requestHandler.getObject(parameters);
    }

    @Test(expected = ValidationException.class)
    public void testUnsupportedClassWithArray() throws ValidationException {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("array", new String[]{"a", "b"});
        RequestHandler<UnsupportedClassWithArray> requestHandler = new RequestHandler<>(UnsupportedClassWithArray.class);
        UnsupportedClassWithArray object = requestHandler.getObject(parameters);
    }
    
    @Test(expected = ValidationException.class)
    public void testUnsupportedClassWithComplexType() throws ValidationException {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("file", new String[]{"file"});
        RequestHandler<UnsupportedClassWithComplexType> requestHandler = new RequestHandler<>(UnsupportedClassWithComplexType.class);
        UnsupportedClassWithComplexType object = requestHandler.getObject(parameters);
    }    

    @Test(expected = ValidationException.class)
    public void testClassWithPrivateConstructor() throws ValidationException {
        Map<String, String[]> parameters = new HashMap<>();
        RequestHandler<ClassWithPrivateConstructor> requestHandler = new RequestHandler<>(ClassWithPrivateConstructor.class);
        ClassWithPrivateConstructor object = requestHandler.getObject(parameters);        
    }
    
    @Test(expected = ValidationException.class)
    public void testClassWithPrivateSetter() throws ValidationException {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("field", new String[]{"field"});
        RequestHandler<ClassWithPrivateSetter> requestHandler = new RequestHandler<>(ClassWithPrivateSetter.class);
        ClassWithPrivateSetter object = requestHandler.getObject(parameters);        
    }  

    @Test
    public void testClassWithoutSetter() throws ValidationException {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("field", new String[]{"field"});
        RequestHandler<ClassWithoutSetter> requestHandler = new RequestHandler<>(ClassWithoutSetter.class);
        ClassWithoutSetter object = requestHandler.getObject(parameters);
        assertTrue(object.getField().equals("value"));
    }      
    
    public static class ClassToTest {

        private byte byteValue;
        private short shortValue;
        private int intValue;
        private long longValue;
        private float floatValue;
        private double doubleValue;
        private char charValue;
        private boolean booleanValue;
        private String stringValue;

        public ClassToTest() {

        }

        public byte getByteValue() {
            return byteValue;
        }

        public void setByteValue(byte byteValue) {
            this.byteValue = byteValue;
        }

        public short getShortValue() {
            return shortValue;
        }

        public void setShortValue(short shortValue) {
            this.shortValue = shortValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public long getLongValue() {
            return longValue;
        }

        public void setLongValue(long longValue) {
            this.longValue = longValue;
        }

        public float getFloatValue() {
            return floatValue;
        }

        public void setFloatValue(float floatValue) {
            this.floatValue = floatValue;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public char getCharValue() {
            return charValue;
        }

        public void setCharValue(char charValue) {
            this.charValue = charValue;
        }

        public boolean getBooleanValue() {
            return booleanValue;
        }

        public void setBooleanValue(boolean booleanValue) {
            this.booleanValue = booleanValue;
        }

        public String getStringValue() {
            return stringValue;
        }

        public void setStringValue(String stringValue) {
            this.stringValue = stringValue;
        }
    }

    public static class UnsupportedClassWithArray {

        private String[] array;

        public UnsupportedClassWithArray() {

        }

        public String[] getArray() {
            return array;
        }

        public void setArray(String[] array) {
            this.array = array;
        }
    }

    public static class UnsupportedClassWithComplexType {

        private File file;

        public UnsupportedClassWithComplexType() {

        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }
    
    public static class ClassWithPrivateConstructor {
        private ClassWithPrivateConstructor() {
            
        }
    }
    
    public static class ClassWithPrivateSetter {
        private String field;

        public ClassWithPrivateSetter() {
            setField(null);
        }

        public String getField() {
            return field;
        }

        private void setField(String field) {
            this.field = field;
        }                
    }
    
    public static class ClassWithoutSetter {
        private String field = "value";
        
        public ClassWithoutSetter() {

        }
        
        public String getField() {
            return field;
        }
    }
}
