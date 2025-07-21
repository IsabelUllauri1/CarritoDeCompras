package ec.edu.ups.poo.carrito.modelo;

import java.io.Serial;
import java.io.Serializable;

public class PreguntaRespondida implements Serializable {
    private static final long serialVersionUID = 1L;
    private Pregunta pregunta;
    private String respuesta;
    private String username;
    /**
     * Crea una nueva instancia de PreguntaRespondida con la pregunta, respuesta y nombre de usuario.
     *
     * @param pregunta La pregunta de seguridad.
     * @param respuesta La respuesta proporcionada por el usuario.
     * @param username El nombre de usuario asociado a la respuesta.
     * @throws IllegalArgumentException si la respuesta o el nombre de usuario son nulos o vacíos.
     */
    public PreguntaRespondida(Pregunta pregunta, String respuesta, String username) {
        this.pregunta = pregunta;
        this.username = username;
        this.respuesta = respuesta;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public void setPregunta(Pregunta pregunta) {

        this.pregunta = pregunta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario no puede estar vacío.");
        }
        this.username = username;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw new IllegalArgumentException("La respuesta no puede estar vacía.");
        }
        this.respuesta = respuesta;
    }
}
