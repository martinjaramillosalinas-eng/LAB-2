public class NotificacionCliente implements Observador {
    private String medio;

    public NotificacionCliente(String medio) {
        this.medio = medio;
    }

    @Override
    public void actualizar(Pedido pedido) {
        System.out.println("  [" + medio + " a " + pedido.getCliente().getNombre() + "] tu pedido " + pedido.getId() + " está " + pedido.getEstado());
    }
}
