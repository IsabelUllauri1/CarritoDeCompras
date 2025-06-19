package ec.edu.ups.poo.carrito.modelo;

public class Producto {
    private String nombre;
    private double precio;
    private int codigo;

    public Producto(String nombre, int codigo, double precio) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.precio = precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }


    public double getPrecio() {
        return precio;
    }


    public int getCodigo() {
        return codigo;
    }



    @Override
    public String toString() {
        return "Producto: "  + nombre + '\'' +
                ", precio: " + precio +
                ", codigo: '" + codigo ;
    }
}
