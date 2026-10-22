import java.util.Objects;

public abstract class Producto {
    private String id;
    private String nombre;

    public Producto(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public abstract double getPrecio();

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Producto producto)) return false;
        return id.equals(producto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre + " $" + (int) getPrecio();
    }
}
