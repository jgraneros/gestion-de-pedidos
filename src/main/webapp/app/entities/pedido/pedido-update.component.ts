import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import PedidoService from './pedido.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import ClienteService from '@/entities/cliente/cliente.service';
import { type ICliente } from '@/shared/model/cliente.model';
import { type IPedido, Pedido } from '@/shared/model/pedido.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'PedidoUpdate',
  setup() {
    const pedidoService = inject('pedidoService', () => new PedidoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const pedido: Ref<IPedido> = ref(new Pedido());

    const clienteService = inject('clienteService', () => new ClienteService());

    const clientes: Ref<ICliente[]> = ref([]);
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrievePedido = async pedidoId => {
      try {
        const res = await pedidoService().find(pedidoId);
        pedido.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.pedidoId) {
      retrievePedido(route.params.pedidoId);
    }

    const initRelationships = () => {
      clienteService()
        .retrieve()
        .then(res => {
          clientes.value = res.data;
        });
    };

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      numeroPedido: {},
      costoTotal: {},
      precioVenta: {},
      gananciaTotal: {},
      pedidos: {},
      pedidos: {},
      cliente: {},
    };
    const v$ = useVuelidate(validationRules, pedido as any);
    v$.value.$validate();

    return {
      pedidoService,
      alertService,
      pedido,
      previousState,
      isSaving,
      currentLanguage,
      clientes,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.pedido.id) {
        this.pedidoService()
          .update(this.pedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.pedido.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.pedidoService()
          .create(this.pedido)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.pedido.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
