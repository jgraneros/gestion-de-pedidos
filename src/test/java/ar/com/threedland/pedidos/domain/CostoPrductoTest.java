package ar.com.threedland.pedidos.domain;

import static org.assertj.core.api.Assertions.assertThat;

import ar.com.threedland.pedidos.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CostoPrductoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CostoPrducto.class);
        CostoPrducto costoPrducto1 = new CostoPrducto();
        costoPrducto1.setId(1L);
        CostoPrducto costoPrducto2 = new CostoPrducto();
        costoPrducto2.setId(costoPrducto1.getId());
        assertThat(costoPrducto1).isEqualTo(costoPrducto2);
        costoPrducto2.setId(2L);
        assertThat(costoPrducto1).isNotEqualTo(costoPrducto2);
        costoPrducto1.setId(null);
        assertThat(costoPrducto1).isNotEqualTo(costoPrducto2);
    }
}
