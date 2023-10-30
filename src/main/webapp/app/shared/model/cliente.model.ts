import { type IPedido } from '@/shared/model/pedido.model';

export interface ICliente {
  id?: number;
  nombre?: string | null;
  email?: string | null;
  telefono?: string | null;
  clientes?: IPedido[] | null;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public email?: string | null,
    public telefono?: string | null,
    public clientes?: IPedido[] | null,
  ) {}
}
