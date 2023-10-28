package ar.com.threedland.pedidos.service;

import ar.com.threedland.pedidos.domain.Modelo3D;
import ar.com.threedland.pedidos.repository.Modelo3DRepository;
import ar.com.threedland.pedidos.service.dto.Modelo3DDTO;
import ar.com.threedland.pedidos.service.mapper.Modelo3DMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ar.com.threedland.pedidos.domain.Modelo3D}.
 */
@Service
@Transactional
public class Modelo3DService {

    private final Logger log = LoggerFactory.getLogger(Modelo3DService.class);

    private final Modelo3DRepository modelo3DRepository;

    private final Modelo3DMapper modelo3DMapper;

    public Modelo3DService(Modelo3DRepository modelo3DRepository, Modelo3DMapper modelo3DMapper) {
        this.modelo3DRepository = modelo3DRepository;
        this.modelo3DMapper = modelo3DMapper;
    }

    /**
     * Save a modelo3D.
     *
     * @param modelo3DDTO the entity to save.
     * @return the persisted entity.
     */
    public Modelo3DDTO save(Modelo3DDTO modelo3DDTO) {
        log.debug("Request to save Modelo3D : {}", modelo3DDTO);
        Modelo3D modelo3D = modelo3DMapper.toEntity(modelo3DDTO);
        modelo3D = modelo3DRepository.save(modelo3D);
        return modelo3DMapper.toDto(modelo3D);
    }

    /**
     * Update a modelo3D.
     *
     * @param modelo3DDTO the entity to save.
     * @return the persisted entity.
     */
    public Modelo3DDTO update(Modelo3DDTO modelo3DDTO) {
        log.debug("Request to update Modelo3D : {}", modelo3DDTO);
        Modelo3D modelo3D = modelo3DMapper.toEntity(modelo3DDTO);
        modelo3D = modelo3DRepository.save(modelo3D);
        return modelo3DMapper.toDto(modelo3D);
    }

    /**
     * Partially update a modelo3D.
     *
     * @param modelo3DDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Modelo3DDTO> partialUpdate(Modelo3DDTO modelo3DDTO) {
        log.debug("Request to partially update Modelo3D : {}", modelo3DDTO);

        return modelo3DRepository
            .findById(modelo3DDTO.getId())
            .map(existingModelo3D -> {
                modelo3DMapper.partialUpdate(existingModelo3D, modelo3DDTO);

                return existingModelo3D;
            })
            .map(modelo3DRepository::save)
            .map(modelo3DMapper::toDto);
    }

    /**
     * Get all the modelo3DS.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Modelo3DDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modelo3DS");
        return modelo3DRepository.findAll(pageable).map(modelo3DMapper::toDto);
    }

    /**
     * Get one modelo3D by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Modelo3DDTO> findOne(Long id) {
        log.debug("Request to get Modelo3D : {}", id);
        return modelo3DRepository.findById(id).map(modelo3DMapper::toDto);
    }

    /**
     * Delete the modelo3D by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Modelo3D : {}", id);
        modelo3DRepository.deleteById(id);
    }
}
