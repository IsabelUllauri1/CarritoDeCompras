package ec.edu.ups.poo.carrito.dao;

import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.util.List;

public interface PreguntaRespondidaDAO {

    void guardar(List<PreguntaRespondida> preguntas);
    List<PreguntaRespondida> buscarPorUsuario(String username);
    void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas);

}
