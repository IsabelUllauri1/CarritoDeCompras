package ec.edu.ups.poo.carrito.modelo;

public class Usuario {
    private String username;
    private String contrasenia;
    private Rol rol;


    public Usuario(String nombreDeUsuario, String contrasenia, Rol rol) {
        this.username = nombreDeUsuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    public Usuario() {}

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
