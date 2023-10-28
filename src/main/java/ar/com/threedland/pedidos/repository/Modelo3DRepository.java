package ar.com.threedland.pedidos.repository;

import ar.com.threedland.pedidos.domain.Modelo3D;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Modelo3D entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Modelo3DRepository extends JpaRepository<Modelo3D, Long> {}
