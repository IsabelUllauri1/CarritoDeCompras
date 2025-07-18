package ec.edu.ups.poo.carrito.modelo;

import ec.edu.ups.poo.carrito.util.exception.CedulaInvalidaExeption;
import ec.edu.ups.poo.carrito.util.exception.ContrasenaInvalidaException;
import ec.edu.ups.poo.carrito.util.exception.CorreoInvalidoException;
import ec.edu.ups.poo.carrito.util.exception.ValidacionException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario {
    private String username;
    private String contrasenia;
    private ROL rol;
    private List<PreguntaRespondida> preguntasRespondidas;
    private String correo;
    private String nombreCompleto;
    private String telefono;
    private Date fechaNacimiento;


    public Usuario(String cedula, String contrasenia, ROL rol, String correo, String nombreCompleto, String telefono, Date fechaNacimiento)
            throws CedulaInvalidaExeption, ContrasenaInvalidaException, CorreoInvalidoException, ValidacionException {

        setUsername(cedula);
        setContrasenia(contrasenia);
        setCorreo(correo);
        setTelefono(telefono);

        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.preguntasRespondidas = new ArrayList<>();
    }


    // Si necesitas este constructor simplificado, quita las líneas incorrectas:
    public Usuario(String cedula, String contrasenia, ROL rol) {
        setUsername(cedula);
        setContrasenia(contrasenia); // <--- esta línea debe estar sí o sí
        this.rol = rol;
        this.nombreCompleto = "";
        this.correo = "";
        this.telefono = "";
        this.fechaNacimiento = new Date(); // por defecto
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

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getUsername() {
        return username;
    }

    public ROL getRol() {
        return rol;
    }

    public void setRol(ROL rol) {
        this.rol = rol;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setCorreo(String correo) throws CorreoInvalidoException {
        if (correo == null || !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new CorreoInvalidoException("Correo electrónico inválido.");
        }
        this.correo = correo;
    }


    public void setTelefono(String telefono)  {
        if (!telefono.matches("\\d{7,10}")) { //7 a 10 digitos
            throw new IllegalArgumentException("El teléfono debe contener solo números.");
        }
        this.telefono = telefono;
    }

    public void setUsername(String cedula) throws CedulaInvalidaExeption {
        if (!esCedulaValida(cedula)) {
            throw new CedulaInvalidaExeption("Cédula inválida: " + cedula);
        }
        //rellenamos con #
        this.username = String.format("%-10s", cedula).replace(' ', '#');
    }

    public void setContrasenia(String contrasena) throws ContrasenaInvalidaException {
        if (contrasena == null || contrasena.length() < 6) {
            throw new ContrasenaInvalidaException("La contraseña debe tener al menos 6 caracteres.");
        }
        boolean mayus = false, minus = false, especial = false;
        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) mayus = true;
            else if (Character.isLowerCase(c)) minus = true;
            else if (c == '@' || c == '_' || c == '-') especial = true;
        }
        if (!mayus || !minus || !especial) {
            throw new ContrasenaInvalidaException("La contraseña debe tener mayúscula, minúscula y carácter especial (@, _, -).");
        }
        this.contrasenia = contrasena;
    }



    public static boolean esCedulaValida(String cedula) {
        if (cedula == null || !cedula.matches("\\d{10}")) return false;

        int provincia = Integer.parseInt(cedula.substring(0,2));
        if (provincia < 1 || provincia > 24) return false;

        int[] coef = {2,1,2,1,2,1,2,1,2};
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int dígito = Character.digit(cedula.charAt(i),10);
            int prod = dígito * coef[i];
            suma += (prod > 9) ? prod - 9 : prod;
        }

        int próximoDecena = ((suma + 9) / 10) * 10;
        int dígitoValidador = (próximoDecena - suma) % 10;

        return dígitoValidador == Character.digit(cedula.charAt(9),10);
    }



    @Override
    public String toString() {
        return username + "," +
                contrasenia + "," +
                rol + "," +
                correo + "," +
                nombreCompleto + "," +
                telefono + "," +
                new SimpleDateFormat("dd/MM/yyyy").format(fechaNacimiento);
    }


}
