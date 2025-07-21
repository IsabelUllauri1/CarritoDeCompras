package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class UsuarioDAOMemoria implements UsuarioDAO {

    private List<Usuario> usuarios = new ArrayList<>();
    /**
     * Constructor que inicializa la lista de usuarios en memoria con dos usuarios de prueba.
     * Se utiliza una fecha fija de nacimiento para ambos.
     */
    public UsuarioDAOMemoria() {
        usuarios = new ArrayList<Usuario>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fecha = sdf.parse("17/07/2000");
            crear(new Usuario("0106745508", "Gini2121_", ROL.ADMINISTRADOR, "isa@gmail.com", "isabel u", "0992849214", fecha));
            crear(new Usuario("0102327558", "Gini2121_", ROL.USUARIO, "gina@gamil.com", "Gina B","0994492239", fecha));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Verifica si existe un usuario que coincida con el username y contraseña proporcionados.
     *
     * @param username Cédula del usuario.
     * @param contrasenia Contraseña del usuario.
     * @return Usuario autenticado o {@code null} si no coincide.
     */
    @Override
    public Usuario autenticar(String username, String contrasenia) {
        for (Usuario u : usuarios) {
            if (u.getUsername() != null && u.getContrasenia() != null &&
                    u.getUsername().equals(username) && u.getContrasenia().equals(contrasenia)) {
                return u;
            }
        }
        return null;
    }


    /**
     * Agrega un nuevo usuario a la lista en memoria.
     *
     * @param usuario Usuario a añadir.
     */
    @Override
    public void crear(Usuario usuario) {
        usuarios.add(usuario);
    }
    /**
     * Busca un usuario en memoria por su nombre de usuario (cédula).
     *
     * @param username Cédula del usuario.
     * @return Usuario encontrado o {@code null} si no existe.
     */
    @Override
    public Usuario buscarPorUsername(String username) {
        for (Usuario usuario : usuarios) {
            if (usuario.getUsername().equals(username)) {
                return usuario;
            }
        }
        return null;
    }
    /**
     * Elimina un usuario de la lista en memoria usando su username.
     *
     * @param username Cédula del usuario a eliminar.
     */
    @Override
    public void eliminar(String username) {
        Iterator<Usuario> iterator = usuarios.iterator();
        while (iterator.hasNext()) {//true o false si es que existen elementos en el iterator
            Usuario usuario = iterator.next();
            if (usuario.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }

    }
    /**
     * Actualiza los datos de un usuario ya existente.
     *
     * @param usuarioActualizado Usuario con la información modificada.
     */
    @Override
    public void actualizar(Usuario usuarioActualizado) {
        for (int i = 0; i < usuarios.size(); i++) {//
            Usuario usuarioAux = usuarios.get(i);
            if(usuarioAux.getUsername().equals(usuarioActualizado.getUsername())) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }

    }
    /**
     * Lista todos los usuarios registrados en memoria.
     *
     * @return Lista completa de usuarios.
     */
    @Override
    public List<Usuario> listarTodos() {
        return usuarios;
    }
    /**
     * Retorna una lista vacía (no implementado).
     *
     * @return Lista vacía.
     */
    @Override
    public List<Usuario> listarAdministradores() {

        return List.of();
    }
    /**
     * Retorna una lista vacía (no implementado).
     *
     * @return Lista vacía.
     */
    @Override
    public List<Usuario> listarUsuarios() {

        return List.of();
    }
    /**
     * Lista todos los usuarios cuyo rol coincida con el proporcionado.
     *
     * @param rol Rol a filtrar.
     * @return Lista de usuarios con el rol especificado.
     */
    @Override
    public List<Usuario> listarPorRol(ROL rol) {
        List<Usuario> usuariosEncontrados = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            if (usuario.getRol().equals(rol)) {
                usuariosEncontrados.add(usuario);


            }
        }
        return usuariosEncontrados;
    }

}
