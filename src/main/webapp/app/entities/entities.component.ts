import { defineComponent, provide } from 'vue';

import ClienteService from './cliente/cliente.service';
import Modelo3DService from './modelo-3-d/modelo-3-d.service';
import PedidoService from './pedido/pedido.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('clienteService', () => new ClienteService());
    provide('modelo3DService', () => new Modelo3DService());
    provide('pedidoService', () => new PedidoService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});
