import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import PedidoService from './pedido.service';
import { type IPedido } from '@/shared/model/pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PedidoDetails',
  setup() {
    const pedidoService = inject('pedidoService', () => new PedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const pedido: Ref<IPedido> = ref({});

    const retrievePedido = async pedidoId => {
      try {
        const res = await pedidoService().find(pedidoId);
        pedido.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.pedidoId) {
      retrievePedido(route.params.pedidoId);
    }

    return {
      alertService,
      pedido,

      previousState,
      t$: useI18n().t,
    };
  },
});
