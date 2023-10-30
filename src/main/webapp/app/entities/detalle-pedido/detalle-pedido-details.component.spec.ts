/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import DetallePedidoDetails from './detalle-pedido-details.vue';
import DetallePedidoService from './detalle-pedido.service';
import AlertService from '@/shared/alert/alert.service';

type DetallePedidoDetailsComponentType = InstanceType<typeof DetallePedidoDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const detallePedidoSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('DetallePedido Management Detail Component', () => {
    let detallePedidoServiceStub: SinonStubbedInstance<DetallePedidoService>;
    let mountOptions: MountingOptions<DetallePedidoDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      detallePedidoServiceStub = sinon.createStubInstance<DetallePedidoService>(DetallePedidoService);

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
          detallePedidoService: () => detallePedidoServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        detallePedidoServiceStub.find.resolves(detallePedidoSample);
        route = {
          params: {
            detallePedidoId: '' + 123,
          },
        };
        const wrapper = shallowMount(DetallePedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.detallePedido).toMatchObject(detallePedidoSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        detallePedidoServiceStub.find.resolves(detallePedidoSample);
        const wrapper = shallowMount(DetallePedidoDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
