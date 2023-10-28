package ar.com.threedland.pedidos.service.mapper;

import ar.com.threedland.pedidos.domain.Modelo3D;
import ar.com.threedland.pedidos.service.dto.Modelo3DDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Modelo3D} and its DTO {@link Modelo3DDTO}.
 */
@Mapper(componentModel = "spring")
public interface Modelo3DMapper extends EntityMapper<Modelo3DDTO, Modelo3D> {}
