<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.estadoPedido.home.createOrEditLabel"
          data-cy="EstadoPedidoCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.estadoPedido.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="estadoPedido.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="estadoPedido.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('gestionPedidosApp.estadoPedido.descripcion')"
              for="estado-pedido-descripcion"
            ></label>
            <select
              class="form-control"
              name="descripcion"
              :class="{ valid: !v$.descripcion.$invalid, invalid: v$.descripcion.$invalid }"
              v-model="v$.descripcion.$model"
              id="estado-pedido-descripcion"
              data-cy="descripcion"
            >
              <option
                v-for="estadosPedido in estadosPedidoValues"
                :key="estadosPedido"
                v-bind:value="estadosPedido"
                v-bind:label="t$('gestionPedidosApp.EstadosPedido.' + estadosPedido)"
              >
                {{ estadosPedido }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.estadoPedido.pedido')" for="estado-pedido-pedido"></label>
            <select class="form-control" id="estado-pedido-pedido" data-cy="pedido" name="pedido" v-model="estadoPedido.pedido">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="estadoPedido.pedido && pedidoOption.id === estadoPedido.pedido.id ? estadoPedido.pedido : pedidoOption"
                v-for="pedidoOption in pedidos"
                :key="pedidoOption.id"
              >
                {{ pedidoOption.id }}
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
<script lang="ts" src="./estado-pedido-update.component.ts"></script>
