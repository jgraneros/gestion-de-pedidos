/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import EstadoPedido from './estado-pedido.vue';
import EstadoPedidoService from './estado-pedido.service';
import AlertService from '@/shared/alert/alert.service';

type EstadoPedidoComponentType = InstanceType<typeof EstadoPedido>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('EstadoPedido Management Component', () => {
    let estadoPedidoServiceStub: SinonStubbedInstance<EstadoPedidoService>;
    let mountOptions: MountingOptions<EstadoPedidoComponentType>['global'];

    beforeEach(() => {
      estadoPedidoServiceStub = sinon.createStubInstance<EstadoPedidoService>(EstadoPedidoService);
      estadoPedidoServiceStub.retrieve.resolves({ headers: {} });

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
          estadoPedidoService: () => estadoPedidoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        estadoPedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(EstadoPedido, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(estadoPedidoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.estadoPedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: EstadoPedidoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(EstadoPedido, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        estadoPedidoServiceStub.retrieve.reset();
        estadoPedidoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        estadoPedidoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeEstadoPedido();
        await comp.$nextTick(); // clear components

        // THEN
        expect(estadoPedidoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(estadoPedidoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
