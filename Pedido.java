import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pedido {
    private String id;
    private Cliente cliente;
    private String sede;
    private Canal canal;
    private LocalDate fecha;
    private EstadoPedido estado = EstadoPedido.RECIBIDO;
    private List<Producto> productos = new ArrayList<>();
    private List<Observador> observadores = new ArrayList<>();
    private Promocion promocion = pedido -> 0;
    private Repartidor repartidor;

    public Pedido(String id, Cliente cliente, String sede, Canal canal, LocalDate fecha) {
        this.id = id;
        this.cliente = cliente;
        this.sede = sede;
        this.canal = canal;
        this.fecha = fecha;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            productos.add(producto);
        }
    }

    public void agregarObservador(Observador observador) {
        observadores.add(observador);
    }

    public void cambiarEstado(EstadoPedido nuevo) {
        if (estado == EstadoPedido.ENTREGADO || estado == EstadoPedido.CANCELADO) {
            System.out.println("El pedido " + id + " ya está " + estado + ", no se puede cambiar");
            return;
        }
        estado = nuevo;
        for (Observador observador : observadores) {
            observador.actualizar(this);
        }
    }

    public void asignarRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
        agregarObservador(new NotificacionRepartidor(repartidor));
    }

    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    public double getSubtotal() {
        return Calculos.calcularTotal(productos);
    }

    public double getTotal() {
        return getSubtotal() - promocion.calcularDescuento(this);
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getSede() {
        return sede;
    }

    public Canal getCanal() {
        return canal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public Optional<Repartidor> getRepartidor() {
        return Optional.ofNullable(repartidor);
    }

    @Override
    public String toString() {
        return id + " " + cliente.getNombre() + " " + sede + " " + canal + " " + estado + " $" + (int) getTotal();
    }
}
