package de.hepisec.validation.converter;

/**
 *
 * @author Hendrik Pilz
 */
public interface Converter<T> {
    public T convert(String value);
}
