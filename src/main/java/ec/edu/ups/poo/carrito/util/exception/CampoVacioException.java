package ec.edu.ups.poo.carrito.util.exception;
/**
 * Excepción lanzada cuando un campo obligatorio está vacío o nulo.
 *
 * Es utilizada en validaciones de clases como {@link ec.edu.ups.poo.carrito.modelo.Pregunta}
 * para asegurar que ciertos valores no se omitan en formularios o al construir objetos.
 *
 * Esta excepción permite una validación centralizada y clara de campos requeridos en el sistema.
 *
 */
public class CampoVacioException extends RuntimeException {
    public CampoVacioException(String message) {

        super(message);
    }
}
