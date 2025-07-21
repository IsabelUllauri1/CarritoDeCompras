package ec.edu.ups.poo.carrito;

import ec.edu.ups.poo.carrito.controlador.*;
import ec.edu.ups.poo.carrito.dao.*;
import ec.edu.ups.poo.carrito.dao.impl.*;
import ec.edu.ups.poo.carrito.dao.impl.binario.*;
import ec.edu.ups.poo.carrito.modelo.Producto;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.ConfiguracionSistema;
import ec.edu.ups.poo.carrito.util.SelectorAlmacenamiento;
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
import java.util.Date;
import java.util.Locale;
import java.util.function.Function;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Locale defaultLocale = Locale.getDefault();
            MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler =
                    new MensajeInternacionalizacionHandler(defaultLocale.getLanguage(), defaultLocale.getCountry());

            SelectorAlmacenamiento.mostrarSeleccionAlmacenamiento(null);
            ConfiguracionSistema config = ConfiguracionSistema.getInstancia();
            System.out.println("Ruta seleccionada: " + config.getRutaArchivos());



            final UsuarioDAO usuarioDAO;
            final ProductoDAO productoDAO;
            final CarritoDAO carritoDAO;
            final PreguntaDAO preguntaDAO;
            final PreguntaRespondidaDAO preguntaRespondidaDAO;
            final ItemCarritoDAO itemCarritoDAO;



            // Ruta si aplica
            String ruta = config.getRutaArchivos();

            switch (config.getTipoAlmacenamiento()) {
                case MEMORIA:
                    usuarioDAO = new UsuarioDAOMemoria();
                    productoDAO = new ProductoDAOMemoria();
                    carritoDAO = new CarritoDAOMemoria();
                    preguntaDAO = new PreguntaDAOMemoria();
                    preguntaRespondidaDAO = new PreguntaRespondidaDAOMemoria();
                    break;

                case ARCHIVOS:
                    usuarioDAO = new UsuarioDAOArchivosTXT(ruta, mensajeInternacionalizacionHandler);
                    final UsuarioDAO usuarioDAOFinalTexto = usuarioDAO; // para el Function
                    productoDAO = new ProductoDAOArchivosB(ruta);
                    itemCarritoDAO= new ItemCarritoDAOBinario(ruta, productoDAO);//--------
                    carritoDAO = new CarritoDAOArchivosTXT(ruta, new Function<String, Usuario>() {
                        @Override
                        public Usuario apply(String cedula) {
                            return usuarioDAOFinalTexto.buscarPorUsername(cedula);
                        }
                    },itemCarritoDAO );//--------

                    preguntaDAO = new PreguntaDAOArchivosB(ruta);
                    preguntaRespondidaDAO = new PreguntaRespondidaDAOBinario(ruta);
                    break;

                case ARCHIVOS_BINARIOS:
                    usuarioDAO = new UsuarioDAOBinario(ruta);
                    final UsuarioDAO usuarioDAOFinalBin = usuarioDAO;

                    productoDAO = new ProductoDAOArchivosB(ruta);
                    final ProductoDAO productoDAOFinal = productoDAO;
                    itemCarritoDAO = new ItemCarritoDAOBinario(ruta, productoDAOFinal);

                    carritoDAO = new CarritoDAOBinario(
                            ruta,
                            new Function<String, Usuario>() {
                                @Override
                                public Usuario apply(String cedula) {
                                    return usuarioDAOFinalBin.buscarPorUsername(cedula);
                                }
                            },
                            new Function<Integer, Producto>() {
                                @Override
                                public Producto apply(Integer codigo) {
                                    return productoDAOFinal.buscarPorCodigo(codigo);
                                }
                            }, itemCarritoDAO
                    );

                    preguntaDAO = new PreguntaDAOArchivosB(ruta);
                    preguntaRespondidaDAO = new PreguntaRespondidaDAOBinario(ruta);
                    Usuario adminBuscado = usuarioDAO.buscarPorUsername("0106745508");
                    if (adminBuscado == null) {
                        try {
                            Usuario admin = new Usuario("0106745508", "12345@Aa", ROL.ADMINISTRADOR, "admin@correo.com", "Administrador", "0999999999", new Date());
                            usuarioDAO.crear(admin);
                        } catch (Exception e) {
                            System.err.println("Error al crear admin: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Admin ya existe: " + adminBuscado.getUsername() + " / " + adminBuscado.getContrasenia());
                    }


                    break;

                default:
                    throw new IllegalStateException("Tipo de almacenamiento desconocido: " + config.getTipoAlmacenamiento());

            }

            if (config.getTipoAlmacenamiento() != ConfiguracionSistema.TipoAlmacenamiento.MEMORIA) {
                if (usuarioDAO.buscarPorUsername("0000000000") == null) {
                    try {
                        Usuario admin = new Usuario("0106745508", "12345@Aa", ROL.ADMINISTRADOR, "admin@correo.com", "Administrador", "0999999999", new Date());
                        usuarioDAO.crear(admin);
                        System.out.println("Admin por defecto creado.");
                    } catch (Exception ex) {
                        System.err.println(" No se pudo crear admin por defecto: " + ex.getMessage());
                    }
                }
            }






            LoginView loginView = new LoginView();
            loginView.actualizarTexto(mensajeInternacionalizacionHandler);


            RegistrarseView registrarseView = new RegistrarseView();
            registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);

            PreguntasView preguntasView = new PreguntasView();
            preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);

            OlvideContrasenaView olvideContrasenaView = new OlvideContrasenaView();
            olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);



            LoginControlador loginControlador = new LoginControlador(usuarioDAO,loginView,registrarseView,preguntasView,olvideContrasenaView,preguntaDAO,mensajeInternacionalizacionHandler,preguntaRespondidaDAO);
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
                    principal.setMensajeInternacionalizacionHandler(mensajeInternacionalizacionHandler);

                    ProductoControlador prodCtrl = new ProductoControlador(productoDAO, principal, anadirProdV, listarProdV,listarProdPorCodigo, anadirCarritoV,eliminarProdV,actualizarProdV);
                    prodCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                    CarritoControlador carritoCtrl = new CarritoControlador(productoDAO, carritoDAO, anadirCarritoV, listarCarritoV, usuarioAut);
                    carritoCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                    UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioAut,carritoDAO,usuarioDAO,miPaginaV,listarMisV,verDetalleV,listarUsuariosView,crearUsuarioView,editarUsuarioView,principal, listarTodosLosCarritosView, preguntasUV,preguntaDAO, preguntaRespondidaDAO);
                    usuarioControlador.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());


                    if (usuarioAut.getRol() == ROL.USUARIO) {
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
                            prodCtrl.recargarEliminarProductos();
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
                        usuarioControlador.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        prodCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        carritoCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());

                    });
                    principal.getMenuIdiomaEspanol().addActionListener(ev -> {
                        principal.cambiarIdioma("es","EC");
                        usuarioControlador.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        prodCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        carritoCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                        usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());
                    });
                    principal.getMenuIdiomaAleman().addActionListener(ev ->{
                        principal.cambiarIdioma("de","DE");
                        usuarioControlador.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        prodCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
                        carritoCtrl.setMensajeInternacionalizacionHandler(principal.getMensajeInternacionalizacionHandler());
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


                    principal.cambiarIdioma(mensajeInternacionalizacionHandler.getLocale().getLanguage(), mensajeInternacionalizacionHandler.getLocale().getCountry());

                    usuarioControlador.actualizarComboRol(principal.getMensajeInternacionalizacionHandler());
                    usuarioControlador.actualizarComboRolesEnFiltros(principal.getMensajeInternacionalizacionHandler());


                    principal.setVisible(true);


                }
            });

        });




    }

    public static void mostrarSeleccionAlmacenamiento() {
        String[] opciones = { "Memoria (no guarda datos)", "Archivos (guardar en disco)" };
        int opcion = JOptionPane.showOptionDialog(
                null,
                "¿Dónde desea guardar los datos?",
                "Modo de almacenamiento",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        ConfiguracionSistema config = ConfiguracionSistema.getInstancia();

        if (opcion == 0) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        } else if (opcion == 1) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.ARCHIVOS);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione la carpeta para guardar los archivos");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showOpenDialog(null);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                config.setRutaArchivos(ruta);
            } else {
                JOptionPane.showMessageDialog(null, "No seleccionó una carpeta. Se usará almacenamiento en memoria.");
                config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No seleccionó una opción. Se usará almacenamiento en memoria.");
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        }
    }

}
