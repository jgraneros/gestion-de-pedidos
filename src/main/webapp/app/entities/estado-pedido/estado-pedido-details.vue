<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="estadoPedido">
        <h2 class="jh-entity-heading" data-cy="estadoPedidoDetailsHeading">
          <span v-text="t$('gestionPedidosApp.estadoPedido.detail.title')"></span> {{ estadoPedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.estadoPedido.descripcion')"></span>
          </dt>
          <dd>
            <span v-text="t$('gestionPedidosApp.EstadosPedido.' + estadoPedido.descripcion)"></span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.estadoPedido.pedido')"></span>
          </dt>
          <dd>
            <div v-if="estadoPedido.pedido">
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: estadoPedido.pedido.id } }">{{
                estadoPedido.pedido.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="estadoPedido.id"
          :to="{ name: 'EstadoPedidoEdit', params: { estadoPedidoId: estadoPedido.id } }"
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

<script lang="ts" src="./estado-pedido-details.component.ts"></script>
