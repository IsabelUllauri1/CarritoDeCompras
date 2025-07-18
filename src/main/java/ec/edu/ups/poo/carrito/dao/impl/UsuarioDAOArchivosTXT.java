package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.exception.ValidacionException;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsuarioDAOArchivosTXT implements UsuarioDAO {

    private final String rutaArchivo;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

    public UsuarioDAOArchivosTXT(String rutaArchivo, MensajeInternacionalizacionHandler handler) {
        this.rutaArchivo = new File(rutaArchivo, "usuarios.txt").getAbsolutePath();
        this.mensajeInternacionalizacionHandler = handler;

        File archivo = new File(this.rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el archivo de usuarios", e);
        }


    }


    public List<Usuario> cargarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                try {
                    String[] partes = linea.split(",");

                    String username = partes[0];
                    String contrasena = partes[1];
                    ROL rol = ROL.valueOf(partes[2]);
                    if (contrasena == null || contrasena.isBlank()) {
                        System.err.println("Saltando usuario con contraseña inválida: " + username);
                        continue;
                    }

                    if (partes.length == 3) {
                        // Usuario creado por ADMIN (sin más datos)
                        usuarios.add(new Usuario(username, contrasena, rol));
                    } else if (partes.length >= 7) {
                        // Usuario completo
                        String correo = partes[3];
                        String nombre = partes[4];
                        String telefono = partes[5];
                        Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(partes[6]);
                        usuarios.add(new Usuario(username, contrasena, rol, correo, nombre, telefono, fechaNacimiento));
                    } else {
                        System.out.println("Línea inválida en archivo usuarios: " + linea);
                    }

                } catch (Exception ex) {
                    System.out.println("Error al cargar usuario: " + linea);
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private void guardarUsuarios(List<Usuario> usuarios) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            for (Usuario u : usuarios) {
                if (u.getContrasenia() == null) {
                    System.err.println("ERROR: Usuario con contraseña null no se guarda: " + u.getUsername());
                    continue;
                }
                writer.println(String.join(",",
                        u.getUsername().replace("#", ""),
                        u.getContrasenia(),
                        u.getRol().name(),
                        u.getCorreo(),
                        u.getNombreCompleto(),
                        u.getTelefono(),
                        formatoFecha.format(u.getFechaNacimiento())
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar usuarios", e);
        }
    }

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return cargarUsuarios().stream()
                .filter(u -> u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst().orElse(null);
    }

    @Override
    public void crear(Usuario usuario) {
        if (usuario.getContrasenia() == null) {
            System.err.println("ERROR: Intento de guardar un usuario con contraseña null: " + usuario.getUsername());
            return;
        }

        List<Usuario> usuarios = cargarUsuarios();
        usuarios.add(usuario);
        guardarUsuarios(usuarios);
        System.out.println("Guardando usuario: " + usuario.getContrasenia());
    }






    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : cargarUsuarios()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = cargarUsuarios();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarUsuarios(usuarios);
    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {
        List<Usuario> usuarios = cargarUsuarios();
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
        guardarUsuarios(usuarios);
    }

    @Override
    public List<Usuario> listarTodos() {
        return cargarUsuarios();
    }

    @Override
    public List<Usuario> listarAdministradores() {
        List<Usuario> admins = new ArrayList<>();
        for (Usuario u : cargarUsuarios()) {
            if (u.getRol() == ROL.ADMINISTRADOR) {
                admins.add(u);
            }
        }
        return admins;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        List<Usuario> normales = new ArrayList<>();
        for (Usuario u : cargarUsuarios()) {
            if (u.getRol() == ROL.USUARIO) {
                normales.add(u);
            }
        }
        return normales;
    }

    @Override
    public List<Usuario> listarPorRol(ROL rol) {
        List<Usuario> filtrados = new ArrayList<>();
        for (Usuario u : cargarUsuarios()) {
            if (u.getRol().equals(rol)) {
                filtrados.add(u);
            }
        }
        return filtrados;
    }
}
