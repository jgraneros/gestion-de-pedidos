import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Cliente = () => import('@/entities/cliente/cliente.vue');
const ClienteUpdate = () => import('@/entities/cliente/cliente-update.vue');
const ClienteDetails = () => import('@/entities/cliente/cliente-details.vue');

const Modelo3D = () => import('@/entities/modelo-3-d/modelo-3-d.vue');
const Modelo3DUpdate = () => import('@/entities/modelo-3-d/modelo-3-d-update.vue');
const Modelo3DDetails = () => import('@/entities/modelo-3-d/modelo-3-d-details.vue');

const Pedido = () => import('@/entities/pedido/pedido.vue');
const PedidoUpdate = () => import('@/entities/pedido/pedido-update.vue');
const PedidoDetails = () => import('@/entities/pedido/pedido-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'cliente',
      name: 'Cliente',
      component: Cliente,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/new',
      name: 'ClienteCreate',
      component: ClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/:clienteId/edit',
      name: 'ClienteEdit',
      component: ClienteUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'cliente/:clienteId/view',
      name: 'ClienteView',
      component: ClienteDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'modelo-3-d',
      name: 'Modelo3D',
      component: Modelo3D,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'modelo-3-d/new',
      name: 'Modelo3DCreate',
      component: Modelo3DUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'modelo-3-d/:modelo3DId/edit',
      name: 'Modelo3DEdit',
      component: Modelo3DUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'modelo-3-d/:modelo3DId/view',
      name: 'Modelo3DView',
      component: Modelo3DDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido',
      name: 'Pedido',
      component: Pedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/new',
      name: 'PedidoCreate',
      component: PedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/:pedidoId/edit',
      name: 'PedidoEdit',
      component: PedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'pedido/:pedidoId/view',
      name: 'PedidoView',
      component: PedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
