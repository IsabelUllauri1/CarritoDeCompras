package ec.edu.ups.poo.carrito.modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private List<PreguntaRespondida> preguntasRespondidas = new ArrayList<>();
    private String correo;


    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.preguntasRespondidas = new ArrayList<>();
    }
    public Usuario() {}

    public void setPreguntasRespondidas(List<PreguntaRespondida> preguntasRespondidas) {
        this.preguntasRespondidas = preguntasRespondidas;
    }
    public List<PreguntaRespondida> getPreguntasRespondidas() {
        return preguntasRespondidas;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String
    toString() {
        return "Usuario: " +
                "Nombre: " + username + '\'' +
                ", contrasenia: '" + contrasenia + '\'' +
                ", rol: " + rol ;
    }
}
