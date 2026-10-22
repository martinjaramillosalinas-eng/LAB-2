public class NotificacionRepartidor implements Observador {
    private Repartidor repartidor;

    public NotificacionRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    @Override
    public void actualizar(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.EN_CAMINO) {
            System.out.println("  [" + repartidor.getNombre() + "] llevar " + pedido.getId() + " a " + pedido.getCliente().getDireccion());
        }
    }
}
