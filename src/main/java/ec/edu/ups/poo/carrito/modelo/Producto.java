package ec.edu.ups.poo.carrito.modelo;

import ec.edu.ups.poo.carrito.util.exception.ValidacionException;

import java.io.Serializable;

public class Producto implements Serializable {
    private String nombre;
    private double precio;
    private int codigo;

    public Producto(String nombre, int codigo, double precio) {
        setNombre(nombre);
        this.codigo = codigo;
        setPrecio(precio);
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new ValidacionException("El nombre del producto no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new ValidacionException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }


    public void setCodigo(int codigo) {
        if (codigo <= 0) {
            throw new IllegalArgumentException("El código debe ser un número positivo.");
        }
        this.codigo = codigo;
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
