package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.DetallePedido;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DetallePedido entity.
 */
@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    default Optional<DetallePedido> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<DetallePedido> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<DetallePedido> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select detallePedido from DetallePedido detallePedido left join fetch detallePedido.pedido left join fetch detallePedido.producto left join fetch detallePedido.pedido left join fetch detallePedido.producto",
        countQuery = "select count(detallePedido) from DetallePedido detallePedido"
    )
    Page<DetallePedido> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select detallePedido from DetallePedido detallePedido left join fetch detallePedido.pedido left join fetch detallePedido.producto left join fetch detallePedido.pedido left join fetch detallePedido.producto"
    )
    List<DetallePedido> findAllWithToOneRelationships();

    @Query(
        "select detallePedido from DetallePedido detallePedido left join fetch detallePedido.pedido left join fetch detallePedido.producto left join fetch detallePedido.pedido left join fetch detallePedido.producto where detallePedido.id =:id"
    )
    Optional<DetallePedido> findOneWithToOneRelationships(@Param("id") Long id);
}
