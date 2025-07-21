package ec.edu.ups.poo.carrito.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MensajeInternacionalizacionHandler {
    private ResourceBundle bundle;
    private Locale locale;
    /**
     * Constructor que inicializa el manejador con un idioma y país específicos.
     *
     * @param lenguaje Código del idioma (por ejemplo, "es" para español, "en" para inglés).
     * @param pais Código del país (por ejemplo, "EC" para Ecuador, "US" para Estados Unidos).
     */
    public MensajeInternacionalizacionHandler(String lenguaje,String pais) {
        this.locale = new Locale(lenguaje, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);

    }
    /**
     * Obtiene el mensaje traducido correspondiente a una clave.
     *
     * @param key Clave del mensaje a recuperar.
     * @return Texto traducido si existe, o la propia clave si no se encuentra en el bundle.
     */
    public String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }
    /**
     * Cambia el idioma y país del ResourceBundle en tiempo de ejecución.
     * Los cambios se reflejarán en los mensajes obtenidos posteriormente.
     *
     * @param language Código del nuevo idioma.
     * @param pais Código del nuevo país.
     */
    public void setLanguage(String language, String pais) {
        this.locale = new Locale(language, pais);
        this.bundle = ResourceBundle.getBundle("mensajes", locale);
    }
    /**
     * Devuelve el {@link Locale} actual configurado para la internacionalización.
     *
     * @return Objeto Locale actual.
     */
    public Locale getLocale() {
        return locale;
    }
}
