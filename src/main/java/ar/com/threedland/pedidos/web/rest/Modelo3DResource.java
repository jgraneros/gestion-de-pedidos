package ar.com.threedland.pedidos.web.rest;

import ar.com.threedland.pedidos.repository.Modelo3DRepository;
import ar.com.threedland.pedidos.service.Modelo3DService;
import ar.com.threedland.pedidos.service.dto.Modelo3DDTO;
import ar.com.threedland.pedidos.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.threedland.pedidos.domain.Modelo3D}.
 */
@RestController
@RequestMapping("/api")
public class Modelo3DResource {

    private final Logger log = LoggerFactory.getLogger(Modelo3DResource.class);

    private static final String ENTITY_NAME = "modelo3D";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Modelo3DService modelo3DService;

    private final Modelo3DRepository modelo3DRepository;

    public Modelo3DResource(Modelo3DService modelo3DService, Modelo3DRepository modelo3DRepository) {
        this.modelo3DService = modelo3DService;
        this.modelo3DRepository = modelo3DRepository;
    }

    /**
     * {@code POST  /modelo-3-ds} : Create a new modelo3D.
     *
     * @param modelo3DDTO the modelo3DDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modelo3DDTO, or with status {@code 400 (Bad Request)} if the modelo3D has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/modelo-3-ds")
    public ResponseEntity<Modelo3DDTO> createModelo3D(@Valid @RequestBody Modelo3DDTO modelo3DDTO) throws URISyntaxException {
        log.debug("REST request to save Modelo3D : {}", modelo3DDTO);
        if (modelo3DDTO.getId() != null) {
            throw new BadRequestAlertException("A new modelo3D cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Modelo3DDTO result = modelo3DService.save(modelo3DDTO);
        return ResponseEntity
            .created(new URI("/api/modelo-3-ds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /modelo-3-ds/:id} : Updates an existing modelo3D.
     *
     * @param id the id of the modelo3DDTO to save.
     * @param modelo3DDTO the modelo3DDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelo3DDTO,
     * or with status {@code 400 (Bad Request)} if the modelo3DDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modelo3DDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/modelo-3-ds/{id}")
    public ResponseEntity<Modelo3DDTO> updateModelo3D(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Modelo3DDTO modelo3DDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Modelo3D : {}, {}", id, modelo3DDTO);
        if (modelo3DDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelo3DDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelo3DRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Modelo3DDTO result = modelo3DService.update(modelo3DDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelo3DDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /modelo-3-ds/:id} : Partial updates given fields of an existing modelo3D, field will ignore if it is null
     *
     * @param id the id of the modelo3DDTO to save.
     * @param modelo3DDTO the modelo3DDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modelo3DDTO,
     * or with status {@code 400 (Bad Request)} if the modelo3DDTO is not valid,
     * or with status {@code 404 (Not Found)} if the modelo3DDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the modelo3DDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/modelo-3-ds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Modelo3DDTO> partialUpdateModelo3D(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Modelo3DDTO modelo3DDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Modelo3D partially : {}, {}", id, modelo3DDTO);
        if (modelo3DDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modelo3DDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modelo3DRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Modelo3DDTO> result = modelo3DService.partialUpdate(modelo3DDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modelo3DDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /modelo-3-ds} : get all the modelo3DS.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modelo3DS in body.
     */
    @GetMapping("/modelo-3-ds")
    public ResponseEntity<List<Modelo3DDTO>> getAllModelo3DS(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Modelo3DS");
        Page<Modelo3DDTO> page = modelo3DService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /modelo-3-ds/:id} : get the "id" modelo3D.
     *
     * @param id the id of the modelo3DDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modelo3DDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/modelo-3-ds/{id}")
    public ResponseEntity<Modelo3DDTO> getModelo3D(@PathVariable Long id) {
        log.debug("REST request to get Modelo3D : {}", id);
        Optional<Modelo3DDTO> modelo3DDTO = modelo3DService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modelo3DDTO);
    }

    /**
     * {@code DELETE  /modelo-3-ds/:id} : delete the "id" modelo3D.
     *
     * @param id the id of the modelo3DDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/modelo-3-ds/{id}")
    public ResponseEntity<Void> deleteModelo3D(@PathVariable Long id) {
        log.debug("REST request to delete Modelo3D : {}", id);
        modelo3DService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
