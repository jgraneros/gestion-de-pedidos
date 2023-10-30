package ar.com.threedland.pedidos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DetallePedido.
 */
@Entity
@Table(name = "detalle_pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DetallePedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "sub_total_costo")
    private Double subTotalCosto;

    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "producto", "producto", "productos" }, allowSetters = true)
    private Producto detalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pedidos", "pedidos", "cliente" }, allowSetters = true)
    private Pedido detalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "pedidos", "pedidos", "cliente" }, allowSetters = true)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "producto", "producto", "productos" }, allowSetters = true)
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DetallePedido id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public DetallePedido cantidad(Integer cantidad) {
        this.setCantidad(cantidad);
        return this;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getSubTotalCosto() {
        return this.subTotalCosto;
    }

    public DetallePedido subTotalCosto(Double subTotalCosto) {
        this.setSubTotalCosto(subTotalCosto);
        return this;
    }

    public void setSubTotalCosto(Double subTotalCosto) {
        this.subTotalCosto = subTotalCosto;
    }

    public Producto getDetalle() {
        return this.detalle;
    }

    public void setDetalle(Producto producto) {
        this.detalle = producto;
    }

    public DetallePedido detalle(Producto producto) {
        this.setDetalle(producto);
        return this;
    }

    public Pedido getDetalle() {
        return this.detalle;
    }

    public void setDetalle(Pedido pedido) {
        this.detalle = pedido;
    }

    public DetallePedido detalle(Pedido pedido) {
        this.setDetalle(pedido);
        return this;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public DetallePedido pedido(Pedido pedido) {
        this.setPedido(pedido);
        return this;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public DetallePedido producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetallePedido)) {
            return false;
        }
        return getId() != null && getId().equals(((DetallePedido) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DetallePedido{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            ", subTotalCosto=" + getSubTotalCosto() +
            "}";
    }
}
