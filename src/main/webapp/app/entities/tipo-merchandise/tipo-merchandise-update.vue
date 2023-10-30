<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.tipoMerchandise.home.createOrEditLabel"
          data-cy="TipoMerchandiseCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.tipoMerchandise.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="tipoMerchandise.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="tipoMerchandise.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.tipoMerchandise.tipo')" for="tipo-merchandise-tipo"></label>
            <select
              class="form-control"
              name="tipo"
              :class="{ valid: !v$.tipo.$invalid, invalid: v$.tipo.$invalid }"
              v-model="v$.tipo.$model"
              id="tipo-merchandise-tipo"
              data-cy="tipo"
            >
              <option
                v-for="merchandise in merchandiseValues"
                :key="merchandise"
                v-bind:value="merchandise"
                v-bind:label="t$('gestionPedidosApp.Merchandise.' + merchandise)"
              >
                {{ merchandise }}
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
<script lang="ts" src="./tipo-merchandise-update.component.ts"></script>
