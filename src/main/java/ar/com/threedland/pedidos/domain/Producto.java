package ar.com.threedland.pedidos.domain;

import ar.com.threedland.pedidos.domain.enumeration.TipoProducto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "peso")
    private Float peso;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoProducto tipo;

    @JsonIgnoreProperties(value = { "producto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnoreProperties(value = { "producto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CostoPrducto producto;

    @JsonIgnoreProperties(value = { "producto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnoreProperties(value = { "producto" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private TipoMerchandise producto;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "producto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pedido", "producto", "pedido", "producto" }, allowSetters = true)
    private Set<DetallePedido> detallePedidos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Producto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Producto nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getPeso() {
        return this.peso;
    }

    public Producto peso(Float peso) {
        this.setPeso(peso);
        return this;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public TipoProducto getTipo() {
        return this.tipo;
    }

    public Producto tipo(TipoProducto tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public CostoPrducto getProducto() {
        return this.producto;
    }

    public void setProducto(CostoPrducto costoPrducto) {
        this.producto = costoPrducto;
    }

    public Producto producto(CostoPrducto costoPrducto) {
        this.setProducto(costoPrducto);
        return this;
    }

    public TipoMerchandise getProducto() {
        return this.producto;
    }

    public void setProducto(TipoMerchandise tipoMerchandise) {
        this.producto = tipoMerchandise;
    }

    public Producto producto(TipoMerchandise tipoMerchandise) {
        this.setProducto(tipoMerchandise);
        return this;
    }

    public Set<DetallePedido> getDetallePedidos() {
        return this.detallePedidos;
    }

    public void setDetallePedidos(Set<DetallePedido> detallePedidos) {
        if (this.detallePedidos != null) {
            this.detallePedidos.forEach(i -> i.setProducto(null));
        }
        if (detallePedidos != null) {
            detallePedidos.forEach(i -> i.setProducto(this));
        }
        this.detallePedidos = detallePedidos;
    }

    public Producto detallePedidos(Set<DetallePedido> detallePedidos) {
        this.setDetallePedidos(detallePedidos);
        return this;
    }

    public Producto addDetallePedido(DetallePedido detallePedido) {
        this.detallePedidos.add(detallePedido);
        detallePedido.setProducto(this);
        return this;
    }

    public Producto removeDetallePedido(DetallePedido detallePedido) {
        this.detallePedidos.remove(detallePedido);
        detallePedido.setProducto(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return getId() != null && getId().equals(((Producto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", peso=" + getPeso() +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
