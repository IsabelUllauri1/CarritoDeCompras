package ec.edu.ups.poo.carrito;

import ec.edu.ups.poo.carrito.modelo.CarritoGeneral;
import ec.edu.ups.poo.carrito.modelo.Producto;

public class Main {
    public static void main(String[] args) {
        Producto prod1= new Producto("Carne",101, 9.00 );
        Producto prod2= new Producto("Galletas",201, 3.00 );
        Producto prod3= new Producto("Gelatina",301, 0.99 );

        CarritoGeneral CG= new CarritoGeneral();
        CG.agregarItemCarrito(prod1,1);
        CG.agregarItemCarrito(prod2,2);
        CG.agregarItemCarrito(prod3,3);

        System.out.println("Carrito de compras \nTotal: $" + CG.calcularTotal());

    }
}
