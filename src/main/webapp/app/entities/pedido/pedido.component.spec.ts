/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Pedido from './pedido.vue';
import PedidoService from './pedido.service';
import AlertService from '@/shared/alert/alert.service';

type PedidoComponentType = InstanceType<typeof Pedido>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Pedido Management Component', () => {
    let pedidoServiceStub: SinonStubbedInstance<PedidoService>;
    let mountOptions: MountingOptions<PedidoComponentType>['global'];

    beforeEach(() => {
      pedidoServiceStub = sinon.createStubInstance<PedidoService>(PedidoService);
      pedidoServiceStub.retrieve.resolves({ headers: {} });

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
          pedidoService: () => pedidoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        pedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Pedido, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(pedidoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.pedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: PedidoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Pedido, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        pedidoServiceStub.retrieve.reset();
        pedidoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        pedidoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removePedido();
        await comp.$nextTick(); // clear components

        // THEN
        expect(pedidoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(pedidoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
