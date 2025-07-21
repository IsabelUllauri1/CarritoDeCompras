package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.ItemCarritoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.ItemCarrito;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ItemCarritoDAOBinario implements ItemCarritoDAO {

    private final String rutaArchivo;
    private final ProductoDAO productoDAO;
    private static final int TAM_ITEM = 10 * 2 + 4 + 4; // 10 chars + int producto + int cantidad
    /**
     * Crea una nueva instancia de ItemCarritoDAOBinario y verifica la existencia del archivo binario.
     *
     * @param rutaBase Ruta base donde se guardará el archivo itemsCarrito.dat.
     * @param productoDAO DAO para recuperar información de productos.
     */
    public ItemCarritoDAOBinario(String rutaBase, ProductoDAO productoDAO) {
        this.rutaArchivo = new File(rutaBase, "itemsCarrito.dat").getAbsolutePath();
        this.productoDAO = productoDAO;
        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) archivo.createNewFile();
        } catch (IOException e) {
            System.err.println("Error al crear archivo de ítems: " + e.getMessage());
        }
    }
    /**
     * Escribe una cadena de longitud fija en el archivo, rellenando con espacios si es necesario.
     *
     * @param raf Archivo binario donde se escribe.
     * @param valor Cadena a escribir.
     * @param longitud Longitud fija (en caracteres).
     * @throws IOException Si ocurre un error de escritura.
     */

    private void escribirCadenaFija(RandomAccessFile raf, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor);
        while (sb.length() < longitud) sb.append(" ");
        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }
    /**
     * Lee una cadena de longitud fija desde el archivo binario, eliminando espacios al final.
     *
     * @param raf Archivo binario desde donde se lee.
     * @param longitud Longitud fija de la cadena a leer (en caracteres).
     * @return Cadena leída sin espacios de relleno.
     * @throws IOException Si ocurre un error de lectura.
     */

    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) sb.append(raf.readChar());
        return sb.toString().trim();
    }
    /**
     * Guarda todos los ítems de un carrito en el archivo binario.
     *
     * @param codigoCarrito Código del carrito al que pertenecen los ítems.
     * @param items Lista de ítems a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */

    @Override
    public void guardarItems(String codigoCarrito, List<ItemCarrito> items) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length()); // Ir al final del archivo
            for (ItemCarrito item : items) {
                escribirCadenaFija(raf, codigoCarrito, 10);
                raf.writeInt(item.getProducto().getCodigo());
                raf.writeInt(item.getCantidad());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar ítems: " + e.getMessage());
        }
    }
    /**
     * Recupera los ítems asociados a un carrito específico desde el archivo binario.
     *
     * @param codigoCarrito Código del carrito del cual se desean recuperar los ítems.
     * @return Lista de ítems del carrito.
     * @throws IOException Si ocurre un error al leer el archivo.
     */

    @Override
    public List<ItemCarrito> obtenerItemsPorCarrito(String codigoCarrito) {
        List<ItemCarrito> items = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long total = raf.length() / TAM_ITEM;
            for (int i = 0; i < total; i++) {
                raf.seek(i * TAM_ITEM);
                String codStr = leerCadenaFija(raf, 10);
                int codProducto = raf.readInt();
                int cantidad = raf.readInt();

                if (codStr.equals(codigoCarrito)) {
                    Producto producto = productoDAO.buscarPorCodigo(codProducto);
                    if (producto != null) {
                        items.add(new ItemCarrito(cantidad, producto));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer ítems: " + e.getMessage());
        }
        return items;
    }
}
