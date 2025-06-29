package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Carrito;
import ec.edu.ups.poo.carrito.modelo.ItemCarrito;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.usuario.CrearUsuarioView;
import ec.edu.ups.poo.carrito.view.usuario.EditarUsuarioView;
import ec.edu.ups.poo.carrito.view.usuario.ListarUsuariosView;
import ec.edu.ups.poo.carrito.view.carrito.ListarMisCarritos;
import ec.edu.ups.poo.carrito.view.usuario.MiPaginaView;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.List;

public class UsuarioControlador {
    private final Usuario usuario;
    private final CarritoDAO carritoDAO;
    private final MiPaginaView miPaginaView;
    private final ListarMisCarritos listarView;
    private final VerDetalleView verDetalleView;
    private UsuarioDAO usuarioDAO;
    private final ListarUsuariosView listaUsuariosView;
    private final CrearUsuarioView crearUsuarioView;
    private final EditarUsuarioView editarUsuarioView;
    private  Principal principal;
    private Rol rol;


    public UsuarioControlador(Usuario usuario, CarritoDAO carritoDAO,UsuarioDAO usuarioDAO, MiPaginaView miPaginaView, ListarMisCarritos listarView, VerDetalleView verDetalleView, ListarUsuariosView listaUsuariosView, CrearUsuarioView crearUsuarioView, EditarUsuarioView editarUsuarioView, Principal principal) {
        this.usuario  = usuario;
        this.carritoDAO = carritoDAO;
        this.usuarioDAO = usuarioDAO;
        this.miPaginaView = miPaginaView;
        this.listarView = listarView;
        this.verDetalleView  = verDetalleView;
        this.listaUsuariosView = listaUsuariosView;
        this.crearUsuarioView = crearUsuarioView;
        this.editarUsuarioView = editarUsuarioView;
        this.principal = principal;

        refrescarMisCarritos();
        listeners();

    }

    private void listeners() {
        miPaginaView.getBtnActualizarDatos().addActionListener(e -> actualizarDatosMet(e));
        miPaginaView.getBtnCerrarSesion().addActionListener(e -> miPaginaView.dispose());
       // miPaginaView.getBtnListarCarritos().addActionListener(e -> );

        editarUsuarioView.getBtnGuardar().addActionListener(e ->  editarUsuario());

        listaUsuariosView.getBtnListar().addActionListener(e -> listarTodos());

        // 2) Buscar por username parcial
        listaUsuariosView.getBtnBuscar().addActionListener(e -> {
            String txt = listaUsuariosView.getTxtBuscar().getText().trim();
            DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
            m.setRowCount(0);

            Usuario u = usuarioDAO.buscarPorUsername(txt);
            if (u != null) {
                m.addRow(new Object[]{ u.getUsername(), u.getRol() });
            } else {
                JOptionPane.showMessageDialog(listaUsuariosView,
                        "Usuario \"" + txt + "\" no encontrado",
                        "Atención",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // 3) Filtrar por rol
        listaUsuariosView.getCbxRol().addActionListener(e -> listarPorRol());

        // 4) Eliminar seleccionado
        listaUsuariosView.getBtnElininar().addActionListener(e -> eliminarUsuarioSeleccionado());

        crearUsuarioView.getBtnGuardar().addActionListener(e -> crearUsuario());

        listarView.getBtnRefrescar().addActionListener(e -> refrescarMisCarritos());
        listarView.getBtnVerDetalles().addActionListener(e -> verDetalles());
        listarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());

    }

    private void refrescarMisCarritos() {
        DefaultTableModel m = (DefaultTableModel) listarView.getTblCarritos().getModel();
        m.setRowCount(0);

        List<Carrito> todos = carritoDAO.listarTodos();
        for (Carrito c : todos) {
            if (c.getUsuario().equals(usuario)) {
                m.addRow(new Object[]{
                        c.getCodigo(), c.getFechaCreacion().getTime(), c.calcularSubtotal(), c.calcularIVA(), c.calcularTotal()
                });
            }
        }
    }


    private void actualizarDatosMet(ActionEvent e) {
        String nu = miPaginaView.getTxtUsuario().getText().trim();
        String np = new String(miPaginaView.getPwdContrasena().getPassword()).trim();
        if (nu.isEmpty() || np.isEmpty()) {
            miPaginaView.mostrarMensaje("Completa ambos campos");
            return;
        }
        usuario.setUsername(nu);
        usuario.setContrasenia(np);
        miPaginaView.mostrarMensaje("Datos actualizados");
    }


    private void listarTodos() {
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarioDAO.listarTodos()) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        mostrarInternal(listaUsuariosView);
    }

//    private void buscarPorUsername() {
//        String txt = listaUsuariosView.getTxtBuscar().getText().trim();
//        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
//        m.setRowCount(0);
//        if (txt.isEmpty()) {
//            listarTodos();
//            return;
//        }
//        List<Usuario> encontrados = usuarioDAO.buscarPorUsername(txt);
//        for (Usuario u : encontrados) {
//            m.addRow(new Object[]{u.getUsername(), u.getRol()});
//        }
//        mostrarInternal(listaUsuariosView);
//    }
    private void listarPorRol() {
        Rol rol = (Rol) listaUsuariosView.getCbxRol().getSelectedItem();
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarioDAO.listarPorRol(rol)) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        mostrarInternal(listaUsuariosView);
    }

    private void mostrarInternal(JInternalFrame f) {
        JDesktopPane dp = principal.getDesktopPanel();
        if (!dp.isAncestorOf(f)) {
            dp.add(f);
        }
        f.setVisible(true);
        try {
            f.setSelected(true);
        } catch (PropertyVetoException ignored) {}
    }

    private void crearUsuario() {
        String username = crearUsuarioView.getTxtUsuarioNuevo().getText().trim();
        String pass     = new String(crearUsuarioView.getPwdContrasenaNueva().getPassword()).trim();
        Rol    rol      = (Rol) crearUsuarioView.getCbxRol().getSelectedItem();
        if (username.isEmpty() || pass.isEmpty()) {
            crearUsuarioView.mostrarMensaje("Completa los campos", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (usuarioDAO.buscarPorUsername(username) != null) {
            crearUsuarioView.mostrarMensaje("Ya existe ese usuario", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        usuarioDAO.crear(new Usuario(username, pass, rol));
        crearUsuarioView.mostrarMensaje("Usuario creado", "Exito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarUsuario() {
        String username = editarUsuarioView.getTxtUsuario().getText().trim();
        Usuario u = usuarioDAO.buscarPorUsername(username);
        if (u == null) {
            editarUsuarioView.mostrarMensaje("Usuario no encontrado", "Atencion", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String nuevaPass = new String(editarUsuarioView.getPwdNContrasena().getPassword()).trim();
        Rol nuevoRol  = (Rol) editarUsuarioView.getCbxRol().getSelectedItem();
        if (!nuevaPass.isEmpty()) {
            u.setContrasenia(nuevaPass);
        }
        u.setRol(nuevoRol);
        usuarioDAO.actualizar(u);
        editarUsuarioView.mostrarMensaje("Usuario actualizado", "Atencion", JOptionPane.INFORMATION_MESSAGE);
        editarUsuarioView.dispose();
    }
    private void eliminarCarrito(){
        listarView.getBtnEliminar().addActionListener(e -> {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo = (int) listarView.getTblCarritos()
                    .getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(listarView, "¿Eliminar carrito #" + codigo + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(codigo);
                refrescarMisCarritos();
                JOptionPane.showMessageDialog(listarView, "Carrito eliminado", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void eliminarUsuarioSeleccionado() {
        int fila = listaUsuariosView.getTblUsuarios().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(listaUsuariosView, "Por favor selecciona primero un usuario de la tabla", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) listaUsuariosView.getTblUsuarios()
                .getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(listaUsuariosView, "¿Estás seguro de eliminar al usuario \"" + username + "\"?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        usuarioDAO.eliminar(username);

        DefaultTableModel modelo = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();modelo.removeRow(fila);

        JOptionPane.showMessageDialog(listaUsuariosView, "Usuario \"" + username + "\" eliminado con éxito", "Eliminación realizada", JOptionPane.INFORMATION_MESSAGE);
    }
    private void verDetalles() {
        int row = listarView.getTblCarritos().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(listarView, "Selecciona un carrito primero", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int codigo = (int) listarView.getTblCarritos()
                .getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c == null) {
            JOptionPane.showMessageDialog(listarView, "No se encontró el carrito", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel dm = (DefaultTableModel) verDetalleView
                .getTblProductos().getModel();
        dm.setRowCount(0);
        for (ItemCarrito it : c.obtenerItems()) {
            dm.addRow(new Object[]{
                    it.getProducto().getCodigo(), it.getProducto().getNombre(), it.getCantidad(), String.format("%.2f", it.getSubtotal())
            });
        }
        verDetalleView.getTxtSubtotal().setText(String.format("%.2f", c.calcularSubtotal()));
        verDetalleView.getTxtIVA().setText(String.format("%.2f", c.calcularIVA()));
        verDetalleView.getTxtTotal().setText(String.format("%.2f", c.calcularTotal()));

        if (!verDetalleView.isShowing()) {
            listarView.getDesktopPane().add(verDetalleView);
        }
        verDetalleView.setVisible(true);
        try { verDetalleView.setSelected(true); }
        catch(PropertyVetoException ignore){}
    }

    public void listarTodosUsuarios() {
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarioDAO.listarTodos()) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        if (!listaUsuariosView.isShowing()) {
            miPaginaView.getDesktopPane().add(listaUsuariosView);
        }
        listaUsuariosView.setVisible(true);
        try { listaUsuariosView.setSelected(true); }
        catch(PropertyVetoException ignore){}
    }

}
