<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="detallePedido">
        <h2 class="jh-entity-heading" data-cy="detallePedidoDetailsHeading">
          <span v-text="t$('gestionPedidosApp.detallePedido.detail.title')"></span> {{ detallePedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.detallePedido.cantidad')"></span>
          </dt>
          <dd>
            <span>{{ detallePedido.cantidad }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.detallePedido.subTotalCosto')"></span>
          </dt>
          <dd>
            <span>{{ detallePedido.subTotalCosto }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.detallePedido.pedido')"></span>
          </dt>
          <dd>
            <div v-if="detallePedido.pedido">
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: detallePedido.pedido.id } }">{{
                detallePedido.pedido.numeroPedido
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.detallePedido.producto')"></span>
          </dt>
          <dd>
            <div v-if="detallePedido.producto">
              <router-link :to="{ name: 'ProductoView', params: { productoId: detallePedido.producto.id } }">{{
                detallePedido.producto.codigo
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="detallePedido.id"
          :to="{ name: 'DetallePedidoEdit', params: { detallePedidoId: detallePedido.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./detalle-pedido-details.component.ts"></script>
