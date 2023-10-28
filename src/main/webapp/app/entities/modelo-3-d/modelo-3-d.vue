<template>
  <div>
    <h2 id="page-heading" data-cy="Modelo3DHeading">
      <span v-text="t$('gestionPedidosApp.modelo3D.home.title')" id="modelo-3-d-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('gestionPedidosApp.modelo3D.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'Modelo3DCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-modelo-3-d"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('gestionPedidosApp.modelo3D.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && modelo3DS && modelo3DS.length === 0">
      <span v-text="t$('gestionPedidosApp.modelo3D.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="modelo3DS && modelo3DS.length > 0">
      <table class="table table-striped" aria-describedby="modelo3DS">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="t$('global.field.id')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nombre')">
              <span v-text="t$('gestionPedidosApp.modelo3D.nombre')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nombre'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('archivo')">
              <span v-text="t$('gestionPedidosApp.modelo3D.archivo')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'archivo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descripcion')">
              <span v-text="t$('gestionPedidosApp.modelo3D.descripcion')"></span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descripcion'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="modelo3D in modelo3DS" :key="modelo3D.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'Modelo3DView', params: { modelo3DId: modelo3D.id } }">{{ modelo3D.id }}</router-link>
            </td>
            <td>{{ modelo3D.nombre }}</td>
            <td>{{ modelo3D.archivo }}</td>
            <td>{{ modelo3D.descripcion }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'Modelo3DView', params: { modelo3DId: modelo3D.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'Modelo3DEdit', params: { modelo3DId: modelo3D.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(modelo3D)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="t$('entity.action.delete')"></span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="gestionPedidosApp.modelo3D.delete.question"
          data-cy="modelo3DDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-modelo3D-heading" v-text="t$('gestionPedidosApp.modelo3D.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-modelo3D"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeModelo3D()"
          ></button>
        </div>
      </template>
    </b-modal>
    <div v-show="modelo3DS && modelo3DS.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./modelo-3-d.component.ts"></script>
