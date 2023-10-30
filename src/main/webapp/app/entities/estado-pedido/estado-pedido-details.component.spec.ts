/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import EstadoPedidoDetails from './estado-pedido-details.vue';
import EstadoPedidoService from './estado-pedido.service';
import AlertService from '@/shared/alert/alert.service';

type EstadoPedidoDetailsComponentType = InstanceType<typeof EstadoPedidoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const estadoPedidoSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('EstadoPedido Management Detail Component', () => {
    let estadoPedidoServiceStub: SinonStubbedInstance<EstadoPedidoService>;
    let mountOptions: MountingOptions<EstadoPedidoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      estadoPedidoServiceStub = sinon.createStubInstance<EstadoPedidoService>(EstadoPedidoService);

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
          estadoPedidoService: () => estadoPedidoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        estadoPedidoServiceStub.find.resolves(estadoPedidoSample);
        route = {
          params: {
            estadoPedidoId: '' + 123,
          },
        };
        const wrapper = shallowMount(EstadoPedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.estadoPedido).toMatchObject(estadoPedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        estadoPedidoServiceStub.find.resolves(estadoPedidoSample);
        const wrapper = shallowMount(EstadoPedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
