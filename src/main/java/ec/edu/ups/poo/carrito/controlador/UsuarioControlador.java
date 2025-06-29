package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
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
    private Rol rol;

    public UsuarioControlador(Usuario usuario, CarritoDAO carritoDAO, MiPaginaView miPaginaView, ListarMisCarritos listarView, VerDetalleView verDetalleView, ListarUsuariosView listaUsuariosView, CrearUsuarioView crearUsuarioView, EditarUsuarioView editarUsuarioView) {
        this.usuario  = usuario;
        this.carritoDAO = carritoDAO;
        this.miPaginaView = miPaginaView;
        this.listarView = listarView;
        this.verDetalleView  = verDetalleView;
        this.listaUsuariosView = listaUsuariosView;
        this.crearUsuarioView = crearUsuarioView;
        this.editarUsuarioView = editarUsuarioView;

        listeners();
    }

    private void listeners() {
        miPaginaView.getBtnActualizarDatos().addActionListener(e -> actualizarDatosMet(e));
        miPaginaView.getBtnCerrarSesion().addActionListener(e -> miPaginaView.dispose());

        editarUsuarioView.getBtnGuardar().addActionListener(e ->  editarUsuario());
        listaUsuariosView.getBtnListar().addActionListener(e -> listarTodosUsuarios());
        listaUsuariosView.getBtnListarRol().addActionListener(e -> listarUsuariosPorRol(rol));
        listaUsuariosView.getBtnElininar().addActionListener(e -> eliminarUsuarioSeleccionado());
        crearUsuarioView.getBtnGuardar().addActionListener(e -> crearUsuario());

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






    private void listarTodosUsuarios() {
        List<Usuario> todos = usuarioDAO.listarTodos();
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : todos) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        showInternalFrame(listaUsuariosView);
    }

    private void listarUsuariosPorRol(Rol rol) {
        List<Usuario> filtrados = usuarioDAO.listarPorRol(rol);
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : filtrados) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        showInternalFrame(listaUsuariosView);
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

    private void eliminarUsuarioSeleccionado() {
        // 1) Obtener la fila seleccionada
        int fila = listaUsuariosView.getTblUsuarios().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(listaUsuariosView, "Por favor selecciona primero un usuario de la tabla", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2) Extraer el username de la primera columna
        String username = (String) listaUsuariosView.getTblUsuarios()
                .getValueAt(fila, 0);

        // 3) Confirmar antes de borrar
        int opcion = JOptionPane.showConfirmDialog(listaUsuariosView, "¿Estás seguro de eliminar al usuario \"" + username + "\"?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        usuarioDAO.eliminar(username);

        DefaultTableModel modelo = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();modelo.removeRow(fila);

        JOptionPane.showMessageDialog(listaUsuariosView, "Usuario \"" + username + "\" eliminado con éxito", "Eliminación realizada", JOptionPane.INFORMATION_MESSAGE);
    }



    private void showInternalFrame(JInternalFrame f) {
        if (f.isClosed()) {
            miPaginaView.getDesktopPane().add(f);
        }
        f.setVisible(true);
        try { f.setSelected(true); } catch(PropertyVetoException ignore){}
    }
}
