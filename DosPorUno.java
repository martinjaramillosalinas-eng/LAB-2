public class DosPorUno implements Promocion {
    private Class<? extends Producto> tipo;

    public DosPorUno(Class<? extends Producto> tipo) {
        this.tipo = tipo;
    }

    @Override
    public double calcularDescuento(Pedido pedido) {
        double[] precios = pedido.getProductos().stream()
                .filter(tipo::isInstance)
                .mapToDouble(Producto::getPrecio)
                .sorted()
                .toArray();
        double descuento = 0;
        for (int i = 0; i < precios.length / 2; i++) {
            descuento += precios[i];
        }
        return descuento;
    }
}
