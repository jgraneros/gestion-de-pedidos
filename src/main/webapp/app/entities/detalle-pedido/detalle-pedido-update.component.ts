import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import DetallePedidoService from './detalle-pedido.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ProductoService from '@/entities/producto/producto.service';
import { type IProducto } from '@/shared/model/producto.model';
import PedidoService from '@/entities/pedido/pedido.service';
import { type IPedido } from '@/shared/model/pedido.model';
import { type IDetallePedido, DetallePedido } from '@/shared/model/detalle-pedido.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'DetallePedidoUpdate',
  setup() {
    const detallePedidoService = inject('detallePedidoService', () => new DetallePedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const detallePedido: Ref<IDetallePedido> = ref(new DetallePedido());

    const productoService = inject('productoService', () => new ProductoService());

    const productos: Ref<IProducto[]> = ref([]);

    const pedidoService = inject('pedidoService', () => new PedidoService());

    const pedidos: Ref<IPedido[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
      productoService()
        .retrieve()
        .then(res => {
          productos.value = res.data;
        });
      pedidoService()
        .retrieve()
        .then(res => {
          pedidos.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      cantidad: {},
      subTotalCosto: {},
      detalle: {},
      detalle: {},
      pedido: {},
      producto: {},
    };
    const v$ = useVuelidate(validationRules, detallePedido as any);
    v$.value.$validate();

    return {
      detallePedidoService,
      alertService,
      detallePedido,
      previousState,
      isSaving,
      currentLanguage,
      productos,
      pedidos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.detallePedido.id) {
        this.detallePedidoService()
          .update(this.detallePedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.detallePedido.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.detallePedidoService()
          .create(this.detallePedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.detallePedido.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
