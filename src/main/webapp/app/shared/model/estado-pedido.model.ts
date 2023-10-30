import { type IPedido } from '@/shared/model/pedido.model';

import { type EstadosPedido } from '@/shared/model/enumerations/estados-pedido.model';
export interface IEstadoPedido {
  id?: number;
  descripcion?: keyof typeof EstadosPedido | null;
  pedido?: IPedido | null;
}

export class EstadoPedido implements IEstadoPedido {
  constructor(
    public id?: number,
    public descripcion?: keyof typeof EstadosPedido | null,
    public pedido?: IPedido | null,
  ) {}
}
