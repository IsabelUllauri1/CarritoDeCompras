package ec.edu.ups.poo.carrito.modelo;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

public class Carrito {

    private final double IVA = 0.12;
    private static int contador = 1;
    private int codigo;
    private GregorianCalendar fechaCreacion;
    private List<ItemCarrito> items;
    private Usuario usuario;
    /**
     * Constructor por defecto que inicializa el carrito con una fecha de creación actual,
     * una lista vacía de ítems y un código autoincremental.
     */
    public Carrito() {
        this.items        = new ArrayList<>();
        this.fechaCreacion= new GregorianCalendar();
        this.codigo       = contador++;
    }


    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public double getIVA() {
        return IVA;
    }
    public List<ItemCarrito> getItems() {
        return items;
    }
    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }
    public GregorianCalendar getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(GregorianCalendar fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public static int getContador() {
        return contador;
    }
    public static void setContador(int contador) {
        Carrito.contador = contador;
    }

    /**
     * Agrega un producto al carrito con la cantidad especificada.
     * Si el producto ya existe en el carrito, se actualiza su cantidad.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad del producto.
     * @throws NullPointerException si el producto es {@code null}.
     * @throws IllegalArgumentException si la cantidad es menor o igual a cero.
     */
    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null) {
            throw new NullPointerException("El producto no puede ser nulo.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }

        for(ItemCarrito item : items) {
            if(item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(cantidad, producto));
    }

    /**
     * Elimina del carrito el producto cuyo código coincida con el especificado.
     *
     * @param codigoProducto Código del producto a eliminar.
     */
    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }
    /**
     * Elimina todos los productos del carrito.
     */
    public void vaciarCarrito() {
        items.clear();
    }

    /**
     * Devuelve la lista actual de ítems del carrito.
     *
     * @return Lista de {@code ItemCarrito}.
     */
    public List<ItemCarrito> obtenerItems() {
        return items;
    }

    /**
     * Verifica si el carrito está vacío.
     *
     * @return {@code true} si no contiene productos, de lo contrario {@code false}.
     */
    public boolean estaVacio() {
        return items.isEmpty();
    }
    /**
     * Calcula el subtotal del carrito, sin incluir IVA.
     *
     * @return Subtotal acumulado de todos los productos.
     */
    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }
    /**
     * Calcula el IVA basado en el subtotal del carrito.
     *
     * @return Valor del IVA calculado.
     */
    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }
    /**
     * Calcula el total a pagar, sumando el subtotal más el IVA.
     *
     * @return Total del carrito.
     */
    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

}
