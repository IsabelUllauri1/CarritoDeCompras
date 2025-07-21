package ec.edu.ups.poo.carrito.dao.impl;



import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOMemoria implements ProductoDAO {

    private List<Producto> productos;
    /**
     * Constructor que inicializa la lista de productos en memoria con algunos productos predeterminados.
     */
    public ProductoDAOMemoria() {
        productos = new ArrayList<Producto>();
        crear(new Producto("Manzanas", 1, 0.5));
        crear(new Producto("Pan", 2, 0.5));
        crear(new Producto("Carne", 3, 0.5));
        crear(new Producto("Galletas", 4, 0.6));

    }
    /**
     * Añade un nuevo producto a la lista.
     *
     * @param producto El producto a añadir.
     */
    @Override
    public void crear(Producto producto) {
        productos.add(producto);
    }
    /**
     * Busca un producto por su código.
     *
     * @param codigo Código único del producto.
     * @return El producto correspondiente o {@code null} si no se encuentra.
     */
    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) {
                return producto;
            }
        }
        return null;
    }
    /**
     * Busca productos cuyo nombre contenga la cadena dada (ignorando mayúsculas/minúsculas).
     *
     * @param nombre Parte del nombre del producto a buscar.
     * @return Lista de productos que coinciden parcial o totalmente con el nombre.
     */
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> productosEncontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                productosEncontrados.add(producto);
            }
        }
        return productosEncontrados;
    }
    /**
     * Actualiza un producto existente en la lista, según su código.
     *
     * @param producto Producto con los nuevos datos a actualizar.
     */
    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
            }
        }
    }
    /**
     * Elimina un producto de la lista basado en su código.
     *
     * @param codigo Código del producto a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> iterator = productos.iterator();
        while (iterator.hasNext()) {
            Producto producto = iterator.next();
            if (producto.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }
    /**
     * Lista todos los productos almacenados en memoria.
     *
     * @return Lista completa de productos.
     */
    @Override
    public List<Producto> listarTodos() {

        return productos;
    }
}
