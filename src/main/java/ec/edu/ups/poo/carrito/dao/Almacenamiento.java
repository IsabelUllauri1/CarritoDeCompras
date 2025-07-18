package ec.edu.ups.poo.carrito.dao;

import java.io.IOException;
import java.util.List;

public interface Almacenamiento {
    <T> void save(String key, T entity) throws IOException;
    <T> T find(String key, Class<T> type) throws IOException;
    <T> List<T> findAll(Class<T> type) throws IOException;
}
