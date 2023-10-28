package ar.com.threedland.pedidos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Modelo3D.
 */
@Entity
@Table(name = "modelo_3_d")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Modelo3D implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "archivo", nullable = false)
    private String archivo;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Modelo3D id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Modelo3D nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArchivo() {
        return this.archivo;
    }

    public Modelo3D archivo(String archivo) {
        this.setArchivo(archivo);
        return this;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Modelo3D descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modelo3D)) {
            return false;
        }
        return getId() != null && getId().equals(((Modelo3D) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Modelo3D{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
