package ec.edu.ups.poo.carrito.modelo;

import ec.edu.ups.poo.carrito.util.exception.CampoVacioException;

import java.io.Serializable;

public class Pregunta implements Serializable {
    private String texto;
    private int id;
    /**
     * Crea una nueva pregunta de seguridad con texto e identificador.
     *
     * @param texto Texto de la pregunta.
     * @param id Identificador numérico positivo.
     * @throws CampoVacioException si el texto es nulo o vacío.
     * @throws IllegalArgumentException si el ID es menor o igual a cero.
     */
    public Pregunta(String texto, int id) {
        setTexto(texto);
        setId(id);
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) throws CampoVacioException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new CampoVacioException("El texto de la pregunta es obligatorio.");
        }
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }
        this.id = id;
    }

    @Override
    public String toString() {
        return texto;
    }
    //las validaciones van en los setters y en el constructor se llama a los setters
    //login jfilechooser

}
