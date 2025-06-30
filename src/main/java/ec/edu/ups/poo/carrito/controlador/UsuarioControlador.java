package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.*;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.carrito.ListarTodosLosCarritosView;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsuarioControlador {
    private final ListarTodosLosCarritosView listarTodosCarritosView;
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
    private FormatosUtils formatosUtils;


    public UsuarioControlador(Usuario usuario, CarritoDAO carritoDAO,UsuarioDAO usuarioDAO, MiPaginaView miPaginaView, ListarMisCarritos listarView, VerDetalleView verDetalleView, ListarUsuariosView listaUsuariosView, CrearUsuarioView crearUsuarioView, EditarUsuarioView editarUsuarioView, Principal principal, ListarTodosLosCarritosView listarTodosCarritosView) {
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
        this.listarTodosCarritosView = listarTodosCarritosView;

        refrescarMisCarritos();
        listeners();

    }

    private void listeners() {
        miPaginaView.getBtnActualizarDatos().addActionListener(e -> actualizarDatosMet(e));
        miPaginaView.getBtnCerrarSesion().addActionListener(e -> miPaginaView.dispose());

        editarUsuarioView.getBtnGuardar().addActionListener(e ->  editarUsuario());

        listaUsuariosView.getBtnListar().addActionListener(e -> listarTodos());
        listaUsuariosView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorNombre());
        listaUsuariosView.getCbxRol().addActionListener(e -> listarPorRol());
        listaUsuariosView.getBtnElininar().addActionListener(e -> eliminarUsuarioSeleccionado());

        crearUsuarioView.getBtnGuardar().addActionListener(e -> crearUsuario());
        crearUsuarioView.getCbxRol().setModel(new DefaultComboBoxModel<>(Rol.values()));
        crearUsuarioView.getBtnSalir().addActionListener(e -> crearUsuarioView.dispose());

        listarView.getBtnRefrescar().addActionListener(e -> refrescarMisCarritos());
        listarView.getBtnVerDetalles().addActionListener(e -> verDetallesDesde(listarView.getTblCarritos(), listarView.getDesktopPane()));
        listarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        listarTodosCarritosView.getBtnVerDetalles().addActionListener(e -> verDetallesDesde(listarTodosCarritosView.getTblUsuarios(), listarTodosCarritosView.getDesktopPane()));

    }

    private void refrescarMisCarritos() {
        DefaultTableModel m = (DefaultTableModel) listarView.getTblCarritos().getModel();
        m.setRowCount(0);

        List<Carrito> todos = carritoDAO.listarTodos();
        for (Carrito c : todos) {
            if (c.getUsuario().equals(usuario)) {
                m.addRow(new Object[]{
                        c.getCodigo(), formatosUtils.formatearFecha(c.getFechaCreacion().getTime(), Locale.getDefault()), c.calcularSubtotal(), c.calcularIVA(), c.calcularTotal()
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

//
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
        String pass  = new String(crearUsuarioView.getPwdContrasenaNueva().getPassword()).trim();
        Rol rol  = (Rol) crearUsuarioView.getCbxRol().getSelectedItem();

        if (username.isEmpty() || pass.isEmpty()) {
            crearUsuarioView.mostrarMensaje("Completa los campos", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (usuarioDAO.buscarPorUsername(username) != null) {
            crearUsuarioView.mostrarMensaje("Ya existe ese usuario", "Atención", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Usuario nuevo = new Usuario(username, pass, rol);
        usuarioDAO.crear(nuevo);

        crearUsuarioView.getTxtUsuarioNuevo().setText("");
        crearUsuarioView.getPwdContrasenaNueva().setText("");
        crearUsuarioView.getCbxRol().setSelectedIndex(0);
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
            int codigo = (int) listarView.getTblCarritos().getValueAt(row, 0);
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
    private void verDetallesDesde(JTable tabla, JDesktopPane contenedor) {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(contenedor, "Selecciona un carrito primero", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) tabla.getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c == null) {
            JOptionPane.showMessageDialog(contenedor, "No se encontró el carrito", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultTableModel dm = (DefaultTableModel) verDetalleView.getTblProductos().getModel();
        dm.setRowCount(0);
        for (ItemCarrito it : c.obtenerItems()) {
            dm.addRow(new Object[]{
                    it.getProducto().getCodigo(), it.getProducto().getNombre(),
                    it.getCantidad(), formatosUtils.formatearMoneda(it.getSubtotal(), Locale.getDefault())
            });
        }

        verDetalleView.getTxtSubtotal().setText(FormatosUtils.formatearMoneda( c.calcularSubtotal(), Locale.getDefault()));
        verDetalleView.getTxtIVA().setText(FormatosUtils.formatearMoneda( c.calcularIVA(), Locale.getDefault()));
        verDetalleView.getTxtTotal().setText(FormatosUtils.formatearMoneda( c.calcularTotal(), Locale.getDefault()));

        if (!verDetalleView.isShowing()) {
            contenedor.add(verDetalleView);
        }
        verDetalleView.setVisible(true);
        try {
            verDetalleView.setSelected(true);
        } catch (PropertyVetoException ignore) {}
    }


    public void buscarUsuarioPorNombre() {
        String txt = listaUsuariosView.getTxtBuscar().getText().trim();
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);

        Usuario u = usuarioDAO.buscarPorUsername(txt);
        if (u != null) {
            m.addRow(new Object[]{ u.getUsername(), u.getRol() });
        } else {
            JOptionPane.showMessageDialog(listaUsuariosView, "Usuario \"" + txt + "\" no encontrado", "Atención", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void mostrarTodosLosCarritos() {
        DefaultTableModel m = listarTodosCarritosView.getModelo();
        m.setRowCount(0);

        for (Carrito c : carritoDAO.listarTodos()) {
            m.addRow(new Object[]{
                    c.getCodigo(),
                    FormatosUtils.formatearFecha(c.getFechaCreacion().getTime(), Locale.getDefault()),
                    c.getUsuario().getUsername()
            });
        }



    }

}
