import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import DetallePedidoService from './detalle-pedido.service';
import { type IDetallePedido } from '@/shared/model/detalle-pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DetallePedido',
  setup() {
    const { t: t$ } = useI18n();
    const detallePedidoService = inject('detallePedidoService', () => new DetallePedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const detallePedidos: Ref<IDetallePedido[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveDetallePedidos = async () => {
      isFetching.value = true;
      try {
        const res = await detallePedidoService().retrieve();
        detallePedidos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveDetallePedidos();
    };

    onMounted(async () => {
      await retrieveDetallePedidos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IDetallePedido) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeDetallePedido = async () => {
      try {
        await detallePedidoService().delete(removeId.value);
        const message = t$('gestionPedidosApp.detallePedido.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveDetallePedidos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      detallePedidos,
      handleSyncList,
      isFetching,
      retrieveDetallePedidos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeDetallePedido,
      t$,
    };
  },
});
