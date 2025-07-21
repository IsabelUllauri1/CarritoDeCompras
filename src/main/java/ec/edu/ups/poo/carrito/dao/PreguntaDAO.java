package ec.edu.ups.poo.carrito.dao;


import ec.edu.ups.poo.carrito.modelo.Pregunta;

import java.util.List;

public interface PreguntaDAO {
    /**
     * Devuelve la lista completa de preguntas de seguridad disponibles en el sistema.
     *
     * @return Lista de objetos {@link Pregunta}.
     */
    List<Pregunta> listarPreguntas();
}
