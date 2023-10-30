import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import TipoMerchandiseService from './tipo-merchandise.service';
import { type ITipoMerchandise } from '@/shared/model/tipo-merchandise.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TipoMerchandiseDetails',
  setup() {
    const tipoMerchandiseService = inject('tipoMerchandiseService', () => new TipoMerchandiseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const tipoMerchandise: Ref<ITipoMerchandise> = ref({});

    const retrieveTipoMerchandise = async tipoMerchandiseId => {
      try {
        const res = await tipoMerchandiseService().find(tipoMerchandiseId);
        tipoMerchandise.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.tipoMerchandiseId) {
      retrieveTipoMerchandise(route.params.tipoMerchandiseId);
    }

    return {
      alertService,
      tipoMerchandise,

      previousState,
      t$: useI18n().t,
    };
  },
});
