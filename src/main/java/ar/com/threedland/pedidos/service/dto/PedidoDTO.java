package ar.com.threedland.pedidos.service.dto;

import ar.com.threedland.pedidos.domain.enumeration.EstadoPedido;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link ar.com.threedland.pedidos.domain.Pedido} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PedidoDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate fechaCreacion;

    private LocalDate fechaEntrega;

    @NotNull
    private EstadoPedido estado;

    private String detalles;

    private Modelo3DDTO modelo3d;

    private ClienteDTO cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Modelo3DDTO getModelo3d() {
        return modelo3d;
    }

    public void setModelo3d(Modelo3DDTO modelo3d) {
        this.modelo3d = modelo3d;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PedidoDTO)) {
            return false;
        }

        PedidoDTO pedidoDTO = (PedidoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pedidoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PedidoDTO{" +
            "id=" + getId() +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaEntrega='" + getFechaEntrega() + "'" +
            ", estado='" + getEstado() + "'" +
            ", detalles='" + getDetalles() + "'" +
            ", modelo3d=" + getModelo3d() +
            ", cliente=" + getCliente() +
            "}";
    }
}
