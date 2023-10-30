<template>
  <div>
    <h2 id="page-heading" data-cy="DetallePedidoHeading">
      <span v-text="t$('gestionPedidosApp.detallePedido.home.title')" id="detalle-pedido-heading"></span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="t$('gestionPedidosApp.detallePedido.home.refreshListLabel')"></span>
        </button>
        <router-link :to="{ name: 'DetallePedidoCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-detalle-pedido"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="t$('gestionPedidosApp.detallePedido.home.createLabel')"></span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && detallePedidos && detallePedidos.length === 0">
      <span v-text="t$('gestionPedidosApp.detallePedido.home.notFound')"></span>
    </div>
    <div class="table-responsive" v-if="detallePedidos && detallePedidos.length > 0">
      <table class="table table-striped" aria-describedby="detallePedidos">
        <thead>
          <tr>
            <th scope="row"><span v-text="t$('global.field.id')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.cantidad')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.subTotalCosto')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.detalle')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.detalle')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.pedido')"></span></th>
            <th scope="row"><span v-text="t$('gestionPedidosApp.detallePedido.producto')"></span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="detallePedido in detallePedidos" :key="detallePedido.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DetallePedidoView', params: { detallePedidoId: detallePedido.id } }">{{
                detallePedido.id
              }}</router-link>
            </td>
            <td>{{ detallePedido.cantidad }}</td>
            <td>{{ detallePedido.subTotalCosto }}</td>
            <td>
              <div v-if="detallePedido.detalle">
                <router-link :to="{ name: 'ProductoView', params: { productoId: detallePedido.detalle.id } }">{{
                  detallePedido.detalle.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="detallePedido.detalle">
                <router-link :to="{ name: 'PedidoView', params: { pedidoId: detallePedido.detalle.id } }">{{
                  detallePedido.detalle.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="detallePedido.pedido">
                <router-link :to="{ name: 'PedidoView', params: { pedidoId: detallePedido.pedido.id } }">{{
                  detallePedido.pedido.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="detallePedido.producto">
                <router-link :to="{ name: 'ProductoView', params: { productoId: detallePedido.producto.id } }">{{
                  detallePedido.producto.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'DetallePedidoView', params: { detallePedidoId: detallePedido.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.view')"></span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'DetallePedidoEdit', params: { detallePedidoId: detallePedido.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="t$('entity.action.edit')"></span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(detallePedido)"
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
          id="gestionPedidosApp.detallePedido.delete.question"
          data-cy="detallePedidoDeleteDialogHeading"
          v-text="t$('entity.delete.title')"
        ></span>
      </template>
      <div class="modal-body">
        <p id="jhi-delete-detallePedido-heading" v-text="t$('gestionPedidosApp.detallePedido.delete.question', { id: removeId })"></p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-text="t$('entity.action.cancel')" v-on:click="closeDialog()"></button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-detallePedido"
            data-cy="entityConfirmDeleteButton"
            v-text="t$('entity.action.delete')"
            v-on:click="removeDetallePedido()"
          ></button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./detalle-pedido.component.ts"></script>
