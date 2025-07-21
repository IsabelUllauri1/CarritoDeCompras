package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.CarritoDAO;
import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.dao.PreguntaRespondidaDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.*;
import ec.edu.ups.poo.carrito.util.*;
import ec.edu.ups.poo.carrito.util.exception.CedulaInvalidaExeption;
import ec.edu.ups.poo.carrito.util.exception.ContrasenaInvalidaException;
import ec.edu.ups.poo.carrito.util.exception.CorreoInvalidoException;
import ec.edu.ups.poo.carrito.view.Principal;
import ec.edu.ups.poo.carrito.view.carrito.ListarTodosLosCarritosView;
import ec.edu.ups.poo.carrito.view.login.RegistrarseView;
import ec.edu.ups.poo.carrito.view.usuario.*;
import ec.edu.ups.poo.carrito.view.carrito.ListarMisCarritos;
import ec.edu.ups.poo.carrito.view.carrito.VerDetalleView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsuarioControlador {
    private final ListarTodosLosCarritosView listarTodosCarritosView;
    private final Usuario usuario;
    private final CarritoDAO carritoDAO;
    private final PreguntaDAO preguntaDAO;
    private final MiPaginaView miPaginaView;
    private final ListarMisCarritos listarView;
    private final VerDetalleView verDetalleView;
    private UsuarioDAO usuarioDAO;
    private final ListarUsuariosView listaUsuariosView;
    private final CrearUsuarioView crearUsuarioView;
    private final EditarUsuarioView editarUsuarioView;
    private  Principal principal;
    private ROL rol;
    private FormatosUtils formatosUtils;
    private RegistrarseView registrarseView;
    private PreguntasUView preguntasViewU;
    private MensajeInternacionalizacionHandler mh;
    private final PreguntaRespondidaDAO preguntaRespondidaDAO;


    /**
     * Inicializa el controlador de usuarios con los DAOs y vistas necesarias.
     *
     * @param usuario Usuario autenticado en sesión.
     * @param carritoDAO DAO de carritos.
     * @param usuarioDAO DAO de usuarios.
     * @param miPaginaView Vista del perfil del usuario.
     * @param listarView Vista para listar carritos del usuario.
     * @param verDetalleView Vista para ver detalles de un carrito.
     * @param listaUsuariosView Vista para listar usuarios (admin).
     * @param crearUsuarioView Vista para crear usuarios (admin).
     * @param editarUsuarioView Vista para editar usuarios (admin).
     * @param principal Ventana principal del sistema.
     * @param listarTodosCarritosView Vista que lista carritos de todos los usuarios (admin).
     * @param preguntasViewU Vista para responder/editar preguntas de seguridad.
     * @param preguntaDAO DAO de preguntas.
     * @param preguntaRespondidaDAO DAO de preguntas respondidas.
     */

    public UsuarioControlador(Usuario usuario, CarritoDAO carritoDAO, UsuarioDAO usuarioDAO, MiPaginaView miPaginaView, ListarMisCarritos listarView, VerDetalleView verDetalleView, ListarUsuariosView listaUsuariosView, CrearUsuarioView crearUsuarioView, EditarUsuarioView editarUsuarioView, Principal principal, ListarTodosLosCarritosView listarTodosCarritosView, PreguntasUView preguntasViewU,PreguntaDAO preguntaDAO, PreguntaRespondidaDAO preguntaRespondidaDAO) {
        this.usuario  = usuario;
        this.carritoDAO = carritoDAO;
        this.usuarioDAO = usuarioDAO;
        this.preguntaDAO = preguntaDAO;
        this.miPaginaView = miPaginaView;
        this.listarView = listarView;
        this.verDetalleView  = verDetalleView;
        this.listaUsuariosView = listaUsuariosView;
        this.crearUsuarioView = crearUsuarioView;
        this.editarUsuarioView = editarUsuarioView;
        this.preguntasViewU = preguntasViewU;
        this.principal = principal;
        this.listarTodosCarritosView = listarTodosCarritosView;
        this.preguntaRespondidaDAO = preguntaRespondidaDAO;


        refrescarMisCarritos();
        listeners();

    }
    /**
     * Configura los eventos para todas las vistas del usuario:
     * perfil, creación/edición/listado de usuarios, carritos y preguntas de seguridad.
     */

    private void listeners() {
        miPaginaView.getBtnActualizarDatos().addActionListener(e -> actualizarDatosMet(e));
        miPaginaView.getBtnCerrarSesion().addActionListener(e -> miPaginaView.dispose());
        miPaginaView.getBtnResponderPreguntas().addActionListener(e -> abrirPreguntasParaEditar());


        editarUsuarioView.getBtnGuardar().addActionListener(e ->  editarUsuario());

        listaUsuariosView.getBtnListar().addActionListener(e -> listarTodos());
        listaUsuariosView.getBtnBuscar().addActionListener(e -> buscarUsuarioPorNombre());
        listaUsuariosView.getCbxRol().addActionListener(e -> {
            if (!inicializandoComboRolFiltro && listaUsuariosView.isVisible()) {
                listarPorRol();
            }
        });
        listaUsuariosView.getBtnElininar().addActionListener(e -> eliminarUsuarioSeleccionado());

        crearUsuarioView.getBtnGuardar().addActionListener(e -> crearUsuario());
        crearUsuarioView.getCbxRol().setModel(new DefaultComboBoxModel<>(ROL.values()));
        crearUsuarioView.getBtnSalir().addActionListener(e -> crearUsuarioView.dispose());

        listarView.getBtnRefrescar().addActionListener(e -> refrescarMisCarritos());
        listarView.getBtnVerDetalles().addActionListener(e -> verDetallesDesde(listarView.getTblCarritos(), listarView.getDesktopPane()));
        listarView.getBtnEliminar().addActionListener(e -> eliminarCarrito());
        listarTodosCarritosView.getBtnVerDetalles().addActionListener(e -> verDetallesDesde(listarTodosCarritosView.getTblUsuarios(), listarTodosCarritosView.getDesktopPane()));

    }
    /**
     * Refresca la tabla de carritos del usuario autenticado en la vista correspondiente.
     */

    private void refrescarMisCarritos() {
        DefaultTableModel m = (DefaultTableModel) listarView.getTblCarritos().getModel();
        m.setRowCount(0);


        List<Carrito> misCarritos = carritoDAO.listarPorUsuario(usuario);


        for (Carrito c : misCarritos) {

            m.addRow(new Object[]{
                    c.getCodigo(),
                    formatosUtils.formatearFecha(c.getFechaCreacion().getTime(), Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularSubtotal(), Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularIVA(), Locale.getDefault()),
                    formatosUtils.formatearMoneda(c.calcularTotal(), Locale.getDefault())
            });
        }

    }

    /**
     * Carga los datos del usuario autenticado en el formulario de "Mi Página".
     */

    public void cargarDatosEnMiPagina() {
        miPaginaView.getTxtUsuario().setText(usuario.getUsername());
        miPaginaView.getPwdContrasena().setText(usuario.getContrasenia());
        miPaginaView.getTxtNombreCompleto().setText(usuario.getNombreCompleto());
        miPaginaView.getTxtCorreo().setText(usuario.getCorreo());
        miPaginaView.getTxtTelefono().setText(usuario.getTelefono());
        if (usuario.getFechaNacimiento() != null) {
            miPaginaView.getSpinnerFecha().setValue(usuario.getFechaNacimiento());
        }
    }


    /**
     * Abre la vista de edición de preguntas de seguridad para el usuario actual,
     * mostrando las preguntas fijas traducidas e inicializando campos.
     */

    private void abrirPreguntasParaEditar() {
        if (!principal.getDesktopPanel().isAncestorOf(preguntasViewU)) {
            principal.getDesktopPanel().add(preguntasViewU);
        }

        JTextField[] camposPreguntas   = preguntasViewU.getCamposPreguntas();
        JTextField[] camposRespuestas = preguntasViewU.getCamposRespuestas();

        List<Pregunta> preguntasFijas = preguntaDAO.listarPreguntas();
        for (int i = 0; i < 10; i++) {
            String clavePregunta = "pregunta." + (i + 1);
            String textoTraducido = mh.get(clavePregunta);
            camposPreguntas[i].setText(textoTraducido);
            camposPreguntas[i].setEditable(false);
            camposRespuestas[i].setText("");
        }

        preguntasViewU.setVisible(true);
        preguntasViewU.moveToFront();
        try {
            preguntasViewU.setSelected(true);
        } catch (Exception ignored) {}
        preguntasViewU.getBtnGuardar().addActionListener(ev -> guardarPreguntasActualizadas(preguntasFijas));
    }
    /**
     * Guarda las respuestas actualizadas a las preguntas de seguridad ingresadas por el usuario.
     *
     * @param preguntasFijas Lista de preguntas fijas que se deben responder.
     */

    private void guardarPreguntasActualizadas(List<Pregunta> preguntasFijas) {
        List<PreguntaRespondida> respuestas = new ArrayList<>();
        JTextField[] respuestasUsuario = preguntasViewU.getCamposRespuestas();

        for (int i = 0; i < 10; i++) {
            String textoRespuesta = respuestasUsuario[i].getText().trim();
            if (!textoRespuesta.isEmpty()) {
                respuestas.add(new PreguntaRespondida(preguntasFijas.get(i), textoRespuesta, usuario.getUsername()
                ));
            }
        }

        if (respuestas.size() < 3) {
            preguntasViewU.mostrarMensaje(mh.get("mensaje.minimoTresRespuestas"));
            return;
        }

        usuario.setPreguntasRespondidas(respuestas);
        usuarioDAO.actualizar(usuario);

        preguntaRespondidaDAO.actualizarPorUsuario(usuario.getUsername(), respuestas);

        preguntasViewU.mostrarMensaje(mh.get("mensaje.preguntasActualizadas"));
        preguntasViewU.dispose();
    }

    /**
     * Actualiza los datos del usuario desde la vista "Mi Página".
     *
     * @param e Evento que activa la actualización.
     * @throws ContrasenaInvalidaException Si la contraseña no es válida.
     * @throws CorreoInvalidoException Si el correo no tiene formato válido.
     * @throws IllegalArgumentException Si el teléfono no es válido.
     */

    private void actualizarDatosMet(ActionEvent e) {
        String nu = miPaginaView.getTxtUsuario().getText().trim();
        String np = new String(miPaginaView.getPwdContrasena().getPassword()).trim();
        String nombre = miPaginaView.getTxtNombreCompleto().getText().trim();
        String correo = miPaginaView.getTxtCorreo().getText().trim();
        String telefono = miPaginaView.getTxtTelefono().getText().trim();
        Date fechaNacimiento = (Date) miPaginaView.getSpinnerFecha().getValue();

        if (nu.isEmpty() || np.isEmpty() || nombre.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
            miPaginaView.mostrarMensaje(mh.get("mensaje.camposObligatorios"));
            return;
        }

        try {
            usuario.setContrasenia(np);
        } catch (ContrasenaInvalidaException e2) {
            miPaginaView.mostrarMensaje(e2.getMessage(), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuario.setCorreo(correo);
        } catch (CorreoInvalidoException e3) {
            miPaginaView.mostrarMensaje(e3.getMessage(), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            usuario.setTelefono(telefono);
        } catch (IllegalArgumentException e4) {
            miPaginaView.mostrarMensaje(e4.getMessage(), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }

        usuario.setNombreCompleto(nombre);
        usuario.setFechaNacimiento(fechaNacimiento);
        usuarioDAO.actualizar(usuario);
        miPaginaView.mostrarMensaje(mh.get("mensaje.datosActualizados"));
    }
    /**
     * Lista todos los usuarios en la vista de administración y la muestra.
     */

    private void listarTodos() {
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarioDAO.listarTodos()) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        mostrarInternal(listaUsuariosView);
    }

    /**
     * Lista los usuarios filtrados por rol seleccionado.
     */

    private void listarPorRol() {
        ROL rol = (ROL) listaUsuariosView.getCbxRol().getSelectedItem();
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);
        for (Usuario u : usuarioDAO.listarPorRol(rol)) {
            m.addRow(new Object[]{u.getUsername(), u.getRol()});
        }
        mostrarInternal(listaUsuariosView);
    }
    /**
     * Lista los usuarios filtrados por rol seleccionado.
     */

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
    /**
     * Crea un nuevo usuario desde la vista de administración, validando los campos.
     *
     * @throws ContrasenaInvalidaException Si la contraseña ingresada no cumple requisitos.
     */

    private void crearUsuario() {
        String username = crearUsuarioView.getTxtUsuarioNuevo().getText().trim();
        String pass  = new String(crearUsuarioView.getPwdContrasenaNueva().getPassword()).trim();
        ROL rol  = (ROL) crearUsuarioView.getCbxRol().getSelectedItem();


        if (username.isEmpty() || pass.isEmpty()) {
            crearUsuarioView.mostrarMensaje( mh.get("mensaje.camposObligatorios"), mh.get("titulo.atencion"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }


        if (usuarioDAO.buscarPorUsername(username) != null) {
            crearUsuarioView.mostrarMensaje(mh.get("mensaje.usuarioYaExiste"), mh.get("titulo.atencion"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }



        Usuario nuevo = new Usuario(username, pass, rol);
        usuarioDAO.crear(nuevo);

        crearUsuarioView.getTxtUsuarioNuevo().setText("");
        crearUsuarioView.getPwdContrasenaNueva().setText("");
        crearUsuarioView.getCbxRol().setSelectedIndex(0);
        crearUsuarioView.mostrarMensaje(mh.get("mensaje.usuarioCreado"), mh.get("titulo.exito"), JOptionPane.INFORMATION_MESSAGE);
    }
    private boolean inicializandoComboRolFiltro = false;
    /**
     * Actualiza el combo de roles en la vista de creación de usuario
     * usando las traducciones del idioma actual.
     *
     * @param mh Manejador de internacionalización.
     */

    public void actualizarComboRolesEnFiltros(MensajeInternacionalizacionHandler mh) {
        inicializandoComboRolFiltro = true;

        JComboBox<ROL> cbx = listaUsuariosView.getCbxRol();
        DefaultComboBoxModel<ROL> modelo = new DefaultComboBoxModel<>();
        modelo.addElement(ROL.ADMINISTRADOR);
        modelo.addElement(ROL.USUARIO);
        cbx.setModel(modelo);

        cbx.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String texto = switch ((ROL) value) {
                    case ADMINISTRADOR -> mh.get("rol.administrador");
                    case USUARIO -> mh.get("rol.usuario");
                };
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });

        inicializandoComboRolFiltro = false;
    }
    /**
     * Actualiza los textos del combo de roles en la vista de filtros,
     * aplicando el idioma actual.
     *
     * @param mh Manejador de internacionalización.
     */

    public void actualizarComboRol(MensajeInternacionalizacionHandler mh) {
        JComboBox<ROL> combo = crearUsuarioView.getCbxRol();

        DefaultComboBoxModel<ROL> modelo = new DefaultComboBoxModel<>();
        modelo.addElement(ROL.ADMINISTRADOR);
        modelo.addElement(ROL.USUARIO);
        combo.setModel(modelo);


        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String texto = switch ((ROL) value) {
                    case ADMINISTRADOR -> mh.get("rol.administrador");
                    case USUARIO -> mh.get("rol.usuario");
                };
                return super.getListCellRendererComponent(list, texto, index, isSelected, cellHasFocus);
            }
        });
    }
    /**
     * Edita el rol y la contraseña del usuario seleccionado en la vista de edición.
     *
     * @throws ContrasenaInvalidaException Si la nueva contraseña no cumple los requisitos.
     */

    private void editarUsuario() {
        String username = editarUsuarioView.getTxtUsuario().getText().trim();
        Usuario u = usuarioDAO.buscarPorUsername(username);
        if (u == null) {
            editarUsuarioView.mostrarMensaje(mh.get("mensaje.usuarioNoEncontrado"), mh.get("titulo.atencion"), JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String nuevaPass = new String(editarUsuarioView.getPwdNContrasena().getPassword()).trim();
        ROL nuevoRol  = (ROL) editarUsuarioView.getCbxRol().getSelectedItem();

        try {
            if (!nuevaPass.isEmpty()) {
                u.setContrasenia(nuevaPass);
            }
            u.setRol(nuevoRol);
            usuarioDAO.actualizar(u);
            editarUsuarioView.mostrarMensaje(mh.get("mensaje.usuarioActualizado"), mh.get("titulo.atencion"), JOptionPane.INFORMATION_MESSAGE);
            editarUsuarioView.dispose();
        } catch (ContrasenaInvalidaException e) {
            editarUsuarioView.mostrarMensaje(e.getMessage(), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Elimina el carrito seleccionado en la tabla del usuario, previa confirmación.
     */

    private void eliminarCarrito(){
        listarView.getBtnEliminar().addActionListener(e -> {
            int row = listarView.getTblCarritos().getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(listarView, mh.get("mensaje.seleccionaCarrito"), mh.get("titulo.atencion"), JOptionPane.WARNING_MESSAGE);
                return;
            }
            int codigo = (int) listarView.getTblCarritos().getValueAt(row, 0);
            int opt = JOptionPane.showConfirmDialog(listarView, mh.get("mensaje.confirmarEliminarCarrito") + codigo + "?", mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                carritoDAO.eliminar(codigo);
                refrescarMisCarritos();
                JOptionPane.showMessageDialog(listarView, mh.get("mensaje.carritoEliminado"), mh.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    /**
     * Elimina el usuario seleccionado en la tabla de usuarios, con confirmación previa.
     */

    private void eliminarUsuarioSeleccionado() {
        int fila = listaUsuariosView.getTblUsuarios().getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(listaUsuariosView,
                    mh.get("mensaje.seleccionaUsuario"),
                    mh.get("titulo.atencion"),
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) listaUsuariosView.getTblUsuarios()
                .getValueAt(fila, 0);

        int opcion = JOptionPane.showConfirmDialog(listaUsuariosView,
                MessageFormat.format(mh.get("mensaje.confirmarEliminacionUsuario"), username), mh.get("titulo.confirmar.eliminacion"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion != JOptionPane.YES_OPTION) {
            return;
        }

        usuarioDAO.eliminar(username);

        DefaultTableModel modelo = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();modelo.removeRow(fila);

        JOptionPane.showMessageDialog(listaUsuariosView, MessageFormat.format(mh.get("mensaje.usuarioEliminado"), username), mh.get("titulo.informacion"), JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Muestra los detalles de un carrito seleccionado en una tabla dada.
     *
     * @param tabla Tabla que contiene los carritos.
     * @param contenedor Contenedor donde se mostrará la vista de detalle.
     */

    private void verDetallesDesde(JTable tabla, JDesktopPane contenedor) {
        int row = tabla.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(contenedor, mh.get("mensaje.seleccionaCarrito"), mh.get("titulo.atencion"), JOptionPane.WARNING_MESSAGE);
            return;
        }

        int codigo = (int) tabla.getValueAt(row, 0);
        Carrito c = carritoDAO.buscarPorCodigo(codigo);
        if (c == null) {
            JOptionPane.showMessageDialog(contenedor, mh.get("mensaje.carritoNoEncontrado"), mh.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
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
        System.out.println("Buscando carrito con código: " + codigo);

        if (!verDetalleView.isShowing()) {
            contenedor.add(verDetalleView);
        }
        verDetalleView.setVisible(true);
        try {
            verDetalleView.setSelected(true);
        } catch (PropertyVetoException ignore) {}
    }
    /**
     * Busca un usuario por su nombre de usuario (username) e imprime el resultado en la tabla.
     */
    public void buscarUsuarioPorNombre() {
        String txt = listaUsuariosView.getTxtBuscar().getText().trim();
        DefaultTableModel m = (DefaultTableModel) listaUsuariosView.getTblUsuarios().getModel();
        m.setRowCount(0);

        Usuario u = usuarioDAO.buscarPorUsername(txt);
        if (u != null) {
            m.addRow(new Object[]{ u.getUsername(), u.getRol() });
        } else {
            JOptionPane.showMessageDialog(listaUsuariosView, MessageFormat.format(mh.get("mensaje.usuarioNoEncontrado"), txt), mh.get("titulo.atencion"), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Muestra en tabla todos los carritos creados por todos los usuarios del sistema.
     * Usado por el administrador.
     */

    public void mostrarTodosLosCarritos() {
        DefaultTableModel m = listarTodosCarritosView.getModelo();
        m.setRowCount(0);

        for (Carrito c : carritoDAO.listarTodos()) {
            m.addRow(new Object[]{c.getCodigo(), FormatosUtils.formatearFecha(c.getFechaCreacion().getTime(), Locale.getDefault()), c.getUsuario().getUsername()
            });
        }
    }
    /**
     * Establece el manejador de internacionalización para traducción de mensajes.
     *
     * @param mh Manejador de mensajes internacionalizados.
     */

    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mh = mh;
    }

}
