package ec.edu.ups.poo.carrito.modelo;

public class PreguntaRespondida {
    private String pregunta;
    private String respuesta;
    private String username;

    public PreguntaRespondida(String pregunta, String username, String respuesta) {
        this.pregunta = pregunta;
        this.username = username;
        this.respuesta = respuesta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}
