<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.detallePedido.home.createOrEditLabel"
          data-cy="DetallePedidoCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.detallePedido.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="detallePedido.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="detallePedido.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.detallePedido.cantidad')" for="detalle-pedido-cantidad"></label>
            <input
              type="number"
              class="form-control"
              name="cantidad"
              id="detalle-pedido-cantidad"
              data-cy="cantidad"
              :class="{ valid: !v$.cantidad.$invalid, invalid: v$.cantidad.$invalid }"
              v-model.number="v$.cantidad.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('gestionPedidosApp.detallePedido.subTotalCosto')"
              for="detalle-pedido-subTotalCosto"
            ></label>
            <input
              type="number"
              class="form-control"
              name="subTotalCosto"
              id="detalle-pedido-subTotalCosto"
              data-cy="subTotalCosto"
              :class="{ valid: !v$.subTotalCosto.$invalid, invalid: v$.subTotalCosto.$invalid }"
              v-model.number="v$.subTotalCosto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.detallePedido.pedido')" for="detalle-pedido-pedido"></label>
            <select class="form-control" id="detalle-pedido-pedido" data-cy="pedido" name="pedido" v-model="detallePedido.pedido">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="detallePedido.pedido && pedidoOption.id === detallePedido.pedido.id ? detallePedido.pedido : pedidoOption"
                v-for="pedidoOption in pedidos"
                :key="pedidoOption.id"
              >
                {{ pedidoOption.numeroPedido }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.detallePedido.producto')" for="detalle-pedido-producto"></label>
            <select class="form-control" id="detalle-pedido-producto" data-cy="producto" name="producto" v-model="detallePedido.producto">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  detallePedido.producto && productoOption.id === detallePedido.producto.id ? detallePedido.producto : productoOption
                "
                v-for="productoOption in productos"
                :key="productoOption.id"
              >
                {{ productoOption.codigo }}
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
<script lang="ts" src="./detalle-pedido-update.component.ts"></script>
