import { defineComponent, inject, onMounted, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';

import ProductoService from './producto.service';
import { type IProducto } from '@/shared/model/producto.model';
import useDataUtils from '@/shared/data/data-utils.service';
import { useAlertService } from '@/shared/alert/alert.service';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Producto',
  setup() {
    const { t: t$ } = useI18n();
    const dataUtils = useDataUtils();
    const productoService = inject('productoService', () => new ProductoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const productos: Ref<IProducto[]> = ref([]);

    const isFetching = ref(false);

    const clear = () => {};

    const retrieveProductos = async () => {
      isFetching.value = true;
      try {
        const res = await productoService().retrieve();
        productos.value = res.data;
      } catch (err) {
        alertService.showHttpError(err.response);
      } finally {
        isFetching.value = false;
      }
    };

    const handleSyncList = () => {
      retrieveProductos();
    };

    onMounted(async () => {
      await retrieveProductos();
    });

    const removeId: Ref<number> = ref(null);
    const removeEntity = ref<any>(null);
    const prepareRemove = (instance: IProducto) => {
      removeId.value = instance.id;
      removeEntity.value.show();
    };
    const closeDialog = () => {
      removeEntity.value.hide();
    };
    const removeProducto = async () => {
      try {
        await productoService().delete(removeId.value);
        const message = t$('gestionPedidosApp.producto.deleted', { param: removeId.value }).toString();
        alertService.showInfo(message, { variant: 'danger' });
        removeId.value = null;
        retrieveProductos();
        closeDialog();
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    return {
      productos,
      handleSyncList,
      isFetching,
      retrieveProductos,
      clear,
      removeId,
      removeEntity,
      prepareRemove,
      closeDialog,
      removeProducto,
      t$,
      ...dataUtils,
    };
  },
});
