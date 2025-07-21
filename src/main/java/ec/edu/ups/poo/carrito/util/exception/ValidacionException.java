package ec.edu.ups.poo.carrito.util.exception;
/**
 * Excepción general utilizada para representar errores de validación personalizados.
 *
 * Es comúnmente usada en clases como {@link ec.edu.ups.poo.carrito.modelo.Producto}
 * o en otros contextos donde se desea indicar que un dato no cumple los requisitos esperados.
 *
 */
public class ValidacionException extends RuntimeException {
    public ValidacionException(String message) {

        super(message);
    }
}
