import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import PedidoService from './pedido.service';
import { type IPedido } from '@/shared/model/pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Pedido',
  setup() {
    const { t: t$ } = useI18n();
    const pedidoService = inject('pedidoService', () => new PedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const pedidos: Ref<IPedido[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrievePedidos = async () => {
      isFetching.value = true;
      try {
        const res = await pedidoService().retrieve();
        pedidos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrievePedidos();
    };

    onMounted(async () => {
      await retrievePedidos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IPedido) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removePedido = async () => {
      try {
        await pedidoService().delete(removeId.value);
        const message = t$('gestionPedidosApp.pedido.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrievePedidos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      pedidos,
      handleSyncList,
      isFetching,
      retrievePedidos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removePedido,
      t$,
    };
  },
});
