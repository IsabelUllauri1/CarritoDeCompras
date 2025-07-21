package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.PreguntaRespondidaDAO;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespondidaDAOMemoria implements PreguntaRespondidaDAO {
    private final List<PreguntaRespondida> respuestas = new ArrayList<>();
    /**
     * Guarda una lista de nuevas preguntas respondidas en la lista existente.
     *
     * @param nuevas Lista de preguntas respondidas que se desea guardar.
     */
    @Override
    public void guardar(List<PreguntaRespondida> nuevas) {
        respuestas.addAll(nuevas);
    }
    /**
     * Busca y devuelve todas las preguntas respondidas asociadas a un usuario.
     *
     * @param username Nombre de usuario (cédula) asociado a las respuestas.
     * @return Lista de preguntas respondidas del usuario.
     */
    @Override
    public List<PreguntaRespondida> buscarPorUsuario(String username) {
        List<PreguntaRespondida> resultado = new ArrayList<>();
        for (PreguntaRespondida p : respuestas) {
            if (p.getUsername().equals(username)) {
                resultado.add(p);
            }
        }
        return resultado;
    }
    /**
     * Reemplaza todas las preguntas respondidas de un usuario con una nueva lista.
     *
     * @param username Nombre de usuario (cédula) cuyas respuestas se actualizarán.
     * @param nuevas Nuevas preguntas respondidas que reemplazarán las anteriores.
     */
    @Override
    public void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas) {
        respuestas.removeIf(p -> p.getUsername().equals(username));
        respuestas.addAll(nuevas);
    }
}
