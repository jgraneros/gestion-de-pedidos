import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const CostoPrducto = () => import('@/entities/costo-prducto/costo-prducto.vue');
const CostoPrductoUpdate = () => import('@/entities/costo-prducto/costo-prducto-update.vue');
const CostoPrductoDetails = () => import('@/entities/costo-prducto/costo-prducto-details.vue');

const EstadoPedido = () => import('@/entities/estado-pedido/estado-pedido.vue');
const EstadoPedidoUpdate = () => import('@/entities/estado-pedido/estado-pedido-update.vue');
const EstadoPedidoDetails = () => import('@/entities/estado-pedido/estado-pedido-details.vue');

const Cliente = () => import('@/entities/cliente/cliente.vue');
const ClienteUpdate = () => import('@/entities/cliente/cliente-update.vue');
const ClienteDetails = () => import('@/entities/cliente/cliente-details.vue');

const Pedido = () => import('@/entities/pedido/pedido.vue');
const PedidoUpdate = () => import('@/entities/pedido/pedido-update.vue');
const PedidoDetails = () => import('@/entities/pedido/pedido-details.vue');

const DetallePedido = () => import('@/entities/detalle-pedido/detalle-pedido.vue');
const DetallePedidoUpdate = () => import('@/entities/detalle-pedido/detalle-pedido-update.vue');
const DetallePedidoDetails = () => import('@/entities/detalle-pedido/detalle-pedido-details.vue');

const Producto = () => import('@/entities/producto/producto.vue');
const ProductoUpdate = () => import('@/entities/producto/producto-update.vue');
const ProductoDetails = () => import('@/entities/producto/producto-details.vue');

const TipoMerchandise = () => import('@/entities/tipo-merchandise/tipo-merchandise.vue');
const TipoMerchandiseUpdate = () => import('@/entities/tipo-merchandise/tipo-merchandise-update.vue');
const TipoMerchandiseDetails = () => import('@/entities/tipo-merchandise/tipo-merchandise-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'costo-prducto',
      name: 'CostoPrducto',
      component: CostoPrducto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'costo-prducto/new',
      name: 'CostoPrductoCreate',
      component: CostoPrductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'costo-prducto/:costoPrductoId/edit',
      name: 'CostoPrductoEdit',
      component: CostoPrductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'costo-prducto/:costoPrductoId/view',
      name: 'CostoPrductoView',
      component: CostoPrductoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado-pedido',
      name: 'EstadoPedido',
      component: EstadoPedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado-pedido/new',
      name: 'EstadoPedidoCreate',
      component: EstadoPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado-pedido/:estadoPedidoId/edit',
      name: 'EstadoPedidoEdit',
      component: EstadoPedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado-pedido/:estadoPedidoId/view',
      name: 'EstadoPedidoView',
      component: EstadoPedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
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
    {
      path: 'detalle-pedido',
      name: 'DetallePedido',
      component: DetallePedido,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'detalle-pedido/new',
      name: 'DetallePedidoCreate',
      component: DetallePedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'detalle-pedido/:detallePedidoId/edit',
      name: 'DetallePedidoEdit',
      component: DetallePedidoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'detalle-pedido/:detallePedidoId/view',
      name: 'DetallePedidoView',
      component: DetallePedidoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto',
      name: 'Producto',
      component: Producto,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/new',
      name: 'ProductoCreate',
      component: ProductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/:productoId/edit',
      name: 'ProductoEdit',
      component: ProductoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'producto/:productoId/view',
      name: 'ProductoView',
      component: ProductoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-merchandise',
      name: 'TipoMerchandise',
      component: TipoMerchandise,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-merchandise/new',
      name: 'TipoMerchandiseCreate',
      component: TipoMerchandiseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-merchandise/:tipoMerchandiseId/edit',
      name: 'TipoMerchandiseEdit',
      component: TipoMerchandiseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-merchandise/:tipoMerchandiseId/view',
      name: 'TipoMerchandiseView',
      component: TipoMerchandiseDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
