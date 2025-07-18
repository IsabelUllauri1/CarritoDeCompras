package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.ItemCarrito;

import java.util.List;

public interface ItemCarritoDAO {
    void guardarItems(String codigoCarrito, List<ItemCarrito> items);
    List<ItemCarrito> obtenerItemsPorCarrito(String codigoCarrito);
}

