package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.CostoPrducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CostoPrducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CostoPrductoRepository extends JpaRepository<CostoPrducto, Long> {}
