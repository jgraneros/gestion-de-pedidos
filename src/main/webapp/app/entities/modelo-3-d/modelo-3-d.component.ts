import { defineComponent, inject, onMounted, ref, type Ref, watch } from 'vue';
import { useI18n } from 'vue-i18n';

import Modelo3DService from './modelo-3-d.service';
import { type IModelo3D } from '@/shared/model/modelo-3-d.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Modelo3D',
  setup() {
    const { t: t$ } = useI18n();
    const dataUtils = useDataUtils();
    const modelo3DService = inject('modelo3DService', () => new Modelo3DService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const itemsPerPage = ref(20);
    const queryCount: Ref<number> = ref(null);
    const page: Ref<number> = ref(1);
    const propOrder = ref('id');
    const reverse = ref(false);
    const totalItems = ref(0);

    const modelo3DS: Ref<IModelo3D[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {
      page.value = 1;
    };

    const sort = (): Array<any> => {
      const result = [propOrder.value + ',' + (reverse.value ? 'desc' : 'asc')];
      if (propOrder.value !== 'id') {
        result.push('id');
      }
      return result;
    };

    const retrieveModelo3Ds = async () => {
      isFetching.value = true;
      try {
        const paginationQuery = {
          page: page.value - 1,
          size: itemsPerPage.value,
          sort: sort(),
        };
        const res = await modelo3DService().retrieve(paginationQuery);
        totalItems.value = Number(res.headers['x-total-count']);
        queryCount.value = totalItems.value;
        modelo3DS.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveModelo3Ds();
    };

    onMounted(async () => {
      await retrieveModelo3Ds();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IModelo3D) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeModelo3D = async () => {
      try {
        await modelo3DService().delete(removeId.value);
        const message = t$('gestionPedidosApp.modelo3D.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveModelo3Ds();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    const changeOrder = (newOrder: string) => {
      if (propOrder.value === newOrder) {
        reverse.value = !reverse.value;
      } else {
        reverse.value = false;
      }
      propOrder.value = newOrder;
    };

    // Whenever order changes, reset the pagination
    watch([propOrder, reverse], async () => {
      if (page.value === 1) {
        // first page, retrieve new data
        await retrieveModelo3Ds();
      } else {
        // reset the pagination
        clear();
      }
    });

    // Whenever page changes, switch to the new page.
    watch(page, async () => {
      await retrieveModelo3Ds();
    });

    return {
      modelo3DS,
      handleSyncList,
      isFetching,
      retrieveModelo3Ds,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeModelo3D,
      itemsPerPage,
      queryCount,
      page,
      propOrder,
      reverse,
      totalItems,
      changeOrder,
      t$,
      ...dataUtils,
    };
  },
});
