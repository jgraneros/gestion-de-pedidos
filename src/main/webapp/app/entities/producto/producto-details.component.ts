import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import ProductoService from './producto.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { type IProducto } from '@/shared/model/producto.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'ProductoDetails',
  setup() {
    const productoService = inject('productoService', () => new ProductoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const dataUtils = useDataUtils();

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const producto: Ref<IProducto> = ref({});

    const retrieveProducto = async productoId => {
      try {
        const res = await productoService().find(productoId);
        producto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.productoId) {
      retrieveProducto(route.params.productoId);
    }

    return {
      alertService,
      producto,

      ...dataUtils,

      previousState,
      t$: useI18n().t,
    };
  },
});
