public class DescuentoPorcentaje implements Promocion {
    private double porcentaje;

    public DescuentoPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularDescuento(Pedido pedido) {
        return pedido.getSubtotal() * porcentaje / 100;
    }
}
