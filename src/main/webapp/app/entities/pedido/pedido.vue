<template>
  <div>
    <h2 id="page-heading" data-cy="PedidoHeading">
      <span v-text="t$('gestionPedidosApp.pedido.home.title')" id="pedido-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('gestionPedidosApp.pedido.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'PedidoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-pedido"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('gestionPedidosApp.pedido.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && pedidos && pedidos.length === 0">
      <span v-text="t$('gestionPedidosApp.pedido.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="pedidos && pedidos.length > 0">
      <table class="table table-striped" aria-describedby="pedidos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.pedido.numeroPedido')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.pedido.costoTotal')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.pedido.precioVenta')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.pedido.gananciaTotal')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.pedido.cliente')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="pedido in pedidos" :key="pedido.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PedidoView', params: { pedidoId: pedido.id } }">{{ pedido.id }}</router-link>
            </td>
            <td>{{ pedido.numeroPedido }}</td>
            <td>{{ pedido.costoTotal }}</td>
            <td>{{ pedido.precioVenta }}</td>
            <td>{{ pedido.gananciaTotal }}</td>
            <td>
              <div v-if="pedido.cliente">
                <router-link :to="{ name: 'ClienteView', params: { clienteId: pedido.cliente.id } }">{{ pedido.cliente.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PedidoView', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PedidoEdit', params: { pedidoId: pedido.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(pedido)"
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
        <span id="gestionPedidosApp.pedido.delete.question" data-cy="pedidoDeleteDialogHeading" v-text="t$('entity.delete.title')"></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-pedido-heading" v-text="t$('gestionPedidosApp.pedido.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-pedido"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removePedido()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./pedido.component.ts"></script>
