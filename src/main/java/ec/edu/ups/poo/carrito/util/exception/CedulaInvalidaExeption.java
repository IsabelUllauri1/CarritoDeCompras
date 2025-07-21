package ec.edu.ups.poo.carrito.util.exception;
/**
 * Excepción lanzada cuando una cédula de identidad no cumple con el formato o la validación esperada.
 *
 * Se utiliza principalmente al asignar el username en la clase {@link ec.edu.ups.poo.carrito.modelo.Usuario}.
 *
 *
 */
public class CedulaInvalidaExeption extends RuntimeException {
    public CedulaInvalidaExeption(String message) {

        super(message);
    }
}
