import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reportes {
    private Reportes() {
    }

    private static Stream<Pedido> validos(List<Pedido> pedidos) {
        return pedidos.stream().filter(p -> p.getEstado() != EstadoPedido.CANCELADO);
    }

    public static Map<String, Long> topPlatos(List<Pedido> pedidos, LocalDate desde, int cantidad) {
        return validos(pedidos)
                .filter(p -> !p.getFecha().isBefore(desde))
                .flatMap(p -> p.getProductos().stream())
                .flatMap(p -> p instanceof Combo combo ? combo.getProductos().stream() : Stream.of(p))
                .filter(p -> p instanceof Plato)
                .collect(Collectors.groupingBy(Producto::getNombre, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .limit(cantidad)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }

    public static Map<String, Double> facturacionPorSede(List<Pedido> pedidos) {
        return validos(pedidos)
                .collect(Collectors.groupingBy(Pedido::getSede, TreeMap::new, Collectors.summingDouble(Pedido::getTotal)));
    }

    public static Optional<Pedido> pedidoMasCaro(List<Pedido> pedidos, Cliente cliente) {
        return validos(pedidos)
                .filter(p -> p.getCliente().equals(cliente))
                .max(Comparator.comparingDouble(Pedido::getTotal));
    }

    public static List<Repartidor> mejoresRepartidores(List<Repartidor> repartidores, int cantidad) {
        Comparator<Repartidor> criterio = Comparator.comparingDouble(Repartidor::getCalificacion).reversed()
                .thenComparing(Comparator.comparingInt(Repartidor::getEntregas).reversed());
        return Calculos.ordenar(repartidores, criterio).stream().limit(cantidad).toList();
    }

    public static <K> Map<K, Double> totalPor(List<Pedido> pedidos, Predicate<? super Pedido> filtro, Function<? super Pedido, ? extends K> grupo) {
        return validos(pedidos)
                .filter(filtro)
                .collect(Collectors.groupingBy(grupo, Collectors.summingDouble(Pedido::getTotal)));
    }
}
