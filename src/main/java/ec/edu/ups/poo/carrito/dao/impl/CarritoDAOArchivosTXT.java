package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;

public class CarritoDAOArchivosTXT implements CarritoDAO {

    private final String rutaArchivo;
    private final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private final Map<Integer, Carrito> carritos = new HashMap<>();
    private final Function<String, Usuario> obtenerUsuarioPorCedula;
    private int siguienteCodigo = 1;
    /**
     * Crea una instancia del DAO de carritos con archivo de texto.
     * Carga los carritos existentes desde el archivo al iniciar.
     *
     * @param rutaBase Ruta base del directorio donde se encuentra el archivo.
     * @param obtenerUsuarioPorCedula Función que obtiene un Usuario a partir de su cédula.
     */
    public CarritoDAOArchivosTXT(String rutaBase, Function<String, Usuario> obtenerUsuarioPorCedula) {
        this.rutaArchivo = new File(rutaBase, "carritos.txt").getAbsolutePath();
        System.out.println("Ruta completa del archivo carritos.txt: " + rutaArchivo);
        this.obtenerUsuarioPorCedula = obtenerUsuarioPorCedula;
        cargar();
    }

    /**
     * Crea un nuevo carrito, le asigna un código incremental, lo guarda en memoria y persiste en el archivo.
     *
     * @param carrito Carrito a guardar.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(siguienteCodigo++);
        carritos.put(carrito.getCodigo(), carrito);
        guardar();
        System.out.println("GUARDANDO carrito:");
        System.out.println("→ Usuario: " + carrito.getUsuario().getUsername() + ", Fecha: " + carrito.getFechaCreacion().getTime());

    }
    /**
     * Busca un carrito por su código.
     *
     * @param codigo Código del carrito.
     * @return Carrito correspondiente, o null si no existe.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.get(codigo);
    }
    /**
     * Actualiza un carrito existente y guarda los cambios en el archivo.
     *
     * @param carrito Carrito con la información actualizada.
     */
    @Override
    public void actualizar(Carrito carrito) {
        carritos.put(carrito.getCodigo(), carrito);
        guardar();
    }
    /**
     * Elimina un carrito de la colección usando su código y actualiza el archivo.
     *
     * @param codigo Código del carrito a eliminar.
     */
    @Override
    public void eliminar(int codigo) {
        carritos.remove(codigo);
        guardar();
    }
    /**
     * Lista los carritos de un usuario específico.
     *
     * @param usuario Usuario cuyos carritos se desean listar.
     * @return Lista vacía (no implementado).
     */
    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        return List.of();
    }
    /**
     * Elimina un carrito usando su código en forma de cadena.
     *
     * @param codigo Código como String.
     */
    @Override
    public void eliminar(String codigo) {
        try {
            int cod = Integer.parseInt(codigo);
            eliminar(cod);
        } catch (NumberFormatException e) {
            System.err.println("Código no válido: " + codigo);
        }
    }

    /**
     * Devuelve todos los carritos almacenados en memoria.
     *
     * @return Lista de carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos.values());
    }
    /**
     * Escribe todos los carritos almacenados en memoria al archivo de texto.
     * Cada carrito se guarda en formato CSV: código, fecha, cédula.
     */
    private void guardar() {
        System.out.println(" [DEBUG] Ejecutando guardar() en CarritoDAOArchivosTXT → ruta: " + rutaArchivo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Carrito carrito : carritos.values()) {
                String linea = carrito.getCodigo() + "," +
                        formatoFecha.format(carrito.getFechaCreacion().getTime()) + "," +
                        carrito.getUsuario().getUsername(); // asumimos cédula como username
                writer.write(linea);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error al guardar carritos: " + e.getMessage());
        }
    }
    /**
     * Carga los carritos desde el archivo de texto al mapa en memoria.
     * Reconstruye los objetos Carrito y actualiza el siguiente código disponible.
     */
    private void cargar() {
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    int codigo = Integer.parseInt(partes[0]);
                    Date fecha = formatoFecha.parse(partes[1]);
                    String cedulaUsuario = partes[2];

                    Usuario usuario = obtenerUsuarioPorCedula.apply(cedulaUsuario);
                    if (usuario != null) {
                        Carrito carrito = new Carrito();
                        carrito.setCodigo(codigo);
                        GregorianCalendar calendar = new GregorianCalendar();
                        calendar.setTime(fecha);
                        carrito.setFechaCreacion(calendar);
                        carrito.setUsuario(usuario);
                        carritos.put(codigo, carrito);
                        siguienteCodigo = Math.max(siguienteCodigo, codigo + 1);
                    }
                }
            }
            System.out.println("CARGADOS desde archivo:");
            for (Carrito c : carritos.values()) {
                System.out.println("→ Carrito " + c.getCodigo() + ", Usuario: " + c.getUsuario().getUsername());
            }
        } catch (IOException | NumberFormatException | java.text.ParseException e) {
            System.err.println("Error al cargar carritos: " + e.getMessage());
        }
    }
}
