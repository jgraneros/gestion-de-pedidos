package ar.com.threedland.pedidos.domain;

import ar.com.threedland.pedidos.domain.enumeration.EstadoPedido;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
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

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoPedido estado;

    @Column(name = "detalles")
    private String detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    private Modelo3D modelo3d;

    @ManyToOne(optional = false)
    @NotNull
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

    public LocalDate getFechaCreacion() {
        return this.fechaCreacion;
    }

    public Pedido fechaCreacion(LocalDate fechaCreacion) {
        this.setFechaCreacion(fechaCreacion);
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEntrega() {
        return this.fechaEntrega;
    }

    public Pedido fechaEntrega(LocalDate fechaEntrega) {
        this.setFechaEntrega(fechaEntrega);
        return this;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoPedido getEstado() {
        return this.estado;
    }

    public Pedido estado(EstadoPedido estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getDetalles() {
        return this.detalles;
    }

    public Pedido detalles(String detalles) {
        this.setDetalles(detalles);
        return this;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Modelo3D getModelo3d() {
        return this.modelo3d;
    }

    public void setModelo3d(Modelo3D modelo3D) {
        this.modelo3d = modelo3D;
    }

    public Pedido modelo3d(Modelo3D modelo3D) {
        this.setModelo3d(modelo3D);
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
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", estado='" + getEstado() + "'" +
            ", detalles='" + getDetalles() + "'" +
            "}";
    }
}
