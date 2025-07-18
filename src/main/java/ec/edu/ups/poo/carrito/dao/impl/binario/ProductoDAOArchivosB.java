package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductoDAOArchivosB implements ProductoDAO {

    private final String rutaArchivo;
    private List<Producto> productos;

    public ProductoDAOArchivosB(String rutaBase) {
        this.rutaArchivo = new File(rutaBase, "productos.dat").getAbsolutePath();
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            productos = cargar();
        } else {
            productos = new ArrayList<>();
            productos.add(new Producto("Manzanas", 1, 0.5));
            productos.add(new Producto("Pan", 2, 0.5));
            productos.add(new Producto("Carne", 3, 0.5));
            productos.add(new Producto("Galletas", 4, 0.6));
            guardar();
        }
    }

    @Override
    public void crear(Producto producto) {
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            System.err.println("Producto con código duplicado: " + producto.getCodigo());
            return;
        }
        productos.add(producto);
        guardar();
    }


    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) return producto;
        }
        return null;
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        List<Producto> encontrados = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                encontrados.add(producto);
            }
        }
        return encontrados;
    }

    @Override
    public void actualizar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getCodigo() == producto.getCodigo()) {
                productos.set(i, producto);
                guardar();
                return;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Producto> it = productos.iterator();
        while (it.hasNext()) {
            if (it.next().getCodigo() == codigo) {
                it.remove();
                guardar();
                return;
            }
        }
    }

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }


    // Guardar lista completa
    private void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }


    // Cargar lista completa
    private List<Producto> cargar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Producto>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
