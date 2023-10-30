import { type IEstadoPedido } from '@/shared/model/estado-pedido.model';
import { type ICliente } from '@/shared/model/cliente.model';

export interface IPedido {
  id?: number;
  numeroPedido?: number | null;
  costoTotal?: number | null;
  precioVenta?: number | null;
  gananciaTotal?: number | null;
  pedidos?: IEstadoPedido[] | null;
  cliente?: ICliente | null;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public numeroPedido?: number | null,
    public costoTotal?: number | null,
    public precioVenta?: number | null,
    public gananciaTotal?: number | null,
    public pedidos?: IEstadoPedido[] | null,
    public cliente?: ICliente | null,
  ) {}
}
