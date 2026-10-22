public class Bebida extends Producto {
    private double precio;
    private int mililitros;

    public Bebida(String id, String nombre, double precio, int mililitros) {
        super(id, nombre);
        this.precio = precio;
        this.mililitros = mililitros;
    }

    @Override
    public double getPrecio() {
        return precio;
    }

    public int getMililitros() {
        return mililitros;
    }
}
