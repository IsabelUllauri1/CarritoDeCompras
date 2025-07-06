package ec.edu.ups.poo.carrito.util;

import ec.edu.ups.poo.carrito.modelo.Rol;

import javax.swing.*;
import java.awt.*;

public class ComboRol {

    public static void aplicarInternacionalizacionRolCombo(JComboBox<Rol> combo, MensajeInternacionalizacionHandler mh) {
        Rol seleccionado = (Rol) combo.getSelectedItem();

        DefaultComboBoxModel<Rol> modelo = new DefaultComboBoxModel<>();
        modelo.addElement(Rol.ADMINISTRADOR);
        modelo.addElement(Rol.USUARIO);
        combo.setModel(modelo);

        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String texto = switch ((Rol) value) {
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
