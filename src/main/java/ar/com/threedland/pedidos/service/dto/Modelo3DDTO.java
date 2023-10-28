package ar.com.threedland.pedidos.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.threedland.pedidos.domain.Modelo3D} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Modelo3DDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String archivo;

    @Lob
    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modelo3DDTO)) {
            return false;
        }

        Modelo3DDTO modelo3DDTO = (Modelo3DDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, modelo3DDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Modelo3DDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", archivo='" + getArchivo() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
