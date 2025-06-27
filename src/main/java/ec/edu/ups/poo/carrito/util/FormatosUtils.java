package ec.edu.ups.poo.carrito.util;

import javax.xml.crypto.Data;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class FormatosUtils {
    public static String formatearMoneda(double cantidad, Locale local) {
        Format formatoMoneda = NumberFormat.getCurrencyInstance(local);
        return formatoMoneda.format(cantidad);

    }
    public static String formatearFecha(Date fecha,Locale local) {
        Format formatoFecha = NumberFormat.getCurrencyInstance(local);
        return formatoFecha.format(fecha);
    }
}
