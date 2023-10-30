import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import CostoPrductoService from './costo-prducto.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ICostoPrducto, CostoPrducto } from '@/shared/model/costo-prducto.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'CostoPrductoUpdate',
  setup() {
    const costoPrductoService = inject('costoPrductoService', () => new CostoPrductoService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const costoPrducto: Ref<ICostoPrducto> = ref(new CostoPrducto());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveCostoPrducto = async costoPrductoId => {
      try {
        const res = await costoPrductoService().find(costoPrductoId);
        costoPrducto.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.costoPrductoId) {
      retrieveCostoPrducto(route.params.costoPrductoId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      costoMaterial: {},
      costosAgregados: {},
      costoPostImpresion: {},
      producto: {},
    };
    const v$ = useVuelidate(validationRules, costoPrducto as any);
    v$.value.$validate();

    return {
      costoPrductoService,
      alertService,
      costoPrducto,
      previousState,
      isSaving,
      currentLanguage,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.costoPrducto.id) {
        this.costoPrductoService()
          .update(this.costoPrducto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.costoPrducto.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.costoPrductoService()
          .create(this.costoPrducto)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.costoPrducto.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
