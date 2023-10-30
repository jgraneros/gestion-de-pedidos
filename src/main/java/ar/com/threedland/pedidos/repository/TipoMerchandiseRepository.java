package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.TipoMerchandise;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TipoMerchandise entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoMerchandiseRepository extends JpaRepository<TipoMerchandise, Long> {}
