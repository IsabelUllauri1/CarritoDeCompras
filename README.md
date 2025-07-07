# 📌 Información General  
**Título:** Sistema de compras con interfaz gráfica y arquitectura orientada a objetos  
**Asignatura:** Programación Orientada a Objetos  
**Práctica:** 4  
**Carrera:** Computación  
**Estudiante:** Isabel Ullauri y Victoria Andrade  
**Fecha:** 25/05/2025  
**Profesor:** Gabriel Alejandro León Paredes  

---


📹 **Explicación del Programa en YouTube:**  
https://youtu.be/mzeQyTjrD1I

---

# 🎯 Objetivo  
Desarrollar una aplicación completa con interfaz gráfica Swing en Java, basada en el paradigma de programación orientada a objetos, que permita a los usuarios registrarse, iniciar sesión, recuperar su contraseña mediante preguntas de seguridad, y gestionar carritos de compra.  

El sistema busca combinar lógica empresarial robusta (DAO + MVC + SOLID) con una experiencia de usuario amigable y adaptable (i18n + MDI + vistas personalizadas).

---

# ⚙️ Conceptos aplicados

- **Java (Swing y AWT combinados)**
- **MVC (Modelo-Vista-Controlador)**
- **DAO (Data Access Object) en memoria**
- **Principios SOLID y separación de responsabilidades**
- **Componentes gráficos:** JFrame, JPanel, JLabel, JTextField, JButton, JMenuBar, JTable, JComboBox, JInternalFrame
- **Layouts:** GridLayout, BorderLayout, FlowLayout
- **Internacionalización (`ResourceBundle`)** con soporte para español, inglés y alemán
- **Recuperación de contraseña** mediante preguntas de seguridad
- **Formateo regional** de fechas y moneda (`NumberFormat`, `DateFormat`)
- **Gráficos con JFreeChart** (si aplica)

---

# 🧪 Inicialización del Programa  
El sistema se inicia ejecutando la clase `Main`, que configura el idioma y despliega la ventana `LoginView`.  
Desde ahí, según el rol del usuario (ADMIN o CLIENTE), se abre la interfaz `Principal`, que actúa como contenedor MDI de todas las funcionalidades.

---

# 🖼️ Interfaz general del sistema

### Ventanas principales:

- **LoginView:** Inicio de sesión con opción de cambio de idioma.
- **RegistrarseView + PreguntasView:** Registro de usuario con preguntas de seguridad.
- **OlvideContrasenaView:** Recuperación de contraseña.
- **Principal:** Ventana contenedora tipo MDI.
- **AnadirProductosView, ProductoListarView, ProductoEliminarView, ProductoActualizarView:** CRUD de productos.
- **CarritoAnadirView, CarritoListarView, VerDetalleView:** Gestión de carritos por usuario.
- **MiPaginaView, PreguntasUView:** Edición de perfil y preguntas de seguridad.
- **ListarUsuariosView, CrearUsuarioView, EditarUsuarioView:** Administración de usuarios (ADMIN).
- **ListarTodosLosCarritosView:** Vista para que el ADMIN vea los carritos de todos los usuarios.

---

# 🧪 Resultados obtenidos

- Se implementó correctamente una interfaz modular basada en `JInternalFrame`.
- El sistema permite registrar, autenticar, y recuperar cuentas mediante preguntas de seguridad.
- Se logró cambiar el idioma dinámicamente (i18n) desde el menú principal.
- Gestión de productos y carritos con acceso restringido por rol.
- Arquitectura clara con MVC y DAO en memoria.
- Validaciones, formateo visual y mensajes internacionalizados mejoraron la experiencia del usuario.

---

# ✅ Recomendaciones

- Usar `JDesktopPane` con cuidado para evitar superposición de ventanas.
- Centralizar el cambio de idioma usando `MensajeInternacionalizacionHandler`.
- Separar listeners del menú (`Principal`) y los botones (`Controladores`).
- Validar datos de entrada para prevenir errores.
- En el futuro, implementar persistencia en archivos o base de datos.
- Controlar la sesión para mayor seguridad.
- Documentar el flujo de controladores desde el `Main`.

