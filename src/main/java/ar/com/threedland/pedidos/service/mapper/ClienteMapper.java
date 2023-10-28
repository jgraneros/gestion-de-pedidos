package ar.com.threedland.pedidos.service.mapper;

import ar.com.threedland.pedidos.domain.Cliente;
import ar.com.threedland.pedidos.service.dto.ClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {}
