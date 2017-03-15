package de.hepisec.validation.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hendrik Pilz
 */
public class DateConverter implements Converter<Date> {

    private String dateFormat;
    
    public DateConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * Convert the given Object to java.util.Date using the dateFormat
     * 
     * @param value
     * @return the new Date object or null if the value cannot be parsed
     */
    @Override
    public Date convert(String value) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        
        try {
            return df.parse(value);
        } catch (ParseException ex) {
            Logger.getLogger(DateConverter.class.getName()).log(Level.WARNING, null, ex);
            return null;
        }
    }
    
}
