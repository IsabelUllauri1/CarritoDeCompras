package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ItemCarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.function.Function;

public class CarritoDAOBinario implements CarritoDAO {

    private final String rutaArchivo;
    private static final int TAM_REGISTRO = 48; // codigo (10) + cedulaUsuario (10) + fecha (8)
    private final ItemCarritoDAO itemDAO;
    private final Function<String, Usuario> buscadorUsuario;
    private final Function<Integer, Producto> buscadorProducto;
    private ProductoDAO productoDAO;

    /**
     * Crea una instancia de CarritoDAOBinario.
     *
     * @param rutaBase Ruta base donde se guardará el archivo binario.
     * @param buscadorUsuario Función para recuperar un Usuario a partir de su cédula.
     * @param buscadorProducto Función para recuperar un Producto a partir de su código.
     */

    public CarritoDAOBinario(String rutaBase, Function<String, Usuario> buscadorUsuario, Function<Integer, Producto> buscadorProducto, ItemCarritoDAO itemDAO) {
        this.rutaArchivo = new File(rutaBase, "carritos.dat").getAbsolutePath();
        this.buscadorUsuario = buscadorUsuario;
        this.buscadorProducto = buscadorProducto;

        this.itemDAO = itemDAO;
        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                archivo.createNewFile();
            }
        } catch (IOException e) {
            System.err.println("Error creando archivo de carritos: " + e.getMessage());
        }
    }




    /**
     * Escribe una cadena con longitud fija rellenando con espacios si es necesario.
     *
     * @param raf Archivo binario.
     * @param valor Cadena a escribir.
     * @param longitud Longitud fija de caracteres (no bytes).
     * @throws IOException Si ocurre un error de escritura.
     */

    private void escribirCadenaFija(RandomAccessFile raf, String valor, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder(valor);
        while (sb.length() < longitud) sb.append(" ");
        sb.setLength(longitud);
        raf.writeChars(sb.toString());
    }
    /**
     * Lee una cadena de longitud fija desde el archivo binario.
     *
     * @param raf Archivo binario.
     * @param longitud Cantidad de caracteres a leer.
     * @return Cadena leída sin espacios extra.
     * @throws IOException Si ocurre un error de lectura.
     */

    private String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) sb.append(raf.readChar());
        return sb.toString().trim();
    }
    /**
     * Guarda un nuevo carrito al final del archivo binario.
     *
     * @param carrito Carrito a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */

    @Override
    public void crear(Carrito carrito) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            raf.seek(raf.length());
            escribirCadenaFija(raf, String.valueOf(carrito.getCodigo()), 10);
            escribirCadenaFija(raf, carrito.getUsuario().getUsername(), 10);
            raf.writeLong(carrito.getFechaCreacion().getTimeInMillis()); //
            itemDAO.guardarItems(String.valueOf(carrito.getCodigo()), carrito.getItems());

        } catch (IOException e) {
            System.err.println("Error al guardar carrito: " + e.getMessage());
        }
    }
    /**
     * Busca un carrito por su código en el archivo binario.
     *
     * @param codigo Código del carrito.
     * @return Carrito encontrado o null si no existe o está eliminado.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long registros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);

                String codStr = leerCadenaFija(raf, 10);
                String cedula = leerCadenaFija(raf, 10);
                long fechaLong = raf.readLong();

                if (!codStr.trim().equals("##########") && Integer.parseInt(codStr.trim()) == codigo) {
                    Carrito c = new Carrito();
                    c.setCodigo(codigo);

                    Usuario usuario = buscadorUsuario.apply(cedula); // busca el usuario real
                    if (usuario == null) usuario = new Usuario();
                    usuario.setUsername(cedula);
                    c.setUsuario(usuario);

                    GregorianCalendar fecha = new GregorianCalendar();
                    fecha.setTimeInMillis(fechaLong);
                    c.setFechaCreacion(fecha);


                    c.setItems(itemDAO.obtenerItemsPorCarrito(String.valueOf(codigo)));

                    return c;
                }


            }
        } catch (IOException e) {
            System.err.println("Error al buscar carrito por código: " + e.getMessage());
        }

        return null;
    }

    /**
     * Actualiza la información de un carrito existente (sobrescribe el registro).
     *
     * @param carrito Carrito con los datos actualizados.
     * @throws IOException Si ocurre un error al acceder al archivo.
     */

    @Override
    public void actualizar(Carrito carrito) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long registros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);
                String codStr = leerCadenaFija(raf, 10);

                if (!codStr.trim().equals("##########") && Integer.parseInt(codStr.trim()) == carrito.getCodigo()) {
                    raf.seek(i * TAM_REGISTRO);
                    escribirCadenaFija(raf, String.valueOf(carrito.getCodigo()), 10);
                    escribirCadenaFija(raf, carrito.getUsuario().getUsername(), 10);
                    raf.writeLong(carrito.getFechaCreacion().getTimeInMillis());
                    System.out.println("Carrito actualizado: " + carrito.getCodigo());
                    return;
                }
            }

            System.out.println("⚠ No se encontró carrito con código: " + carrito.getCodigo());
        } catch (IOException e) {
            System.err.println("Error al actualizar carrito: " + e.getMessage());
        }
    }

    /**
     * Elimina un carrito marcándolo como "##########" en el archivo (eliminación lógica).
     *
     * @param codigo Código del carrito a eliminar.
     * @throws IOException Si ocurre un error de escritura.
     */

    @Override
    public void eliminar(int codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long registros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);
                String codStr = leerCadenaFija(raf, 10);

                if (!codStr.trim().equals("##########") && Integer.parseInt(codStr.trim()) == codigo) {
                    raf.seek(i * TAM_REGISTRO);
                    escribirCadenaFija(raf, "##########", 10); // Marcamos como eliminado
                    System.out.println("🗑 Carrito eliminado: " + codigo);
                    return;
                }
            }

            System.out.println("Carrito no encontrado para eliminar: " + codigo);
        } catch (IOException e) {
            System.err.println("Error al eliminar carrito: " + e.getMessage());
        }
    }

    /**
     * Lista todos los carritos asociados a un usuario específico.
     *
     * @param usuario Usuario del cual se listan los carritos.
     * @return Lista de carritos pertenecientes al usuario.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */

    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        List<Carrito> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long registros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);
                String codigoStr = leerCadenaFija(raf, 10);
                String cedula = leerCadenaFija(raf, 10);
                long fechaMillis = raf.readLong();

                if (cedula.trim().equals(usuario.getUsername())) {
                    Carrito c = new Carrito();
                    c.setCodigo(Integer.parseInt(codigoStr.trim()));
                    c.setUsuario(usuario);

                    GregorianCalendar fecha = new GregorianCalendar();
                    fecha.setTimeInMillis(fechaMillis);
                    c.setFechaCreacion(fecha);

                    lista.add(c);
                    String codigoStrin = String.valueOf(c.getCodigo());
                    c.setItems(itemDAO.obtenerItemsPorCarrito(codigoStrin));

                }
            }
        } catch (IOException e) {
            System.err.println("Error al listar carritos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina un carrito usando su código como String, marcándolo como eliminado.
     *
     * @param codigo Código del carrito en formato String.
     * @throws IOException Si ocurre un error de acceso al archivo.
     */

    @Override
    public void eliminar(String codigo) {
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "rw")) {
            long registros = raf.length() / TAM_REGISTRO;
            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);
                String cod = leerCadenaFija(raf, 10);
                if (cod.equals(codigo)) {
                    raf.seek(i * TAM_REGISTRO);
                    escribirCadenaFija(raf, "##########", 10); // marcar como eliminado
                    return;
                } else {
                    raf.skipBytes(TAM_REGISTRO - 20); // saltar resto
                }
            }
        } catch (IOException e) {
            System.err.println("Error al eliminar carrito: " + e.getMessage());
        }
    }


    /**
     * Lista todos los carritos disponibles en el archivo (excepto los eliminados).
     *
     * @return Lista de todos los carritos válidos.
     * @throws IOException Si ocurre un error de lectura.
     */

    @Override
    public List<Carrito> listarTodos() {
        List<Carrito> lista = new ArrayList<>();
        try (RandomAccessFile raf = new RandomAccessFile(rutaArchivo, "r")) {
            long registros = raf.length() / TAM_REGISTRO;

            for (int i = 0; i < registros; i++) {
                raf.seek(i * TAM_REGISTRO);
                String codStr = leerCadenaFija(raf, 10);
                String cedula = leerCadenaFija(raf, 10);
                long fechaMillis = raf.readLong();

                if (!codStr.trim().equals("##########") && !codStr.trim().isEmpty()) {
                    Carrito c = new Carrito();
                    c.setCodigo(Integer.parseInt(codStr.trim()));
                    Usuario u = new Usuario();
                    u.setUsername(cedula.trim());
                    c.setUsuario(u);
                    c.setFechaCreacion(new java.util.GregorianCalendar());
                    c.getFechaCreacion().setTimeInMillis(fechaMillis);


                    if (itemDAO != null) {
                        c.setItems(itemDAO.obtenerItemsPorCarrito(codStr.trim()));
                    }

                    lista.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println(" Error al listar carritos: " + e.getMessage());
        }
        return lista;
    }

}
