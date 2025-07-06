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
import ec.edu.ups.poo.carrito.view.usuario.*;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.util.Locale;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            UsuarioDAO  usuarioDAO  = new UsuarioDAOMemoria();
            ProductoDAO productoDAO = new ProductoDAOMemoria();
            CarritoDAO  carritoDAO  = new CarritoDAOMemoria();
            PreguntaDAO  preguntaDAO = new PreguntaDAOMemoria();
            Locale defaultLocale = Locale.getDefault();
            MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler(defaultLocale.getLanguage(), defaultLocale.getCountry());

            LoginView loginView = new LoginView();

            loginView.actualizarTexto(mensajeInternacionalizacionHandler);

            RegistrarseView registrarseView = new RegistrarseView();
            registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);

            PreguntasView preguntasView = new PreguntasView();
            preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);

            OlvideContrasenaView olvideContrasenaView = new OlvideContrasenaView();
            olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);



            LoginControlador loginControlador = new LoginControlador(usuarioDAO,loginView,registrarseView,preguntasView,olvideContrasenaView,preguntaDAO);
            loginControlador.setMensajeInternacionalizacionHandler(mensajeInternacionalizacionHandler);
            loginView.setVisible(true);

            loginView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    Usuario usuarioAut = loginControlador.getUsuarioAutenticado();
                    if (usuarioAut == null) {
                        System.exit(0);
                    }

                    AnadirProductosView anadirProdV = new AnadirProductosView();
                    ProductoListarView listarProdV   = new ProductoListarView();
                    ProductoEliminarView eliminarProdV = new ProductoEliminarView();
                    ProductoActualizarView actualizarProdV = new ProductoActualizarView();
                    ListarProductosPorCodigoView listarProdPorCodigo = new ListarProductosPorCodigoView();
                    ListarTodosLosCarritosView listarTodosLosCarritosView = new ListarTodosLosCarritosView();

                    CarritoAnadirView anadirCarritoV = new CarritoAnadirView();
                    CarritoListarView listarCarritoV = new CarritoListarView();

                    MiPaginaView miPaginaV  = new MiPaginaView();
                    ListarMisCarritos listarMisV  = new ListarMisCarritos();
                    VerDetalleView verDetalleV  = new VerDetalleView();
                    PreguntasUView preguntasUV = new PreguntasUView();

                    ListarUsuariosView listarUsuariosView = new ListarUsuariosView();
                    CrearUsuarioView crearUsuarioView = new CrearUsuarioView();
                    EditarUsuarioView editarUsuarioView = new EditarUsuarioView();

                    Principal principal = new Principal();

                    ProductoControlador prodCtrl = new ProductoControlador(productoDAO, principal, anadirProdV, listarProdV,listarProdPorCodigo, anadirCarritoV,eliminarProdV,actualizarProdV);
                    CarritoControlador carritoCtrl = new CarritoControlador(productoDAO, carritoDAO, anadirCarritoV, listarCarritoV, usuarioAut);
                    UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioAut,carritoDAO,usuarioDAO,miPaginaV,listarMisV,verDetalleV,listarUsuariosView,crearUsuarioView,editarUsuarioView,principal, listarTodosLosCarritosView, preguntasUV,preguntaDAO);


                    if (usuarioAut.getRol() == Rol.USUARIO) {
                        principal.deshabilitarMenuAdministrador();
                    }

                    // — Producto —
                    principal.getMenuItemCrear().addActionListener(ev -> {
                        if (!anadirProdV.isShowing()) {
                            principal.getDesktopPanel().add(anadirProdV);
                        }
                        anadirProdV.setVisible(true);
                        anadirProdV.moveToFront();
                        try {
                            anadirProdV.setSelected(true);
                        } catch (PropertyVetoException ex) {
                            ex.printStackTrace();
                        }
                    });
                    principal.getMenuItemActualizar().addActionListener(ev -> {
                        if (!actualizarProdV.isShowing()) {
                            principal.getDesktopPanel().add(actualizarProdV);
                        }
                        actualizarProdV.setVisible(true);
                        try {
                            actualizarProdV.setSelected(true);
                            actualizarProdV.moveToFront();
                        } catch (PropertyVetoException ignored) {}
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
                        if (!listarProdV.isShowing()) {
                            principal.getDesktopPanel().add(listarProdV);
                        }
                        prodCtrl.listarProductos();
                        listarProdV.setVisible(true);
                        try {
                            listarProdV.setSelected(true);
                            listarProdV.moveToFront();
                        } catch (Exception ignored) {}
                    });

                    principal.getMenuItemEliminar().addActionListener(ev -> {
                        if (!eliminarProdV.isShowing()) {
                            if (!principal.getDesktopPanel().isAncestorOf(eliminarProdV)) {
                                principal.getDesktopPanel().add(eliminarProdV);
                            }
                            eliminarProdV.setVisible(true);
                            eliminarProdV.moveToFront();
                            try {
                                eliminarProdV.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                    // — Carrito —
                    principal.getMenuItemCarrito().addActionListener(ev -> {
                        if (!anadirCarritoV.isShowing()) {
                            if (!principal.getDesktopPanel().isAncestorOf(anadirCarritoV)) {
                                principal.getDesktopPanel().add(anadirCarritoV);
                            }
                            System.out.println("Mostrando CarritoAnadirView");
                            anadirCarritoV.setVisible(true);
                            anadirCarritoV.moveToFront();
                            try {
                                anadirCarritoV.setSelected(true);
                            } catch (PropertyVetoException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });



                    // — Cuenta —
                    principal.getMenuItemMiPagina().addActionListener(ev -> {
                        if (!miPaginaV.isShowing()) {
                            principal.getDesktopPanel().add(miPaginaV);
                        }
                        usuarioControlador.cargarDatosEnMiPagina();
                        miPaginaV.setVisible(true);
                        miPaginaV.moveToFront();
                    });
                    principal.getMenuItemMisCarritos().addActionListener(ev -> {
                        if (!listarMisV.isShowing()) {
                            principal.getDesktopPanel().add(listarMisV);
                        }

                        listarMisV.actualizarTexto(principal.getMensajeInternacionalizacionHandler());
                        listarMisV.setVisible(true);
                        try { listarMisV.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });

                    // — Admin —
                    principal.getMenuItemListarTodosUsuarios().addActionListener(e1 -> {
                        if (!principal.getDesktopPanel().isAncestorOf(listarUsuariosView)) {
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

                        listarTodosLosCarritosView.actualizarTexto(principal.getMensajeInternacionalizacionHandler());
                        listarTodosLosCarritosView.setVisible(true);
                        listarTodosLosCarritosView.moveToFront();
                        try { listarTodosLosCarritosView.setSelected(true); }
                        catch(PropertyVetoException ignore){}
                    });

                    // — Internacionalizacion —
                    principal.getMenuIdiomaIngles().addActionListener(ev -> {
                        principal.cambiarIdioma("en","US");
                        usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());

                    });
                    principal.getMenuIdiomaEspanol().addActionListener(ev -> {
                        principal.cambiarIdioma("es","EC");
                        usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());
                    });
                    principal.getMenuIdiomaAleman().addActionListener(ev ->{
                        principal.cambiarIdioma("de","DE");
                        usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());

                    });

                    // — Salir —
                    principal.getMenuItemSalir().addActionListener(ev -> {
                        principal.dispose();
                        loginView.actualizarTexto(principal.getMensajeInternacionalizacionHandler());
                        loginView.setVisible(true);
                    });



                    // PRODUCTO
                    principal.setActualizarProducto(actualizarProdV);

                    principal.setAnadirProducto(anadirProdV);
                    principal.setEliminarProducto(eliminarProdV);
                    principal.setListarProducto(listarProdV);
                    principal.setListarProductoPorCodigo(listarProdPorCodigo);

                    // USUARIO
                    principal.setCrearUsuario(crearUsuarioView);
                    principal.setEditarUsuario(editarUsuarioView);
                    principal.setListarUsuarios(listarUsuariosView);
                    principal.setMiPagina(miPaginaV);

                    // LOGIN
                    principal.setPreguntasView(preguntasView);
                    principal.setPreguntasUView(preguntasUV);
                    principal.setRegistrarseView(registrarseView);
                    principal.setOlvideContrasenaView(olvideContrasenaView);

                    // CARRITO
                    principal.setCarritoAnadir(anadirCarritoV);
                    principal.setCarritoListar(listarCarritoV);
                    principal.setListarMisCarritos(listarMisV);
                    principal.setVerDetalle(verDetalleV);
                    principal.setListarTodosLosCarritos(listarTodosLosCarritosView);


                    principal.cambiarIdioma(defaultLocale.getLanguage(), defaultLocale.getCountry());

                    usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                    usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());


                    principal.setVisible(true);
                }
            });
        });
    }
}
