package ec.edu.ups.poo.carrito.dao.impl;

import ec.edu.ups.poo.carrito.dao.PreguntaRespondidaDAO;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PreguntaRespondidaDAOBinario implements PreguntaRespondidaDAO {

    private final String rutaArchivo;

    public PreguntaRespondidaDAOBinario(String rutaBase) {
        this.rutaArchivo = new File(rutaBase, "preguntas_respondidas.dat").getAbsolutePath();
        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
                out.writeObject(new ArrayList<PreguntaRespondida>());
            } catch (IOException e) {
                System.err.println("Error al crear archivo preguntas respondidas: " + e.getMessage());
            }
        }
    }

    @Override
    public void guardar(List<PreguntaRespondida> nuevas) {
        List<PreguntaRespondida> todas = listar();
        todas.addAll(nuevas);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(todas);
        } catch (IOException e) {
            System.err.println("Error al guardar preguntas respondidas: " + e.getMessage());
        }
    }

    private List<PreguntaRespondida> listar() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(rutaArchivo))) {
            return (List<PreguntaRespondida>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PreguntaRespondida> buscarPorUsuario(String username) {
        List<PreguntaRespondida> resultado = new ArrayList<>();
        for (PreguntaRespondida pr : listar()) {
            if (pr.getUsername().equals(username)) {
                resultado.add(pr);
            }
        }
        return resultado;
    }

    @Override
    public void actualizarPorUsuario(String username, List<PreguntaRespondida> nuevas) {
        List<PreguntaRespondida> todas = listar();
        List<PreguntaRespondida> actualizadas = new ArrayList<>();

        for (PreguntaRespondida p : todas) {
            if (!p.getUsername().equals(username)) {
                actualizadas.add(p);
            }
        }

        actualizadas.addAll(nuevas);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
            out.writeObject(actualizadas);
        } catch (IOException e) {
            System.err.println("Error al actualizar preguntas respondidas: " + e.getMessage());
        }
    }

}

