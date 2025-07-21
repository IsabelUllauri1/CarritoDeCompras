package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.PreguntaRespondidaDAO;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaRespondidaDAOBinario implements PreguntaRespondidaDAO {

    private final String rutaArchivo;
    /**
     * Crea una nueva instancia de PreguntaRespondidaDAOBinario.
     * Si el archivo de almacenamiento no existe, lo inicializa como una lista vacía.
     *
     * @param rutaBase Ruta base donde se ubicará el archivo preguntas_respondidas.dat.
     */

    public PreguntaRespondidaDAOBinario(String rutaBase) {
        this.rutaArchivo = new File(rutaBase, "preguntas_respondidas.dat").getAbsolutePath();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
                out.writeObject(new ArrayList<PreguntaRespondida>());
            } catch (IOException e) {
                System.err.println("Error al crear archivo preguntas respondidas: " + e.getMessage());
            }
        }
    }
    /**
     * Guarda una lista de nuevas respuestas de seguridad,
     * agregándolas a las ya existentes.
     *
     * @param nuevas Lista de nuevas respuestas a guardar.
     * @throws IOException Si ocurre un error al escribir en el archivo binario.
     */

    @Override
    public void guardar(List<PreguntaRespondida> nuevas) {
        List<PreguntaRespondida> todas = listar();
        todas.addAll(nuevas);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(todas);
        } catch (IOException e) {
            System.err.println("Error al guardar preguntas respondidas: " + e.getMessage());
        }
    }
    /**
     * Lista todas las respuestas de seguridad almacenadas.
     *
     * @return Lista completa de PreguntaRespondida.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si la clase no puede ser deserializada.
     */

    private List<PreguntaRespondida> listar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<PreguntaRespondida>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
    /**
     * Devuelve todas las respuestas asociadas a un usuario específico.
     *
     * @param username Nombre de usuario.
     * @return Lista de respuestas correspondientes al usuario.
     */

    @Override
    public List<PreguntaRespondida> buscarPorUsuario(String username) {
        List<PreguntaRespondida> resultado = new ArrayList<>();
        for (PreguntaRespondida pr : listar()) {
            if (pr.getUsername().equals(username)) {
                resultado.add(pr);
            }
        }
        return resultado;
    }
    /**
     * Reemplaza todas las respuestas de seguridad existentes de un usuario
     * con una nueva lista de respuestas.
     *
     * @param username Usuario al que se le actualizarán las respuestas.
     * @param nuevas Lista de nuevas respuestas que se guardarán.
     * @throws IOException Si ocurre un error al escribir el archivo.
     */

    @Override
    public void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas) {
        List<PreguntaRespondida> todas = listar();
        List<PreguntaRespondida> actualizadas = new ArrayList<>();

        for (PreguntaRespondida p : todas) {
            if (!p.getUsername().equals(username)) {
                actualizadas.add(p);
            }
        }

        actualizadas.addAll(nuevas);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(actualizadas);
        } catch (IOException e) {
            System.err.println("Error al actualizar preguntas respondidas: " + e.getMessage());
        }
    }

}

