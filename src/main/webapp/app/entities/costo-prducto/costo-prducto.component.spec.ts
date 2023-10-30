/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import CostoPrducto from './costo-prducto.vue';
import CostoPrductoService from './costo-prducto.service';
import AlertService from '@/shared/alert/alert.service';

type CostoPrductoComponentType = InstanceType<typeof CostoPrducto>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('CostoPrducto Management Component', () => {
    let costoPrductoServiceStub: SinonStubbedInstance<CostoPrductoService>;
    let mountOptions: MountingOptions<CostoPrductoComponentType>['global'];

    beforeEach(() => {
      costoPrductoServiceStub = sinon.createStubInstance<CostoPrductoService>(CostoPrductoService);
      costoPrductoServiceStub.retrieve.resolves({ headers: {} });

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
          costoPrductoService: () => costoPrductoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        costoPrductoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(CostoPrducto, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(costoPrductoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.costoPrductos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: CostoPrductoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(CostoPrducto, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        costoPrductoServiceStub.retrieve.reset();
        costoPrductoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        costoPrductoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeCostoPrducto();
        await comp.$nextTick(); // clear components

        // THEN
        expect(costoPrductoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(costoPrductoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
