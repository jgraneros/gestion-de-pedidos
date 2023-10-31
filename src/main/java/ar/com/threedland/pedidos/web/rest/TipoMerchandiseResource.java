package ar.com.threedland.pedidos.web.rest;

import ar.com.threedland.pedidos.domain.TipoMerchandise;
import ar.com.threedland.pedidos.repository.TipoMerchandiseRepository;
import ar.com.threedland.pedidos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.threedland.pedidos.domain.TipoMerchandise}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoMerchandiseResource {

    private final Logger log = LoggerFactory.getLogger(TipoMerchandiseResource.class);

    private static final String ENTITY_NAME = "tipoMerchandise";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoMerchandiseRepository tipoMerchandiseRepository;

    public TipoMerchandiseResource(TipoMerchandiseRepository tipoMerchandiseRepository) {
        this.tipoMerchandiseRepository = tipoMerchandiseRepository;
    }

    /**
     * {@code POST  /tipo-merchandises} : Create a new tipoMerchandise.
     *
     * @param tipoMerchandise the tipoMerchandise to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoMerchandise, or with status {@code 400 (Bad Request)} if the tipoMerchandise has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-merchandises")
    public ResponseEntity<TipoMerchandise> createTipoMerchandise(@RequestBody TipoMerchandise tipoMerchandise) throws URISyntaxException {
        log.debug("REST request to save TipoMerchandise : {}", tipoMerchandise);
        if (tipoMerchandise.getId() != null) {
            throw new BadRequestAlertException("A new tipoMerchandise cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoMerchandise result = tipoMerchandiseRepository.save(tipoMerchandise);
        return ResponseEntity
            .created(new URI("/api/tipo-merchandises/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-merchandises/:id} : Updates an existing tipoMerchandise.
     *
     * @param id the id of the tipoMerchandise to save.
     * @param tipoMerchandise the tipoMerchandise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMerchandise,
     * or with status {@code 400 (Bad Request)} if the tipoMerchandise is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoMerchandise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-merchandises/{id}")
    public ResponseEntity<TipoMerchandise> updateTipoMerchandise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoMerchandise tipoMerchandise
    ) throws URISyntaxException {
        log.debug("REST request to update TipoMerchandise : {}, {}", id, tipoMerchandise);
        if (tipoMerchandise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoMerchandise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoMerchandiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoMerchandise result = tipoMerchandiseRepository.save(tipoMerchandise);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoMerchandise.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-merchandises/:id} : Partial updates given fields of an existing tipoMerchandise, field will ignore if it is null
     *
     * @param id the id of the tipoMerchandise to save.
     * @param tipoMerchandise the tipoMerchandise to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoMerchandise,
     * or with status {@code 400 (Bad Request)} if the tipoMerchandise is not valid,
     * or with status {@code 404 (Not Found)} if the tipoMerchandise is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoMerchandise couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-merchandises/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoMerchandise> partialUpdateTipoMerchandise(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TipoMerchandise tipoMerchandise
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoMerchandise partially : {}, {}", id, tipoMerchandise);
        if (tipoMerchandise.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoMerchandise.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoMerchandiseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoMerchandise> result = tipoMerchandiseRepository
            .findById(tipoMerchandise.getId())
            .map(existingTipoMerchandise -> {
                if (tipoMerchandise.getTipo() != null) {
                    existingTipoMerchandise.setTipo(tipoMerchandise.getTipo());
                }

                return existingTipoMerchandise;
            })
            .map(tipoMerchandiseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoMerchandise.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-merchandises} : get all the tipoMerchandises.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoMerchandises in body.
     */
    @GetMapping("/tipo-merchandises")
    public List<TipoMerchandise> getAllTipoMerchandises() {
        log.debug("REST request to get all TipoMerchandises");
        return tipoMerchandiseRepository.findAll();
    }

    /**
     * {@code GET  /tipo-merchandises/:id} : get the "id" tipoMerchandise.
     *
     * @param id the id of the tipoMerchandise to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoMerchandise, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-merchandises/{id}")
    public ResponseEntity<TipoMerchandise> getTipoMerchandise(@PathVariable Long id) {
        log.debug("REST request to get TipoMerchandise : {}", id);
        Optional<TipoMerchandise> tipoMerchandise = tipoMerchandiseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoMerchandise);
    }

    /**
     * {@code DELETE  /tipo-merchandises/:id} : delete the "id" tipoMerchandise.
     *
     * @param id the id of the tipoMerchandise to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-merchandises/{id}")
    public ResponseEntity<Void> deleteTipoMerchandise(@PathVariable Long id) {
        log.debug("REST request to delete TipoMerchandise : {}", id);
        tipoMerchandiseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
