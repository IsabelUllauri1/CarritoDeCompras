package ec.edu.ups.poo.carrito.util.exception;
/**
 * Excepción lanzada cuando el formato del correo electrónico es inválido.
 *
 * Aplicada en la clase {@link ec.edu.ups.poo.carrito.modelo.Usuario} durante la validación del campo correo.
 *
 */
public class CorreoInvalidoException extends RuntimeException {
    public CorreoInvalidoException(String message) {

        super(message);
    }
}
