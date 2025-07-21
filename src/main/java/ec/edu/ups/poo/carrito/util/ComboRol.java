package ec.edu.ups.poo.carrito.util;

import ec.edu.ups.poo.carrito.modelo.ROL;

import javax.swing.*;
import java.awt.*;

public class ComboRol {
    /**
     * Aplica la internacionalización a un {@link JComboBox} que contiene valores del enum {@link ROL}.
     * Se actualizan las etiquetas visibles en el combo para reflejar el idioma activo, usando los valores del
     * {@link MensajeInternacionalizacionHandler}.
     *
     * El valor previamente seleccionado se conserva después de aplicar la internacionalización.
     *
     * @param combo JComboBox al que se aplicará la traducción de los elementos.
     * @param mh Handler encargado de obtener los mensajes internacionalizados.
     */
    public static void aplicarInternacionalizacionRolCombo(JComboBox<ROL> combo, MensajeInternacionalizacionHandler mh) {
        ROL seleccionado = (ROL) combo.getSelectedItem();

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

        if (seleccionado != null) {
            combo.setSelectedItem(seleccionado);
        }
    }
}
