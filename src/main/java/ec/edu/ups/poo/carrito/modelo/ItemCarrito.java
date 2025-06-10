package ec.edu.ups.poo.carrito.modelo;

public class ItemCarrito {
    private int cantidad;
    private Producto producto;

    public ItemCarrito(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Producto getProducto() {
        return producto;
    }



    public double getSubtotal() {
     return producto.getPrecio()*cantidad;
    }

    @Override
    public String toString() {
        return "ItemCarrito->" +
                "cantidad: " + cantidad +
                ", producto: " + producto + ", subtotal: " + getSubtotal();
    }
}
