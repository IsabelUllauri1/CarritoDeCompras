package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class UsuarioDAOArchivos implements UsuarioDAO {

    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    @Override
    public Usuario autenticar(String username, String contrasenia) {
        return null;
    }

    @Override
    public void crear(Usuario usuario) {

    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return null;
    }

    @Override
    public void eliminar(String username) {

    }

    @Override
    public void actualizar(Usuario usuarioActualizado) {

    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
    }

    @Override
    public List<Usuario> listarAdministradores() {
        return List.of();
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return List.of();
    }

    @Override
    public List<Usuario> listarPorRol(ROL rol) {
        return List.of();
    }
}



