import { type IProducto } from '@/shared/model/producto.model';
import { type IPedido } from '@/shared/model/pedido.model';

export interface IDetallePedido {
  id?: number;
  cantidad?: number | null;
  subTotalCosto?: number | null;
  detalle?: IProducto | null;
  detalle?: IPedido | null;
  pedido?: IPedido | null;
  producto?: IProducto | null;
}

export class DetallePedido implements IDetallePedido {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public subTotalCosto?: number | null,
    public detalle?: IProducto | null,
    public detalle?: IPedido | null,
    public pedido?: IPedido | null,
    public producto?: IProducto | null,
  ) {}
}
