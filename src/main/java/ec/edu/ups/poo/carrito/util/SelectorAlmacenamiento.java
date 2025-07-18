package ec.edu.ups.poo.carrito.util;

import javax.swing.*;
import java.io.File;

public class SelectorAlmacenamiento {

    public static void mostrarSeleccionAlmacenamiento(JFrame parent) {
        String[] opciones = { "Memoria (no guarda datos)", "Archivos (guardar en disco)" };
        int opcion = JOptionPane.showOptionDialog(
                parent,
                "¿Dónde desea guardar los datos?",
                "Modo de almacenamiento",
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

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Seleccione una carpeta para guardar los archivos");

            int seleccion = chooser.showOpenDialog(parent);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File carpetaSeleccionada = chooser.getSelectedFile();
                config.setRutaArchivos(carpetaSeleccionada.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(parent, "No seleccionó una carpeta. Se usará almacenamiento en memoria.");
                config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
            }
        } else {
            JOptionPane.showMessageDialog(parent, "No seleccionó una opción. Se usará almacenamiento en memoria.");
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        }
    }
}
