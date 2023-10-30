<template>
  <div>
    <b-dropdown-item to="/costo-prducto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.costoPrducto')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/estado-pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.estadoPedido')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/cliente">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.cliente')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.pedido')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/detalle-pedido">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.detallePedido')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/producto">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.producto')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/tipo-merchandise">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.tipoMerchandise')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
