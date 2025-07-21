package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {
    /**
     * Autentica un usuario comparando su username y contraseña.
     *
     * @param username    El nombre de usuario (cédula).
     * @param contrasenia La contraseña del usuario.
     * @return El objeto {@link Usuario} si las credenciales son válidas, o {@code null} si no lo son.
     */
    Usuario autenticar(String username, String contrasenia);
    /**
     * Crea un nuevo usuario en el sistema.
     *
     * @param usuario El usuario que se desea agregar.
     */
    void crear(Usuario usuario);
    /**
     * Busca un usuario por su nombre de usuario (cédula).
     *
     * @param username El nombre de usuario a buscar.
     * @return El usuario correspondiente o {@code null} si no existe.
     */
    Usuario buscarPorUsername(String username);
    /**
     * Elimina un usuario del sistema por su nombre de usuario.
     *
     * @param username El nombre de usuario del usuario a eliminar.
     */
    void eliminar(String username);
    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuarioActualizado El objeto usuario con los nuevos datos.
     */
    void actualizar(Usuario usuarioActualizado);
    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return Una lista completa de usuarios.
     */
    List<Usuario> listarTodos();
    /**
     * Lista únicamente los usuarios con rol ADMINISTRADOR.
     *
     * @return Lista de usuarios con rol ADMINISTRADOR.
     */
    List<Usuario> listarAdministradores();
    /**
     * Lista únicamente los usuarios con rol USUARIO.
     *
     * @return Lista de usuarios con rol USUARIO.
     */
    List<Usuario> listarUsuarios();
    /**
     * Lista los usuarios filtrados por un rol específico.
     *
     * @param rol El rol por el cual se desea filtrar (ADMINISTRADOR o USUARIO).
     * @return Lista de usuarios que coinciden con el rol indicado.
     */
    List<Usuario> listarPorRol(ROL rol);


}