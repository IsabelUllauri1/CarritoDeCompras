package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarritoDAOMemoria implements CarritoDAO {

    private final List<Carrito> carritos;
    private int siguienteCodigo = 1;

    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }

    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(siguienteCodigo++);
        carritos.add(carrito);
    }

    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }

    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }

    @Override
    public void eliminar(int codigo) {
        Iterator<Carrito> iterator = carritos.iterator();
        while (iterator.hasNext()) {
            Carrito carrito = iterator.next();
            if (carrito.getCodigo() == codigo) {
                iterator.remove();
            }
        }
    }

    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        return List.of();
    }

    @Override
    public void eliminar(String codigo) {
        try {
            int cod = Integer.parseInt(codigo);
            eliminar(cod); // si ya tienes un eliminar(int)
        } catch (NumberFormatException e) {
            System.err.println("Código no válido: " + codigo);
        }
    }


    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }


}
