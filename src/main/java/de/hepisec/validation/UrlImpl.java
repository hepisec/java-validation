package de.hepisec.validation;

import de.hepisec.validation.annotations.Url;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The value must be a valid URL 
 * 
 * @author Hendrik Pilz
 */
public class UrlImpl implements Validator {
    public UrlImpl() {        
    }
    
    @Override
    public void validate(Field field, Object value) throws ValidationException {
        Url aUrl = field.getAnnotation(Url.class);

        if (aUrl == null || value == null) {
            return;
        }

        Logger.getLogger(Validation.class.getName()).log(Level.FINE, "Validating Url on {0}", field.getName());

        try {
            URL url = new URL(value.toString());
            String[] allowedProtocols = aUrl.allowedProtocols();
            boolean protocolAllowed = false;

            for (String allowedProtocol : allowedProtocols) {
                if (url.getProtocol().toLowerCase().equals(allowedProtocol.toLowerCase())) {
                    protocolAllowed = true;
                    break;
                }
            }

            if (allowedProtocols.length > 0 && !protocolAllowed) {
                throw new ValidationException(url.getProtocol() + " URLs are not supported");
            }
            
            if (aUrl.checkHttpStatus().length > 0 && (url.getProtocol().equalsIgnoreCase("http") || url.getProtocol().equalsIgnoreCase("https"))) {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                boolean correctStatusCode = false;
                
                for (int allowedStatusCode : aUrl.checkHttpStatus()) {
                    if (conn.getResponseCode() == allowedStatusCode) {
                        correctStatusCode = true;
                        break;
                    }
                }
                
                if (!correctStatusCode) {
                    throw new ValidationException(value.toString() + " returned status code is not allowed: " + conn.getResponseCode());
                }
            }
        } catch (MalformedURLException ex) {
            throw new ValidationException(value.toString() + " is not a valid URL");
        } catch (IOException ex) {
            throw new ValidationException(value.toString() + " is not available");
        }
    }    
}
