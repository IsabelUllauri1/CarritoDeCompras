package ec.edu.ups.poo.carrito.dao;


import ec.edu.ups.poo.carrito.modelo.Producto;

import java.util.List;

public interface ProductoDAO {
    /**
     * Crea un nuevo producto en el sistema.
     *
     * @param producto El producto que se desea agregar.
     */
    void crear(Producto producto);
    /**
     * Busca un producto por su código único.
     *
     * @param codigo El código del producto a buscar.
     * @return El producto correspondiente al código o {@code null} si no existe.
     */
    Producto buscarPorCodigo(int codigo);
    /**
     * Busca productos que contengan el nombre proporcionado (parcial o completo).
     *
     * @param nombre El nombre o parte del nombre a buscar.
     * @return Lista de productos que coinciden con el nombre dado.
     */
    List<Producto> buscarPorNombre(String nombre);
    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto El producto con la información actualizada.
     */
    void actualizar(Producto producto);
    /**
     * Elimina un producto del sistema usando su código.
     *
     * @param codigo El código del producto a eliminar.
     */
    void eliminar(int codigo);
    /**
     * Lista todos los productos almacenados en el sistema.
     *
     * @return Lista completa de productos.
     */
    List<Producto> listarTodos();

}