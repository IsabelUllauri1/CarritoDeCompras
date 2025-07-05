package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Pregunta;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.view.login.LoginView;
import ec.edu.ups.poo.carrito.view.login.OlvideContrasenaView;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;
import ec.edu.ups.poo.carrito.view.login.RegistrarseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class LoginControlador {
    private final UsuarioDAO usuarioDAO;
    private final LoginView  loginView;
    private Usuario usuarioAutenticado;
    private RegistrarseView registrarseView;
    private PreguntasView preguntasView;
    private Usuario usuarioTemp;
    private PreguntaRespondida preguntaRespondida;
    private OlvideContrasenaView olvideContrasenaView;
    private PreguntaRespondida preguntaRespondidaTemp;
    private final PreguntaDAO preguntaDAO;

    public LoginControlador(UsuarioDAO usuarioDAO, LoginView loginView, RegistrarseView registrarseView, PreguntasView preguntasView, OlvideContrasenaView olvideContrasenaView, PreguntaDAO preguntaDAO) {
        this.usuarioDAO = usuarioDAO;
        this.loginView  = loginView;
        this.usuarioAutenticado = null;
        this.registrarseView = registrarseView;
        this.preguntasView = preguntasView;
        this.olvideContrasenaView = olvideContrasenaView;
        this.preguntaDAO = preguntaDAO;
        loginListeners();
    }

    private void loginListeners() {
        loginView.getBtnIniciarSesion().addActionListener(e -> logear(e));
        loginView.getBtnRegistrarse().addActionListener(e -> {
            registrarseView.setVisible(true);
        });

        registrarseView.getBtnSiguiente().addActionListener(e -> {
            String username = registrarseView.getTextField1().getText().trim();
            String password = new String(registrarseView.getPasswordField1().getPassword()).trim();
            String nombreCompleto = registrarseView.getTxtNombre().getText().trim();
            String correo = registrarseView.getTxtCorreo().getText().trim();
            String telefono = registrarseView.getTxtTelefono().getText().trim();
            Date fechaNacimiento = (Date) registrarseView.getSpinnerFecha().getValue();

            if (username.isEmpty() || password.isEmpty() || nombreCompleto.isEmpty()
                    || correo.isEmpty() || telefono.isEmpty()) {
                registrarseView.mostrarMensaje("Completa todos los campos");
                return;
            }

            if (!nombreCompleto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                registrarseView.mostrarMensaje("Nombre inválido. Solo se permiten letras y espacios.");
                return;
            }


            if (!telefono.matches("\\d{7,10}")) {
                registrarseView.mostrarMensaje("Teléfono inválido. Debe tener entre 7 y 10 dígitos.");
                return;
            }

            this.usuarioTemp = new Usuario(username, password, Rol.USUARIO, correo, nombreCompleto, telefono, fechaNacimiento);
            preguntasView.setVisible(true);
            registrarseView.setVisible(false);
        });

        registrarseView.getBtnRegresar().addActionListener(e -> {
            registrarseView.setVisible(false);
            loginView.setVisible(true);


        });


        preguntasView.getBtnGuardar().addActionListener(e -> {
            List<PreguntaRespondida> respuestas = new ArrayList<>();
            JTextField[] preguntas = preguntasView.getCamposPreguntas();
            JTextField[] respuestasUsuario = preguntasView.getCamposRespuestas();

            for (int i = 0; i < 10; i++) {
                String textoPregunta = preguntas[i].getText().trim();
                String textoRespuesta = respuestasUsuario[i].getText().trim();

                if (!textoRespuesta.isEmpty()) {
                    respuestas.add(new PreguntaRespondida(
                            new Pregunta(textoPregunta,i + 1),
                            textoRespuesta,
                            usuarioTemp.getUsername()
                    ));
                }
            }

            if (respuestas.size() < 3) {
                preguntasView.mostrarMensaje("Debes responder al menos 3 preguntas");
                return;
            }

            usuarioTemp.setPreguntasRespondidas(respuestas);
            usuarioDAO.crear(usuarioTemp);

            preguntasView.mostrarMensaje("¡Usuario registrado con éxito!");
            preguntasView.dispose();
            loginView.setVisible(true);
        });


        preguntasView.getBtnGuardar().addActionListener(e -> {
            JTextField[] preguntas = preguntasView.getCamposPreguntas();
            JTextField[] respuestas = preguntasView.getCamposRespuestas();
            List<PreguntaRespondida> respondidas = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                String textoPregunta = preguntas[i].getText().trim();
                String respuesta = respuestas[i].getText().trim();

                if (!respuesta.isEmpty()) {
                    respondidas.add(new PreguntaRespondida(new Pregunta(textoPregunta,i + 1), respuesta, usuarioTemp.getUsername()));
                }
            }

            if (respondidas.size() < 3) {
                preguntasView.mostrarMensaje("Debes responder al menos 3 preguntas");
                return;
            }


            preguntasView.dispose();
            loginView.setVisible(true);


        });

        preguntasView.getBtnRegresar().addActionListener(e -> {
            preguntasView.setVisible(false);
            registrarseView.setVisible(true);
        });

        olvideContrasenaView.getBtnBuscarUsuario().addActionListener(e -> {
            String username = olvideContrasenaView.getTxtUser().getText().trim();
            usuarioTemp = usuarioDAO.buscarPorUsername(username);

            if (usuarioTemp == null) {
                JOptionPane.showMessageDialog(olvideContrasenaView, "Usuario no encontrado");
                return;
            }

            List<PreguntaRespondida> respuestas = usuarioTemp.getPreguntasRespondidas();
            if (respuestas == null || respuestas.isEmpty()) {
                JOptionPane.showMessageDialog(olvideContrasenaView, "No hay preguntas registradas para este usuario");
                return;
            }

            preguntaRespondida = respuestas.get(new Random().nextInt(respuestas.size()));
            olvideContrasenaView.getTxtPregunta().setText(preguntaRespondida.getPregunta().getTexto());
        });
        olvideContrasenaView.getBtnGuardar().addActionListener(e -> {
            String respuestaIngresada = olvideContrasenaView.getTxtRespuesta().getText().trim();

            if (preguntaRespondida == null || usuarioTemp == null) {
                JOptionPane.showMessageDialog(olvideContrasenaView, "Primero busca el usuario");
                return;
            }

            System.out.println("Esperado: " + preguntaRespondida.getRespuesta());
            System.out.println("Ingresado: " + respuestaIngresada);

            if (respuestaIngresada.equalsIgnoreCase(preguntaRespondida.getRespuesta().trim())) {
                String nuevaPass = JOptionPane.showInputDialog(olvideContrasenaView, "Respuesta correcta. Ingresa nueva contraseña:");
                if (nuevaPass != null && !nuevaPass.isBlank()) {
                    usuarioTemp.setContrasenia(nuevaPass.trim());
                    usuarioDAO.actualizar(usuarioTemp);
                    JOptionPane.showMessageDialog(olvideContrasenaView, "Contraseña actualizada correctamente");
                    olvideContrasenaView.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(olvideContrasenaView, "Respuesta incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        loginView.getBtnOlvide().addActionListener(e ->{
            olvideContrasenaView.setVisible(true);
        } );

    }

    private void logear (ActionEvent e) {
        String user = loginView.getTxtUsername().getText();
        String passw = loginView.getTxtContrasena().getText();
        usuarioAutenticado = usuarioDAO.autenticar(user, passw);
        loginView.getTxtUsername().setText("");
        loginView.getTxtContrasena().setText("");
        if (usuarioAutenticado == null) {
            loginView.mostrarMensaje("Usuario o contraseña incorrectos");
            return;
        }
        loginView.dispose();


    }



    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

}
