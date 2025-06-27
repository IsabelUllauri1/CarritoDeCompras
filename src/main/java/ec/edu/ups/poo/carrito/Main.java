package ec.edu.ups.poo.carrito;

import ec.edu.ups.poo.carrito.controlador.*;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.dao.impl.CarritoDAOMemoria;
import ec.edu.ups.poo.carrito.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.carrito.dao.impl.UsuarioDAOMemoria;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.*;
import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.view.CarritoAnadirView;
import ec.edu.ups.poo.carrito.view.CarritoListarView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //usuario login
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            MiPaginaView miPaginaView = new MiPaginaView();
            VerDetalleView verDetalleView = new VerDetalleView();
            LoginView loginView = new LoginView();
            ListarMisCarritos listar = new ListarMisCarritos();
            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();


            productoDAO.crear(new Producto("Manzana", 1, 0.50));
            productoDAO.crear(new Producto("Pan",     2, 1.20));
            productoDAO.crear(new Producto("Leche",   3, 0.75));
            productoDAO.crear(new Producto("Huevos",  4, 2.30));
            productoDAO.crear(new Producto("Arroz",   5, 0.90));

            new UsuarioControlador(new UsuarioDAOMemoria(), productoDAO, new CarritoDAOMemoria(), loginView, miPaginaView, verDetalleView, listar);

            loginView.setVisible(true);
        });
    }


}
