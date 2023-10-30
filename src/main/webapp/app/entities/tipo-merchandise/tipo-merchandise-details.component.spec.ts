/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import TipoMerchandiseDetails from './tipo-merchandise-details.vue';
import TipoMerchandiseService from './tipo-merchandise.service';
import AlertService from '@/shared/alert/alert.service';

type TipoMerchandiseDetailsComponentType = InstanceType<typeof TipoMerchandiseDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const tipoMerchandiseSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('TipoMerchandise Management Detail Component', () => {
    let tipoMerchandiseServiceStub: SinonStubbedInstance<TipoMerchandiseService>;
    let mountOptions: MountingOptions<TipoMerchandiseDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      tipoMerchandiseServiceStub = sinon.createStubInstance<TipoMerchandiseService>(TipoMerchandiseService);

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
          tipoMerchandiseService: () => tipoMerchandiseServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        tipoMerchandiseServiceStub.find.resolves(tipoMerchandiseSample);
        route = {
          params: {
            tipoMerchandiseId: '' + 123,
          },
        };
        const wrapper = shallowMount(TipoMerchandiseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.tipoMerchandise).toMatchObject(tipoMerchandiseSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        tipoMerchandiseServiceStub.find.resolves(tipoMerchandiseSample);
        const wrapper = shallowMount(TipoMerchandiseDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
