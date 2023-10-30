package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.EstadoPedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadoPedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long> {}
