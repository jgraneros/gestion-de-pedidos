import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import EstadoPedidoService from './estado-pedido.service';
import { type IEstadoPedido } from '@/shared/model/estado-pedido.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EstadoPedido',
  setup() {
    const { t: t$ } = useI18n();
    const estadoPedidoService = inject('estadoPedidoService', () => new EstadoPedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const estadoPedidos: Ref<IEstadoPedido[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveEstadoPedidos = async () => {
      isFetching.value = true;
      try {
        const res = await estadoPedidoService().retrieve();
        estadoPedidos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveEstadoPedidos();
    };

    onMounted(async () => {
      await retrieveEstadoPedidos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IEstadoPedido) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeEstadoPedido = async () => {
      try {
        await estadoPedidoService().delete(removeId.value);
        const message = t$('gestionPedidosApp.estadoPedido.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveEstadoPedidos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      estadoPedidos,
      handleSyncList,
      isFetching,
      retrieveEstadoPedidos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeEstadoPedido,
      t$,
    };
  },
});
