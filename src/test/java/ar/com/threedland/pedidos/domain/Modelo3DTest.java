package ar.com.threedland.pedidos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.threedland.pedidos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Modelo3DTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modelo3D.class);
        Modelo3D modelo3D1 = new Modelo3D();
        modelo3D1.setId(1L);
        Modelo3D modelo3D2 = new Modelo3D();
        modelo3D2.setId(modelo3D1.getId());
        assertThat(modelo3D1).isEqualTo(modelo3D2);
        modelo3D2.setId(2L);
        assertThat(modelo3D1).isNotEqualTo(modelo3D2);
        modelo3D1.setId(null);
        assertThat(modelo3D1).isNotEqualTo(modelo3D2);
    }
}
