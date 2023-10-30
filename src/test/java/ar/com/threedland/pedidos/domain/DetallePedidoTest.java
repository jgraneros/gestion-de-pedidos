package ar.com.threedland.pedidos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.threedland.pedidos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DetallePedidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DetallePedido.class);
        DetallePedido detallePedido1 = new DetallePedido();
        detallePedido1.setId(1L);
        DetallePedido detallePedido2 = new DetallePedido();
        detallePedido2.setId(detallePedido1.getId());
        assertThat(detallePedido1).isEqualTo(detallePedido2);
        detallePedido2.setId(2L);
        assertThat(detallePedido1).isNotEqualTo(detallePedido2);
        detallePedido1.setId(null);
        assertThat(detallePedido1).isNotEqualTo(detallePedido2);
    }
}
