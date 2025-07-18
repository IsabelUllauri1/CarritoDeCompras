package ec.edu.ups.poo.carrito.controlador;

import ec.edu.ups.poo.carrito.dao.PreguntaDAO;
import ec.edu.ups.poo.carrito.dao.UsuarioDAO;
import ec.edu.ups.poo.carrito.modelo.Pregunta;
import ec.edu.ups.poo.carrito.modelo.PreguntaRespondida;
import ec.edu.ups.poo.carrito.modelo.ROL;
import ec.edu.ups.poo.carrito.modelo.Usuario;
import ec.edu.ups.poo.carrito.util.exception.CedulaInvalidaExeption;
import ec.edu.ups.poo.carrito.util.exception.ContrasenaInvalidaException;
import ec.edu.ups.poo.carrito.util.MensajeInternacionalizacionHandler;
import ec.edu.ups.poo.carrito.util.exception.CorreoInvalidoException;
import ec.edu.ups.poo.carrito.util.exception.ValidacionException;
import ec.edu.ups.poo.carrito.view.login.LoginView;
import ec.edu.ups.poo.carrito.view.login.OlvideContrasenaView;
import ec.edu.ups.poo.carrito.view.login.PreguntasView;
import ec.edu.ups.poo.carrito.view.login.RegistrarseView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public LoginControlador(UsuarioDAO usuarioDAO, LoginView loginView, RegistrarseView registrarseView, PreguntasView preguntasView, OlvideContrasenaView olvideContrasenaView, PreguntaDAO preguntaDAO, MensajeInternacionalizacionHandler mensajeInternacionalizacionHandler) {
        this.usuarioDAO = usuarioDAO;
        this.loginView  = loginView;
        this.usuarioAutenticado = null;
        this.registrarseView = registrarseView;
        this.preguntasView = preguntasView;
        this.olvideContrasenaView = olvideContrasenaView;
        this.preguntaDAO = preguntaDAO;
        this.mensajeInternacionalizacionHandler = mensajeInternacionalizacionHandler;
        loginListeners();
    }

    private void loginListeners() {
        loginView.getItemEspanol().addActionListener(e -> {
            cambiarIdioma("es", "EC");
            loginView.actualizarTexto(mensajeInternacionalizacionHandler);
            registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);
            preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);
            olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);
        });

        loginView.getItemIngles().addActionListener(e -> {
            cambiarIdioma("en", "US");
            loginView.actualizarTexto(mensajeInternacionalizacionHandler);
            registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);
            preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);
            olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);
        });
        loginView.getItemAleman().addActionListener(e -> {
            cambiarIdioma("de", "DE");
            loginView.actualizarTexto(mensajeInternacionalizacionHandler);
            registrarseView.actualizarTexto(mensajeInternacionalizacionHandler);
            preguntasView.actualizarTexto(mensajeInternacionalizacionHandler);
            olvideContrasenaView.actualizarTexto(mensajeInternacionalizacionHandler);
        });


        loginView.getBtnIniciarSesion().addActionListener(e -> logear(e));
        loginView.getBtnRegistrarse().addActionListener(e -> {
            registrarseView.setVisible(true);
        });

        registrarseView.getBtnSiguiente().addActionListener(e -> {
            String username = registrarseView.getTextField1().getText().trim();
            String password = new String(registrarseView.getPasswordField1().getPassword()).trim();
            System.out.println("Contraseña capturada: '" + password + "'");
            String nombreCompleto = registrarseView.getTxtNombre().getText().trim();
            String correo = registrarseView.getTxtCorreo().getText().trim();
            String telefono = registrarseView.getTxtTelefono().getText().trim();
            String fechaTexto = registrarseView.getTxtFechaNacimiento().getText().trim();

            if (username.isEmpty() || password.isEmpty() || nombreCompleto.isEmpty() || correo.isEmpty() || telefono.isEmpty() || fechaTexto.isEmpty()) {
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

            if (!fechaTexto.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.fechaFormatoInvalido"));
                return;
            }

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            formato.setLenient(false);
            Date fechaNacimiento;
            try {
                fechaNacimiento = formato.parse(fechaTexto);
            } catch (ParseException ex) {
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.fechaInvalida"));
                return;
            }

            try {
                System.out.println("Intentando crear usuario:");
                System.out.println("Cedula: " + username);
                System.out.println("Contrasena: " + password);
                System.out.println("Correo: " + correo);
                System.out.println("Nombre: " + nombreCompleto);
                System.out.println("Telefono: " + telefono);
                System.out.println("Fecha: " + fechaTexto);

                this.usuarioTemp = new Usuario(username, password, ROL.USUARIO, correo, nombreCompleto, telefono, fechaNacimiento);

                System.out.println("Usuario creado con contraseña: " + usuarioTemp.getContrasenia());
                preguntasView.setVisible(true);
                registrarseView.setVisible(false);


            } catch (CedulaInvalidaExeption | ContrasenaInvalidaException | CorreoInvalidoException | ValidacionException ex) {
                System.out.println("Error validando campos: " + ex.getMessage());
                System.out.println("Error al construir usuario: " + ex.getMessage());
                ex.printStackTrace();
                usuarioTemp = null;
                registrarseView.mostrarMensaje(ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Error inesperado al crear usuario con contraseña: [" + password + "]");

                ex.printStackTrace();
                usuarioTemp = null;
                registrarseView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.errorRegistro"));
            }


        });



        registrarseView.getBtnRegresar().addActionListener(e -> {
            registrarseView.setVisible(false);
            loginView.setVisible(true);


        });


        preguntasView.getBtnGuardar().addActionListener(e -> {
            if (usuarioTemp == null) {
                preguntasView.mostrarMensaje("Error: El usuario no fue creado correctamente.");
                return;
            }

            List<PreguntaRespondida> respuestas = new ArrayList<>();
            JTextField[] preguntas = preguntasView.getCamposPreguntas();
            JTextField[] respuestasUsuario = preguntasView.getCamposRespuestas();

            for (int i = 0; i < 10; i++) {
                String textoPregunta = preguntas[i].getText().trim();
                String textoRespuesta = respuestasUsuario[i].getText().trim();

                if (!textoRespuesta.isEmpty()) {
                    respuestas.add(new PreguntaRespondida(
                            new Pregunta(textoPregunta, i + 1),
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
            try {
                System.out.println("ANTES DE GUARDAR → Contraseña actual: " + usuarioTemp.getContrasenia());
                if (usuarioTemp.getContrasenia() == null) {
                    System.err.println("ERROR: usuarioTemp tiene contraseña null antes de guardar.");
                    preguntasView.mostrarMensaje("Error inesperado: El usuario no tiene contraseña válida.");
                    return;
                }

                usuarioDAO.crear(usuarioTemp);
                preguntasView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.usuarioRegistrado"));
                preguntasView.dispose();
                loginView.setVisible(true);
            } catch (ValidacionException ex) {
                preguntasView.mostrarMensaje(ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                preguntasView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.errorRegistro"));
            }
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
                    try {
                        usuarioTemp.setContrasenia(nuevaPass.trim());
                        usuarioDAO.actualizar(usuarioTemp);
                        JOptionPane.showMessageDialog(olvideContrasenaView, mensajeInternacionalizacionHandler.get("mensaje.contrasenaAct"));
                        olvideContrasenaView.dispose();
                    } catch (ContrasenaInvalidaException | ValidacionException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), mensajeInternacionalizacionHandler.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, mensajeInternacionalizacionHandler.get("mensaje.respuestaIncorrecta"), mensajeInternacionalizacionHandler.get("titulo.error"), JOptionPane.ERROR_MESSAGE);
            }
        });

        loginView.getBtnOlvide().addActionListener(e ->{
            olvideContrasenaView.setVisible(true);
        } );

    }


    private void logear (ActionEvent e) {
        try {
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
        }catch (ValidacionException ex) {
            loginView.mostrarMensaje(ex.getMessage());
        } catch (Exception ex) {
            loginView.mostrarMensaje(mensajeInternacionalizacionHandler.get("mensaje.errorLogin"));
        } finally {
            loginView.getTxtUsername().setText("");
            loginView.getTxtContrasena().setText("");
        }


    }
    public void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler mh) {
        this.mensajeInternacionalizacionHandler = mh;
    }

    public void cambiarIdioma(String lenguaje, String pais){
        Locale locale = new Locale(lenguaje, pais);
        Locale.setDefault(locale);
        mensajeInternacionalizacionHandler.setLanguage(lenguaje, pais);
    }


    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

}
