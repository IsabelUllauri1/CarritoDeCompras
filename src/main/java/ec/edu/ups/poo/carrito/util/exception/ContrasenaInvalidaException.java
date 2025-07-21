package ec.edu.ups.poo.carrito.util.exception;
/**
 * Excepción lanzada cuando una contraseña no cumple con los requisitos de seguridad definidos.
 *
 * Usada en {@link ec.edu.ups.poo.carrito.modelo.Usuario} para validar reglas como longitud mínima,
 * inclusión de mayúsculas, minúsculas y caracteres especiales (@, _, -).
 *
 * @author Brandon
 * @version 1.0
 */
public class ContrasenaInvalidaException extends RuntimeException {
    public ContrasenaInvalidaException(String message) {
        super(message);
    }
}
