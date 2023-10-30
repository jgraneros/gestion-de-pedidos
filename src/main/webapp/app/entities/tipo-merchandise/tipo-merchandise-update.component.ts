import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import TipoMerchandiseService from './tipo-merchandise.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type ITipoMerchandise, TipoMerchandise } from '@/shared/model/tipo-merchandise.model';
import { Merchandise } from '@/shared/model/enumerations/merchandise.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'TipoMerchandiseUpdate',
  setup() {
    const tipoMerchandiseService = inject('tipoMerchandiseService', () => new TipoMerchandiseService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const tipoMerchandise: Ref<ITipoMerchandise> = ref(new TipoMerchandise());
    const merchandiseValues: Ref<string[]> = ref(Object.keys(Merchandise));
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveTipoMerchandise = async tipoMerchandiseId => {
      try {
        const res = await tipoMerchandiseService().find(tipoMerchandiseId);
        tipoMerchandise.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.tipoMerchandiseId) {
      retrieveTipoMerchandise(route.params.tipoMerchandiseId);
    }

    const initRelationships = () => {};

    initRelationships();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      tipo: {},
      producto: {},
    };
    const v$ = useVuelidate(validationRules, tipoMerchandise as any);
    v$.value.$validate();

    return {
      tipoMerchandiseService,
      alertService,
      tipoMerchandise,
      previousState,
      merchandiseValues,
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
      if (this.tipoMerchandise.id) {
        this.tipoMerchandiseService()
          .update(this.tipoMerchandise)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.tipoMerchandise.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.tipoMerchandiseService()
          .create(this.tipoMerchandise)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.tipoMerchandise.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
