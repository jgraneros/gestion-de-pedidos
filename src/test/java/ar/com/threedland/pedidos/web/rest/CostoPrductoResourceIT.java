package ar.com.threedland.pedidos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.threedland.pedidos.IntegrationTest;
import ar.com.threedland.pedidos.domain.CostoPrducto;
import ar.com.threedland.pedidos.repository.CostoPrductoRepository;
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
 * Integration tests for the {@link CostoPrductoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CostoPrductoResourceIT {

    private static final Double DEFAULT_COSTO_MATERIAL = 1D;
    private static final Double UPDATED_COSTO_MATERIAL = 2D;

    private static final Double DEFAULT_COSTOS_AGREGADOS = 1D;
    private static final Double UPDATED_COSTOS_AGREGADOS = 2D;

    private static final Double DEFAULT_COSTO_POST_IMPRESION = 1D;
    private static final Double UPDATED_COSTO_POST_IMPRESION = 2D;

    private static final String ENTITY_API_URL = "/api/costo-prductos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CostoPrductoRepository costoPrductoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCostoPrductoMockMvc;

    private CostoPrducto costoPrducto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostoPrducto createEntity(EntityManager em) {
        CostoPrducto costoPrducto = new CostoPrducto()
            .costoMaterial(DEFAULT_COSTO_MATERIAL)
            .costosAgregados(DEFAULT_COSTOS_AGREGADOS)
            .costoPostImpresion(DEFAULT_COSTO_POST_IMPRESION);
        return costoPrducto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CostoPrducto createUpdatedEntity(EntityManager em) {
        CostoPrducto costoPrducto = new CostoPrducto()
            .costoMaterial(UPDATED_COSTO_MATERIAL)
            .costosAgregados(UPDATED_COSTOS_AGREGADOS)
            .costoPostImpresion(UPDATED_COSTO_POST_IMPRESION);
        return costoPrducto;
    }

    @BeforeEach
    public void initTest() {
        costoPrducto = createEntity(em);
    }

    @Test
    @Transactional
    void createCostoPrducto() throws Exception {
        int databaseSizeBeforeCreate = costoPrductoRepository.findAll().size();
        // Create the CostoPrducto
        restCostoPrductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costoPrducto)))
            .andExpect(status().isCreated());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeCreate + 1);
        CostoPrducto testCostoPrducto = costoPrductoList.get(costoPrductoList.size() - 1);
        assertThat(testCostoPrducto.getCostoMaterial()).isEqualTo(DEFAULT_COSTO_MATERIAL);
        assertThat(testCostoPrducto.getCostosAgregados()).isEqualTo(DEFAULT_COSTOS_AGREGADOS);
        assertThat(testCostoPrducto.getCostoPostImpresion()).isEqualTo(DEFAULT_COSTO_POST_IMPRESION);
    }

    @Test
    @Transactional
    void createCostoPrductoWithExistingId() throws Exception {
        // Create the CostoPrducto with an existing ID
        costoPrducto.setId(1L);

        int databaseSizeBeforeCreate = costoPrductoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCostoPrductoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costoPrducto)))
            .andExpect(status().isBadRequest());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCostoPrductos() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        // Get all the costoPrductoList
        restCostoPrductoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(costoPrducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].costoMaterial").value(hasItem(DEFAULT_COSTO_MATERIAL.doubleValue())))
            .andExpect(jsonPath("$.[*].costosAgregados").value(hasItem(DEFAULT_COSTOS_AGREGADOS.doubleValue())))
            .andExpect(jsonPath("$.[*].costoPostImpresion").value(hasItem(DEFAULT_COSTO_POST_IMPRESION.doubleValue())));
    }

    @Test
    @Transactional
    void getCostoPrducto() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        // Get the costoPrducto
        restCostoPrductoMockMvc
            .perform(get(ENTITY_API_URL_ID, costoPrducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(costoPrducto.getId().intValue()))
            .andExpect(jsonPath("$.costoMaterial").value(DEFAULT_COSTO_MATERIAL.doubleValue()))
            .andExpect(jsonPath("$.costosAgregados").value(DEFAULT_COSTOS_AGREGADOS.doubleValue()))
            .andExpect(jsonPath("$.costoPostImpresion").value(DEFAULT_COSTO_POST_IMPRESION.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingCostoPrducto() throws Exception {
        // Get the costoPrducto
        restCostoPrductoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCostoPrducto() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();

        // Update the costoPrducto
        CostoPrducto updatedCostoPrducto = costoPrductoRepository.findById(costoPrducto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCostoPrducto are not directly saved in db
        em.detach(updatedCostoPrducto);
        updatedCostoPrducto
            .costoMaterial(UPDATED_COSTO_MATERIAL)
            .costosAgregados(UPDATED_COSTOS_AGREGADOS)
            .costoPostImpresion(UPDATED_COSTO_POST_IMPRESION);

        restCostoPrductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCostoPrducto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCostoPrducto))
            )
            .andExpect(status().isOk());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
        CostoPrducto testCostoPrducto = costoPrductoList.get(costoPrductoList.size() - 1);
        assertThat(testCostoPrducto.getCostoMaterial()).isEqualTo(UPDATED_COSTO_MATERIAL);
        assertThat(testCostoPrducto.getCostosAgregados()).isEqualTo(UPDATED_COSTOS_AGREGADOS);
        assertThat(testCostoPrducto.getCostoPostImpresion()).isEqualTo(UPDATED_COSTO_POST_IMPRESION);
    }

    @Test
    @Transactional
    void putNonExistingCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, costoPrducto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costoPrducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(costoPrducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(costoPrducto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCostoPrductoWithPatch() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();

        // Update the costoPrducto using partial update
        CostoPrducto partialUpdatedCostoPrducto = new CostoPrducto();
        partialUpdatedCostoPrducto.setId(costoPrducto.getId());

        partialUpdatedCostoPrducto.costoMaterial(UPDATED_COSTO_MATERIAL).costoPostImpresion(UPDATED_COSTO_POST_IMPRESION);

        restCostoPrductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostoPrducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostoPrducto))
            )
            .andExpect(status().isOk());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
        CostoPrducto testCostoPrducto = costoPrductoList.get(costoPrductoList.size() - 1);
        assertThat(testCostoPrducto.getCostoMaterial()).isEqualTo(UPDATED_COSTO_MATERIAL);
        assertThat(testCostoPrducto.getCostosAgregados()).isEqualTo(DEFAULT_COSTOS_AGREGADOS);
        assertThat(testCostoPrducto.getCostoPostImpresion()).isEqualTo(UPDATED_COSTO_POST_IMPRESION);
    }

    @Test
    @Transactional
    void fullUpdateCostoPrductoWithPatch() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();

        // Update the costoPrducto using partial update
        CostoPrducto partialUpdatedCostoPrducto = new CostoPrducto();
        partialUpdatedCostoPrducto.setId(costoPrducto.getId());

        partialUpdatedCostoPrducto
            .costoMaterial(UPDATED_COSTO_MATERIAL)
            .costosAgregados(UPDATED_COSTOS_AGREGADOS)
            .costoPostImpresion(UPDATED_COSTO_POST_IMPRESION);

        restCostoPrductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCostoPrducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCostoPrducto))
            )
            .andExpect(status().isOk());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
        CostoPrducto testCostoPrducto = costoPrductoList.get(costoPrductoList.size() - 1);
        assertThat(testCostoPrducto.getCostoMaterial()).isEqualTo(UPDATED_COSTO_MATERIAL);
        assertThat(testCostoPrducto.getCostosAgregados()).isEqualTo(UPDATED_COSTOS_AGREGADOS);
        assertThat(testCostoPrducto.getCostoPostImpresion()).isEqualTo(UPDATED_COSTO_POST_IMPRESION);
    }

    @Test
    @Transactional
    void patchNonExistingCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, costoPrducto.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costoPrducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(costoPrducto))
            )
            .andExpect(status().isBadRequest());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCostoPrducto() throws Exception {
        int databaseSizeBeforeUpdate = costoPrductoRepository.findAll().size();
        costoPrducto.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCostoPrductoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(costoPrducto))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CostoPrducto in the database
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCostoPrducto() throws Exception {
        // Initialize the database
        costoPrductoRepository.saveAndFlush(costoPrducto);

        int databaseSizeBeforeDelete = costoPrductoRepository.findAll().size();

        // Delete the costoPrducto
        restCostoPrductoMockMvc
            .perform(delete(ENTITY_API_URL_ID, costoPrducto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CostoPrducto> costoPrductoList = costoPrductoRepository.findAll();
        assertThat(costoPrductoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
