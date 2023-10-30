/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import PedidoDetails from './pedido-details.vue';
import PedidoService from './pedido.service';
import AlertService from '@/shared/alert/alert.service';

type PedidoDetailsComponentType = InstanceType<typeof PedidoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const pedidoSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Pedido Management Detail Component', () => {
    let pedidoServiceStub: SinonStubbedInstance<PedidoService>;
    let mountOptions: MountingOptions<PedidoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      pedidoServiceStub = sinon.createStubInstance<PedidoService>(PedidoService);

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          'font-awesome-icon': true,
          'router-link': true,
        },
        provide: {
          alertService,
          pedidoService: () => pedidoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        pedidoServiceStub.find.resolves(pedidoSample);
        route = {
          params: {
            pedidoId: '' + 123,
          },
        };
        const wrapper = shallowMount(PedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.pedido).toMatchObject(pedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        pedidoServiceStub.find.resolves(pedidoSample);
        const wrapper = shallowMount(PedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
