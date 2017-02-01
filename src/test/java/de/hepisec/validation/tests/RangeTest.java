package de.hepisec.validation.tests;

import de.hepisec.validation.Validation;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.annotations.Range;
import de.hepisec.validation.annotations.Ranges;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Hendrik pilz
 */
public class RangeTest {
    @Test
    public void testRange() {
        ClassWithRange object = new ClassWithRange();
        Validation validation = new Validation();
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("Range on valid Range");
        } 

        object.setLongNumber(1000);
        
        try {
            validation.validate(object);
            fail("Range on invalid Range (gt max long)");
        } catch (ValidationException ex) {
        } 

        object.setLongNumber(0);
        
        try {
            validation.validate(object);
            fail("Range on invalid Range (lt min long)");
        } catch (ValidationException ex) {
        } 

        object.setLongNumber(50);
        object.setIntNumber(1000);
        
        try {
            validation.validate(object);
            fail("Range on invalid Range (gt max int)");
        } catch (ValidationException ex) {
        }         
        
        object.setIntNumber(0);
        
        try {
            validation.validate(object);
            fail("Range on invalid Range (lt min int)");
        } catch (ValidationException ex) {
        }
        
        object.setIntNumber(50);
        object.setDoubleRangeNumber(20);
        
        try {
            validation.validate(object);
            fail("Range on invalid Double Range (between 1st and 2nd Range)");
        } catch (ValidationException ex) {
        }

        object.setDoubleRangeNumber(0);
        
        try {
            validation.validate(object);
            fail("Range on invalid Double Range (lt min)");
        } catch (ValidationException ex) {
        }

        object.setDoubleRangeNumber(120);
        
        try {
            validation.validate(object);
            fail("Range on invalid Double Range (gt max)");
        } catch (ValidationException ex) {
        }

        object.setDoubleRangeNumber(102);
        
        try {
            validation.validate(object);
        } catch (ValidationException ex) {
            fail("Range on valid Double Range (2nd Range)");
        }        
    }

    public class ClassWithRange {
        @Range(min = 10, max = 100)
        private long longNumber = 50;
        
        @Range(min = 10, max = 100)
        private int intNumber = 50;
        
        @Ranges({
            @Range(min = 1, max = 10),
            @Range(min = 100, max = 110)
        })
        private int doubleRangeNumber = 2;
        
        public long getLongNumber() {
            return longNumber;
        }
        
        public void setLongNumber(long longNumber) {
            this.longNumber = longNumber;
        }

        public int getIntNumber() {
            return intNumber;
        }

        public void setIntNumber(int intNumber) {
            this.intNumber = intNumber;
        }

        public int getDoubleRangeNumber() {
            return doubleRangeNumber;
        }

        public void setDoubleRangeNumber(int doubleRangeNumber) {
            this.doubleRangeNumber = doubleRangeNumber;
        }
    }    
}
