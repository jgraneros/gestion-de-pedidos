/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TipoMerchandiseUpdate from './tipo-merchandise-update.vue';
import TipoMerchandiseService from './tipo-merchandise.service';
import AlertService from '@/shared/alert/alert.service';

type TipoMerchandiseUpdateComponentType = InstanceType<typeof TipoMerchandiseUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const tipoMerchandiseSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<TipoMerchandiseUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('TipoMerchandise Management Update Component', () => {
    let comp: TipoMerchandiseUpdateComponentType;
    let tipoMerchandiseServiceStub: SinonStubbedInstance<TipoMerchandiseService>;

    beforeEach(() => {
      route = {};
      tipoMerchandiseServiceStub = sinon.createStubInstance<TipoMerchandiseService>(TipoMerchandiseService);
      tipoMerchandiseServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'b-input-group': true,
          'b-input-group-prepend': true,
          'b-form-datepicker': true,
          'b-form-input': true,
        },
        provide: {
          alertService,
          tipoMerchandiseService: () => tipoMerchandiseServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(TipoMerchandiseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tipoMerchandise = tipoMerchandiseSample;
        tipoMerchandiseServiceStub.update.resolves(tipoMerchandiseSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoMerchandiseServiceStub.update.calledWith(tipoMerchandiseSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        tipoMerchandiseServiceStub.create.resolves(entity);
        const wrapper = shallowMount(TipoMerchandiseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.tipoMerchandise = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoMerchandiseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        tipoMerchandiseServiceStub.find.resolves(tipoMerchandiseSample);
        tipoMerchandiseServiceStub.retrieve.resolves([tipoMerchandiseSample]);

        // WHEN
        route = {
          params: {
            tipoMerchandiseId: '' + tipoMerchandiseSample.id,
          },
        };
        const wrapper = shallowMount(TipoMerchandiseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.tipoMerchandise).toMatchObject(tipoMerchandiseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        tipoMerchandiseServiceStub.find.resolves(tipoMerchandiseSample);
        const wrapper = shallowMount(TipoMerchandiseUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
