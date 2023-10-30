package ar.com.threedland.pedidos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.threedland.pedidos.IntegrationTest;
import ar.com.threedland.pedidos.domain.TipoMerchandise;
import ar.com.threedland.pedidos.domain.enumeration.Merchandise;
import ar.com.threedland.pedidos.repository.TipoMerchandiseRepository;
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
 * Integration tests for the {@link TipoMerchandiseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoMerchandiseResourceIT {

    private static final Merchandise DEFAULT_TIPO = Merchandise.LLAVEROS;
    private static final Merchandise UPDATED_TIPO = Merchandise.CARTELES;

    private static final String ENTITY_API_URL = "/api/tipo-merchandises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoMerchandiseRepository tipoMerchandiseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoMerchandiseMockMvc;

    private TipoMerchandise tipoMerchandise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMerchandise createEntity(EntityManager em) {
        TipoMerchandise tipoMerchandise = new TipoMerchandise().tipo(DEFAULT_TIPO);
        return tipoMerchandise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoMerchandise createUpdatedEntity(EntityManager em) {
        TipoMerchandise tipoMerchandise = new TipoMerchandise().tipo(UPDATED_TIPO);
        return tipoMerchandise;
    }

    @BeforeEach
    public void initTest() {
        tipoMerchandise = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoMerchandise() throws Exception {
        int databaseSizeBeforeCreate = tipoMerchandiseRepository.findAll().size();
        // Create the TipoMerchandise
        restTipoMerchandiseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isCreated());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeCreate + 1);
        TipoMerchandise testTipoMerchandise = tipoMerchandiseList.get(tipoMerchandiseList.size() - 1);
        assertThat(testTipoMerchandise.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    void createTipoMerchandiseWithExistingId() throws Exception {
        // Create the TipoMerchandise with an existing ID
        tipoMerchandise.setId(1L);

        int databaseSizeBeforeCreate = tipoMerchandiseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoMerchandiseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoMerchandises() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        // Get all the tipoMerchandiseList
        restTipoMerchandiseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoMerchandise.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    void getTipoMerchandise() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        // Get the tipoMerchandise
        restTipoMerchandiseMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoMerchandise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoMerchandise.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTipoMerchandise() throws Exception {
        // Get the tipoMerchandise
        restTipoMerchandiseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoMerchandise() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();

        // Update the tipoMerchandise
        TipoMerchandise updatedTipoMerchandise = tipoMerchandiseRepository.findById(tipoMerchandise.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTipoMerchandise are not directly saved in db
        em.detach(updatedTipoMerchandise);
        updatedTipoMerchandise.tipo(UPDATED_TIPO);

        restTipoMerchandiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTipoMerchandise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTipoMerchandise))
            )
            .andExpect(status().isOk());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
        TipoMerchandise testTipoMerchandise = tipoMerchandiseList.get(tipoMerchandiseList.size() - 1);
        assertThat(testTipoMerchandise.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    void putNonExistingTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoMerchandise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoMerchandiseWithPatch() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();

        // Update the tipoMerchandise using partial update
        TipoMerchandise partialUpdatedTipoMerchandise = new TipoMerchandise();
        partialUpdatedTipoMerchandise.setId(tipoMerchandise.getId());

        partialUpdatedTipoMerchandise.tipo(UPDATED_TIPO);

        restTipoMerchandiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoMerchandise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoMerchandise))
            )
            .andExpect(status().isOk());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
        TipoMerchandise testTipoMerchandise = tipoMerchandiseList.get(tipoMerchandiseList.size() - 1);
        assertThat(testTipoMerchandise.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    void fullUpdateTipoMerchandiseWithPatch() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();

        // Update the tipoMerchandise using partial update
        TipoMerchandise partialUpdatedTipoMerchandise = new TipoMerchandise();
        partialUpdatedTipoMerchandise.setId(tipoMerchandise.getId());

        partialUpdatedTipoMerchandise.tipo(UPDATED_TIPO);

        restTipoMerchandiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoMerchandise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoMerchandise))
            )
            .andExpect(status().isOk());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
        TipoMerchandise testTipoMerchandise = tipoMerchandiseList.get(tipoMerchandiseList.size() - 1);
        assertThat(testTipoMerchandise.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoMerchandise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoMerchandise() throws Exception {
        int databaseSizeBeforeUpdate = tipoMerchandiseRepository.findAll().size();
        tipoMerchandise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoMerchandiseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoMerchandise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoMerchandise in the database
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoMerchandise() throws Exception {
        // Initialize the database
        tipoMerchandiseRepository.saveAndFlush(tipoMerchandise);

        int databaseSizeBeforeDelete = tipoMerchandiseRepository.findAll().size();

        // Delete the tipoMerchandise
        restTipoMerchandiseMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoMerchandise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoMerchandise> tipoMerchandiseList = tipoMerchandiseRepository.findAll();
        assertThat(tipoMerchandiseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
