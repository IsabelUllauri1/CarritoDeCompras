package ec.edu.ups.poo.carrito.util;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class FormatosUtils {
    /**
     * Devuelve un texto con formato monetario según la región indicada.
     *
     * @param cantidad Monto numérico a formatear.
     * @param local Locale que define el formato regional.
     * @return Cadena con el monto formateado como moneda.
     */
    public static String formatearMoneda(double cantidad, Locale local) {
        Format formatoMoneda = NumberFormat.getCurrencyInstance(local);
        return formatoMoneda.format(cantidad);

    }
    /**
     * Devuelve una fecha en formato de texto según el locale proporcionado.
     *
     * @param fecha Objeto Date a formatear.
     * @param local Locale que define el formato de fecha (ej. es_EC, en_US).
     * @return Cadena con la fecha formateada.
     */
    public static String formatearFecha(Date fecha,Locale local) {
        Format formatoFecha = DateFormat.getDateInstance(DateFormat.MEDIUM, local);
        return formatoFecha.format(fecha);
    }
    /**
     * Convierte una cadena de texto en un objeto Date utilizando el formato regional especificado.
     *
     * @param texto Fecha en formato texto a parsear.
     * @param locale Locale que define cómo se interpreta la fecha.
     * @return Objeto Date correspondiente a la cadena de entrada.
     * @throws ParseException Si el texto no tiene el formato adecuado para la región.
     */
    public static Date parsearFecha(String texto, Locale locale) throws ParseException {
        DateFormat formato = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        return formato.parse(texto);
    }

    /**
     * Devuelve una cadena con formato numérico basado en el locale por defecto del sistema.
     *
     * @param numero Número a formatear.
     * @return Representación en texto del número con formato regional.
     */
    public static String formatearNumero(double numero) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.getDefault());
        return nf.format(numero);
    }

}
