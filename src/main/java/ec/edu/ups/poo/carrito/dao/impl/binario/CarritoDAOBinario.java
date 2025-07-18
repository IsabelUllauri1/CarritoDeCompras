package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
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
    private final ItemCarritoDAOBinario itemDAO;
    private final Function<String, Usuario> buscadorUsuario;
    private final Function<Integer, Producto> buscadorProducto;


    public CarritoDAOBinario(String rutaBase, Function<String, Usuario> buscadorUsuario, Function<Integer, Producto> buscadorProducto) {
        this.rutaArchivo = new File(rutaBase, "carritos.dat").getAbsolutePath();
        this.buscadorUsuario = buscadorUsuario;
        this.buscadorProducto = buscadorProducto;

        // DAO de ítems con ruta y productoDAO
        this.itemDAO = new ItemCarritoDAOBinario(rutaBase, new ProductoDAOArchivosB(rutaBase));

        //  Crear archivo si no existe
        File archivo = new File(rutaArchivo);
        try {
            if (!archivo.exists()) {
                boolean creado = archivo.createNewFile();
                System.out.println(creado ? "Archivo carritos.dat creado." : "⚠ No se creó carritos.dat");
            }
        } catch (IOException e) {
            System.err.println(" Error al crear archivo de carritos: " + e.getMessage());
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
                    Usuario u = new Usuario(); // Carga mínima: solo cédula
                    u.setUsername(cedula.trim());
                    c.setUsuario(u);
                    c.setFechaCreacion(new java.util.GregorianCalendar());
                    c.getFechaCreacion().setTimeInMillis(fechaMillis);
                    lista.add(c);
                }
            }
        } catch (IOException e) {
            System.err.println(" Error al listar carritos: " + e.getMessage());
        }
        return lista;
    }

}
