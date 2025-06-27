package ec.edu.ups.poo.carrito.dao;

import java.util.List;
import ec.edu.ups.poo.carrito.modelo.Carrito;

public interface CarritoDAO {

    void crear(Carrito carrito);

    Carrito buscarPorCodigo(int codigo);

    void actualizar(Carrito carrito);

    void eliminar(int codigo);

    List<Carrito> listarTodos();

}