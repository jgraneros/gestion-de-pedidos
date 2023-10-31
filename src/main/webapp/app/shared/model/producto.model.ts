import { type IDetallePedido } from '@/shared/model/detalle-pedido.model';
import { type ICostoPrducto } from '@/shared/model/costo-prducto.model';
import { type ITipoMerchandise } from '@/shared/model/tipo-merchandise.model';

import { type TipoProducto } from '@/shared/model/enumerations/tipo-producto.model';
export interface IProducto {
  id?: number;
  codigo?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
  peso?: number | null;
  tipo?: keyof typeof TipoProducto | null;
  detallePedidos?: IDetallePedido[] | null;
  costoPrducto?: ICostoPrducto | null;
  tipoMerchandise?: ITipoMerchandise | null;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public codigo?: string | null,
    public nombre?: string | null,
    public descripcion?: string | null,
    public peso?: number | null,
    public tipo?: keyof typeof TipoProducto | null,
    public detallePedidos?: IDetallePedido[] | null,
    public costoPrducto?: ICostoPrducto | null,
    public tipoMerchandise?: ITipoMerchandise | null,
  ) {}
}
