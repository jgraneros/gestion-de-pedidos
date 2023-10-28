package ar.com.threedland.pedidos.service.mapper;

import ar.com.threedland.pedidos.domain.Cliente;
import ar.com.threedland.pedidos.domain.Modelo3D;
import ar.com.threedland.pedidos.domain.Pedido;
import ar.com.threedland.pedidos.service.dto.ClienteDTO;
import ar.com.threedland.pedidos.service.dto.Modelo3DDTO;
import ar.com.threedland.pedidos.service.dto.PedidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pedido} and its DTO {@link PedidoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PedidoMapper extends EntityMapper<PedidoDTO, Pedido> {
    @Mapping(target = "modelo3d", source = "modelo3d", qualifiedByName = "modelo3DId")
    @Mapping(target = "cliente", source = "cliente", qualifiedByName = "clienteNombre")
    PedidoDTO toDto(Pedido s);

    @Named("modelo3DId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Modelo3DDTO toDtoModelo3DId(Modelo3D modelo3D);

    @Named("clienteNombre")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    ClienteDTO toDtoClienteNombre(Cliente cliente);
}
