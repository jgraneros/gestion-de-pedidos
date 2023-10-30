<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="gestionPedidosApp.costoPrducto.home.createOrEditLabel"
          data-cy="CostoPrductoCreateUpdateHeading"
          v-text="t$('gestionPedidosApp.costoPrducto.home.createOrEditLabel')"
        ></h2>
        <div>
          <div class="form-group" v-if="costoPrducto.id">
            <label for="id" v-text="t$('global.field.id')"></label>
            <input type="text" class="form-control" id="id" name="id" v-model="costoPrducto.id" readonly />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('gestionPedidosApp.costoPrducto.costoMaterial')"
              for="costo-prducto-costoMaterial"
            ></label>
            <input
              type="number"
              class="form-control"
              name="costoMaterial"
              id="costo-prducto-costoMaterial"
              data-cy="costoMaterial"
              :class="{ valid: !v$.costoMaterial.$invalid, invalid: v$.costoMaterial.$invalid }"
              v-model.number="v$.costoMaterial.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('gestionPedidosApp.costoPrducto.costosAgregados')"
              for="costo-prducto-costosAgregados"
            ></label>
            <input
              type="number"
              class="form-control"
              name="costosAgregados"
              id="costo-prducto-costosAgregados"
              data-cy="costosAgregados"
              :class="{ valid: !v$.costosAgregados.$invalid, invalid: v$.costosAgregados.$invalid }"
              v-model.number="v$.costosAgregados.$model"
            />
          </div>
          <div class="form-group">
            <label
              class="form-control-label"
              v-text="t$('gestionPedidosApp.costoPrducto.costoPostImpresion')"
              for="costo-prducto-costoPostImpresion"
            ></label>
            <input
              type="number"
              class="form-control"
              name="costoPostImpresion"
              id="costo-prducto-costoPostImpresion"
              data-cy="costoPostImpresion"
              :class="{ valid: !v$.costoPostImpresion.$invalid, invalid: v$.costoPostImpresion.$invalid }"
              v-model.number="v$.costoPostImpresion.$model"
            />
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
<script lang="ts" src="./costo-prducto-update.component.ts"></script>
