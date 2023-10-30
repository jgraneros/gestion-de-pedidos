import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import DetallePedidoService from './detalle-pedido.service';
import { type IDetallePedido } from '@/shared/model/detalle-pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DetallePedidoDetails',
  setup() {
    const detallePedidoService = inject('detallePedidoService', () => new DetallePedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const detallePedido: Ref<IDetallePedido> = ref({});

    const retrieveDetallePedido = async detallePedidoId => {
      try {
        const res = await detallePedidoService().find(detallePedidoId);
        detallePedido.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.detallePedidoId) {
      retrieveDetallePedido(route.params.detallePedidoId);
    }

    return {
      alertService,
      detallePedido,

      previousState,
      t$: useI18n().t,
    };
  },
});
