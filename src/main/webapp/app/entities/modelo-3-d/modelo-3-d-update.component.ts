import { computed, defineComponent, inject, ref, type Ref } from 'vue';
import { useI18n } from 'vue-i18n';
import { useRoute, useRouter } from 'vue-router';
import { useVuelidate } from '@vuelidate/core';

import Modelo3DService from './modelo-3-d.service';
import useDataUtils from '@/shared/data/data-utils.service';
import { useValidation } from '@/shared/composables';
import { useAlertService } from '@/shared/alert/alert.service';

import { type IModelo3D, Modelo3D } from '@/shared/model/modelo-3-d.model';

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Modelo3DUpdate',
  setup() {
    const modelo3DService = inject('modelo3DService', () => new Modelo3DService());
    const alertService = inject('alertService', () => useAlertService(), true);

    const modelo3D: Ref<IModelo3D> = ref(new Modelo3D());
    const isSaving = ref(false);
    const currentLanguage = inject('currentLanguage', () => computed(() => navigator.language ?? 'es'), true);

    const route = useRoute();
    const router = useRouter();

    const previousState = () => router.go(-1);

    const retrieveModelo3D = async modelo3DId => {
      try {
        const res = await modelo3DService().find(modelo3DId);
        modelo3D.value = res;
      } catch (error) {
        alertService.showHttpError(error.response);
      }
    };

    if (route.params?.modelo3DId) {
      retrieveModelo3D(route.params.modelo3DId);
    }

    const dataUtils = useDataUtils();

    const { t: t$ } = useI18n();
    const validations = useValidation();
    const validationRules = {
      nombre: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      archivo: {
        required: validations.required(t$('entity.validation.required').toString()),
      },
      descripcion: {},
    };
    const v$ = useVuelidate(validationRules, modelo3D as any);
    v$.value.$validate();

    return {
      modelo3DService,
      alertService,
      modelo3D,
      previousState,
      isSaving,
      currentLanguage,
      ...dataUtils,
      v$,
      t$,
    };
  },
  created(): void {},
  methods: {
    save(): void {
      this.isSaving = true;
      if (this.modelo3D.id) {
        this.modelo3DService()
          .update(this.modelo3D)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showInfo(this.t$('gestionPedidosApp.modelo3D.updated', { param: param.id }));
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      } else {
        this.modelo3DService()
          .create(this.modelo3D)
          .then(param => {
            this.isSaving = false;
            this.previousState();
            this.alertService.showSuccess(this.t$('gestionPedidosApp.modelo3D.created', { param: param.id }).toString());
          })
          .catch(error => {
            this.isSaving = false;
            this.alertService.showHttpError(error.response);
          });
      }
    },
  },
});
