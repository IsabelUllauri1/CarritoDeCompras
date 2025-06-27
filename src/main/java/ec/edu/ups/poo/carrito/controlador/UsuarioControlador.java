package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.ProductoDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {
    private final UsuarioDAO usuarioDAO;
    private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO;
    private  Usuario usuario;
    private final LoginView loginView;
    private Principal principal;
    private CarritoListarView carritoListarView;
    private final VerDetalleView verDetalleView;
    private final MiPaginaView miPaginaView;
    private final ListarMisCarritos listarMisCarritos;



        public UsuarioControlador(UsuarioDAO usuarioDAO, ProductoDAO productoDAO, CarritoDAO carritoDAO, LoginView loginView, MiPaginaView miPaginaView, VerDetalleView verDetalleView, ListarMisCarritos listar ) {
        this.usuarioDAO = usuarioDAO;
        this.productoDAO= productoDAO;
        this.carritoDAO = carritoDAO;
        this.loginView = loginView;
        this.usuario = null;
        this.verDetalleView = verDetalleView;
        this.miPaginaView = miPaginaView;
        this.listarMisCarritos = listar;
        loginListeners();
    }
    private void loginListeners() {
        loginView.getBtnIniciarSesion().addActionListener(e -> autenticar());
        loginView.getBtnRegistrarse().addActionListener(e -> registrarNuevoUsuario());
    }
    private void autenticar() {

        String username = loginView.getTxtUsername().getText();
        String contrasena = loginView.getTxtContrasena().getText();

        usuario=usuarioDAO.autenticar(username, contrasena);
        if(usuario==null){
            loginView.mostrarMensaje("Usuario o contrasenia incorreto");
            return;
        }
        loginView.dispose();
        this.principal = new Principal();

        //idiomas
        principal.getMenuIdiomaIngles().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principal.cambiarIdioma("en", "US");
            }
        });
        principal.getMenuIdiomaEspanol().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principal.cambiarIdioma("es", "EC");
            }
        });
        principal.getMenuIdiomaAleman().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                principal.cambiarIdioma("de", "DE");
            }
        });



        //paginas usuario
        principal.getDesktopPanel().add(miPaginaView);
        principal.getDesktopPanel().add(listarMisCarritos);
        principal.getDesktopPanel().add(verDetalleView);


        try { miPaginaView.setSelected(true); } catch(PropertyVetoException ignore){}
        ListenersCliente();
        MenuCliente();

        boolean esAdmin = usuario.getRol() == Rol.ADMINISTRADOR;



        //listar
        principal.getMenuItemListarTodosUsuarios().setEnabled(esAdmin);
        principal.getMenuItemListarAdministradores().setEnabled(esAdmin);
        principal.getMenuItemListarUsuarios().setEnabled(esAdmin);
        principal.getMenuItemListarPorRol().setEnabled(esAdmin);
        principal.getMenuUsuario().setEnabled(esAdmin);
        //listar
        principal.getMenuItemListar().setVisible(esAdmin);
        principal.getMenuItemCrear().setVisible(esAdmin);
        principal.getMenuItemEliminar().setVisible(esAdmin);
        principal.getMenuItemActualizar().setVisible(esAdmin);
        principal.getMenuItemAnadir().setVisible(esAdmin);

        principal.getMenuItemCarrito().setEnabled(true);
        principal.getMenuItemMiPagina().setEnabled(true);
        principal.getMenuCuenta().setEnabled(true);
        principal.getMenuUsuario().setEnabled(true);
        principal.getMenuItemMisCarritos().setEnabled(true);

        //carritodecompras
        JMenuItem mCar = principal.getMenuItemCarrito();
        mCar.setVisible(true);
        mCar.addActionListener(e -> {

            CarritoAnadirView carritoAnadirView = new CarritoAnadirView();
            CarritoListarView cListarV = new CarritoListarView();
            principal.getDesktopPanel().add(carritoAnadirView);
            principal.getDesktopPanel().add(cListarV);
            new CarritoControlador(productoDAO, carritoDAO, carritoAnadirView, cListarV, usuario);

            carritoAnadirView.setVisible(true);
            try { carritoAnadirView.setSelected(true); } catch (PropertyVetoException ignore) {}
        });
        principal.getMenuItemMisCarritos().addActionListener(e -> {
            if (!miPaginaView.isVisible()) {
                principal.getDesktopPanel().add(miPaginaView);
            }
            // 2) la mostramos y la traemos al frente
            miPaginaView.setVisible(true);
            miPaginaView.toFront();
            try {
                miPaginaView.setSelected(true);
            } catch (PropertyVetoException ignore) {
            }
        });
        principal.getMenuItemSalir().addActionListener(e -> {
            principal.dispose();
            loginView.getTxtContrasena().setText("");
            loginView.getTxtUsername().setText("");
            loginView.setVisible(true);
        });

        if (esAdmin) {
            //productos
            AnadirProductosView  prodAdd  = new AnadirProductosView();
            ProductoListarView   prodList = new ProductoListarView();
            principal.getDesktopPanel().add(prodAdd);
            principal.getDesktopPanel().add(prodList);
            new ProductoControlador(productoDAO, principal, prodAdd, prodList);
            //carrito


            UsuarioView userView = new UsuarioView();
            principal.getDesktopPanel().add(userView);



            principal.getMenuItemListarTodosUsuarios().addActionListener(ev -> {
                listarUsuarios(usuarioDAO.listarTodos());
            });


            principal.getMenuItemListarAdministradores().addActionListener(ev -> {
                listarUsuarios(usuarioDAO.listarAdministradores());
            });


            principal.getMenuItemListarUsuarios().addActionListener(ev -> {
                listarUsuarios(usuarioDAO.listarUsuarios());
            });


            principal.getMenuItemListarPorRol().addActionListener(ev -> {
                String rolStr = JOptionPane.showInputDialog(principal,
                        "Ingrese rol (ADMINISTRADOR/USUARIO):");
                try {
                    Rol r = Rol.valueOf(rolStr.toUpperCase());
                    listarUsuarios(usuarioDAO.listarPorRol(r));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(principal, "Rol inválido");
                }
            });
        }


        principal.setVisible(true);
        try { miPaginaView.setSelected(true); } catch(PropertyVetoException ignore){}
    }

    private void MenuCliente() {
        principal.getMenuItemMiPagina().addActionListener(e -> {

            miPaginaView.setVisible(true);
            miPaginaView.toFront();
            try { miPaginaView.setSelected(true); } catch(PropertyVetoException ign){}
        });

        principal.getMenuItemMisCarritos().addActionListener(e -> {
            miPaginaView.getBtnListarCarritos().doClick();

            // ahora simplemente muestra la ventana
            if (!listarMisCarritos.isShowing()) {
                principal.getDesktopPanel().add(listarMisCarritos);
            }
            listarMisCarritos.setVisible(true);
            listarMisCarritos.toFront();
            try { listarMisCarritos.setSelected(true); } catch(PropertyVetoException ignore){}

        });
    }

    private void ListenersCliente() {


        miPaginaView.getBtnListarCarritos().addActionListener(e -> {
            //datos
            List<Carrito> lista = carritoDAO.listarTodos();
            List<Carrito> losMios = new ArrayList<>();
            for (Carrito c : lista) {
                if (c.getUsuario().equals(usuario)) {
                    losMios.add(c);
                }
            }
            //tabla con carritos
            DefaultTableModel m = (DefaultTableModel) listarMisCarritos.getTblCarritos().getModel();
            m.setRowCount(0);
            for (Carrito c : losMios) {
                m.addRow(new Object[]{
                        c.getCodigo(),
                        c.getFechaCreacion().getTime(),
                        c.calcularSubtotal(),
                        c.calcularIVA(),
                        c.calcularTotal()
                });
            }
            listarMisCarritos.setVisible(true);
            try { listarMisCarritos.setSelected(true); } catch(PropertyVetoException _){}
        });

        miPaginaView.getBtnActualizarDatos().addActionListener(e -> {
            String nuevoUser = miPaginaView.getTxtUsuario().getText().trim();
            String nuevaPass = new String(miPaginaView.getPwdContrasena().getPassword()).trim();

            if (nuevoUser.isEmpty() || nuevaPass.isEmpty()) {
                miPaginaView.mostrarMensaje("Por favor completa ambos campos");
                return;
            }

            // actualizo el objeto en memoria
            usuario.setUsername(nuevoUser);
            usuario.setContrasenia(nuevaPass);
            // persisto el cambio
            usuarioDAO.actualizar(usuario);

            miPaginaView.mostrarMensaje("Datos actualizados correctamente");
        });


        miPaginaView.getBtnCerrarSesion().addActionListener(e -> {
            principal.dispose();
            loginView.setVisible(true);
        });

        listarMisCarritos.getBtnVerDetalles().addActionListener(e -> {
            int row = listarMisCarritos.getTblCarritos().getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(listarMisCarritos,
                        "Selecciona un carrito primero", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo = (int) listarMisCarritos.getTblCarritos().getValueAt(row, 0);
            Carrito c = carritoDAO.buscarPorCodigo(codigo);
            if (c == null) {
                JOptionPane.showMessageDialog(listarMisCarritos,
                        "No se encontró el carrito", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //pone los productos la tabla
            DefaultTableModel dm = (DefaultTableModel) verDetalleView.getTblProductos().getModel();
            dm.setRowCount(0);
            c.obtenerItems().forEach(it -> dm.addRow(new Object[]{it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(), String.format("%.2f",it.getSubtotal() )
            }));

            verDetalleView.getTxtSubtotal().setText(String.format(" %.2f", c.calcularSubtotal())
            );
            verDetalleView.getTxtIVA().setText(String.format(" %.2f", c.calcularIVA())
            );
            verDetalleView.getTxtTotal().setText(String.format(" %.2f", c.calcularTotal())
            );

            if (verDetalleView.isClosed()) {
                principal.getDesktopPanel().add(verDetalleView);
            }
            verDetalleView.setVisible(true);
            verDetalleView.toFront();
            try { verDetalleView.setSelected(true); } catch(PropertyVetoException ignored){}

        }
        );

        listarMisCarritos.getBtnEliminar().addActionListener(e -> {
            int row = listarMisCarritos.getTblCarritos().getSelectedRow();
            if(row<0){
                JOptionPane.showMessageDialog(listarMisCarritos, "seleccione un carrito primero", "Atencion", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo = (int) listarMisCarritos.getTblCarritos().getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(listarMisCarritos,
                    "¿Eliminar carrito #" + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(codigo);
                //actualiza tabla
                DefaultTableModel m = (DefaultTableModel) listarMisCarritos.getTblCarritos().getModel();
                m.removeRow(row);
                JOptionPane.showMessageDialog(listarMisCarritos, "Carrito eliminado");
            }
        });

    }


    private void listarUsuarios(java.util.List<Usuario> lista) {
        UsuarioView uv = new UsuarioView();
        DefaultTableModel m = uv.getModelo();
        m.setRowCount(0);
        for (Usuario u : lista) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        principal.getDesktopPanel().add(uv);
        uv.setVisible(true);
    }
    private void registrarNuevoUsuario(){
        String username  = loginView.getTxtUsername().getText().trim();
        String password  = new String(loginView.getTxtContrasena().getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            loginView.mostrarMensaje("Por favor completa ambos campos");
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            loginView.mostrarMensaje("El nombre de usuario ya está en uso");
            return;
        }

        Usuario nuevo = new Usuario(username, password, Rol.USUARIO);
        usuarioDAO.crear(nuevo);

        loginView.mostrarMensaje("Usuario registrado con exito");
        loginView.getTxtUsername().setText("");
        loginView.getTxtContrasena().setText("");
    }







    public Usuario getUsuarioAutenticado() {
        return usuario;
    }

    private void listarTodos() {
        List<Usuario> list = usuarioDAO.listarTodos();
        mostrarUsuariosEnTabla(list);
    }

    private void listarPorRol(Rol rol) {
        List<Usuario> list = usuarioDAO.listarPorRol(rol);
        mostrarUsuariosEnTabla(list);
    }

    private void mostrarUsuariosEnTabla(List<Usuario> usuarios) {
        UsuarioView v = new UsuarioView();
        principal.getDesktopPanel().add(v);
        DefaultTableModel m = (DefaultTableModel) v.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarios) {
            m.addRow(new Object[]{
                    u.getUsername(), u.getRol()
            });
        }
        principal.getDesktopPanel().add(v);
        v.setVisible(true);
    }
}
