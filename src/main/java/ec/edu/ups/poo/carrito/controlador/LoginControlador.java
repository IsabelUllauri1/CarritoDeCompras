package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.view.LoginView;

import java.awt.event.ActionEvent;

public class LoginControlador {
    private final UsuarioDAO usuarioDAO;
    private final LoginView  loginView;
    private       Usuario    usuarioAutenticado;

    public LoginControlador(UsuarioDAO usuarioDAO, LoginView loginView) {
        this.usuarioDAO = usuarioDAO;
        this.loginView  = loginView;
        this.usuarioAutenticado = null;
        loginListeners();
    }

    private void loginListeners() {
        loginView.getBtnIniciarSesion().addActionListener(this::logear);
        loginView.getBtnRegistrarse().addActionListener(this::registrarse);
    }

    private void logear (ActionEvent e) {
        String user = loginView.getTxtUsername().getText();
        String passw = loginView.getTxtContrasena().getText();
        usuarioAutenticado = usuarioDAO.autenticar(user, passw);
        if (usuarioAutenticado == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos");
            return;
        }
        loginView.dispose();
    }

    private void registrarse(ActionEvent e) {
        String u = loginView.getTxtUsername().getText().trim();
        String p = loginView.getTxtContrasena().getText().trim();
        if (u.isEmpty() || p.isEmpty()) {
            loginView.mostrarMensaje("Completa ambos campos");
            return;
        }
        if (usuarioDAO.buscarPorUsername(u) != null) {
            loginView.mostrarMensaje("Usuario ya en uso");
            return;
        }
        usuarioDAO.crear(new Usuario(u, p, Rol.USUARIO));
        loginView.mostrarMensaje("Usuario registrado");
        loginView.getTxtUsername().setText("");
        loginView.getTxtContrasena().setText("");
    }

    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}
