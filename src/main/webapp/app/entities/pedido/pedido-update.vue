<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.pedido.home.createOrEditLabel"
          data-cy="PedidoCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.pedido.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="pedido.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="pedido.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.numeroPedido')" for="pedido-numeroPedido"></label>
            <input
              type="number"
              class="form-control"
              name="numeroPedido"
              id="pedido-numeroPedido"
              data-cy="numeroPedido"
              :class="{ valid: !v$.numeroPedido.$invalid, invalid: v$.numeroPedido.$invalid }"
              v-model.number="v$.numeroPedido.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.costoTotal')" for="pedido-costoTotal"></label>
            <input
              type="number"
              class="form-control"
              name="costoTotal"
              id="pedido-costoTotal"
              data-cy="costoTotal"
              :class="{ valid: !v$.costoTotal.$invalid, invalid: v$.costoTotal.$invalid }"
              v-model.number="v$.costoTotal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.precioVenta')" for="pedido-precioVenta"></label>
            <input
              type="number"
              class="form-control"
              name="precioVenta"
              id="pedido-precioVenta"
              data-cy="precioVenta"
              :class="{ valid: !v$.precioVenta.$invalid, invalid: v$.precioVenta.$invalid }"
              v-model.number="v$.precioVenta.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.gananciaTotal')" for="pedido-gananciaTotal"></label>
            <input
              type="number"
              class="form-control"
              name="gananciaTotal"
              id="pedido-gananciaTotal"
              data-cy="gananciaTotal"
              :class="{ valid: !v$.gananciaTotal.$invalid, invalid: v$.gananciaTotal.$invalid }"
              v-model.number="v$.gananciaTotal.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.cliente')" for="pedido-cliente"></label>
            <select class="form-control" id="pedido-cliente" data-cy="cliente" name="cliente" v-model="pedido.cliente">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="pedido.cliente && clienteOption.id === pedido.cliente.id ? pedido.cliente : clienteOption"
                v-for="clienteOption in clientes"
                :key="clienteOption.id"
              >
                {{ clienteOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.cancel')"></span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="t$('entity.action.save')"></span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./pedido-update.component.ts"></script>
