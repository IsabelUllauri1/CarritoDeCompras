package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.PreguntaRespondidaDAO;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.util.ArrayList;
import java.util.List;

public class PreguntaRespondidaDAOMemoria implements PreguntaRespondidaDAO {
    private final List<PreguntaRespondida> respuestas = new ArrayList<>();

    @Override
    public void guardar(List<PreguntaRespondida> nuevas) {
        respuestas.addAll(nuevas);
    }

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
    @Override
    public void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas) {
        respuestas.removeIf(p -> p.getUsername().equals(username));
        respuestas.addAll(nuevas);
    }
}
