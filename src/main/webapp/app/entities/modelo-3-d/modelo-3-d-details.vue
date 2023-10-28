<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="modelo3D">
        <h2 class="jh-entity-heading" data-cy="modelo3DDetailsHeading">
          <span v-text="t$('gestionPedidosApp.modelo3D.detail.title')"></span> {{ modelo3D.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="t$('gestionPedidosApp.modelo3D.nombre')"></span>
          </dt>
          <dd>
            <span>{{ modelo3D.nombre }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.modelo3D.archivo')"></span>
          </dt>
          <dd>
            <span>{{ modelo3D.archivo }}</span>
          </dd>
          <dt>
            <span v-text="t$('gestionPedidosApp.modelo3D.descripcion')"></span>
          </dt>
          <dd>
            <span>{{ modelo3D.descripcion }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.back')"></span>
        </button>
        <router-link v-if="modelo3D.id" :to="{ name: 'Modelo3DEdit', params: { modelo3DId: modelo3D.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.edit')"></span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./modelo-3-d-details.component.ts"></script>
