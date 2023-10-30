package ar.com.threedland.pedidos.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CostoPrducto.
 */
@Entity
@Table(name = "costo_prducto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CostoPrducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "costo_material")
    private Double costoMaterial;

    @Column(name = "costos_agregados")
    private Double costosAgregados;

    @Column(name = "costo_post_impresion")
    private Double costoPostImpresion;

    @JsonIgnoreProperties(value = { "producto", "producto", "detallePedidos" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "producto")
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CostoPrducto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCostoMaterial() {
        return this.costoMaterial;
    }

    public CostoPrducto costoMaterial(Double costoMaterial) {
        this.setCostoMaterial(costoMaterial);
        return this;
    }

    public void setCostoMaterial(Double costoMaterial) {
        this.costoMaterial = costoMaterial;
    }

    public Double getCostosAgregados() {
        return this.costosAgregados;
    }

    public CostoPrducto costosAgregados(Double costosAgregados) {
        this.setCostosAgregados(costosAgregados);
        return this;
    }

    public void setCostosAgregados(Double costosAgregados) {
        this.costosAgregados = costosAgregados;
    }

    public Double getCostoPostImpresion() {
        return this.costoPostImpresion;
    }

    public CostoPrducto costoPostImpresion(Double costoPostImpresion) {
        this.setCostoPostImpresion(costoPostImpresion);
        return this;
    }

    public void setCostoPostImpresion(Double costoPostImpresion) {
        this.costoPostImpresion = costoPostImpresion;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        if (this.producto != null) {
            this.producto.setProducto(null);
        }
        if (producto != null) {
            producto.setProducto(this);
        }
        this.producto = producto;
    }

    public CostoPrducto producto(Producto producto) {
        this.setProducto(producto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CostoPrducto)) {
            return false;
        }
        return getId() != null && getId().equals(((CostoPrducto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CostoPrducto{" +
            "id=" + getId() +
            ", costoMaterial=" + getCostoMaterial() +
            ", costosAgregados=" + getCostosAgregados() +
            ", costoPostImpresion=" + getCostoPostImpresion() +
            "}";
    }
}
