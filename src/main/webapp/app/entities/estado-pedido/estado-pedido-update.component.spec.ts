/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EstadoPedidoUpdate from './estado-pedido-update.vue';
import EstadoPedidoService from './estado-pedido.service';
import AlertService from '@/shared/alert/alert.service';

import PedidoService from '@/entities/pedido/pedido.service';

type EstadoPedidoUpdateComponentType = InstanceType<typeof EstadoPedidoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const estadoPedidoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<EstadoPedidoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('EstadoPedido Management Update Component', () => {
    let comp: EstadoPedidoUpdateComponentType;
    let estadoPedidoServiceStub: SinonStubbedInstance<EstadoPedidoService>;

    beforeEach(() => {
      route = {};
      estadoPedidoServiceStub = sinon.createStubInstance<EstadoPedidoService>(EstadoPedidoService);
      estadoPedidoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          estadoPedidoService: () => estadoPedidoServiceStub,
          pedidoService: () =>
            sinon.createStubInstance<PedidoService>(PedidoService, {
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
        const wrapper = shallowMount(EstadoPedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.estadoPedido = estadoPedidoSample;
        estadoPedidoServiceStub.update.resolves(estadoPedidoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estadoPedidoServiceStub.update.calledWith(estadoPedidoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        estadoPedidoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(EstadoPedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.estadoPedido = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(estadoPedidoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        estadoPedidoServiceStub.find.resolves(estadoPedidoSample);
        estadoPedidoServiceStub.retrieve.resolves([estadoPedidoSample]);

        // WHEN
        route = {
          params: {
            estadoPedidoId: '' + estadoPedidoSample.id,
          },
        };
        const wrapper = shallowMount(EstadoPedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.estadoPedido).toMatchObject(estadoPedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        estadoPedidoServiceStub.find.resolves(estadoPedidoSample);
        const wrapper = shallowMount(EstadoPedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
