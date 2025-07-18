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

    private void escribirCadenaFija(RandomAccessFile raf, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor);
        while (sb.length() < longitud) sb.append(" ");
        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }

    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) sb.append(raf.readChar());
        return sb.toString().trim();
    }

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
