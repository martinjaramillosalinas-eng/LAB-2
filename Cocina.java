public class Cocina implements Observador {

    @Override
    public void actualizar(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.EN_PREPARACION) {
            System.out.println("  [Cocina " + pedido.getSede() + "] preparar " + pedido.getId() + ": " + pedido.getProductos());
        } else if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            System.out.println("  [Cocina " + pedido.getSede() + "] cancelar " + pedido.getId());
        }
    }
}
