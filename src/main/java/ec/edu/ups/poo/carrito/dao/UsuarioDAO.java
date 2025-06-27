package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.util.List;

public interface UsuarioDAO {

    Usuario autenticar(String username, String contrasenia);

    void crear(Usuario usuario);

    Usuario buscarPorUsername(String username);

    void eliminar(String username);

    void actualizar(Usuario usuarioActualizado);

    List<Usuario> listarTodos();

    List<Usuario> listarAdministradores();

    List<Usuario> listarUsuarios();
    List<Usuario> listarPorRol(Rol rol);

}