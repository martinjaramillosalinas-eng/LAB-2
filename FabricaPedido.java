import java.time.LocalDate;

public class FabricaPedido {
    private static int consecutivo = 0;

    public static Pedido crear(Cliente cliente, String sede, Canal canal) {
        consecutivo++;
        cliente.agregarCanal(canal);
        Pedido pedido = new Pedido("P" + consecutivo, cliente, sede, canal, LocalDate.now());
        pedido.agregarObservador(new Cocina());
        pedido.agregarObservador(crearNotificacion(canal));
        return pedido;
    }

    private static Observador crearNotificacion(Canal canal) {
        return switch (canal) {
            case APP -> new NotificacionCliente("Push");
            case WHATSAPP -> new NotificacionCliente("WhatsApp");
            case TELEFONO -> new NotificacionCliente("SMS");
        };
    }
}
