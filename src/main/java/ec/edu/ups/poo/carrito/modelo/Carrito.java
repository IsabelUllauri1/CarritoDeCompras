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

    public void agregarProducto(Producto producto, int cantidad) {

        for(ItemCarrito item : items) {
            if(item.getProducto().getCodigo() == producto.getCodigo()) {
                item.setCantidad(cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(cantidad, producto));
    }


    public void eliminarProducto(int codigoProducto) {
        Iterator<ItemCarrito> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().getProducto().getCodigo() == codigoProducto) {
                it.remove();
                break;
            }
        }
    }

    public void vaciarCarrito() {
        items.clear();
    }


    public List<ItemCarrito> obtenerItems() {
        return items;
    }


    public boolean estaVacio() {
        return items.isEmpty();
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (ItemCarrito item : items) {
            subtotal += item.getProducto().getPrecio() * item.getCantidad();
        }
        return subtotal;
    }

    public double calcularIVA() {
        double subtotal = calcularSubtotal();
        return subtotal * IVA;
    }

    public double calcularTotal() {
        return calcularSubtotal() + calcularIVA();
    }

}
