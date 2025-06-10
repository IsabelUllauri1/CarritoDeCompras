package ec.edu.ups.poo.carrito.modelo;

import java.util.ArrayList;
import java.util.List;

public class CarritoGeneral {
    private List<ItemCarrito> itemCarrito;

    public CarritoGeneral() {
        this.itemCarrito = new ArrayList<>();
    }

    public void agregarItemCarrito(Producto producto, int cantidad ) {

        itemCarrito.add(new ItemCarrito(cantidad, producto));
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemCarrito itemCarrito : itemCarrito) {
            total += itemCarrito.getSubtotal();

        }
        return total;
    }
}

