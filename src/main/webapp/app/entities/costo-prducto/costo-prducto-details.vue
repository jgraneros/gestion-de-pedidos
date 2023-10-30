<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="costoPrducto">
        <h2 class="jh-entity-heading" data-cy="costoPrductoDetailsHeading">
          <span v-text="t$('gestionPedidosApp.costoPrducto.detail.title')"></span> {{ costoPrducto.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.costoPrducto.costoMaterial')"></span>
          </dt>
          <dd>
            <span>{{ costoPrducto.costoMaterial }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.costoPrducto.costosAgregados')"></span>
          </dt>
          <dd>
            <span>{{ costoPrducto.costosAgregados }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.costoPrducto.costoPostImpresion')"></span>
          </dt>
          <dd>
            <span>{{ costoPrducto.costoPostImpresion }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.costoPrducto.producto')"></span>
          </dt>
          <dd>
            <div v-if="costoPrducto.producto">
              <router-link :to="{ name: 'ProductoView', params: { productoId: costoPrducto.producto.id } }">{{
                costoPrducto.producto.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link
          v-if="costoPrducto.id"
          :to="{ name: 'CostoPrductoEdit', params: { costoPrductoId: costoPrducto.id } }"
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

<script lang="ts" src="./costo-prducto-details.component.ts"></script>
