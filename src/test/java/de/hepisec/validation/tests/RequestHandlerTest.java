package de.hepisec.validation.tests;

import de.hepisec.validation.RequestHandler;
import de.hepisec.validation.ValidationException;
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
        String[] numericValue = { "1" };
        parameters.put("byteValue", numericValue);
        parameters.put("shortValue", numericValue);
        parameters.put("intValue", numericValue);
        parameters.put("longValue", numericValue);
        String[] floatingValue = { "1.1" };
        parameters.put("floatValue", floatingValue);
        parameters.put("doubleValue", floatingValue);
        parameters.put("booleanValue", new String[] { "true" });
        parameters.put("stringValue", new String[] { "hello world" });
//        parameters.put("charValue", new String[] { "a" });        
        RequestHandler<ClassToTest> requestHandler = new RequestHandler<>(ClassToTest.class);
        ClassToTest object = requestHandler.getObject(parameters);
        assertTrue(object.getByteValue() == 1);
        assertTrue(object.getShortValue() == 1);
        assertTrue(object.getIntValue() == 1);
        assertTrue(object.getLongValue() == 1);
        assertTrue(Math.abs(object.getFloatValue() - 1.1) < 0.0000001);
        assertTrue(Math.abs(object.getDoubleValue() - 1.1) < 0.0000001);
        assertTrue(object.getBooleanValue());
        assertTrue(object.getStringValue().equals("hello world"));
//        assertTrue(object.getCharValue() == 'a');
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
}
