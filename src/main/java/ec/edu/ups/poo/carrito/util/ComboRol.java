package ec.edu.ups.poo.carrito.util;

import ec.edu.ups.poo.carrito.modelo.ROL;

import javax.swing.*;
import java.awt.*;

public class ComboRol {

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
