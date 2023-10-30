import { type IPedido } from '@/shared/model/pedido.model';
import { type IProducto } from '@/shared/model/producto.model';

export interface IDetallePedido {
  id?: number;
  cantidad?: number | null;
  subTotalCosto?: number | null;
  detalle?: IPedido | null;
  detalle?: IProducto | null;
}

export class DetallePedido implements IDetallePedido {
  constructor(
    public id?: number,
    public cantidad?: number | null,
    public subTotalCosto?: number | null,
    public detalle?: IPedido | null,
    public detalle?: IProducto | null,
  ) {}
}
