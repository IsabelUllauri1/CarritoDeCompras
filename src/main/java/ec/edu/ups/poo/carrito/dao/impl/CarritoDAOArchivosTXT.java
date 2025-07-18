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

    public CarritoDAOArchivosTXT(String rutaBase, Function<String, Usuario> obtenerUsuarioPorCedula) {
        this.rutaArchivo = new File(rutaBase, "carritos.txt").getAbsolutePath();
        System.out.println("Ruta completa del archivo carritos.txt: " + rutaArchivo);
        this.obtenerUsuarioPorCedula = obtenerUsuarioPorCedula;
        cargar();
    }


    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(siguienteCodigo++);
        carritos.put(carrito.getCodigo(), carrito);
        guardar();
        System.out.println("GUARDANDO carrito:");
        System.out.println("→ Usuario: " + carrito.getUsuario().getUsername() + ", Fecha: " + carrito.getFechaCreacion().getTime());

    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        return carritos.get(codigo);
    }

    @Override
    public void actualizar(Carrito carrito) {
        carritos.put(carrito.getCodigo(), carrito);
        guardar();
    }

    @Override
    public void eliminar(int codigo) {
        carritos.remove(codigo);
        guardar();
    }

    @Override
    public List<Carrito> listarTodos() {
        return new ArrayList<>(carritos.values());
    }

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
