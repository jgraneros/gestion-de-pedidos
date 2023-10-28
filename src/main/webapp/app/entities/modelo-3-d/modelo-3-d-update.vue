<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.modelo3D.home.createOrEditLabel"
          data-cy="Modelo3DCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.modelo3D.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="modelo3D.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="modelo3D.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.modelo3D.nombre')" for="modelo-3-d-nombre"></label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="modelo-3-d-nombre"
              data-cy="nombre"
              :class="{ valid: !v$.nombre.$invalid, invalid: v$.nombre.$invalid }"
              v-model="v$.nombre.$model"
              required
            />
            <div v-if="v$.nombre.$anyDirty && v$.nombre.$invalid">
              <small class="form-text text-danger" v-for="error of v$.nombre.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.modelo3D.archivo')" for="modelo-3-d-archivo"></label>
            <input
              type="text"
              class="form-control"
              name="archivo"
              id="modelo-3-d-archivo"
              data-cy="archivo"
              :class="{ valid: !v$.archivo.$invalid, invalid: v$.archivo.$invalid }"
              v-model="v$.archivo.$model"
              required
            />
            <div v-if="v$.archivo.$anyDirty && v$.archivo.$invalid">
              <small class="form-text text-danger" v-for="error of v$.archivo.$errors" :key="error.$uid">{{ error.$message }}</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="t$('gestionPedidosApp.modelo3D.descripcion')" for="modelo-3-d-descripcion"></label>
            <textarea
              class="form-control"
              name="descripcion"
              id="modelo-3-d-descripcion"
              data-cy="descripcion"
              :class="{ valid: !v$.descripcion.$invalid, invalid: v$.descripcion.$invalid }"
              v-model="v$.descripcion.$model"
            ></textarea>
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
<script lang="ts" src="./modelo-3-d-update.component.ts"></script>
