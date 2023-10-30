/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DetallePedidoUpdate from './detalle-pedido-update.vue';
import DetallePedidoService from './detalle-pedido.service';
import AlertService from '@/shared/alert/alert.service';

import ProductoService from '@/entities/producto/producto.service';
import PedidoService from '@/entities/pedido/pedido.service';

type DetallePedidoUpdateComponentType = InstanceType<typeof DetallePedidoUpdate>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const detallePedidoSample = { id: 123 };

describe('Component Tests', () => {
  let mountOptions: MountingOptions<DetallePedidoUpdateComponentType>['global'];
  let alertService: AlertService;

  describe('DetallePedido Management Update Component', () => {
    let comp: DetallePedidoUpdateComponentType;
    let detallePedidoServiceStub: SinonStubbedInstance<DetallePedidoService>;

    beforeEach(() => {
      route = {};
      detallePedidoServiceStub = sinon.createStubInstance<DetallePedidoService>(DetallePedidoService);
      detallePedidoServiceStub.retrieve.onFirstCall().resolves(Promise.resolve([]));

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
          detallePedidoService: () => detallePedidoServiceStub,
          productoService: () =>
            sinon.createStubInstance<ProductoService>(ProductoService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
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
        const wrapper = shallowMount(DetallePedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.detallePedido = detallePedidoSample;
        detallePedidoServiceStub.update.resolves(detallePedidoSample);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detallePedidoServiceStub.update.calledWith(detallePedidoSample)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        detallePedidoServiceStub.create.resolves(entity);
        const wrapper = shallowMount(DetallePedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        comp.detallePedido = entity;

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(detallePedidoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        detallePedidoServiceStub.find.resolves(detallePedidoSample);
        detallePedidoServiceStub.retrieve.resolves([detallePedidoSample]);

        // WHEN
        route = {
          params: {
            detallePedidoId: '' + detallePedidoSample.id,
          },
        };
        const wrapper = shallowMount(DetallePedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(comp.detallePedido).toMatchObject(detallePedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        detallePedidoServiceStub.find.resolves(detallePedidoSample);
        const wrapper = shallowMount(DetallePedidoUpdate, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
