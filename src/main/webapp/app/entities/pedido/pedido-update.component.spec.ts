/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PedidoUpdate from './pedido-update.vue';
import PedidoService from './pedido.service';
import AlertService from '@/shared/alert/alert.service';

import Modelo3DService from '@/entities/modelo-3-d/modelo-3-d.service';
import ClienteService from '@/entities/cliente/cliente.service';

type PedidoUpdateComponentType = InstanceType<typeof PedidoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const pedidoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<PedidoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('Pedido Management Update Component', () => {
    let comp: PedidoUpdateComponentType;
    let pedidoServiceStub: SinonStubbedInstance<PedidoService>;

    beforeEach(() => {
      route = {};
      pedidoServiceStub = sinon.createStubInstance<PedidoService>(PedidoService);
      pedidoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          pedidoService: () => pedidoServiceStub,
          modelo3DService: () =>
            sinon.createStubInstance<Modelo3DService>(Modelo3DService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
          clienteService: () =>
            sinon.createStubInstance<ClienteService>(ClienteService, {
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
        const wrapper = shallowMount(PedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.pedido = pedidoSample;
        pedidoServiceStub.update.resolves(pedidoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(pedidoServiceStub.update.calledWith(pedidoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        pedidoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(PedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.pedido = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(pedidoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        pedidoServiceStub.find.resolves(pedidoSample);
        pedidoServiceStub.retrieve.resolves([pedidoSample]);

        // WHEN
        route = {
          params: {
            pedidoId: '' + pedidoSample.id,
          },
        };
        const wrapper = shallowMount(PedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.pedido).toMatchObject(pedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        pedidoServiceStub.find.resolves(pedidoSample);
        const wrapper = shallowMount(PedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
