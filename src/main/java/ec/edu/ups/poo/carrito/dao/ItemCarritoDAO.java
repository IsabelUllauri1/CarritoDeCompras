package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.ItemCarrito;

import java.util.List;

public interface ItemCarritoDAO {
    /**
     * Guarda una lista de ítems asociada a un carrito.
     *
     * @param codigoCarrito Código del carrito al que pertenecen los ítems.
     * @param items Lista de ítems a guardar.
     */
    void guardarItems(String codigoCarrito, List<ItemCarrito> items);
    /**
     * Obtiene los ítems asociados a un carrito específico.
     *
     * @param codigoCarrito Código del carrito del que se quieren obtener los ítems.
     * @return Lista de ítems del carrito, o una lista vacía si no hay ítems.
     */
    List<ItemCarrito> obtenerItemsPorCarrito(String codigoCarrito);
}

