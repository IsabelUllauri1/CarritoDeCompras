package ec.edu.ups.poo.carrito.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {
    private ResourceBundle bundle;
    private Locale locale;

    public MensajeInternacionalizacionHandler(String lenguaje,String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);

    }

    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;    // fall back to the literal key
        }
    }
    public void setLanguage(String language, String pais) {
        this.locale = new Locale(language, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }

    public Locale getLocale() {
        return locale;
    }
}
