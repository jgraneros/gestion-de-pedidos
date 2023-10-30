<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.producto.home.createOrEditLabel"
          data-cy="ProductoCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.producto.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="producto.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="producto.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.nombre')" for="producto-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="producto-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.descripcion')" for="producto-descripcion"></label>
            <textarea
              class="form-control"
              name="descripcion"
              id="producto-descripcion"
              data-cy="descripcion"
              :class="{ valid: !v$.descripcion.$invalid, invalid: v$.descripcion.$invalid }"
              v-model="v$.descripcion.$model"
            ></textarea>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.peso')" for="producto-peso"></label>
            <input
              type="number"
              class="form-control"
              name="peso"
              id="producto-peso"
              data-cy="peso"
              :class="{ valid: !v$.peso.$invalid, invalid: v$.peso.$invalid }"
              v-model.number="v$.peso.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.tipo')" for="producto-tipo"></label>
            <select
              class="form-control"
              name="tipo"
              :class="{ valid: !v$.tipo.$invalid, invalid: v$.tipo.$invalid }"
              v-model="v$.tipo.$model"
              id="producto-tipo"
              data-cy="tipo"
            >
              <option
                v-for="tipoProducto in tipoProductoValues"
                :key="tipoProducto"
                v-bind:value="tipoProducto"
                v-bind:label="t$('gestionPedidosApp.TipoProducto.' + tipoProducto)"
              >
                {{ tipoProducto }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.producto')" for="producto-producto"></label>
            <select class="form-control" id="producto-producto" data-cy="producto" name="producto" v-model="producto.producto">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="producto.producto && costoPrductoOption.id === producto.producto.id ? producto.producto : costoPrductoOption"
                v-for="costoPrductoOption in costoPrductos"
                :key="costoPrductoOption.id"
              >
                {{ costoPrductoOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.producto.producto')" for="producto-producto"></label>
            <select class="form-control" id="producto-producto" data-cy="producto" name="producto" v-model="producto.producto">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  producto.producto && tipoMerchandiseOption.id === producto.producto.id ? producto.producto : tipoMerchandiseOption
                "
                v-for="tipoMerchandiseOption in tipoMerchandises"
                :key="tipoMerchandiseOption.id"
              >
                {{ tipoMerchandiseOption.id }}
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
<script lang="ts" src="./producto-update.component.ts"></script>
