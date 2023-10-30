package ar.com.threedland.pedidos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Pedido.
 */
@Entity
@Table(name = "pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "numero_pedido")
    private Long numeroPedido;

    @Column(name = "costo_total")
    private Double costoTotal;

    @Column(name = "precio_venta")
    private Double precioVenta;

    @Column(name = "ganancia_total")
    private Double gananciaTotal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedido", "producto" }, allowSetters = true)
    private Set<DetallePedido> detallePedidos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedido" }, allowSetters = true)
    private Set<EstadoPedido> estadoPedidos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pedidos" }, allowSetters = true)
    private Cliente cliente;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Pedido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumeroPedido() {
        return this.numeroPedido;
    }

    public Pedido numeroPedido(Long numeroPedido) {
        this.setNumeroPedido(numeroPedido);
        return this;
    }

    public void setNumeroPedido(Long numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public Double getCostoTotal() {
        return this.costoTotal;
    }

    public Pedido costoTotal(Double costoTotal) {
        this.setCostoTotal(costoTotal);
        return this;
    }

    public void setCostoTotal(Double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Double getPrecioVenta() {
        return this.precioVenta;
    }

    public Pedido precioVenta(Double precioVenta) {
        this.setPrecioVenta(precioVenta);
        return this;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getGananciaTotal() {
        return this.gananciaTotal;
    }

    public Pedido gananciaTotal(Double gananciaTotal) {
        this.setGananciaTotal(gananciaTotal);
        return this;
    }

    public void setGananciaTotal(Double gananciaTotal) {
        this.gananciaTotal = gananciaTotal;
    }

    public Set<DetallePedido> getDetallePedidos() {
        return this.detallePedidos;
    }

    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        if (this.detallePedidos != null) {
            this.detallePedidos.forEach(i -> i.setPedido(null));
        }
        if (detallePedidos != null) {
            detallePedidos.forEach(i -> i.setPedido(this));
        }
        this.detallePedidos = detallePedidos;
    }

    public Pedido detallePedidos(Set<DetallePedido> detallePedidos) {
        this.setDetallePedidos(detallePedidos);
        return this;
    }

    public Pedido addDetallePedido(DetallePedido detallePedido) {
        this.detallePedidos.add(detallePedido);
        detallePedido.setPedido(this);
        return this;
    }

    public Pedido removeDetallePedido(DetallePedido detallePedido) {
        this.detallePedidos.remove(detallePedido);
        detallePedido.setPedido(null);
        return this;
    }

    public Set<EstadoPedido> getEstadoPedidos() {
        return this.estadoPedidos;
    }

    public void setEstadoPedidos(Set<EstadoPedido> estadoPedidos) {
        if (this.estadoPedidos != null) {
            this.estadoPedidos.forEach(i -> i.setPedido(null));
        }
        if (estadoPedidos != null) {
            estadoPedidos.forEach(i -> i.setPedido(this));
        }
        this.estadoPedidos = estadoPedidos;
    }

    public Pedido estadoPedidos(Set<EstadoPedido> estadoPedidos) {
        this.setEstadoPedidos(estadoPedidos);
        return this;
    }

    public Pedido addEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedidos.add(estadoPedido);
        estadoPedido.setPedido(this);
        return this;
    }

    public Pedido removeEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedidos.remove(estadoPedido);
        estadoPedido.setPedido(null);
        return this;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pedido cliente(Cliente cliente) {
        this.setCliente(cliente);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pedido)) {
            return false;
        }
        return getId() != null && getId().equals(((Pedido) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Pedido{" +
            "id=" + getId() +
            ", numeroPedido=" + getNumeroPedido() +
            ", costoTotal=" + getCostoTotal() +
            ", precioVenta=" + getPrecioVenta() +
            ", gananciaTotal=" + getGananciaTotal() +
            "}";
    }
}
