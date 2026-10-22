import java.util.ArrayList;
import java.util.List;

public class Combo extends Producto {
    private double descuento;
    private List<Producto> productos = new ArrayList<>();

    public Combo(String id, String nombre, double descuento, Producto... productos) {
        super(id, nombre);
        this.descuento = descuento;
        this.productos.addAll(List.of(productos));
    }

    public List<Producto> getProductos() {
        return productos;
    }

    @Override
    public double getPrecio() {
        double suma = Calculos.calcularTotal(productos);
        return suma - suma * descuento / 100;
    }
}
