import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Cliente {
    private String documento;
    private String nombre;
    private String direccion;
    private Set<Canal> canales = EnumSet.noneOf(Canal.class);

    public Cliente(String documento, String nombre, String direccion) {
        this.documento = documento.replaceAll("[^0-9]", "");
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public void agregarCanal(Canal canal) {
        canales.add(canal);
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public Set<Canal> getCanales() {
        return canales;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cliente cliente)) return false;
        return documento.equals(cliente.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    @Override
    public String toString() {
        return nombre + " (" + documento + ") " + canales;
    }
}
