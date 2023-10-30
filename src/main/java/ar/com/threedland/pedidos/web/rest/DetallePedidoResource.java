package ar.com.threedland.pedidos.web.rest;

import ar.com.threedland.pedidos.domain.DetallePedido;
import ar.com.threedland.pedidos.repository.DetallePedidoRepository;
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
 * REST controller for managing {@link ar.com.threedland.pedidos.domain.DetallePedido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DetallePedidoResource {

    private final Logger log = LoggerFactory.getLogger(DetallePedidoResource.class);

    private static final String ENTITY_NAME = "detallePedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DetallePedidoRepository detallePedidoRepository;

    public DetallePedidoResource(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    /**
     * {@code POST  /detalle-pedidos} : Create a new detallePedido.
     *
     * @param detallePedido the detallePedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new detallePedido, or with status {@code 400 (Bad Request)} if the detallePedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/detalle-pedidos")
    public ResponseEntity<DetallePedido> createDetallePedido(@RequestBody DetallePedido detallePedido) throws URISyntaxException {
        log.debug("REST request to save DetallePedido : {}", detallePedido);
        if (detallePedido.getId() != null) {
            throw new BadRequestAlertException("A new detallePedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DetallePedido result = detallePedidoRepository.save(detallePedido);
        return ResponseEntity
            .created(new URI("/api/detalle-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /detalle-pedidos/:id} : Updates an existing detallePedido.
     *
     * @param id the id of the detallePedido to save.
     * @param detallePedido the detallePedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detallePedido,
     * or with status {@code 400 (Bad Request)} if the detallePedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the detallePedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/detalle-pedidos/{id}")
    public ResponseEntity<DetallePedido> updateDetallePedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetallePedido detallePedido
    ) throws URISyntaxException {
        log.debug("REST request to update DetallePedido : {}, {}", id, detallePedido);
        if (detallePedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallePedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallePedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DetallePedido result = detallePedidoRepository.save(detallePedido);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallePedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /detalle-pedidos/:id} : Partial updates given fields of an existing detallePedido, field will ignore if it is null
     *
     * @param id the id of the detallePedido to save.
     * @param detallePedido the detallePedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated detallePedido,
     * or with status {@code 400 (Bad Request)} if the detallePedido is not valid,
     * or with status {@code 404 (Not Found)} if the detallePedido is not found,
     * or with status {@code 500 (Internal Server Error)} if the detallePedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/detalle-pedidos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DetallePedido> partialUpdateDetallePedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DetallePedido detallePedido
    ) throws URISyntaxException {
        log.debug("REST request to partial update DetallePedido partially : {}, {}", id, detallePedido);
        if (detallePedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, detallePedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!detallePedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DetallePedido> result = detallePedidoRepository
            .findById(detallePedido.getId())
            .map(existingDetallePedido -> {
                if (detallePedido.getCantidad() != null) {
                    existingDetallePedido.setCantidad(detallePedido.getCantidad());
                }
                if (detallePedido.getSubTotalCosto() != null) {
                    existingDetallePedido.setSubTotalCosto(detallePedido.getSubTotalCosto());
                }

                return existingDetallePedido;
            })
            .map(detallePedidoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, detallePedido.getId().toString())
        );
    }

    /**
     * {@code GET  /detalle-pedidos} : get all the detallePedidos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of detallePedidos in body.
     */
    @GetMapping("/detalle-pedidos")
    public List<DetallePedido> getAllDetallePedidos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all DetallePedidos");
        if (eagerload) {
            return detallePedidoRepository.findAllWithEagerRelationships();
        } else {
            return detallePedidoRepository.findAll();
        }
    }

    /**
     * {@code GET  /detalle-pedidos/:id} : get the "id" detallePedido.
     *
     * @param id the id of the detallePedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the detallePedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/detalle-pedidos/{id}")
    public ResponseEntity<DetallePedido> getDetallePedido(@PathVariable Long id) {
        log.debug("REST request to get DetallePedido : {}", id);
        Optional<DetallePedido> detallePedido = detallePedidoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(detallePedido);
    }

    /**
     * {@code DELETE  /detalle-pedidos/:id} : delete the "id" detallePedido.
     *
     * @param id the id of the detallePedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/detalle-pedidos/{id}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable Long id) {
        log.debug("REST request to delete DetallePedido : {}", id);
        detallePedidoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
