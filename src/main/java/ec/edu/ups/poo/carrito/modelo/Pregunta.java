package ec.edu.ups.poo.carrito.modelo;

public class Pregunta {
    private String texto;
    private int id;

    public Pregunta(String texto, int id) {
        this.texto = texto;
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
