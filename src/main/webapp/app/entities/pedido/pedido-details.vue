<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="pedido">
        <h2 class="jh-entity-heading" data-cy="pedidoDetailsHeading">
          <span v-text="t$('gestionPedidosApp.pedido.detail.title')"></span> {{ pedido.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.fechaCreacion')"></span>
          </dt>
          <dd>
            <span>{{ pedido.fechaCreacion }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.fechaEntrega')"></span>
          </dt>
          <dd>
            <span>{{ pedido.fechaEntrega }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.estado')"></span>
          </dt>
          <dd>
            <span v-text="t$('gestionPedidosApp.EstadoPedido.' + pedido.estado)"></span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.detalles')"></span>
          </dt>
          <dd>
            <span>{{ pedido.detalles }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.modelo3d')"></span>
          </dt>
          <dd>
            <div v-if="pedido.modelo3d">
              <router-link :to="{ name: 'Modelo3DView', params: { modelo3DId: pedido.modelo3d.id } }">{{ pedido.modelo3d.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.pedido.cliente')"></span>
          </dt>
          <dd>
            <div v-if="pedido.cliente">
              <router-link :to="{ name: 'ClienteView', params: { clienteId: pedido.cliente.id } }">{{ pedido.cliente.nombre }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="pedido.id" :to="{ name: 'PedidoEdit', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./pedido-details.component.ts"></script>
