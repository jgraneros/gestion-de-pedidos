/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import TipoMerchandise from './tipo-merchandise.vue';
import TipoMerchandiseService from './tipo-merchandise.service';
import AlertService from '@/shared/alert/alert.service';

type TipoMerchandiseComponentType = InstanceType<typeof TipoMerchandise>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('TipoMerchandise Management Component', () => {
    let tipoMerchandiseServiceStub: SinonStubbedInstance<TipoMerchandiseService>;
    let mountOptions: MountingOptions<TipoMerchandiseComponentType>['global'];

    beforeEach(() => {
      tipoMerchandiseServiceStub = sinon.createStubInstance<TipoMerchandiseService>(TipoMerchandiseService);
      tipoMerchandiseServiceStub.retrieve.resolves({ headers: {} });

      alertService = new AlertService({
        i18n: { t: vitest.fn() } as any,
        bvToast: {
          toast: vitest.fn(),
        } as any,
      });

      mountOptions = {
        stubs: {
          bModal: bModalStub as any,
          'font-awesome-icon': true,
          'b-badge': true,
          'b-button': true,
          'router-link': true,
        },
        directives: {
          'b-modal': {},
        },
        provide: {
          alertService,
          tipoMerchandiseService: () => tipoMerchandiseServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        tipoMerchandiseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(TipoMerchandise, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(tipoMerchandiseServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.tipoMerchandises[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: TipoMerchandiseComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(TipoMerchandise, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        tipoMerchandiseServiceStub.retrieve.reset();
        tipoMerchandiseServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        tipoMerchandiseServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeTipoMerchandise();
        await comp.$nextTick(); // clear components

        // THEN
        expect(tipoMerchandiseServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(tipoMerchandiseServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
