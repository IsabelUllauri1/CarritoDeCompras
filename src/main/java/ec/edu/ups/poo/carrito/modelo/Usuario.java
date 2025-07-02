package ec.edu.ups.poo.carrito.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;
    private List<PreguntaRespondida> preguntasRespondidas = new ArrayList<>();
    private String correo;
    private String nombreCompleto;
    private String telefono;
    private Date fechaNacimiento;


    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol, String correo,String nombreCompleto, String telefono, Date fechaNacimiento) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.correo = correo;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;

        this.preguntasRespondidas = new ArrayList<>();
    }

    public Usuario(String username, String contrasenia, Rol rol) {
        this.username = username;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.nombreCompleto = "";
        this.correo = "";
        this.telefono = "";
        this.fechaNacimiento = new Date();
    }

    public Usuario() {}

    public void setPreguntasRespondidas(List<PreguntaRespondida> preguntasRespondidas) {
        this.preguntasRespondidas = preguntasRespondidas;
    }
    public List<PreguntaRespondida> getPreguntasRespondidas() {
        return preguntasRespondidas;
    }


    public String getCorreo() {return correo;}

    public Date getFechaNacimiento() {return fechaNacimiento;}

    public String getNombreCompleto() {return nombreCompleto;}

    public String getTelefono() {return telefono;}


    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", preguntasRespondidas=" + preguntasRespondidas +
                ", correo='" + correo + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
