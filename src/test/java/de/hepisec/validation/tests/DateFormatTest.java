package de.hepisec.validation.tests;

import de.hepisec.validation.RequestHandler;
import de.hepisec.validation.ValidationException;
import de.hepisec.validation.annotations.DateFormat;
import de.hepisec.validation.annotations.NotNullOrEmpty;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hendrik pilz
 */
public class DateFormatTest {
    @Test
    public void testDateFormat() throws ValidationException {
        RequestHandler<ClassWithDateFormat> requestHandler = new RequestHandler<>(ClassWithDateFormat.class);
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("date", new String[]{"2017-03-15"});
        ClassWithDateFormat object = requestHandler.getObject(parameters);
        Date date = object.getDate();
        assertEquals(date.getTime(), new GregorianCalendar(2017, 2, 15).getTimeInMillis()); // GregorianCalendar month parameter is 0-based
    }

    @Test(expected = ValidationException.class)
    public void testInvalidDateFormat() throws ValidationException {
        RequestHandler<ClassWithDateFormat> requestHandler = new RequestHandler<>(ClassWithDateFormat.class);
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("date", new String[]{"hello world"});
        requestHandler.getObject(parameters);
    }
    
    @Test(expected = ValidationException.class)
    public void testDateWithoutFormat() throws ValidationException {
        RequestHandler<ClassWithDateFormat> requestHandler = new RequestHandler<>(ClassWithDateFormat.class);
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put("date", new String[]{"2017-03-15"});
        parameters.put("dateWithoutFormat", new String[]{"2017-03-15"});
        requestHandler.getObject(parameters);
    }    
    
    public static class ClassWithDateFormat {
        @NotNullOrEmpty
        @DateFormat(format = "yyyy-MM-dd")
        private Date date;

        private Date dateWithoutFormat;
        
        public ClassWithDateFormat() {
            
        }
        
        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }             

        public Date getDateWithoutFormat() {
            return dateWithoutFormat;
        }

        public void setDateWithoutFormat(Date dateWithoutFormat) {
            this.dateWithoutFormat = dateWithoutFormat;
        }                
    }    
}
