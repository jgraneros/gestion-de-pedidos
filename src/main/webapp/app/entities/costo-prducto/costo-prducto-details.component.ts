import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import CostoPrductoService from './costo-prducto.service';
import { type ICostoPrducto } from '@/shared/model/costo-prducto.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CostoPrductoDetails',
  setup() {
    const costoPrductoService = inject('costoPrductoService', () => new CostoPrductoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const costoPrducto: Ref<ICostoPrducto> = ref({});

    const retrieveCostoPrducto = async costoPrductoId => {
      try {
        const res = await costoPrductoService().find(costoPrductoId);
        costoPrducto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.costoPrductoId) {
      retrieveCostoPrducto(route.params.costoPrductoId);
    }

    return {
      alertService,
      costoPrducto,

      previousState,
      t$: useI18n().t,
    };
  },
});
