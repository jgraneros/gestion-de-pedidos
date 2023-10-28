import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import Modelo3DService from './modelo-3-d.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IModelo3D } from '@/shared/model/modelo-3-d.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Modelo3DDetails',
  setup() {
    const modelo3DService = inject('modelo3DService', () => new Modelo3DService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const modelo3D: Ref<IModelo3D> = ref({});

    const retrieveModelo3D = async modelo3DId => {
      try {
        const res = await modelo3DService().find(modelo3DId);
        modelo3D.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.modelo3DId) {
      retrieveModelo3D(route.params.modelo3DId);
    }

    return {
      alertService,
      modelo3D,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
