/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import DetallePedido from './detalle-pedido.vue';
import DetallePedidoService from './detalle-pedido.service';
import AlertService from '@/shared/alert/alert.service';

type DetallePedidoComponentType = InstanceType<typeof DetallePedido>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('DetallePedido Management Component', () => {
    let detallePedidoServiceStub: SinonStubbedInstance<DetallePedidoService>;
    let mountOptions: MountingOptions<DetallePedidoComponentType>['global'];

    beforeEach(() => {
      detallePedidoServiceStub = sinon.createStubInstance<DetallePedidoService>(DetallePedidoService);
      detallePedidoServiceStub.retrieve.resolves({ headers: {} });

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
          detallePedidoService: () => detallePedidoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        detallePedidoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(DetallePedido, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(detallePedidoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.detallePedidos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: DetallePedidoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(DetallePedido, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        detallePedidoServiceStub.retrieve.reset();
        detallePedidoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        detallePedidoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeDetallePedido();
        await comp.$nextTick(); // clear components

        // THEN
        expect(detallePedidoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(detallePedidoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
