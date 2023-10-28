/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';
import { type RouteLocation } from 'vue-router';

import Modelo3DDetails from './modelo-3-d-details.vue';
import Modelo3DService from './modelo-3-d.service';
import AlertService from '@/shared/alert/alert.service';

type Modelo3DDetailsComponentType = InstanceType<typeof Modelo3DDetails>;

let route: Partial<RouteLocation>;
const routerGoMock = vitest.fn();

vitest.mock('vue-router', () => ({
  useRoute: () => route,
  useRouter: () => ({ go: routerGoMock }),
}));

const modelo3DSample = { id: 123 };

describe('Component Tests', () => {
  let alertService: AlertService;

  afterEach(() => {
    vitest.resetAllMocks();
  });

  describe('Modelo3D Management Detail Component', () => {
    let modelo3DServiceStub: SinonStubbedInstance<Modelo3DService>;
    let mountOptions: MountingOptions<Modelo3DDetailsComponentType>['global'];

    beforeEach(() => {
      route = {};
      modelo3DServiceStub = sinon.createStubInstance<Modelo3DService>(Modelo3DService);

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
          modelo3DService: () => modelo3DServiceStub,
        },
      };
    });

    describe('Navigate to details', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        modelo3DServiceStub.find.resolves(modelo3DSample);
        route = {
          params: {
            modelo3DId: '' + 123,
          },
        };
        const wrapper = shallowMount(Modelo3DDetails, { global: mountOptions });
        const comp = wrapper.vm;
        // WHEN
        await comp.$nextTick();

        // THEN
        expect(comp.modelo3D).toMatchObject(modelo3DSample);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        modelo3DServiceStub.find.resolves(modelo3DSample);
        const wrapper = shallowMount(Modelo3DDetails, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        comp.previousState();
        await comp.$nextTick();

        expect(routerGoMock).toHaveBeenCalledWith(-1);
      });
    });
  });
});
