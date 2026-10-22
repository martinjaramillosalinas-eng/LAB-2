import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class Main {
    static Repositorio<String, Producto> menu = new Repositorio<>(Producto::getId);
    static Repositorio<String, Cliente> clientes = new Repositorio<>(Cliente::getDocumento);
    static Repositorio<String, Repartidor> repartidores = new Repositorio<>(Repartidor::getId);
    static Repositorio<String, Pedido> pedidos = new Repositorio<>(Pedido::getId);

    public static void main(String[] args) {
        cargarDatos();

        System.out.println("=== Clientes ===");
        registrarCliente("1036111222", "Laura Ríos", "Cl 10 # 32-15", Canal.APP);
        registrarCliente("1.036.111.222", "Laura Ríos", "Cl 10 # 32-15", Canal.WHATSAPP);
        registrarCliente(" 1036111222 ", "Laura Ríos", "Cl 10 # 32-15", Canal.TELEFONO);
        clientes.listar().forEach(System.out::println);
        System.out.println("Buscar 1036111222: " + clientes.buscar("1036111222").map(Cliente::getNombre).orElse("no existe"));
        System.out.println("Buscar 999: " + clientes.buscar("999").map(Cliente::getNombre).orElse("no existe"));
        System.out.println("Buscar plato PL9: " + menu.buscar("PL9").map(Producto::getNombre).orElse("no existe"));

        System.out.println("\n=== Menú por precio desc y nombre ===");
        Calculos.ordenar(menu.listar(), Comparator.comparing(Producto::getPrecio).reversed().thenComparing(Producto::getNombre))
                .forEach(System.out::println);

        System.out.println("\n=== Repartidores por nombre ===");
        Calculos.ordenar(repartidores.listar(), Comparator.comparing(Repartidor::getNombre)).forEach(System.out::println);

        System.out.println("\n=== Cálculos genéricos ===");
        List<Plato> platos = List.of((Plato) menu.buscar("PL1").orElseThrow(), (Plato) menu.buscar("PL2").orElseThrow());
        List<Bebida> bebidas = List.of((Bebida) menu.buscar("BE1").orElseThrow(), (Bebida) menu.buscar("BE2").orElseThrow());
        System.out.println("Total platos: " + Calculos.calcularTotal(platos));
        System.out.println("Total bebidas: " + Calculos.calcularTotal(bebidas));
        System.out.println("Más caro del menú: " + Calculos.masCaro(menu.listar()).map(Producto::getNombre).orElse("ninguno"));

        System.out.println("\n=== Pedido nuevo ===");
        Cliente laura = clientes.buscar("1036111222").orElseThrow();
        Pedido pedido = pedidos.guardar(FabricaPedido.crear(laura, "Poblado", Canal.WHATSAPP));
        pedido.agregarProducto(menu.buscar("PL1").orElseThrow(), 1);
        pedido.agregarProducto(menu.buscar("CB1").orElseThrow(), 1);
        pedido.agregarProducto(menu.buscar("BE1").orElseThrow(), 2);
        pedido.agregarObservador(p -> {
            if (p.getEstado() == EstadoPedido.ENTREGADO) {
                System.out.println("  [Encuesta] enviada a " + p.getCliente().getNombre());
            }
        });
        System.out.println("Repartidor: " + pedido.getRepartidor().map(Repartidor::getNombre).orElse("sin asignar"));
        Repartidor julian = repartidores.buscar("R3").orElseThrow();
        pedido.asignarRepartidor(julian);
        pedido.cambiarEstado(EstadoPedido.EN_PREPARACION);
        pedido.cambiarEstado(EstadoPedido.EN_CAMINO);
        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
        julian.registrarEntrega(5);
        pedido.cambiarEstado(EstadoPedido.CANCELADO);

        System.out.println("\n=== Promociones ===");
        System.out.println("Subtotal: " + pedido.getSubtotal());
        pedido.setPromocion(new DescuentoPorcentaje(10));
        System.out.println("10%: " + pedido.getTotal());
        pedido.setPromocion(new DosPorUno(Bebida.class));
        System.out.println("2x1 bebidas: " + pedido.getTotal());
        pedido.setPromocion(p -> p.getSubtotal() > 70000 ? 5000 : 0);
        System.out.println("$5000 en compras mayores a $70000: " + pedido.getTotal());

        System.out.println("\n=== Reportes ===");
        System.out.println("Top 3 platos de la semana: " + Reportes.topPlatos(pedidos.listar(), LocalDate.now().minusDays(7), 3));
        System.out.println("Facturación por sede: " + Reportes.facturacionPorSede(pedidos.listar()));
        System.out.println("Pedido más caro de Laura: " + Reportes.pedidoMasCaro(pedidos.listar(), laura).map(Pedido::toString).orElse("no tiene"));
        System.out.println("Mejores repartidores:");
        Reportes.mejoresRepartidores(repartidores.listar(), 4).forEach(r -> System.out.println("  " + r));
        System.out.println("Total por canal de pedidos mayores a $50000: "
                + Reportes.totalPor(pedidos.listar(), p -> p.getTotal() > 50000, Pedido::getCanal));
    }

    static Cliente registrarCliente(String documento, String nombre, String direccion, Canal canal) {
        Cliente cliente = clientes.guardar(new Cliente(documento, nombre, direccion));
        cliente.agregarCanal(canal);
        return cliente;
    }

    static void cargarDatos() {
        Plato bandeja = new Plato("PL1", "Bandeja Paisa", 32000);
        Plato hamburguesa = new Plato("PL2", "Hamburguesa", 24000);
        Plato ajiaco = new Plato("PL3", "Ajiaco", 28000);
        Plato pizza = new Plato("PL4", "Pizza", 30000);
        Bebida limonada = new Bebida("BE1", "Limonada", 9000, 400);
        Bebida gaseosa = new Bebida("BE2", "Gaseosa", 5000, 350);
        List.of(bandeja, hamburguesa, ajiaco, pizza, limonada, gaseosa,
                new Combo("CB1", "Combo Hamburguesa", 10, hamburguesa, gaseosa)).forEach(menu::guardar);

        List.of(new Repartidor("R1", "Carlos"), new Repartidor("R2", "Ana"),
                new Repartidor("R3", "Julián"), new Repartidor("R4", "Sofía")).forEach(repartidores::guardar);

        Cliente mateo = registrarCliente("71555666", "Mateo Zapata", "Cra 80 # 33-40", Canal.APP);
        Cliente valentina = registrarCliente("43999888", "Valentina Osorio", "Cl 33 # 74-10", Canal.TELEFONO);

        historico("H1", mateo, "Laureles", Canal.APP, 1, "R1", 5, bandeja, bandeja, limonada);
        historico("H2", valentina, "Poblado", Canal.TELEFONO, 2, "R2", 4.5, pizza, hamburguesa, menu.buscar("CB1").orElseThrow());
        historico("H3", mateo, "Envigado", Canal.APP, 3, "R2", 5, bandeja, pizza, gaseosa);
        historico("H4", valentina, "Laureles", Canal.TELEFONO, 4, "R1", 4.5, hamburguesa, limonada);
        historico("H5", mateo, "Poblado", Canal.APP, 12, "R4", 5, ajiaco, ajiaco, ajiaco, ajiaco);
        historico("H6", valentina, "Envigado", Canal.TELEFONO, 5, "R2", 4.75, ajiaco, gaseosa);
    }

    static void historico(String id, Cliente cliente, String sede, Canal canal, int diasAtras, String idRepartidor, double nota, Producto... productos) {
        Pedido pedido = new Pedido(id, cliente, sede, canal, LocalDate.now().minusDays(diasAtras));
        for (Producto producto : productos) {
            pedido.agregarProducto(producto, 1);
        }
        repartidores.buscar(idRepartidor).ifPresent(r -> {
            pedido.asignarRepartidor(r);
            r.registrarEntrega(nota);
        });
        pedido.cambiarEstado(EstadoPedido.ENTREGADO);
        pedidos.guardar(pedido);
    }
}
