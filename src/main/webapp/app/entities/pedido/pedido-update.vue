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
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.fechaCreacion')" for="pedido-fechaCreacion"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="pedido-fechaCreacion"
                  v-model="v$.fechaCreacion.$model"
                  name="fechaCreacion"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="pedido-fechaCreacion"
                data-cy="fechaCreacion"
                type="text"
                class="form-control"
                name="fechaCreacion"
                :class="{ valid: !v$.fechaCreacion.$invalid, invalid: v$.fechaCreacion.$invalid }"
                v-model="v$.fechaCreacion.$model"
                required
              />
            </b-input-group>
            <div v-if="v$.fechaCreacion.$anyDirty && v$.fechaCreacion.$invalid">
              <small class="form-text text-danger" v-for="error of v$.fechaCreacion.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.fechaEntrega')" for="pedido-fechaEntrega"></label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="pedido-fechaEntrega"
                  v-model="v$.fechaEntrega.$model"
                  name="fechaEntrega"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="pedido-fechaEntrega"
                data-cy="fechaEntrega"
                type="text"
                class="form-control"
                name="fechaEntrega"
                :class="{ valid: !v$.fechaEntrega.$invalid, invalid: v$.fechaEntrega.$invalid }"
                v-model="v$.fechaEntrega.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.estado')" for="pedido-estado"></label>
            <select
              class="form-control"
              name="estado"
              :class="{ valid: !v$.estado.$invalid, invalid: v$.estado.$invalid }"
              v-model="v$.estado.$model"
              id="pedido-estado"
              data-cy="estado"
              required
            >
              <option
                v-for="estadoPedido in estadoPedidoValues"
                :key="estadoPedido"
                v-bind:value="estadoPedido"
                v-bind:label="t$('gestionPedidosApp.EstadoPedido.' + estadoPedido)"
              >
                {{ estadoPedido }}
              </option>
            </select>
            <div v-if="v$.estado.$anyDirty && v$.estado.$invalid">
              <small class="form-text text-danger" v-for="error of v$.estado.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.detalles')" for="pedido-detalles"></label>
            <input
              type="text"
              class="form-control"
              name="detalles"
              id="pedido-detalles"
              data-cy="detalles"
              :class="{ valid: !v$.detalles.$invalid, invalid: v$.detalles.$invalid }"
              v-model="v$.detalles.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.modelo3d')" for="pedido-modelo3d"></label>
            <select class="form-control" id="pedido-modelo3d" data-cy="modelo3d" name="modelo3d" v-model="pedido.modelo3d">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="pedido.modelo3d && modelo3DOption.id === pedido.modelo3d.id ? pedido.modelo3d : modelo3DOption"
                v-for="modelo3DOption in modelo3DS"
                :key="modelo3DOption.id"
              >
                {{ modelo3DOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.pedido.cliente')" for="pedido-cliente"></label>
            <select class="form-control" id="pedido-cliente" data-cy="cliente" name="cliente" v-model="pedido.cliente" required>
              <option v-if="!pedido.cliente" v-bind:value="null" selected></option>
              <option
                v-bind:value="pedido.cliente && clienteOption.id === pedido.cliente.id ? pedido.cliente : clienteOption"
                v-for="clienteOption in clientes"
                :key="clienteOption.id"
              >
                {{ clienteOption.nombre }}
              </option>
            </select>
          </div>
          <div v-if="v$.cliente.$anyDirty && v$.cliente.$invalid">
            <small class="form-text text-danger" v-for="error of v$.cliente.$errors" :key="error.$uid">{{ error.$message }}</small>
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
