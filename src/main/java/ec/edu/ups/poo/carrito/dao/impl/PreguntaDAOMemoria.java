package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.modelo.Pregunta;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAOMemoria implements PreguntaDAO {

    private List<Pregunta> preguntas;

    public PreguntaDAOMemoria() {
        preguntas = new ArrayList<>();
        preguntas.add(new Pregunta("¿Nombre de tu primer perro?",1));
        preguntas.add(new Pregunta("¿Ciudad donde naciste?",2));
        preguntas.add(new Pregunta("¿Ciudad donde naciste?",3));
        preguntas.add(new Pregunta("¿Cuál es el apellido de tu abuelo?",4));
        preguntas.add(new Pregunta("¿Cómo se llamaba su primer amigo de la infancia?", 5));
        preguntas.add(new Pregunta("¿Cuál es su sueño más brillante de la infancia?",6));
        preguntas.add(new Pregunta("¿Cuál es su artista favorito?",7));
        preguntas.add(new Pregunta("¿Cómo se llamaba tu profesor favorito?",8));
        preguntas.add(new Pregunta("¿En qué año ingresó en la universidad?",9));
        preguntas.add(new Pregunta("¿Cuál es el apodo de su mejor amigo?",10));
    }

    @Override
    public List<Pregunta> listarPreguntas() {
        return preguntas;
    }
}
