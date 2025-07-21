package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioDAOBinario implements UsuarioDAO {
    private final String rutaArchivo;
    private static final int tamanioRegistro = 10 * 2 + 20 * 2 + 15 * 2 + 50 * 2 + 50 * 2 + 15 * 2 + 8;
    // Cada carácter = 2 bytes con writeChar() / writeChars()
    // +8 = long para la fecha

    /**
     * Crea una nueva instancia del DAO binario de usuarios.
     * Si el archivo no existe, lo crea automáticamente.
     *
     * @param rutaBase Ruta base donde se almacenará el archivo usuarios.dat.
     */

    public UsuarioDAOBinario(String rutaBase) {
        this.rutaArchivo = new File(rutaBase, "usuarios.dat").getAbsolutePath();
        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creando archivo de usuarios: " + e.getMessage());
        }
    }
    /**
     * Guarda un nuevo usuario al final del archivo binario.
     *
     * @param usuario Usuario a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */

    @Override
    public void crear(Usuario usuario) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length()); // Ir al final del archivo

            escribirCadenaFija(raf, usuario.getUsername(), 10);
            escribirCadenaFija(raf, usuario.getContrasenia(), 20);
            escribirCadenaFija(raf, usuario.getTelefono(), 15);
            escribirCadenaFija(raf, usuario.getCorreo(), 50);
            escribirCadenaFija(raf, usuario.getNombreCompleto(), 50);
            escribirCadenaFija(raf, usuario.getRol().toString(), 15);
            raf.writeLong(usuario.getFechaNacimiento().getTime());

        } catch (IOException e) {
            System.err.println("Error al crear usuario RAF: " + e.getMessage());
        }
    }

    /**
     * Busca un usuario por su nombre de usuario (cédula) en el archivo binario.
     *
     * @param username Nombre de usuario a buscar.
     * @return Usuario encontrado o null si no existe.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */

    @Override
    public Usuario buscarPorUsername(String username) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long numeroRegistros = raf.length() / tamanioRegistro;

            for (int i = 0; i < numeroRegistros; i++) {
                raf.seek(i * tamanioRegistro);

                String cedula = leerCadenaFija(raf, 10);

                if (cedula.trim().equals(username)) {
                    String contrasenia = leerCadenaFija(raf, 20);
                    String telefono = leerCadenaFija(raf, 15);
                    String correo = leerCadenaFija(raf, 50);
                    String nombre = leerCadenaFija(raf, 50);
                    String rolStr = leerCadenaFija(raf, 15).trim();
                    long fecha = raf.readLong();

                    Usuario u = new Usuario();
                    u.setUsername(cedula.trim());
                    u.setContrasenia(contrasenia.trim());
                    u.setTelefono(telefono.trim());
                    u.setCorreo(correo.trim());
                    u.setNombreCompleto(nombre.trim());
                    u.setRol(ROL.valueOf(rolStr));
                    u.setFechaNacimiento(new Date(fecha));
                    return u;
                } else {
                    // Saltar el resto del registro si no coincide
                    raf.skipBytes((tamanioRegistro - 10 * 2));
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al buscar usuario: " + e.getMessage());
        }

        return null;
    }

    /**
     * Autentica un usuario por username y contraseña.
     *
     * @param username Nombre de usuario.
     * @param contrasena Contraseña a verificar.
     * @return Usuario si la autenticación es exitosa, null en caso contrario.
     */
    @Override
    public Usuario autenticar(String username, String contrasena) {
        Usuario u = buscarPorUsername(username);
        if (u != null && u.getContrasenia().equals(contrasena)) {
            return u;
        }
        return null;
    }

    /**
     * Actualiza los datos de un usuario existente sobrescribiendo su registro.
     *
     * @param usuario Usuario con los nuevos datos.
     * @throws IOException Si ocurre un error al modificar el archivo.
     */
    @Override
    public void actualizar(Usuario usuario) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long numeroRegistros = raf.length() / tamanioRegistro;

            for (int i = 0; i < numeroRegistros; i++) {
                raf.seek(i * tamanioRegistro);

                String cedulaLeida = leerCadenaFija(raf, 10).trim();

                if (cedulaLeida.equals(usuario.getUsername())) {

                    raf.seek(i * tamanioRegistro);

                    escribirCadenaFija(raf, usuario.getUsername(), 10);
                    escribirCadenaFija(raf, usuario.getContrasenia(), 20);
                    escribirCadenaFija(raf, usuario.getTelefono(), 15);
                    escribirCadenaFija(raf, usuario.getCorreo(), 50);
                    escribirCadenaFija(raf, usuario.getNombreCompleto(), 50);
                    escribirCadenaFija(raf, usuario.getRol().toString(), 15);
                    raf.writeLong(usuario.getFechaNacimiento().getTime());

                    return;
                }
            }

            System.err.println("No se encontró el usuario con cédula: " + usuario.getUsername());

        } catch (IOException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    /**
     * Elimina un usuario marcando su cédula con "0000000000" (eliminación lógica).
     *
     * @param username Usuario a eliminar.
     * @throws IOException Si ocurre un error durante el acceso al archivo.
     */
    @Override
    public void eliminar(String username) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long numeroRegistros = raf.length() / tamanioRegistro;

            for (int i = 0; i < numeroRegistros; i++) {
                raf.seek(i * tamanioRegistro);

                String cedula = leerCadenaFija(raf, 10);

                if (cedula.trim().equals(username)) {
                    raf.seek(i * tamanioRegistro);
                    escribirCadenaFija(raf, "0000000000", 10);
                    System.out.println("🗑 Usuario eliminado: " + username);
                    return;
                }
            }

            System.out.println("Usuario no encontrado para eliminar: " + username);
        } catch (IOException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
    /**
     * Lista todos los usuarios válidos (no eliminados) almacenados en el archivo.
     *
     * @return Lista completa de usuarios activos.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long numeroRegistros = raf.length() / tamanioRegistro;

            for (int i = 0; i < numeroRegistros; i++) {
                raf.seek(i * tamanioRegistro);

                String cedula = leerCadenaFija(raf, 10);

                if (!cedula.trim().equals("0000000000") && !cedula.trim().isEmpty()) {
                    String contrasenia = leerCadenaFija(raf, 20);
                    String telefono = leerCadenaFija(raf, 15);
                    String correo = leerCadenaFija(raf, 50);
                    String nombre = leerCadenaFija(raf, 50);
                    String rolStr = leerCadenaFija(raf, 15).trim();
                    long fecha = raf.readLong();

                    Usuario u = new Usuario();
                    u.setUsername(cedula.trim());
                    u.setContrasenia(contrasenia.trim());
                    u.setTelefono(telefono.trim());
                    u.setCorreo(correo.trim());
                    u.setNombreCompleto(nombre.trim());
                    u.setRol(ROL.valueOf(rolStr));
                    u.setFechaNacimiento(new Date(fecha));

                    lista.add(u);
                }
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Lista todos los usuarios con rol ADMINISTRADOR.
     *
     * @return Lista de administradores.
     */
    @Override
    public List<Usuario> listarAdministradores() {
        List<Usuario> admins = new ArrayList<>();
        for (Usuario u : listarTodos()) {
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
        for (Usuario u : listarTodos()) {
            if (u.getRol() == ROL.USUARIO) {
                normales.add(u);
            }
        }
        return normales;
    }

    /**
     * Lista todos los usuarios que tienen un rol específico.
     *
     * @param rol Rol a filtrar (ADMINISTRADOR o USUARIO).
     * @return Lista de usuarios con el rol dado.
     */
    @Override
    public List<Usuario> listarPorRol(ROL rol) {
        List<Usuario> resultado = new ArrayList<>();
        for (Usuario u : listarTodos()) {
            if (u.getRol().equals(rol)) {
                resultado.add(u);
            }
        }
        return resultado;
    }
    /**
     * Lee una cadena de longitud fija del archivo eliminando espacios sobrantes.
     *
     * @param raf Archivo binario.
     * @param longitud Número de caracteres a leer.
     * @return Cadena leída sin espacios finales.
     * @throws IOException Si ocurre un error de lectura.
     */
    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString();
    }
    /**
     * Escribe una cadena rellenada o truncada a longitud fija y la guarda en el archivo.
     *
     * @param raf Archivo binario.
     * @param valor Cadena a escribir.
     * @param longitud Longitud exacta (rellenada o recortada).
     * @throws IOException Si ocurre un error al escribir.
     */
    private void escribirCadenaFija(RandomAccessFile raf, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor);
        while (sb.length() < longitud) {
            sb.append(" ");
        }
        sb.setLength(longitud); //por si se pasa
        raf.writeChars(sb.toString());
    }


}
