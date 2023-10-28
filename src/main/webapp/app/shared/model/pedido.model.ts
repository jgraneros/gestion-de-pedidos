import { type IModelo3D } from '@/shared/model/modelo-3-d.model';
import { type ICliente } from '@/shared/model/cliente.model';

import { type EstadoPedido } from '@/shared/model/enumerations/estado-pedido.model';
export interface IPedido {
  id?: number;
  fechaCreacion?: Date;
  fechaEntrega?: Date | null;
  estado?: keyof typeof EstadoPedido;
  detalles?: string | null;
  modelo3d?: IModelo3D | null;
  cliente?: ICliente;
}

export class Pedido implements IPedido {
  constructor(
    public id?: number,
    public fechaCreacion?: Date,
    public fechaEntrega?: Date | null,
    public estado?: keyof typeof EstadoPedido,
    public detalles?: string | null,
    public modelo3d?: IModelo3D | null,
    public cliente?: ICliente,
  ) {}
}
