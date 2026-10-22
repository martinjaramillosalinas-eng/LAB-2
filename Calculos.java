import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Calculos {
    private Calculos() {
    }

    public static double calcularTotal(List<? extends Producto> productos) {
        return productos.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }

    public static Optional<? extends Producto> masCaro(List<? extends Producto> productos) {
        return productos.stream()
                .max(Comparator.comparingDouble(Producto::getPrecio));
    }

    public static <T> List<T> ordenar(List<? extends T> lista, Comparator<? super T> criterio) {
        List<T> copia = new ArrayList<>(lista);
        copia.sort(criterio);
        return copia;
    }
}
