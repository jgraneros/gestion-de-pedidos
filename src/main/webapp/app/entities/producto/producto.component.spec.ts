/* tslint:disable max-line-length */
import { vitest } from 'vitest';
import { shallowMount, type MountingOptions } from '@vue/test-utils';
import sinon, { type SinonStubbedInstance } from 'sinon';

import Producto from './producto.vue';
import ProductoService from './producto.service';
import AlertService from '@/shared/alert/alert.service';

type ProductoComponentType = InstanceType<typeof Producto>;

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  let alertService: AlertService;

  describe('Producto Management Component', () => {
    let productoServiceStub: SinonStubbedInstance<ProductoService>;
    let mountOptions: MountingOptions<ProductoComponentType>['global'];

    beforeEach(() => {
      productoServiceStub = sinon.createStubInstance<ProductoService>(ProductoService);
      productoServiceStub.retrieve.resolves({ headers: {} });

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
          productoService: () => productoServiceStub,
        },
      };
    });

    describe('Mount', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        productoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

        // WHEN
        const wrapper = shallowMount(Producto, { global: mountOptions });
        const comp = wrapper.vm;
        await comp.$nextTick();

        // THEN
        expect(productoServiceStub.retrieve.calledOnce).toBeTruthy();
        expect(comp.productos[0]).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
    describe('Handles', () => {
      let comp: ProductoComponentType;

      beforeEach(async () => {
        const wrapper = shallowMount(Producto, { global: mountOptions });
        comp = wrapper.vm;
        await comp.$nextTick();
        productoServiceStub.retrieve.reset();
        productoServiceStub.retrieve.resolves({ headers: {}, data: [] });
      });

      it('Should call delete service on confirmDelete', async () => {
        // GIVEN
        productoServiceStub.delete.resolves({});

        // WHEN
        comp.prepareRemove({ id: 123 });

        comp.removeProducto();
        await comp.$nextTick(); // clear components

        // THEN
        expect(productoServiceStub.delete.called).toBeTruthy();

        // THEN
        await comp.$nextTick(); // handle component clear watch
        expect(productoServiceStub.retrieve.callCount).toEqual(1);
      });
    });
  });
});
