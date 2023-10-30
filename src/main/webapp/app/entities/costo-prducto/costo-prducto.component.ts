import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import CostoPrductoService from './costo-prducto.service';
import { type ICostoPrducto } from '@/shared/model/costo-prducto.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CostoPrducto',
  setup() {
    const { t: t$ } = useI18n();
    const costoPrductoService = inject('costoPrductoService', () => new CostoPrductoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const costoPrductos: Ref<ICostoPrducto[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveCostoPrductos = async () => {
      isFetching.value = true;
      try {
        const res = await costoPrductoService().retrieve();
        costoPrductos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveCostoPrductos();
    };

    onMounted(async () => {
      await retrieveCostoPrductos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ICostoPrducto) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeCostoPrducto = async () => {
      try {
        await costoPrductoService().delete(removeId.value);
        const message = t$('gestionPedidosApp.costoPrducto.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveCostoPrductos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      costoPrductos,
      handleSyncList,
      isFetching,
      retrieveCostoPrductos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeCostoPrducto,
      t$,
    };
  },
});
