package ar.com.threedland.pedidos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.threedland.pedidos.IntegrationTest;
import ar.com.threedland.pedidos.domain.DetallePedido;
import ar.com.threedland.pedidos.repository.DetallePedidoRepository;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DetallePedidoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DetallePedidoResourceIT {

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Double DEFAULT_SUB_TOTAL_COSTO = 1D;
    private static final Double UPDATED_SUB_TOTAL_COSTO = 2D;

    private static final String ENTITY_API_URL = "/api/detalle-pedidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Mock
    private DetallePedidoRepository detallePedidoRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDetallePedidoMockMvc;

    private DetallePedido detallePedido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallePedido createEntity(EntityManager em) {
        DetallePedido detallePedido = new DetallePedido().cantidad(DEFAULT_CANTIDAD).subTotalCosto(DEFAULT_SUB_TOTAL_COSTO);
        return detallePedido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DetallePedido createUpdatedEntity(EntityManager em) {
        DetallePedido detallePedido = new DetallePedido().cantidad(UPDATED_CANTIDAD).subTotalCosto(UPDATED_SUB_TOTAL_COSTO);
        return detallePedido;
    }

    @BeforeEach
    public void initTest() {
        detallePedido = createEntity(em);
    }

    @Test
    @Transactional
    void createDetallePedido() throws Exception {
        int databaseSizeBeforeCreate = detallePedidoRepository.findAll().size();
        // Create the DetallePedido
        restDetallePedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePedido)))
            .andExpect(status().isCreated());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeCreate + 1);
        DetallePedido testDetallePedido = detallePedidoList.get(detallePedidoList.size() - 1);
        assertThat(testDetallePedido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetallePedido.getSubTotalCosto()).isEqualTo(DEFAULT_SUB_TOTAL_COSTO);
    }

    @Test
    @Transactional
    void createDetallePedidoWithExistingId() throws Exception {
        // Create the DetallePedido with an existing ID
        detallePedido.setId(1L);

        int databaseSizeBeforeCreate = detallePedidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDetallePedidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePedido)))
            .andExpect(status().isBadRequest());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDetallePedidos() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        // Get all the detallePedidoList
        restDetallePedidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(detallePedido.getId().intValue())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].subTotalCosto").value(hasItem(DEFAULT_SUB_TOTAL_COSTO.doubleValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetallePedidosWithEagerRelationshipsIsEnabled() throws Exception {
        when(detallePedidoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetallePedidoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(detallePedidoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDetallePedidosWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(detallePedidoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDetallePedidoMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(detallePedidoRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDetallePedido() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        // Get the detallePedido
        restDetallePedidoMockMvc
            .perform(get(ENTITY_API_URL_ID, detallePedido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(detallePedido.getId().intValue()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.subTotalCosto").value(DEFAULT_SUB_TOTAL_COSTO.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingDetallePedido() throws Exception {
        // Get the detallePedido
        restDetallePedidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDetallePedido() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();

        // Update the detallePedido
        DetallePedido updatedDetallePedido = detallePedidoRepository.findById(detallePedido.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDetallePedido are not directly saved in db
        em.detach(updatedDetallePedido);
        updatedDetallePedido.cantidad(UPDATED_CANTIDAD).subTotalCosto(UPDATED_SUB_TOTAL_COSTO);

        restDetallePedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDetallePedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDetallePedido))
            )
            .andExpect(status().isOk());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
        DetallePedido testDetallePedido = detallePedidoList.get(detallePedidoList.size() - 1);
        assertThat(testDetallePedido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetallePedido.getSubTotalCosto()).isEqualTo(UPDATED_SUB_TOTAL_COSTO);
    }

    @Test
    @Transactional
    void putNonExistingDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, detallePedido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallePedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(detallePedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(detallePedido)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDetallePedidoWithPatch() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();

        // Update the detallePedido using partial update
        DetallePedido partialUpdatedDetallePedido = new DetallePedido();
        partialUpdatedDetallePedido.setId(detallePedido.getId());

        restDetallePedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallePedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallePedido))
            )
            .andExpect(status().isOk());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
        DetallePedido testDetallePedido = detallePedidoList.get(detallePedidoList.size() - 1);
        assertThat(testDetallePedido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testDetallePedido.getSubTotalCosto()).isEqualTo(DEFAULT_SUB_TOTAL_COSTO);
    }

    @Test
    @Transactional
    void fullUpdateDetallePedidoWithPatch() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();

        // Update the detallePedido using partial update
        DetallePedido partialUpdatedDetallePedido = new DetallePedido();
        partialUpdatedDetallePedido.setId(detallePedido.getId());

        partialUpdatedDetallePedido.cantidad(UPDATED_CANTIDAD).subTotalCosto(UPDATED_SUB_TOTAL_COSTO);

        restDetallePedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDetallePedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDetallePedido))
            )
            .andExpect(status().isOk());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
        DetallePedido testDetallePedido = detallePedidoList.get(detallePedidoList.size() - 1);
        assertThat(testDetallePedido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testDetallePedido.getSubTotalCosto()).isEqualTo(UPDATED_SUB_TOTAL_COSTO);
    }

    @Test
    @Transactional
    void patchNonExistingDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, detallePedido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallePedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(detallePedido))
            )
            .andExpect(status().isBadRequest());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDetallePedido() throws Exception {
        int databaseSizeBeforeUpdate = detallePedidoRepository.findAll().size();
        detallePedido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDetallePedidoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(detallePedido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DetallePedido in the database
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDetallePedido() throws Exception {
        // Initialize the database
        detallePedidoRepository.saveAndFlush(detallePedido);

        int databaseSizeBeforeDelete = detallePedidoRepository.findAll().size();

        // Delete the detallePedido
        restDetallePedidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, detallePedido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DetallePedido> detallePedidoList = detallePedidoRepository.findAll();
        assertThat(detallePedidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
