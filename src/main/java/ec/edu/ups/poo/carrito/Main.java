package ec.edu.ups.poo.carrito;

import ec.edu.ups.poo.carrito.controlador.*;
import ec.edu.ups.poo.carrito.dao.*;
import ec.edu.ups.poo.carrito.dao.impl.*;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.*;
import ec.edu.ups.poo.carrito.view.carrito.*;
import ec.edu.ups.poo.carrito.view.login.LoginView;
import ec.edu.ups.poo.carrito.view.login.OlvideContrasenaView;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;
import ec.edu.ups.poo.carrito.view.login.RegistrarseView;
import ec.edu.ups.poo.carrito.view.producto.*;
import ec.edu.ups.poo.carrito.view.usuario.CrearUsuarioView;
import ec.edu.ups.poo.carrito.view.usuario.EditarUsuarioView;
import ec.edu.ups.poo.carrito.view.usuario.ListarUsuariosView;
import ec.edu.ups.poo.carrito.view.usuario.MiPaginaView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            UsuarioDAO  usuarioDAO  = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO  carritoDAO  = new CarritoDAOMemoria();
            PreguntaDAO  preguntaDAO = new PreguntaDAOMemoria();

            LoginView loginView = new LoginView();
            RegistrarseView registrarseView = new RegistrarseView();
            PreguntasView preguntasView = new PreguntasView();
            OlvideContrasenaView olvideContrasenaView = new OlvideContrasenaView();


            LoginControlador loginControlador = new LoginControlador(usuarioDAO,loginView,registrarseView,preguntasView,olvideContrasenaView,preguntaDAO);
            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAut = loginControlador.getUsuarioAutenticado();
                    if (usuarioAut == null) {
                        System.exit(0);
                    }

                    Principal principal = new Principal();

                    AnadirProductosView anadirProdV = new AnadirProductosView();
                    ProductoListarView listarProdV   = new ProductoListarView();
                    ProductoEliminarView eliminarProdV = new ProductoEliminarView();
                    ProductoActualizarView actualizarProdV = new ProductoActualizarView();
                    ListarProductosPorCodigoView listarProdPorCodigo = new ListarProductosPorCodigoView();
                    ListarTodosLosCarritosView listarTodosLosCarritosView = new ListarTodosLosCarritosView();

                    CarritoAnadirView anadirCarritoV = new CarritoAnadirView();
                    CarritoListarView listarCarritoV = new CarritoListarView();

                    MiPaginaView miPaginaV           = new MiPaginaView();
                    ListarMisCarritos listarMisV     = new ListarMisCarritos();
                    VerDetalleView verDetalleV       = new VerDetalleView();

                    ListarUsuariosView listarUsuariosView = new ListarUsuariosView();
                    CrearUsuarioView crearUsuarioView = new CrearUsuarioView();
                    EditarUsuarioView editarUsuarioView = new EditarUsuarioView();



                    ProductoControlador prodCtrl = new ProductoControlador(productoDAO, principal, anadirProdV, listarProdV,listarProdPorCodigo, anadirCarritoV);
                    CarritoControlador carritoCtrl = new CarritoControlador(productoDAO, carritoDAO, anadirCarritoV, listarCarritoV, usuarioAut);
                    UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioAut,carritoDAO,usuarioDAO,miPaginaV,listarMisV,verDetalleV,listarUsuariosView,crearUsuarioView,editarUsuarioView,principal, listarTodosLosCarritosView );

                    if (usuarioAut.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenuAdministrador();
                    }

                    // — Producto —
                    principal.getMenuItemCrear().addActionListener(ev -> {

                        if(!anadirProdV.isVisible()){
                            anadirProdV.setVisible(true);
                            anadirProdV.moveToFront();
                            try {
                                anadirProdV.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                    principal.getMenuItemListarCodigo().addActionListener(ev -> {
                        if (!listarProdPorCodigo.isShowing()) {
                            principal.getDesktopPanel().add(listarProdPorCodigo);
                        }
                        prodCtrl.listarProductosEnVistaPorCodigo();
                        listarProdPorCodigo.setVisible(true);
                        try {
                            listarProdPorCodigo.setSelected(true);
                            listarProdPorCodigo.moveToFront();
                        } catch (PropertyVetoException ignore) {}

                    });
                    principal.getMenuItemListarProductos().addActionListener(ev -> {
                        principal.getDesktopPanel().add(listarProdV);
                        listarProdV.setVisible(true);
                    });
                    principal.getMenuItemEliminar().addActionListener(ev -> {
                        principal.getDesktopPanel().add(eliminarProdV);
                        eliminarProdV.setVisible(true);
                    });
                    principal.getMenuItemActualizar().addActionListener(ev -> {
                        principal.getDesktopPanel().add(actualizarProdV);
                        actualizarProdV.setVisible(true);
                    });

                    // — Carrito —
                    principal.getMenuItemCarrito().addActionListener(ev -> {
                        principal.getDesktopPanel().add(anadirCarritoV);
                        anadirCarritoV.setVisible(true);
                    });

                    // — Cuenta —
                    principal.getMenuItemMiPagina().addActionListener(ev -> {
                        principal.getDesktopPanel().add(miPaginaV);
                        miPaginaV.setVisible(true);
                    });
                    principal.getMenuItemMisCarritos().addActionListener(ev -> {
                        if (!listarMisV.isShowing()) {
                            principal.getDesktopPanel().add(listarMisV);
                        }
                        listarMisV.setVisible(true);
                        try { listarMisV.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });
                    // — Admin —
                    principal.getMenuItemListarTodosUsuarios().addActionListener( e1 -> {
                        if (!listarUsuariosView.isShowing()) {
                            principal.getDesktopPanel().add(listarUsuariosView);
                        }
                        listarUsuariosView.setVisible(true);
                        listarUsuariosView.moveToFront();
                        try { listarUsuariosView.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });
                    principal.getMenuItemCrearUsuario().addActionListener(ev -> {
                        if (!principal.getDesktopPanel().isAncestorOf(crearUsuarioView)) {
                            principal.getDesktopPanel().add(crearUsuarioView);
                        }
                        crearUsuarioView.setVisible(true);
                        crearUsuarioView.moveToFront();
                        try { crearUsuarioView.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });
                    principal.getMenuItemListarTodosLosCarritos().addActionListener(e1 -> {
                        usuarioControlador.mostrarTodosLosCarritos();
                        if (!listarTodosLosCarritosView.isShowing()) {
                            principal.getDesktopPanel().add(listarTodosLosCarritosView);
                        }
                        listarTodosLosCarritosView.setVisible(true);
                        listarTodosLosCarritosView.moveToFront();
                        try { listarTodosLosCarritosView.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });

                    // — Salir —
                    principal.getMenuItemSalir().addActionListener(ev -> {
                        principal.dispose();
                        loginView.setVisible(true);
                    });

                    // — Internacionalizacion —
                    principal.getMenuIdiomaIngles().addActionListener(ev -> {
                        principal.cambiarIdioma("en","US");
                        usuarioControlador.mostrarTodosLosCarritos();
                    });
                    principal.getMenuIdiomaEspanol().addActionListener(ev -> {
                            principal.cambiarIdioma("es","EC");
                            usuarioControlador.mostrarTodosLosCarritos();}
                    );
                    principal.getMenuIdiomaAleman().addActionListener(ev ->{
                        principal.cambiarIdioma("de","DE");
                        usuarioControlador.mostrarTodosLosCarritos();
                    });
                    System.out.println("Idioma del sistema: " + Locale.getDefault());


                    principal.setVisible(true);
                }
            });
        });
    }
}
