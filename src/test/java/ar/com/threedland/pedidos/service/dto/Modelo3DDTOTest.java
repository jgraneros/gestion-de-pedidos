package ar.com.threedland.pedidos.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.threedland.pedidos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Modelo3DDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modelo3DDTO.class);
        Modelo3DDTO modelo3DDTO1 = new Modelo3DDTO();
        modelo3DDTO1.setId(1L);
        Modelo3DDTO modelo3DDTO2 = new Modelo3DDTO();
        assertThat(modelo3DDTO1).isNotEqualTo(modelo3DDTO2);
        modelo3DDTO2.setId(modelo3DDTO1.getId());
        assertThat(modelo3DDTO1).isEqualTo(modelo3DDTO2);
        modelo3DDTO2.setId(2L);
        assertThat(modelo3DDTO1).isNotEqualTo(modelo3DDTO2);
        modelo3DDTO1.setId(null);
        assertThat(modelo3DDTO1).isNotEqualTo(modelo3DDTO2);
    }
}
