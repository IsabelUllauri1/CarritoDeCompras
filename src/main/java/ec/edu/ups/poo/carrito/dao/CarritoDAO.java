package ec.edu.ups.poo.carrito.dao;

import java.util.List;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Usuario;

public interface CarritoDAO {
    /**
     * Crea un nuevo carrito y lo agrega al sistema.
     *
     * @param carrito El carrito a crear.
     */
    void crear(Carrito carrito);
    /**
     * Busca un carrito por su código único.
     *
     * @param codigo Código del carrito.
     * @return El carrito encontrado, o {@code null} si no existe.
     */
    Carrito buscarPorCodigo(int codigo);
    /**
     * Actualiza la información de un carrito existente.
     *
     * @param carrito Carrito con los datos actualizados.
     */
    void actualizar(Carrito carrito);

    /**
     * Elimina un carrito del sistema utilizando su código.
     *
     * @param codigo Código del carrito a eliminar.
     */
    void eliminar(int codigo);
    /**
     * Lista todos los carritos asociados a un usuario específico.
     *
     * @param usuario Usuario del que se quieren obtener los carritos.
     * @return Lista de carritos del usuario.
     */
    List<Carrito> listarPorUsuario(Usuario usuario);
    /**
     * Elimina un carrito utilizando un identificador en forma de cadena.
     *
     * @param codigo Código en formato {@code String}.
     */
    void eliminar(String codigo);
    /**
     * Lista todos los carritos registrados en el sistema.
     *
     * @return Lista de todos los carritos.
     */
    List<Carrito> listarTodos();

}