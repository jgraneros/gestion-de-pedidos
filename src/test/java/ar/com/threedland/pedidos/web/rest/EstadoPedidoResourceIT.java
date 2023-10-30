package ar.com.threedland.pedidos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.threedland.pedidos.IntegrationTest;
import ar.com.threedland.pedidos.domain.EstadoPedido;
import ar.com.threedland.pedidos.domain.enumeration.EstadosPedido;
import ar.com.threedland.pedidos.repository.EstadoPedidoRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstadoPedidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadoPedidoResourceIT {

    private static final EstadosPedido DEFAULT_DESCRIPCION = EstadosPedido.PENDIENTE;
    private static final EstadosPedido UPDATED_DESCRIPCION = EstadosPedido.COMPLETADO;

    private static final String ENTITY_API_URL = "/api/estado-pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadoPedidoRepository estadoPedidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadoPedidoMockMvc;

    private EstadoPedido estadoPedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPedido createEntity(EntityManager em) {
        EstadoPedido estadoPedido = new EstadoPedido().descripcion(DEFAULT_DESCRIPCION);
        return estadoPedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadoPedido createUpdatedEntity(EntityManager em) {
        EstadoPedido estadoPedido = new EstadoPedido().descripcion(UPDATED_DESCRIPCION);
        return estadoPedido;
    }

    @BeforeEach
    public void initTest() {
        estadoPedido = createEntity(em);
    }

    @Test
    @Transactional
    void createEstadoPedido() throws Exception {
        int databaseSizeBeforeCreate = estadoPedidoRepository.findAll().size();
        // Create the EstadoPedido
        restEstadoPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoPedido)))
            .andExpect(status().isCreated());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeCreate + 1);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createEstadoPedidoWithExistingId() throws Exception {
        // Create the EstadoPedido with an existing ID
        estadoPedido.setId(1L);

        int databaseSizeBeforeCreate = estadoPedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadoPedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoPedido)))
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstadoPedidos() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        // Get all the estadoPedidoList
        restEstadoPedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadoPedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    void getEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        // Get the estadoPedido
        restEstadoPedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, estadoPedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadoPedido.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEstadoPedido() throws Exception {
        // Get the estadoPedido
        restEstadoPedidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();

        // Update the estadoPedido
        EstadoPedido updatedEstadoPedido = estadoPedidoRepository.findById(estadoPedido.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEstadoPedido are not directly saved in db
        em.detach(updatedEstadoPedido);
        updatedEstadoPedido.descripcion(UPDATED_DESCRIPCION);

        restEstadoPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstadoPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstadoPedido))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadoPedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadoPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadoPedido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadoPedidoWithPatch() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();

        // Update the estadoPedido using partial update
        EstadoPedido partialUpdatedEstadoPedido = new EstadoPedido();
        partialUpdatedEstadoPedido.setId(estadoPedido.getId());

        partialUpdatedEstadoPedido.descripcion(UPDATED_DESCRIPCION);

        restEstadoPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoPedido))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateEstadoPedidoWithPatch() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();

        // Update the estadoPedido using partial update
        EstadoPedido partialUpdatedEstadoPedido = new EstadoPedido();
        partialUpdatedEstadoPedido.setId(estadoPedido.getId());

        partialUpdatedEstadoPedido.descripcion(UPDATED_DESCRIPCION);

        restEstadoPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadoPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstadoPedido))
            )
            .andExpect(status().isOk());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
        EstadoPedido testEstadoPedido = estadoPedidoList.get(estadoPedidoList.size() - 1);
        assertThat(testEstadoPedido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadoPedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadoPedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadoPedido() throws Exception {
        int databaseSizeBeforeUpdate = estadoPedidoRepository.findAll().size();
        estadoPedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadoPedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estadoPedido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadoPedido in the database
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadoPedido() throws Exception {
        // Initialize the database
        estadoPedidoRepository.saveAndFlush(estadoPedido);

        int databaseSizeBeforeDelete = estadoPedidoRepository.findAll().size();

        // Delete the estadoPedido
        restEstadoPedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadoPedido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstadoPedido> estadoPedidoList = estadoPedidoRepository.findAll();
        assertThat(estadoPedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
