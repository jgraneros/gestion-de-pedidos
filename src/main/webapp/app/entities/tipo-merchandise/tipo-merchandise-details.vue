<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="tipoMerchandise">
        <h2 class="jh-entity-heading" data-cy="tipoMerchandiseDetailsHeading">
          <span v-text="t$('gestionPedidosApp.tipoMerchandise.detail.title')"></span> {{ tipoMerchandise.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.tipoMerchandise.tipo')"></span>
          </dt>
          <dd>
            <span v-text="t$('gestionPedidosApp.Merchandise.' + tipoMerchandise.tipo)"></span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.tipoMerchandise.producto')"></span>
          </dt>
          <dd>
            <div v-if="tipoMerchandise.producto">
              <router-link :to="{ name: 'ProductoView', params: { productoId: tipoMerchandise.producto.id } }">{{
                tipoMerchandise.producto.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="tipoMerchandise.id"
          :to="{ name: 'TipoMerchandiseEdit', params: { tipoMerchandiseId: tipoMerchandise.id } }"
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

<script lang="ts" src="./tipo-merchandise-details.component.ts"></script>
