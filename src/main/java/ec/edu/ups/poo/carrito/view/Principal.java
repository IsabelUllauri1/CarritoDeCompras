package ec.edu.ups.poo.carrito.view;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.view.carrito.*;
import ec.edu.ups.poo.carrito.view.login.LoginView;
import ec.edu.ups.poo.carrito.view.login.OlvideContrasenaView;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;
import ec.edu.ups.poo.carrito.view.login.RegistrarseView;
import ec.edu.ups.poo.carrito.view.producto.*;
import ec.edu.ups.poo.carrito.view.usuario.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Principal extends JFrame {
    private final MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private final JMenu menuIdioma;
    private final JMenuItem menuIdiomaEspanol;
    private final JMenuItem menuIdiomaAleman;
    private final  JMenuItem menuIdiomaIngles;
    private JMenu menuSalir;
    private JMenuItem menuItemSalir;
    private JPanel panelPrincipal;
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuItemCrear;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem menuItemListarCodigo;
    private MiJDesktopPane desktopPanel;
    private JMenuItem menuItemListarProductos;
    private JMenuItem menuItemCarrito;
    private JMenu menuUsuario;
    private JMenuItem menuItemListarTodosUsuarios;
    private JMenuItem menuItemListarAdministradores;
    private JMenuItem menuItemListarUsuarios;
    private JMenuItem menuItemListarPorRol;
    private JMenuItem menuCuenta;
    private JMenuItem menuItemMiPagina;
    private JMenuItem menuItemMisCarritos;
    private JMenuItem menuItemCrearUsuario;
    private JMenuItem menuItemListarTodosLosCarritos;
    //PRODUCTO
    private ProductoActualizarView actualizarProducto;
    private AnadirProductosView anadirProducto;
    private ProductoEliminarView eliminarProducto;
    private ProductoListarView listarProducto;
    private ListarProductosPorCodigoView listarProductoPorCodigo;
    //USUARIO
    private CrearUsuarioView crearUsuario;
    private EditarUsuarioView editarUsuario;
    private ListarUsuariosView listarUsuarios;
    private MiPaginaView miPagina;
    //LOGIN
    private LoginView loginView;
    private RegistrarseView registrarseView;
    private PreguntasView preguntasView;
    private OlvideContrasenaView olvideContrasenaView;
    //PREGUNTAS
    private PreguntasUView preguntasUView;
    //CARRITO
    private CarritoAnadirView carritoAnadir;
    private CarritoListarView carritoListar;
    private ListarMisCarritos listarMisCarritos;
    private VerDetalleView verDetalle;
    private ListarTodosLosCarritosView listarTodosLosCarritos;



    //el modelo es el que hace los metodos. en controlador solo dice que en el view se usen ciertos metodos

    public Principal() {
        Locale defaultLocale = Locale.getDefault();
        mensajeInternacionalizacionHandler = new MensajeInternacionalizacionHandler(defaultLocale.getLanguage(), defaultLocale.getCountry());


        menuBar = new JMenuBar();
        menuIdioma = new JMenu(mensajeInternacionalizacionHandler.get("menu.Idioma"));
        menuProducto = new JMenu(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuUsuario = new JMenu(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuCuenta = new JMenu(mensajeInternacionalizacionHandler.get("menu.cuenta"));//----

        menuItemListarProductos = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ListarProductos"));
        menuItemCrear  = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.CrearProducto"));
        menuItemEliminar = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.EliminarProducto"));
        menuItemActualizar = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ActualizarProducto"));
        menuItemListarCodigo = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ListarCodigo"));

        menuItemCarrito  = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.GestionDeCarrito"));


        menuItemListarTodosUsuarios   = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarTodos"));
        menuItemCrearUsuario = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.CrearUsuario"));
        menuItemListarTodosLosCarritos= new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarTodosLosCarritos"));

        menuItemMiPagina  = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.miPagina"));
        menuItemMisCarritos    = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.misCarritos"));

        menuIdiomaAleman = new JMenuItem();
        menuIdiomaEspanol = new JMenuItem();
        menuIdiomaIngles = new JMenuItem();

        menuSalir = new JMenu(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuItemSalir = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ItemSalir"));


        menuSalir.add(menuItemSalir);
        menuProducto.add(menuItemListarProductos);
        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);
        menuProducto.add(menuItemCarrito);
        menuProducto.add(menuItemListarCodigo);

        menuUsuario.add(menuItemListarTodosUsuarios);
        menuUsuario.add(menuItemCrearUsuario);
        menuUsuario.add(menuItemListarTodosLosCarritos);


        menuCuenta.add(menuItemMiPagina);
        menuCuenta.add(menuItemMisCarritos);

        menuIdioma.add(menuIdiomaAleman);
        menuIdioma.add(menuIdiomaEspanol);
        menuIdioma.add(menuIdiomaIngles);


        menuBar.add(menuProducto);
        menuBar.add(menuUsuario);
        menuBar.add(menuCuenta);
        menuBar.add(menuIdioma);
        menuBar.add(menuSalir);


        cargarIconosIdiomas();

        super.setJMenuBar(menuBar);

        desktopPanel = new MiJDesktopPane();
        setContentPane(desktopPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setTitle("Menu De Carrito De Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);



    }

    private void cargarIconosIdiomas() {
        ponerIcono(menuIdiomaEspanol, "imagenes/bandera.png");
        ponerIcono(menuIdiomaIngles, "imagenes/USA_Flag.png");
        ponerIcono(menuIdiomaAleman, "imagenes/alemania.png");
    }

    private void ponerIcono(JMenuItem item, String ruta) {
        URL url = Principal.class.getClassLoader().getResource(ruta);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image imgEscalada = original.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            item.setIcon(new ImageIcon(imgEscalada));
        } else {
            System.err.println("No se pudo cargar el ícono de " + ruta);
        }
    }
    //PRODUCTO
    public void setActualizarProducto(ProductoActualizarView vista) { this.actualizarProducto = vista; }
    public void setAnadirProducto(AnadirProductosView vista) { this.anadirProducto = vista; }
    public void setEliminarProducto(ProductoEliminarView vista) { this.eliminarProducto = vista; }
    public void setListarProducto(ProductoListarView vista) { this.listarProducto = vista; }
    public void setListarProductoPorCodigo(ListarProductosPorCodigoView vista) { this.listarProductoPorCodigo = vista; }
    //USUARIO
    public void setCrearUsuario(CrearUsuarioView vista) { this.crearUsuario = vista; }
    public void setEditarUsuario(EditarUsuarioView vista) { this.editarUsuario = vista; }
    public void setListarUsuarios(ListarUsuariosView vista) { this.listarUsuarios = vista; }
    public void setMiPagina(MiPaginaView vista) { this.miPagina = vista; }
    //LOGIN
    public void setLoginView(LoginView vista) { this.loginView = vista; }
    public void setRegistrarseView(RegistrarseView vista) { this.registrarseView = vista; }
    public void setPreguntasView(PreguntasView vista) { this.preguntasView = vista; }
    public void setOlvideContrasenaView(OlvideContrasenaView vista) { this.olvideContrasenaView = vista; }
    //CREGUNTAS
    public void setPreguntasUView(PreguntasUView vista) { this.preguntasUView = vista; }
    //CARRITO
    public void setCarritoAnadir(CarritoAnadirView vista) { this.carritoAnadir = vista; }
    public void setCarritoListar(CarritoListarView vista) { this.carritoListar = vista; }
    public void setListarMisCarritos(ListarMisCarritos vista) {
        this.listarMisCarritos = vista;
        vista.actualizarTexto(mensajeInternacionalizacionHandler); // Se actualiza apenas se asigna
    }
    public void setVerDetalle(VerDetalleView vista) { this.verDetalle = vista; }
    public void setListarTodosLosCarritos(ListarTodosLosCarritosView vista) { this.listarTodosLosCarritos = vista; }

    public void cambiarIdioma(String lenguaje, String pais){
        Locale locale = new Locale(lenguaje, pais);
        Locale.setDefault(locale);
        mensajeInternacionalizacionHandler.setLanguage(lenguaje, pais);

        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);


        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuItemCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCuenta.setText(mensajeInternacionalizacionHandler.get("menu.cuenta"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.idioma"));
        menuUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuItemMiPagina.setText(mensajeInternacionalizacionHandler.get("menu.miPagina"));
        menuItemMisCarritos.setText(mensajeInternacionalizacionHandler.get("menu.misCarritos"));

//        menuIdiomaIngles.setText(mensajeInternacionalizacionHandler.get("menu.en"));
//        menuIdiomaEspanol.setText(mensajeInternacionalizacionHandler.get("menu.es"));
//        menuIdiomaAleman.setText(mensajeInternacionalizacionHandler.get("menu.de"));

        menuItemCrearUsuario.setText(mensajeInternacionalizacionHandler.get("menu.crearUsuario"));
        menuItemListarTodosUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.listarTodosLosUsarios"));
        menuItemListarTodosLosCarritos.setText(mensajeInternacionalizacionHandler.get("menu.listarTodosLosCarritos"));


        menuItemListarProductos.setText(mensajeInternacionalizacionHandler.get("menu.listar"));
        menuItemCrear.setText(mensajeInternacionalizacionHandler.get("menu.crear"));
        menuItemEliminar.setText(mensajeInternacionalizacionHandler.get("menu.eliminar"));
        menuItemActualizar.setText(mensajeInternacionalizacionHandler.get("menu.actualizar"));
        menuItemListarCodigo.setText(mensajeInternacionalizacionHandler.get("menu.listarCodigo"));

        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.Idioma"));
        menuItemCrearUsuario.setText(mensajeInternacionalizacionHandler.get("menu.crearUsuario"));
        menuItemListarTodosLosCarritos.setText(mensajeInternacionalizacionHandler.get("menu.listarTodosLosCarritos"));
        actualizarProducto.actualizarTexto(mensajeInternacionalizacionHandler);

        if (actualizarProducto != null) actualizarProducto.actualizarTexto(mensajeInternacionalizacionHandler);
        if (anadirProducto != null) anadirProducto.actualizarTexto(mensajeInternacionalizacionHandler);
        if (eliminarProducto != null) eliminarProducto.actualizarTexto(mensajeInternacionalizacionHandler);
        if (listarProducto != null) listarProducto.actualizarTexto(mensajeInternacionalizacionHandler);
        if (listarProductoPorCodigo != null) listarProductoPorCodigo.actualizarTexto(mensajeInternacionalizacionHandler);

        if (crearUsuario != null) crearUsuario.actualizarTexto(mensajeInternacionalizacionHandler);
        if (editarUsuario != null) editarUsuario.actualizarTexto(mensajeInternacionalizacionHandler);
        if (listarUsuarios != null) listarUsuarios.actualizarTexto(mensajeInternacionalizacionHandler);
        if (miPagina != null) miPagina.actualizarTexto(mensajeInternacionalizacionHandler);

        if (preguntasView != null) preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);
        if (preguntasUView != null) preguntasUView.actualizarTexto(mensajeInternacionalizacionHandler);
        if (registrarseView != null) registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);
        if (olvideContrasenaView != null) olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);

        if (carritoAnadir != null) carritoAnadir.actualizarTexto(mensajeInternacionalizacionHandler);
        if (carritoListar != null) carritoListar.actualizarTexto(mensajeInternacionalizacionHandler);
        if (listarMisCarritos != null) listarMisCarritos.actualizarTexto(mensajeInternacionalizacionHandler);
        if (verDetalle != null) verDetalle.actualizarTexto(mensajeInternacionalizacionHandler);
        if (listarTodosLosCarritos != null) {
            listarTodosLosCarritos.actualizarTexto(mensajeInternacionalizacionHandler);
            listarTodosLosCarritos.actualizarColumnas(mensajeInternacionalizacionHandler);
        }
    }


    public JMenuItem getMenuItemListarProductos() {
        return menuItemListarProductos;
    }

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public JMenuItem getMenuItemListarPorRol() {
        return menuItemListarPorRol;
    }

    public JMenuItem getMenuItemListarUsuarios() {
        return menuItemListarUsuarios;
    }

    public JMenuItem getMenuItemListarTodosUsuarios() {
        return menuItemListarTodosUsuarios;
    }

    public JMenuItem getMenuItemListarAdministradores() {
        return menuItemListarAdministradores;
    }

    public JMenuItem getMenuItemListarTodosLosCarritos() {return menuItemListarTodosLosCarritos;}

    public JMenuItem getMenuItemCarrito() {
        return menuItemCarrito;
    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public MiJDesktopPane getDesktopPanel() { return desktopPanel;}

    public JMenuItem getMenuItemCrear() {
        return menuItemCrear;
    }

    public JMenuItem getMenuItemEliminar() {
        return menuItemEliminar;
    }

    public JMenuItem getMenuItemActualizar() {
        return menuItemActualizar;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }

    public JMenu getMenuUsuario() {
        return menuUsuario;
    }

    public JMenuItem getMenuCuenta() {
        return menuCuenta;
    }

    public JMenuItem getMenuItemCrearUsuario() {return menuItemCrearUsuario;}

    public JMenuItem getMenuItemMiPagina() {
        return menuItemMiPagina;
    }

    public JMenuItem getMenuItemMisCarritos() {
        return menuItemMisCarritos;
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() {return mensajeInternacionalizacionHandler;}

    public JMenu getMenuIdioma() {
        return menuIdioma;
    }

    public JMenuItem getMenuIdiomaEspanol() {
        return menuIdiomaEspanol;
    }

    public JMenuItem getMenuIdiomaAleman() {
        return menuIdiomaAleman;
    }

    public JMenuItem getMenuIdiomaIngles() {
        return menuIdiomaIngles;
    }

    public JMenu getMenuSalir() {
        return menuSalir;
    }

    public JMenuItem getMenuItemSalir() {
        return menuItemSalir;
    }

    public JMenuItem getMenuItemListarCodigo() {return menuItemListarCodigo;}

    public void deshabilitarMenuAdministrador() {
        menuItemCrear.setEnabled(false);
        menuItemActualizar.setEnabled(false);
        menuItemEliminar.setEnabled(false);
        menuItemListarCodigo.setEnabled(false);
        menuItemListarTodosLosCarritos.setEnabled(false);
        menuItemListarTodosUsuarios.setEnabled(false);
        menuItemCrearUsuario.setEnabled(false);

    }

}

