package ec.edu.ups.poo.carrito.modelo;

public class ItemCarrito {
    private int cantidad;
    private Producto producto;

    public ItemCarrito(int cantidad, Producto producto) {
        setCantidad(cantidad);
        if (producto == null) {
            throw new NullPointerException("El producto no puede ser nulo.");
        }
        this.cantidad = cantidad;
        this.producto = producto;

    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
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
