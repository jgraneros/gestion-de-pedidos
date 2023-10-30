<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="producto">
        <h2 class="jh-entity-heading" data-cy="productoDetailsHeading">
          <span v-text="t$('gestionPedidosApp.producto.detail.title')"></span> {{ producto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.nombre')"></span>
          </dt>
          <dd>
            <span>{{ producto.nombre }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.descripcion')"></span>
          </dt>
          <dd>
            <span>{{ producto.descripcion }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.peso')"></span>
          </dt>
          <dd>
            <span>{{ producto.peso }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.tipo')"></span>
          </dt>
          <dd>
            <span v-text="t$('gestionPedidosApp.TipoProducto.' + producto.tipo)"></span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.producto')"></span>
          </dt>
          <dd>
            <div v-if="producto.producto">
              <router-link :to="{ name: 'CostoPrductoView', params: { costoPrductoId: producto.producto.id } }">{{
                producto.producto.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.producto.producto')"></span>
          </dt>
          <dd>
            <div v-if="producto.producto">
              <router-link :to="{ name: 'TipoMerchandiseView', params: { tipoMerchandiseId: producto.producto.id } }">{{
                producto.producto.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="producto.id" :to="{ name: 'ProductoEdit', params: { productoId: producto.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./producto-details.component.ts"></script>
