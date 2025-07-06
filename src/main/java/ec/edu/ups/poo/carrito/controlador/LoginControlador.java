package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Pregunta;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;
import ec.edu.ups.poo.carrito.modelo.Rol;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.FormatosUtils;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
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
    private MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler;

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
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.camposObligatorios"));
                return;
            }

            if (!nombreCompleto.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")) {
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.nombreInvalido"));
                return;
            }


            if (!telefono.matches("\\d{7,10}")) {
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.telefonoInvalido"));
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
                preguntasView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.minimoTresRespuestas"));
                return;
            }

            usuarioTemp.setPreguntasRespondidas(respuestas);
            usuarioDAO.crear(usuarioTemp);

            preguntasView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.usuarioRegistrado"));

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
                preguntasView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.minimoTresRespuestas"));
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
                JOptionPane.showMessageDialog(olvideContrasenaView, mensajeInternacionalizacionHandler.get("mensaje.usuarioNoEncontrado"));
                return;
            }

            List<PreguntaRespondida> respuestas = usuarioTemp.getPreguntasRespondidas();
            if (respuestas == null || respuestas.isEmpty()) {
                JOptionPane.showMessageDialog(olvideContrasenaView, mensajeInternacionalizacionHandler.get("mensaje.preguntasNoRegistradas"));
                return;
            }

            preguntaRespondida = respuestas.get(new Random().nextInt(respuestas.size()));
            olvideContrasenaView.getTxtPregunta().setText(preguntaRespondida.getPregunta().getTexto());
        });
        olvideContrasenaView.getBtnGuardar().addActionListener(e -> {
            String respuestaIngresada = olvideContrasenaView.getTxtRespuesta().getText().trim();

            if (preguntaRespondida == null || usuarioTemp == null) {
                JOptionPane.showMessageDialog(olvideContrasenaView, mensajeInternacionalizacionHandler.get("mensaje.buscarUsuarioPrimero"));
                return;
            }

            System.out.println("Esperado: " + preguntaRespondida.getRespuesta());
            System.out.println("Ingresado: " + respuestaIngresada);

            if (respuestaIngresada.equalsIgnoreCase(preguntaRespondida.getRespuesta().trim())) {
                String mensaje = mensajeInternacionalizacionHandler.get("input.nuevaContrasena");
                String nuevaPass = JOptionPane.showInputDialog(mensaje);
                if (nuevaPass != null && !nuevaPass.isBlank()) {
                    usuarioTemp.setContrasenia(nuevaPass.trim());
                    usuarioDAO.actualizar(usuarioTemp);
                    JOptionPane.showMessageDialog(olvideContrasenaView, mensajeInternacionalizacionHandler.get("mensaje.contrasenaAct"));
                    olvideContrasenaView.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, mensajeInternacionalizacionHandler.get("mensaje.respuestaIncorrecta"), mensajeInternacionalizacionHandler.get("titulo.error"), JOptionPane.ERROR_MESSAGE);            }
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
            loginView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.credencialesInvalidas"));
            return;
        }
        loginView.dispose();


    }
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mensajeInternacionalizacionHandler = mh;
    }


    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

}
