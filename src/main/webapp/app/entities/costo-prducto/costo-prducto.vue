<template>
  <div>
    <h2 id="page-heading" data-cy="CostoPrductoHeading">
      <span v-text="t$('gestionPedidosApp.costoPrducto.home.title')" id="costo-prducto-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('gestionPedidosApp.costoPrducto.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'CostoPrductoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-costo-prducto"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('gestionPedidosApp.costoPrducto.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && costoPrductos && costoPrductos.length === 0">
      <span v-text="t$('gestionPedidosApp.costoPrducto.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="costoPrductos && costoPrductos.length > 0">
      <table class="table table-striped" aria-describedby="costoPrductos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.costoPrducto.costoMaterial')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.costoPrducto.costosAgregados')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.costoPrducto.costoPostImpresion')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.costoPrducto.producto')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="costoPrducto in costoPrductos" :key="costoPrducto.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CostoPrductoView', params: { costoPrductoId: costoPrducto.id } }">{{
                costoPrducto.id
              }}</router-link>
            </td>
            <td>{{ costoPrducto.costoMaterial }}</td>
            <td>{{ costoPrducto.costosAgregados }}</td>
            <td>{{ costoPrducto.costoPostImpresion }}</td>
            <td>
              <div v-if="costoPrducto.producto">
                <router-link :to="{ name: 'ProductoView', params: { productoId: costoPrducto.producto.id } }">{{
                  costoPrducto.producto.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CostoPrductoView', params: { costoPrductoId: costoPrducto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CostoPrductoEdit', params: { costoPrductoId: costoPrducto.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(costoPrducto)"
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
          id="gestionPedidosApp.costoPrducto.delete.question"
          data-cy="costoPrductoDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-costoPrducto-heading" v-text="t$('gestionPedidosApp.costoPrducto.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-costoPrducto"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeCostoPrducto()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./costo-prducto.component.ts"></script>
