package ar.com.threedland.pedidos.service;

import ar.com.threedland.pedidos.domain.Pedido;
import ar.com.threedland.pedidos.repository.PedidoRepository;
import ar.com.threedland.pedidos.service.dto.PedidoDTO;
import ar.com.threedland.pedidos.service.mapper.PedidoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.threedland.pedidos.domain.Pedido}.
 */
@Service
@Transactional
public class PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;

    private final PedidoMapper pedidoMapper;

    public PedidoService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
    }

    /**
     * Save a pedido.
     *
     * @param pedidoDTO the entity to save.
     * @return the persisted entity.
     */
    public PedidoDTO save(PedidoDTO pedidoDTO) {
        log.debug("Request to save Pedido : {}", pedidoDTO);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    /**
     * Update a pedido.
     *
     * @param pedidoDTO the entity to save.
     * @return the persisted entity.
     */
    public PedidoDTO update(PedidoDTO pedidoDTO) {
        log.debug("Request to update Pedido : {}", pedidoDTO);
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDto(pedido);
    }

    /**
     * Partially update a pedido.
     *
     * @param pedidoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PedidoDTO> partialUpdate(PedidoDTO pedidoDTO) {
        log.debug("Request to partially update Pedido : {}", pedidoDTO);

        return pedidoRepository
            .findById(pedidoDTO.getId())
            .map(existingPedido -> {
                pedidoMapper.partialUpdate(existingPedido, pedidoDTO);

                return existingPedido;
            })
            .map(pedidoRepository::save)
            .map(pedidoMapper::toDto);
    }

    /**
     * Get all the pedidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PedidoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAll(pageable).map(pedidoMapper::toDto);
    }

    /**
     * Get all the pedidos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PedidoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return pedidoRepository.findAllWithEagerRelationships(pageable).map(pedidoMapper::toDto);
    }

    /**
     * Get one pedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PedidoDTO> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findOneWithEagerRelationships(id).map(pedidoMapper::toDto);
    }

    /**
     * Delete the pedido by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }
}
