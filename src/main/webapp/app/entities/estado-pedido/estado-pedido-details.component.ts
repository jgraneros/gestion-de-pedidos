import { defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';

import EstadoPedidoService from './estado-pedido.service';
import { type IEstadoPedido } from '@/shared/model/estado-pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EstadoPedidoDetails',
  setup() {
    const estadoPedidoService = inject('estadoPedidoService', () => new EstadoPedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);
    const estadoPedido: Ref<IEstadoPedido> = ref({});

    const retrieveEstadoPedido = async estadoPedidoId => {
      try {
        const res = await estadoPedidoService().find(estadoPedidoId);
        estadoPedido.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.estadoPedidoId) {
      retrieveEstadoPedido(route.params.estadoPedidoId);
    }

    return {
      alertService,
      estadoPedido,

      previousState,
      t$: useI18n().t,
    };
  },
});
