/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import Modelo3DUpdate from './modelo-3-d-update.vue';
import Modelo3DService from './modelo-3-d.service';
import AlertService from '@/shared/alert/alert.service';

type Modelo3DUpdateComponentType = InstanceType<typeof Modelo3DUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const modelo3DSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<Modelo3DUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Modelo3D Management Update Component', () => {
    let comp: Modelo3DUpdateComponentType;
    let modelo3DServiceStub: SinonStubbedInstance<Modelo3DService>;

    beforeEach(() => {
      route = {};
      modelo3DServiceStub = sinon.createStubInstance<Modelo3DService>(Modelo3DService);
      modelo3DServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          modelo3DService: () => modelo3DServiceStub,
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(Modelo3DUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.modelo3D = modelo3DSample;
        modelo3DServiceStub.update.resolves(modelo3DSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modelo3DServiceStub.update.calledWith(modelo3DSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        modelo3DServiceStub.create.resolves(entity);
        const wrapper = shallowMount(Modelo3DUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.modelo3D = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(modelo3DServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        modelo3DServiceStub.find.resolves(modelo3DSample);
        modelo3DServiceStub.retrieve.resolves([modelo3DSample]);

        // WHEN
        route = {
          params: {
            modelo3DId: '' + modelo3DSample.id,
          },
        };
        const wrapper = shallowMount(Modelo3DUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.modelo3D).toMatchObject(modelo3DSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        modelo3DServiceStub.find.resolves(modelo3DSample);
        const wrapper = shallowMount(Modelo3DUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
