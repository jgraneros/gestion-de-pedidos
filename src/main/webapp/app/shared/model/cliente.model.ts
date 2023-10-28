import { type IPedido } from '@/shared/model/pedido.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  email?: string;
  telefono?: string | null;
  pedidos?: IPedido[] | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public email?: string,
    public telefono?: string | null,
    public pedidos?: IPedido[] | null,
  ) {}
}
