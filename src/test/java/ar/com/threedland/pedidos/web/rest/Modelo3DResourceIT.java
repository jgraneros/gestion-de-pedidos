package ar.com.threedland.pedidos.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.com.threedland.pedidos.IntegrationTest;
import ar.com.threedland.pedidos.domain.Modelo3D;
import ar.com.threedland.pedidos.repository.Modelo3DRepository;
import ar.com.threedland.pedidos.service.dto.Modelo3DDTO;
import ar.com.threedland.pedidos.service.mapper.Modelo3DMapper;
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
 * Integration tests for the {@link Modelo3DResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class Modelo3DResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ARCHIVO = "AAAAAAAAAA";
    private static final String UPDATED_ARCHIVO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/modelo-3-ds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Modelo3DRepository modelo3DRepository;

    @Autowired
    private Modelo3DMapper modelo3DMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModelo3DMockMvc;

    private Modelo3D modelo3D;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modelo3D createEntity(EntityManager em) {
        Modelo3D modelo3D = new Modelo3D().nombre(DEFAULT_NOMBRE).archivo(DEFAULT_ARCHIVO).descripcion(DEFAULT_DESCRIPCION);
        return modelo3D;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Modelo3D createUpdatedEntity(EntityManager em) {
        Modelo3D modelo3D = new Modelo3D().nombre(UPDATED_NOMBRE).archivo(UPDATED_ARCHIVO).descripcion(UPDATED_DESCRIPCION);
        return modelo3D;
    }

    @BeforeEach
    public void initTest() {
        modelo3D = createEntity(em);
    }

    @Test
    @Transactional
    void createModelo3D() throws Exception {
        int databaseSizeBeforeCreate = modelo3DRepository.findAll().size();
        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);
        restModelo3DMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelo3DDTO)))
            .andExpect(status().isCreated());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeCreate + 1);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testModelo3D.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testModelo3D.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    void createModelo3DWithExistingId() throws Exception {
        // Create the Modelo3D with an existing ID
        modelo3D.setId(1L);
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        int databaseSizeBeforeCreate = modelo3DRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModelo3DMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelo3DDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelo3DRepository.findAll().size();
        // set the field null
        modelo3D.setNombre(null);

        // Create the Modelo3D, which fails.
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        restModelo3DMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelo3DDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkArchivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = modelo3DRepository.findAll().size();
        // set the field null
        modelo3D.setArchivo(null);

        // Create the Modelo3D, which fails.
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        restModelo3DMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelo3DDTO)))
            .andExpect(status().isBadRequest());

        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllModelo3DS() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get all the modelo3DList
        restModelo3DMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modelo3D.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].archivo").value(hasItem(DEFAULT_ARCHIVO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    void getModelo3D() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        // Get the modelo3D
        restModelo3DMockMvc
            .perform(get(ENTITY_API_URL_ID, modelo3D.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modelo3D.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.archivo").value(DEFAULT_ARCHIVO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingModelo3D() throws Exception {
        // Get the modelo3D
        restModelo3DMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingModelo3D() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();

        // Update the modelo3D
        Modelo3D updatedModelo3D = modelo3DRepository.findById(modelo3D.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedModelo3D are not directly saved in db
        em.detach(updatedModelo3D);
        updatedModelo3D.nombre(UPDATED_NOMBRE).archivo(UPDATED_ARCHIVO).descripcion(UPDATED_DESCRIPCION);
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(updatedModelo3D);

        restModelo3DMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelo3DDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isOk());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModelo3D.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testModelo3D.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void putNonExistingModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modelo3DDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modelo3DDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModelo3DWithPatch() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();

        // Update the modelo3D using partial update
        Modelo3D partialUpdatedModelo3D = new Modelo3D();
        partialUpdatedModelo3D.setId(modelo3D.getId());

        partialUpdatedModelo3D.nombre(UPDATED_NOMBRE).descripcion(UPDATED_DESCRIPCION);

        restModelo3DMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelo3D.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelo3D))
            )
            .andExpect(status().isOk());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModelo3D.getArchivo()).isEqualTo(DEFAULT_ARCHIVO);
        assertThat(testModelo3D.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void fullUpdateModelo3DWithPatch() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();

        // Update the modelo3D using partial update
        Modelo3D partialUpdatedModelo3D = new Modelo3D();
        partialUpdatedModelo3D.setId(modelo3D.getId());

        partialUpdatedModelo3D.nombre(UPDATED_NOMBRE).archivo(UPDATED_ARCHIVO).descripcion(UPDATED_DESCRIPCION);

        restModelo3DMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModelo3D.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModelo3D))
            )
            .andExpect(status().isOk());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
        Modelo3D testModelo3D = modelo3DList.get(modelo3DList.size() - 1);
        assertThat(testModelo3D.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testModelo3D.getArchivo()).isEqualTo(UPDATED_ARCHIVO);
        assertThat(testModelo3D.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    void patchNonExistingModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modelo3DDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModelo3D() throws Exception {
        int databaseSizeBeforeUpdate = modelo3DRepository.findAll().size();
        modelo3D.setId(count.incrementAndGet());

        // Create the Modelo3D
        Modelo3DDTO modelo3DDTO = modelo3DMapper.toDto(modelo3D);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModelo3DMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modelo3DDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Modelo3D in the database
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModelo3D() throws Exception {
        // Initialize the database
        modelo3DRepository.saveAndFlush(modelo3D);

        int databaseSizeBeforeDelete = modelo3DRepository.findAll().size();

        // Delete the modelo3D
        restModelo3DMockMvc
            .perform(delete(ENTITY_API_URL_ID, modelo3D.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Modelo3D> modelo3DList = modelo3DRepository.findAll();
        assertThat(modelo3DList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
