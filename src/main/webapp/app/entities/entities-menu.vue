<template>
  <div>
    <b-dropdown-item to="/cliente">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cliente')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/modelo-3-d">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.modelo3D')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.pedido')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
