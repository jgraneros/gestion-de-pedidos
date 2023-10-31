import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import EstadoPedidoService from './estado-pedido.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import PedidoService from '@/entities/pedido/pedido.service';
import { type IPedido } from '@/shared/model/pedido.model';
import { type IEstadoPedido, EstadoPedido } from '@/shared/model/estado-pedido.model';
import { EstadosPedido } from '@/shared/model/enumerations/estados-pedido.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'EstadoPedidoUpdate',
  setup() {
    const estadoPedidoService = inject('estadoPedidoService', () => new EstadoPedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const estadoPedido: Ref<IEstadoPedido> = ref(new EstadoPedido());

    const pedidoService = inject('pedidoService', () => new PedidoService());

    const pedidos: Ref<IPedido[]> = ref([]);
    const estadosPedidoValues: Ref<string[]> = ref(Object.keys(EstadosPedido));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

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

    const initRelationships = () => {
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
      descripcion: {},
      pedido: {},
    };
    const v$ = useVuelidate(validationRules, estadoPedido as any);
    v$.value.$validate();

    return {
      estadoPedidoService,
      alertService,
      estadoPedido,
      previousState,
      estadosPedidoValues,
      isSaving,
      currentLanguage,
      pedidos,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.estadoPedido.id) {
        this.estadoPedidoService()
          .update(this.estadoPedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.estadoPedido.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.estadoPedidoService()
          .create(this.estadoPedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.estadoPedido.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
