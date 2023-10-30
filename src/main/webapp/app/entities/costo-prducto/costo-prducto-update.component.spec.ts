/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import CostoPrductoUpdate from './costo-prducto-update.vue';
import CostoPrductoService from './costo-prducto.service';
import AlertService from '@/shared/alert/alert.service';

import ProductoService from '@/entities/producto/producto.service';

type CostoPrductoUpdateComponentType = InstanceType<typeof CostoPrductoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const costoPrductoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<CostoPrductoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('CostoPrducto Management Update Component', () => {
    let comp: CostoPrductoUpdateComponentType;
    let costoPrductoServiceStub: SinonStubbedInstance<CostoPrductoService>;

    beforeEach(() => {
      route = {};
      costoPrductoServiceStub = sinon.createStubInstance<CostoPrductoService>(CostoPrductoService);
      costoPrductoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          costoPrductoService: () => costoPrductoServiceStub,
          productoService: () =>
            sinon.createStubInstance<ProductoService>(ProductoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      };
    });

    afterEach(() => {
      vitest.resetAllMocks();
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const wrapper = shallowMount(CostoPrductoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.costoPrducto = costoPrductoSample;
        costoPrductoServiceStub.update.resolves(costoPrductoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(costoPrductoServiceStub.update.calledWith(costoPrductoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        costoPrductoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(CostoPrductoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.costoPrducto = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(costoPrductoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        costoPrductoServiceStub.find.resolves(costoPrductoSample);
        costoPrductoServiceStub.retrieve.resolves([costoPrductoSample]);

        // WHEN
        route = {
          params: {
            costoPrductoId: '' + costoPrductoSample.id,
          },
        };
        const wrapper = shallowMount(CostoPrductoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.costoPrducto).toMatchObject(costoPrductoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        costoPrductoServiceStub.find.resolves(costoPrductoSample);
        const wrapper = shallowMount(CostoPrductoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
