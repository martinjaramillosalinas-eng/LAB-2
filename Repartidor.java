import java.util.Objects;

public class Repartidor {
    private String id;
    private String nombre;
    private double calificacion;
    private int entregas;

    public Repartidor(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void registrarEntrega(double nota) {
        calificacion = (calificacion * entregas + nota) / (entregas + 1);
        entregas++;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public int getEntregas() {
        return entregas;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Repartidor repartidor)) return false;
        return id.equals(repartidor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nombre + " - " + String.format("%.2f", calificacion) + " - " + entregas + " entregas";
    }
}
