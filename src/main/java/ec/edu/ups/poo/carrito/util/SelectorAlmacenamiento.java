package ec.edu.ups.poo.carrito.util;

import javax.swing.*;
import java.io.File;

public class SelectorAlmacenamiento {
    private static MensajeInternacionalizacionHandler handler;

    /**
     * Muestra un cuadro de diálogo para que el usuario seleccione el tipo de almacenamiento.
     *
     * Si se elige almacenamiento en archivos (texto o binario), se solicita una carpeta destino.
     * En caso de cancelar la selección, se usará almacenamiento en memoria por defecto.
     *
     * @param parent La ventana padre del diálogo (puede ser null si no se desea asociar a una ventana).
     */
    public static void mostrarSeleccionAlmacenamiento(JFrame parent) {
        String[] opciones = {
                handler.get("selector.almacenamiento.memoria"),
                handler.get("selector.almacenamiento.mixto"),
                handler.get("selector.almacenamiento.binario")
        };

        int opcion = JOptionPane.showOptionDialog(
                parent,
                handler.get("selector.almacenamiento.pregunta"),
                handler.get("selector.almacenamiento.titulo"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        ConfiguracionSistema config = ConfiguracionSistema.getInstancia();

        if (opcion == 0) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        } else if (opcion == 1) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.ARCHIVOS);
        } else if (opcion == 2) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.ARCHIVOS_BINARIOS);
        } else {

            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        }


        if (config.getTipoAlmacenamiento() != ConfiguracionSistema.TipoAlmacenamiento.MEMORIA) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(handler.get("selector.almacenamiento.tituloSeleccionCarpeta"));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showOpenDialog(parent);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                config.setRutaArchivos(fileChooser.getSelectedFile().getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(parent, handler.get("selector.almacenamiento.cancelado"));
                config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
            }
        }
    }
    public static void setMensajeInternacionalizacionHandler(MensajeInternacionalizacionHandler h) {
        handler = h;
    }


}
