package ec.edu.ups.poo.carrito.dao.impl.binario;

import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.modelo.Pregunta;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOArchivosB implements PreguntaDAO {

    private final String rutaArchivo;
    /**
     * Crea una nueva instancia de PreguntaDAOArchivosB.
     * Verifica si el archivo binario de preguntas existe, y si no,
     * lo inicializa con un conjunto de preguntas fijas predeterminadas.
     *
     * @param rutaBase Ruta base donde se almacenará el archivo preguntas.dat.
     */

    public PreguntaDAOArchivosB(String rutaBase) {
        this.rutaArchivo = new File(rutaBase, "preguntas.dat").getAbsolutePath();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            inicializarPreguntas();
        }
    }
    /**
     * Lista todas las preguntas de seguridad almacenadas en el archivo binario.
     *
     * @return Lista de objetos Pregunta.
     * @throws IOException Si ocurre un error al leer el archivo.
     * @throws ClassNotFoundException Si no se puede deserializar la lista de preguntas.
     */

    @Override
    public List<Pregunta> listarPreguntas() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<Pregunta>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer preguntas desde binario: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    /**
     * Inicializa el archivo binario con 10 preguntas predeterminadas si no existe previamente.
     *
     * @throws IOException Si ocurre un error al escribir en el archivo binario.
     */

    private void inicializarPreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Nombre de tu primer perro?", 1));
        preguntas.add(new Pregunta("¿Ciudad donde naciste?", 2));
        preguntas.add(new Pregunta("¿Ciudad donde naciste?", 3));
        preguntas.add(new Pregunta("¿Cuál es el apellido de tu abuelo?", 4));
        preguntas.add(new Pregunta("¿Cómo se llamaba su primer amigo de la infancia?", 5));
        preguntas.add(new Pregunta("¿Cuál es su sueño más brillante de la infancia?", 6));
        preguntas.add(new Pregunta("¿Cuál es su artista favorito?", 7));
        preguntas.add(new Pregunta("¿Cómo se llamaba tu profesor favorito?", 8));
        preguntas.add(new Pregunta("¿En qué año ingresó en la universidad?", 9));
        preguntas.add(new Pregunta("¿Cuál es el apodo de su mejor amigo?", 10));

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(preguntas);
        } catch (IOException e) {
            System.err.println("Error al guardar preguntas iniciales: " + e.getMessage());
        }
    }
}
