package ec.edu.ups.poo.carrito.view;

import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;

import javax.swing.*;

public class Principal extends JFrame {
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;
    private JMenu menuIdioma;
    private JMenuItem menuIdiomaEspanol;
    private JMenuItem menuIdiomaAleman;
    private JMenuItem menuIdiomaIngles;
    private JMenu menuSalir;
    private JMenuItem menuItemSalir;
    private JPanel panelPrincipal;
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuItemCrear;
    private JMenuItem menuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem menuItemAnadir;
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


    //el modelo es el que hace los metodos. en controlador solo dice que en el view se usen ciertos metodos

    public Principal() {
        mensajeInternacionalizacionHandler= new MensajeInternacionalizacionHandler("en", "US");
        mensajeInternacionalizacionHandler= new MensajeInternacionalizacionHandler("es", "EC");
        mensajeInternacionalizacionHandler= new MensajeInternacionalizacionHandler("de", "DE");

        menuBar = new JMenuBar();
        menuIdioma = new JMenu(mensajeInternacionalizacionHandler.get("menu.Idioma"));
        menuProducto = new JMenu(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuUsuario = new JMenu(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuCuenta = new JMenu(mensajeInternacionalizacionHandler.get("menu.cuenta"));//----

        menuItemListarProductos = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ListarProductos"));
        menuItemCrear    = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.CrearProducto"));
        menuItemEliminar = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.EliminarProducto"));
        menuItemActualizar = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ActualizarProducto"));
        menuItemAnadir   = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.AnadirProducto"));
        menuItemCarrito  = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.GestionDeCarrito"));


        menuItemListarTodosUsuarios   = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarTodos"));
        menuItemListarAdministradores = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarAdministradores"));
        menuItemListarUsuarios        = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarUsuarios"));
        menuItemListarPorRol          = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.listarPorRol"));

        menuItemMiPagina          = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.miPagina"));
        menuItemMisCarritos    = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.misCarritos"));

        menuIdiomaAleman = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.en"));
        menuIdiomaEspanol = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.es"));
        menuIdiomaIngles = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.de"));

        menuSalir = new JMenu(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuItemSalir = new JMenuItem(mensajeInternacionalizacionHandler.get("menu.ItemSalir"));


        menuSalir.add(menuItemSalir);
        menuProducto.add(menuItemListarProductos);
        menuProducto.add(menuItemCrear);
        menuProducto.add(menuItemEliminar);
        menuProducto.add(menuItemActualizar);
        menuProducto.add(menuItemCarrito);

        menuUsuario.add(menuItemListarTodosUsuarios);
        menuUsuario.add(menuItemListarAdministradores);
        menuUsuario.add(menuItemListarUsuarios);
        menuUsuario.add(menuItemListarPorRol);

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
    public void cambiarIdioma(String lenguaje, String pais){
        mensajeInternacionalizacionHandler.setLanguage(lenguaje, pais);

        setTitle(mensajeInternacionalizacionHandler.get("app.titulo"));

        menuItemCarrito.setText(mensajeInternacionalizacionHandler.get("menu.carrito"));
        menuProducto.setText(mensajeInternacionalizacionHandler.get("menu.producto"));
        menuCuenta.setText(mensajeInternacionalizacionHandler.get("menu.cuenta"));
        menuSalir.setText(mensajeInternacionalizacionHandler.get("menu.salir"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.idioma"));
        menuUsuario.setText(mensajeInternacionalizacionHandler.get("menu.usuario"));
        menuItemMiPagina.setText(mensajeInternacionalizacionHandler.get("menu.miPagina"));
        menuItemMisCarritos.setText(mensajeInternacionalizacionHandler.get("menu.misCarritos"));

        menuIdiomaIngles.setText(mensajeInternacionalizacionHandler.get("menu.en"));
        menuIdiomaEspanol.setText(mensajeInternacionalizacionHandler.get("menu.es"));
        menuIdiomaAleman.setText(mensajeInternacionalizacionHandler.get("menu.de"));

        menuItemListarTodosUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.listarTodosLosUsarios"));
        menuItemListarAdministradores.setText(mensajeInternacionalizacionHandler.get("menu.listarAdministradores"));
        menuItemListarUsuarios.setText(mensajeInternacionalizacionHandler.get("menu.listarUsuarios"));
        menuItemListarPorRol.setText(mensajeInternacionalizacionHandler.get("menu.listarPorRol"));

        menuItemListarProductos.setText(mensajeInternacionalizacionHandler.get("menu.listar"));
        menuItemCrear.setText(mensajeInternacionalizacionHandler.get("menu.crear"));
        menuItemEliminar.setText(mensajeInternacionalizacionHandler.get("menu.eliminar"));
        menuItemActualizar.setText(mensajeInternacionalizacionHandler.get("menu.actualizar"));
        menuItemAnadir.setText(mensajeInternacionalizacionHandler.get("menu.anadir"));
        menuIdioma.setText(mensajeInternacionalizacionHandler.get("menu.Idioma"));

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

    public JMenuItem getMenuItemAnadir() {
        return menuItemAnadir;
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

    public JMenuItem getMenuItemMiPagina() {
        return menuItemMiPagina;
    }

    public JMenuItem getMenuItemMisCarritos() {
        return menuItemMisCarritos;
    }

    public MensajeInternacionalizacionHandler getMensajeInternacionalizacionHandler() {
        return mensajeInternacionalizacionHandler;
    }

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


    public void deshabilitarMenuAdministrador() {
        menuItemCrear.setEnabled(false);
        menuItemActualizar.setEnabled(false);
        menuItemEliminar.setEnabled(false);


    }




}

