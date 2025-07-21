package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.util.List;

public interface PreguntaRespondidaDAO {
    /**
     * Guarda una lista de preguntas respondidas por el usuario.
     *
     * @param preguntas Lista de {@link PreguntaRespondida} que se desea almacenar.
     */
    void guardar(List<PreguntaRespondida> preguntas);
    /**
     * Busca todas las preguntas respondidas asociadas a un nombre de usuario específico.
     *
     * @param username El nombre de usuario (cedula) del usuario.
     * @return Lista de {@link PreguntaRespondida} encontradas para ese usuario.
     */
    List<PreguntaRespondida> buscarPorUsuario(String username);
    /**
     * Actualiza las preguntas respondidas de un usuario, reemplazando las existentes.
     *
     * @param username El nombre de usuario (cedula) al que pertenecen las preguntas.
     * @param nuevas Lista de nuevas {@link PreguntaRespondida} que reemplazarán a las anteriores.
     */
    void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas);

}
