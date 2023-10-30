package ar.com.threedland.pedidos.web.rest;

import ar.com.threedland.pedidos.domain.EstadoPedido;
import ar.com.threedland.pedidos.repository.EstadoPedidoRepository;
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
 * REST controller for managing {@link ar.com.threedland.pedidos.domain.EstadoPedido}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstadoPedidoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoPedidoResource.class);

    private static final String ENTITY_NAME = "estadoPedido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadoPedidoRepository estadoPedidoRepository;

    public EstadoPedidoResource(EstadoPedidoRepository estadoPedidoRepository) {
        this.estadoPedidoRepository = estadoPedidoRepository;
    }

    /**
     * {@code POST  /estado-pedidos} : Create a new estadoPedido.
     *
     * @param estadoPedido the estadoPedido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadoPedido, or with status {@code 400 (Bad Request)} if the estadoPedido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estado-pedidos")
    public ResponseEntity<EstadoPedido> createEstadoPedido(@RequestBody EstadoPedido estadoPedido) throws URISyntaxException {
        log.debug("REST request to save EstadoPedido : {}", estadoPedido);
        if (estadoPedido.getId() != null) {
            throw new BadRequestAlertException("A new estadoPedido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadoPedido result = estadoPedidoRepository.save(estadoPedido);
        return ResponseEntity
            .created(new URI("/api/estado-pedidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estado-pedidos/:id} : Updates an existing estadoPedido.
     *
     * @param id the id of the estadoPedido to save.
     * @param estadoPedido the estadoPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoPedido,
     * or with status {@code 400 (Bad Request)} if the estadoPedido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadoPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estado-pedidos/{id}")
    public ResponseEntity<EstadoPedido> updateEstadoPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoPedido estadoPedido
    ) throws URISyntaxException {
        log.debug("REST request to update EstadoPedido : {}, {}", id, estadoPedido);
        if (estadoPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadoPedido result = estadoPedidoRepository.save(estadoPedido);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoPedido.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estado-pedidos/:id} : Partial updates given fields of an existing estadoPedido, field will ignore if it is null
     *
     * @param id the id of the estadoPedido to save.
     * @param estadoPedido the estadoPedido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadoPedido,
     * or with status {@code 400 (Bad Request)} if the estadoPedido is not valid,
     * or with status {@code 404 (Not Found)} if the estadoPedido is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadoPedido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estado-pedidos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadoPedido> partialUpdateEstadoPedido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EstadoPedido estadoPedido
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstadoPedido partially : {}, {}", id, estadoPedido);
        if (estadoPedido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadoPedido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadoPedidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadoPedido> result = estadoPedidoRepository
            .findById(estadoPedido.getId())
            .map(existingEstadoPedido -> {
                if (estadoPedido.getDescripcion() != null) {
                    existingEstadoPedido.setDescripcion(estadoPedido.getDescripcion());
                }

                return existingEstadoPedido;
            })
            .map(estadoPedidoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadoPedido.getId().toString())
        );
    }

    /**
     * {@code GET  /estado-pedidos} : get all the estadoPedidos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estadoPedidos in body.
     */
    @GetMapping("/estado-pedidos")
    public List<EstadoPedido> getAllEstadoPedidos() {
        log.debug("REST request to get all EstadoPedidos");
        return estadoPedidoRepository.findAll();
    }

    /**
     * {@code GET  /estado-pedidos/:id} : get the "id" estadoPedido.
     *
     * @param id the id of the estadoPedido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadoPedido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estado-pedidos/{id}")
    public ResponseEntity<EstadoPedido> getEstadoPedido(@PathVariable Long id) {
        log.debug("REST request to get EstadoPedido : {}", id);
        Optional<EstadoPedido> estadoPedido = estadoPedidoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(estadoPedido);
    }

    /**
     * {@code DELETE  /estado-pedidos/:id} : delete the "id" estadoPedido.
     *
     * @param id the id of the estadoPedido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estado-pedidos/{id}")
    public ResponseEntity<Void> deleteEstadoPedido(@PathVariable Long id) {
        log.debug("REST request to delete EstadoPedido : {}", id);
        estadoPedidoRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
