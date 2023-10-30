import { defineComponent, provide } from 'vue';

import CostoPrductoService from './costo-prducto/costo-prducto.service';
import EstadoPedidoService from './estado-pedido/estado-pedido.service';
import ClienteService from './cliente/cliente.service';
import PedidoService from './pedido/pedido.service';
import DetallePedidoService from './detalle-pedido/detalle-pedido.service';
import ProductoService from './producto/producto.service';
import TipoMerchandiseService from './tipo-merchandise/tipo-merchandise.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('costoPrductoService', () => new CostoPrductoService());
    provide('estadoPedidoService', () => new EstadoPedidoService());
    provide('clienteService', () => new ClienteService());
    provide('pedidoService', () => new PedidoService());
    provide('detallePedidoService', () => new DetallePedidoService());
    provide('productoService', () => new ProductoService());
    provide('tipoMerchandiseService', () => new TipoMerchandiseService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
