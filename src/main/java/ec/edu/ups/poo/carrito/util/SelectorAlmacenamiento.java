package ec.edu.ups.poo.carrito.util;

import javax.swing.*;
import java.io.File;

public class SelectorAlmacenamiento {

    public static void mostrarSeleccionAlmacenamiento(JFrame parent) {
        String[] opciones = {
                "Memoria (no guarda datos)",
                "Archivos de texto",
                "Archivos binarios"
        };

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
        } else if (opcion == 2) {
            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.ARCHIVOS_BINARIOS);
        } else {

            config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
        }


        if (config.getTipoAlmacenamiento() != ConfiguracionSistema.TipoAlmacenamiento.MEMORIA) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione la carpeta para guardar los archivos");
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int seleccion = fileChooser.showOpenDialog(parent);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                config.setRutaArchivos(fileChooser.getSelectedFile().getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(parent, "No seleccionó carpeta. Se usará almacenamiento en memoria.");
                config.setTipoAlmacenamiento(ConfiguracionSistema.TipoAlmacenamiento.MEMORIA);
            }
        }
    }

}
