package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.DetallePedido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetallePedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {}
