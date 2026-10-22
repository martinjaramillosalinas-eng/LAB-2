public class Plato extends Producto {
    private double precio;

    public Plato(String id, String nombre, double precio) {
        super(id, nombre);
        this.precio = precio;
    }

    @Override
    public double getPrecio() {
        return precio;
    }
}
