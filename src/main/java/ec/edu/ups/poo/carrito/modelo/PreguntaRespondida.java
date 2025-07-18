package ec.edu.ups.poo.carrito.modelo;

public class PreguntaRespondida {
    private Pregunta pregunta;
    private String respuesta;
    private String username;

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
