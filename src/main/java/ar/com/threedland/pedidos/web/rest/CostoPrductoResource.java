package ar.com.threedland.pedidos.web.rest;

import ar.com.threedland.pedidos.domain.CostoPrducto;
import ar.com.threedland.pedidos.repository.CostoPrductoRepository;
import ar.com.threedland.pedidos.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.com.threedland.pedidos.domain.CostoPrducto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CostoPrductoResource {

    private final Logger log = LoggerFactory.getLogger(CostoPrductoResource.class);

    private static final String ENTITY_NAME = "costoPrducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CostoPrductoRepository costoPrductoRepository;

    public CostoPrductoResource(CostoPrductoRepository costoPrductoRepository) {
        this.costoPrductoRepository = costoPrductoRepository;
    }

    /**
     * {@code POST  /costo-prductos} : Create a new costoPrducto.
     *
     * @param costoPrducto the costoPrducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new costoPrducto, or with status {@code 400 (Bad Request)} if the costoPrducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/costo-prductos")
    public ResponseEntity<CostoPrducto> createCostoPrducto(@RequestBody CostoPrducto costoPrducto) throws URISyntaxException {
        log.debug("REST request to save CostoPrducto : {}", costoPrducto);
        if (costoPrducto.getId() != null) {
            throw new BadRequestAlertException("A new costoPrducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CostoPrducto result = costoPrductoRepository.save(costoPrducto);
        return ResponseEntity
            .created(new URI("/api/costo-prductos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /costo-prductos/:id} : Updates an existing costoPrducto.
     *
     * @param id the id of the costoPrducto to save.
     * @param costoPrducto the costoPrducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costoPrducto,
     * or with status {@code 400 (Bad Request)} if the costoPrducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the costoPrducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/costo-prductos/{id}")
    public ResponseEntity<CostoPrducto> updateCostoPrducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CostoPrducto costoPrducto
    ) throws URISyntaxException {
        log.debug("REST request to update CostoPrducto : {}, {}", id, costoPrducto);
        if (costoPrducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costoPrducto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costoPrductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CostoPrducto result = costoPrductoRepository.save(costoPrducto);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costoPrducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /costo-prductos/:id} : Partial updates given fields of an existing costoPrducto, field will ignore if it is null
     *
     * @param id the id of the costoPrducto to save.
     * @param costoPrducto the costoPrducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated costoPrducto,
     * or with status {@code 400 (Bad Request)} if the costoPrducto is not valid,
     * or with status {@code 404 (Not Found)} if the costoPrducto is not found,
     * or with status {@code 500 (Internal Server Error)} if the costoPrducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/costo-prductos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CostoPrducto> partialUpdateCostoPrducto(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CostoPrducto costoPrducto
    ) throws URISyntaxException {
        log.debug("REST request to partial update CostoPrducto partially : {}, {}", id, costoPrducto);
        if (costoPrducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, costoPrducto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!costoPrductoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CostoPrducto> result = costoPrductoRepository
            .findById(costoPrducto.getId())
            .map(existingCostoPrducto -> {
                if (costoPrducto.getCostoMaterial() != null) {
                    existingCostoPrducto.setCostoMaterial(costoPrducto.getCostoMaterial());
                }
                if (costoPrducto.getCostosAgregados() != null) {
                    existingCostoPrducto.setCostosAgregados(costoPrducto.getCostosAgregados());
                }
                if (costoPrducto.getCostoPostImpresion() != null) {
                    existingCostoPrducto.setCostoPostImpresion(costoPrducto.getCostoPostImpresion());
                }

                return existingCostoPrducto;
            })
            .map(costoPrductoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, costoPrducto.getId().toString())
        );
    }

    /**
     * {@code GET  /costo-prductos} : get all the costoPrductos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of costoPrductos in body.
     */
    @GetMapping("/costo-prductos")
    public List<CostoPrducto> getAllCostoPrductos(@RequestParam(required = false) String filter) {
        if ("producto-is-null".equals(filter)) {
            log.debug("REST request to get all CostoPrductos where producto is null");
            return StreamSupport
                .stream(costoPrductoRepository.findAll().spliterator(), false)
                .filter(costoPrducto -> costoPrducto.getProducto() == null)
                .toList();
        }
        log.debug("REST request to get all CostoPrductos");
        return costoPrductoRepository.findAll();
    }

    /**
     * {@code GET  /costo-prductos/:id} : get the "id" costoPrducto.
     *
     * @param id the id of the costoPrducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the costoPrducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/costo-prductos/{id}")
    public ResponseEntity<CostoPrducto> getCostoPrducto(@PathVariable Long id) {
        log.debug("REST request to get CostoPrducto : {}", id);
        Optional<CostoPrducto> costoPrducto = costoPrductoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(costoPrducto);
    }

    /**
     * {@code DELETE  /costo-prductos/:id} : delete the "id" costoPrducto.
     *
     * @param id the id of the costoPrducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/costo-prductos/{id}")
    public ResponseEntity<Void> deleteCostoPrducto(@PathVariable Long id) {
        log.debug("REST request to delete CostoPrducto : {}", id);
        costoPrductoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
