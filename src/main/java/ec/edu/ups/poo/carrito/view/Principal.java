package ec.edu.ups.poo.carrito.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Principal extends JFrame {

    private JPanel panelPrincipal;
    private JMenuBar menuBar;
    private JMenu menuProducto;
    private JMenuItem menuItemCrear;
    private JMenuItem MenuItemEliminar;
    private JMenuItem menuItemActualizar;
    private JMenuItem MenuItemAnadir;
    private JDesktopPane desktopPanel;
    private JMenuItem menuItemListar;

    public Principal() {
        menuBar = new JMenuBar();
        menuProducto = new JMenu("Producto");

        menuItemListar = new JMenuItem("Listar");
        menuItemCrear = new JMenuItem("Crear Producto");
        MenuItemEliminar = new JMenuItem("Eliminar Producto");
        menuItemActualizar = new JMenuItem("Actualizar Producto");
        MenuItemAnadir = new JMenuItem("Anadir Producto");

        desktopPanel = new JDesktopPane();

        menuBar.add(menuProducto);
        menuProducto.add(menuItemListar);
        menuProducto.add(menuItemCrear);
        menuProducto.add(MenuItemEliminar);
        menuProducto.add(menuItemActualizar);
        menuProducto.add(MenuItemAnadir);
        menuProducto.add(menuItemListar);

        setJMenuBar(menuBar);
        setContentPane(desktopPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setTitle("Menu De Carrito De Compras");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);


    }

    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }

    public JDesktopPane getDesktopPanel() {
        return desktopPanel;
    }


    public JMenuItem getMenuItemCrear() {
        return menuItemCrear;
    }

    public JMenuItem getMenuItemEliminar() {
        return MenuItemEliminar;
    }

    public JMenuItem getMenuItemActualizar() {
        return menuItemActualizar;
    }

    public JMenuItem getMenuItemAnadir() {
        return MenuItemAnadir;
    }

    public JMenu getMenuProducto() {
        return menuProducto;
    }
    public JMenuItem getMenuItemListar() {
        return menuItemListar;
    }



    public static void main(String[] args) {
        new Principal();
    }



}

