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
    /**
     * Crea una nueva instancia de ProductoDAOArchivosB.
     * Si el archivo existe, carga los productos desde él; de lo contrario, crea algunos productos por defecto y los guarda.
     *
     * @param rutaBase Ruta base donde se ubicará el archivo productos.dat.
     */

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
    /**
     * Crea un nuevo producto si no existe otro con el mismo código.
     *
     * @param producto Producto a añadir.
     */

    @Override
    public void crear(Producto producto) {
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            System.err.println("Producto con código duplicado: " + producto.getCodigo());
            return;
        }
        productos.add(producto);
        guardar();
    }

    /**
     * Busca un producto por su código.
     *
     * @param codigo Código del producto.
     * @return Producto encontrado o null si no existe.
     */

    @Override
    public Producto buscarPorCodigo(int codigo) {
        for (Producto producto : productos) {
            if (producto.getCodigo() == codigo) return producto;
        }
        return null;
    }
    /**
     * Busca productos cuyo nombre contenga una subcadena dada, sin distinción entre mayúsculas y minúsculas.
     *
     * @param nombre Subcadena del nombre a buscar.
     * @return Lista de productos encontrados.
     */

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
    /**
     * Actualiza un producto existente, identificándolo por su código.
     *
     * @param producto Producto actualizado.
     */

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
    /**
     * Elimina un producto según su código.
     *
     * @param codigo Código del producto a eliminar.
     */

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
    /**
     * Retorna una copia de la lista completa de productos.
     *
     * @return Lista de todos los productos.
     */

    @Override
    public List<Producto> listarTodos() {
        return new ArrayList<>(productos);
    }
    /**
     * Guarda la lista completa de productos en el archivo binario.
     *
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */

    private void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            oos.writeObject(productos);
        } catch (IOException e) {
            System.err.println("Error al guardar productos: " + e.getMessage());
        }
    }

    /**
     * Carga la lista de productos desde el archivo binario.
     *
     * @return Lista de productos cargados o vacía si ocurre un error.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si no se puede deserializar el archivo.
     */

    private List<Producto> cargar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Producto>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar productos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

}
