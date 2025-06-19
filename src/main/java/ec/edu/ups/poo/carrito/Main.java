package ec.edu.ups.poo.carrito;

import ec.edu.ups.poo.carrito.controlador.ProductoActualizarControlador;
import ec.edu.ups.poo.carrito.controlador.ProductoControlador;
import ec.edu.ups.poo.carrito.controlador.ProductoEliminarControlador;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.dao.impl.ProductoDAOMemoria;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.view.AnadirProductosView;
import ec.edu.ups.poo.carrito.view.ProductoActualizarView;
import ec.edu.ups.poo.carrito.view.ProductoEliminarView;
import ec.edu.ups.poo.carrito.view.ProductoListarView;
import ec.edu.ups.poo.carrito.view.Principal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {

        ProductoDAO dao = new ProductoDAOMemoria();
        dao.crear(new Producto("Manzana", 1, 0.50));
        dao.crear(new Producto("Pan", 2, 1.20));
        dao.crear(new Producto("Leche", 3, 0.75));
        dao.crear(new Producto("Huevos", 4, 2.30));
        dao.crear(new Producto("Arroz", 5, 0.90));

        Principal principal = new Principal();

        /* java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Principal principal = new Principal();
                ProductoDAO productoDAO = new ProductoDAOMemoria();

                //vistas
                AnadirProductosView anadirProductosView = new AnadirProductosView();
                ProductoListarView productoListarView = new ProductoListarView();


                ProductoControlador productoControlador = new ProductoControlador(productoDAO, principal);
                //anadir
                productoControlador.setVistaAnadir(anadirProductosView);
                //listar
                productoControlador.setProductoListarView(productoListarView);


                principal.getMenuItemCrear().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!anadirProductosView.isVisible()){
                            anadirProductosView.setVisible(true);
                            principal.getDesktopPanel().add(anadirProductosView);
                        }
                    }
                });

                principal.getMenuItemEliminar().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!productoListarView.isVisible()){
                            productoListarView.setVisible(true);
                            principal.getDesktopPanel().add(productoListarView);
                        }
                    }
                });


            }
        });


            AnadirProductosView vA = new AnadirProductosView();
        ProductoListarView   vL = new ProductoListarView();
        ProductoActualizarView   vistaAct    = new ProductoActualizarView();
        ProductoEliminarView     vistaDel    = new ProductoEliminarView();






         */
    }
}
