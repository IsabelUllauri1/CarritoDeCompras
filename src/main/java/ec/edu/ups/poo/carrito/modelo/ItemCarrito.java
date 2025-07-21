package ec.edu.ups.poo.carrito.modelo;

public class ItemCarrito {
    private int cantidad;
    private Producto producto;
    /**
     * Crea un nuevo ítem de carrito con una cantidad y un producto asociados.
     *
     * @param cantidad Cantidad del producto (debe ser mayor a cero).
     * @param producto Producto asociado al ítem (no puede ser {@code null}).
     * @throws IllegalArgumentException si la cantidad es menor o igual a cero.
     * @throws NullPointerException si el producto es {@code null}.
     */
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
