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
    /**
     * Constructor que inicializa el archivo de almacenamiento y el manejador de internacionalización.
     *
     * @param rutaArchivo Directorio base donde se almacenará el archivo `usuarios.txt`.
     * @param handler Manejador de mensajes internacionalizados.
     */
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

    /**
     * Carga todos los usuarios desde el archivo de texto plano.
     * Soporta líneas con estructura mínima (3 campos) o completa (7 campos).
     *
     * @return Lista de usuarios válidos cargados desde el archivo.
     */
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
                    String correo = partes[3];
                    String nombre = partes[4];
                    String telefono = partes[5];
                    String fechaStr = partes[6];

                    if (correo == null || correo.isBlank() ||
                            nombre == null || nombre.isBlank() ||
                            telefono == null || telefono.isBlank() ||
                            fechaStr == null || fechaStr.isBlank()) {

                        System.err.println("Campos obligatorios vacíos para usuario: " + username);
                        continue;
                    }

                    Date fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechaStr);
                    usuarios.add(new Usuario(username, contrasena, rol, correo, nombre, telefono, fechaNacimiento));
                }
                else {
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
    /**
     * Guarda todos los usuarios en el archivo `usuarios.txt`, sobrescribiendo su contenido.
     *
     * @param usuarios Lista de usuarios a guardar.
     * @throws RuntimeException Si ocurre un error al escribir el archivo.
     */
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
    /**
     * Busca un usuario que coincida con el username y contraseña especificados.
     *
     * @param username Cédula del usuario.
     * @param contrasenia Contraseña del usuario.
     * @return El usuario autenticado o {@code null} si no coincide.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return cargarUsuarios().stream()
                .filter(u -> u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia))
                .findFirst().orElse(null);
    }
    /**
     * Crea un nuevo usuario y lo agrega al archivo.
     *
     * @param usuario Usuario a guardar.
     */
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

    /**
     * Busca un usuario por su nombre de usuario (cédula).
     *
     * @param username Cédula del usuario.
     * @return El usuario encontrado o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario u : cargarUsuarios()) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }
    /**
     * Elimina un usuario del archivo según su username.
     *
     * @param username Cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        List<Usuario> usuarios = cargarUsuarios();
        usuarios.removeIf(u -> u.getUsername().equals(username));
        guardarUsuarios(usuarios);
    }
    /**
     * Actualiza los datos de un usuario en el archivo.
     *
     * @param usuarioActualizado Usuario con la información modificada.
     */
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
    /**
     * Retorna la lista completa de usuarios cargados desde el archivo.
     *
     * @return Lista de todos los usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return cargarUsuarios();
    }
    /**
     * Lista todos los usuarios con rol ADMINISTRADOR.
     *
     * @return Lista de administradores.
     */
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
    /**
     * Lista todos los usuarios con rol USUARIO.
     *
     * @return Lista de usuarios normales.
     */
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
    /**
     * Lista todos los usuarios cuyo rol coincida con el especificado.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios que tienen ese rol.
     */
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
