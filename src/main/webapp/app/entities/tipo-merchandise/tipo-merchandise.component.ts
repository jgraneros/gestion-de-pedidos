import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import TipoMerchandiseService from './tipo-merchandise.service';
import { type ITipoMerchandise } from '@/shared/model/tipo-merchandise.model';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TipoMerchandise',
  setup() {
    const { t: t$ } = useI18n();
    const tipoMerchandiseService = inject('tipoMerchandiseService', () => new TipoMerchandiseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tipoMerchandises: Ref<ITipoMerchandise[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveTipoMerchandises = async () => {
      isFetching.value = true;
      try {
        const res = await tipoMerchandiseService().retrieve();
        tipoMerchandises.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveTipoMerchandises();
    };

    onMounted(async () => {
      await retrieveTipoMerchandises();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: ITipoMerchandise) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeTipoMerchandise = async () => {
      try {
        await tipoMerchandiseService().delete(removeId.value);
        const message = t$('gestionPedidosApp.tipoMerchandise.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveTipoMerchandises();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      tipoMerchandises,
      handleSyncList,
      isFetching,
      retrieveTipoMerchandises,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeTipoMerchandise,
      t$,
    };
  },
});
