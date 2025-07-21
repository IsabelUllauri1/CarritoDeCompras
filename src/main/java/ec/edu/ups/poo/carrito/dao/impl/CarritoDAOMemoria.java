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

    /**
     * Crea una instancia del DAO de carritos en memoria.
     * Inicializa la lista de carritos vacía.
     */
    public CarritoDAOMemoria() {
        this.carritos = new ArrayList<Carrito>();
    }
    /**
     * Crea un nuevo carrito asignándole un código incremental
     * y lo agrega a la lista en memoria.
     *
     * @param carrito Carrito a agregar.
     */
    @Override
    public void crear(Carrito carrito) {
        carrito.setCodigo(siguienteCodigo++);
        carritos.add(carrito);
    }
    /**
     * Busca un carrito en la lista por su código.
     *
     * @param codigo Código del carrito a buscar.
     * @return Carrito correspondiente o null si no se encuentra.
     */
    @Override
    public Carrito buscarPorCodigo(int codigo) {
        for (Carrito carrito : carritos) {
            if (carrito.getCodigo() == codigo) {
                return carrito;
            }
        }
        return null;
    }
    /**
     * Actualiza la información de un carrito en la lista en base a su código.
     *
     * @param carrito Carrito actualizado.
     */
    @Override
    public void actualizar(Carrito carrito) {
        for (int i = 0; i < carritos.size(); i++) {
            if (carritos.get(i).getCodigo() == carrito.getCodigo()) {
                carritos.set(i, carrito);
                break;
            }
        }
    }
    /**
     * Elimina un carrito de la lista utilizando su código numérico.
     *
     * @param codigo Código del carrito a eliminar.
     */
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
    /**
     * Lista los carritos de un usuario específico.
     *
     * @param usuario Usuario cuyos carritos se desean listar.
     * @return Lista vacía (no implementado).
     */
    @Override
    public List<Carrito> listarPorUsuario(Usuario usuario) {
        return List.of();
    }
    /**
     * Elimina un carrito utilizando su código representado como cadena.
     * Internamente lo convierte a entero.
     *
     * @param codigo Código del carrito como String.
     */
    @Override
    public void eliminar(String codigo) {
        try {
            int cod = Integer.parseInt(codigo);
            eliminar(cod); // si ya tienes un eliminar(int)
        } catch (NumberFormatException e) {
            System.err.println("Código no válido: " + codigo);
        }
    }

    /**
     * Devuelve todos los carritos almacenados en memoria.
     *
     * @return Lista de todos los carritos.
     */
    @Override
    public List<Carrito> listarTodos() {
        return carritos;
    }


}
