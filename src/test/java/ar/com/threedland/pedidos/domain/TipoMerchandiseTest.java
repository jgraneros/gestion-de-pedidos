package ar.com.threedland.pedidos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.threedland.pedidos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoMerchandiseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoMerchandise.class);
        TipoMerchandise tipoMerchandise1 = new TipoMerchandise();
        tipoMerchandise1.setId(1L);
        TipoMerchandise tipoMerchandise2 = new TipoMerchandise();
        tipoMerchandise2.setId(tipoMerchandise1.getId());
        assertThat(tipoMerchandise1).isEqualTo(tipoMerchandise2);
        tipoMerchandise2.setId(2L);
        assertThat(tipoMerchandise1).isNotEqualTo(tipoMerchandise2);
        tipoMerchandise1.setId(null);
        assertThat(tipoMerchandise1).isNotEqualTo(tipoMerchandise2);
    }
}
