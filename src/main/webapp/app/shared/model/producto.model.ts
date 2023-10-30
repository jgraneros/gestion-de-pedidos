import { type ICostoPrducto } from '@/shared/model/costo-prducto.model';
import { type ITipoMerchandise } from '@/shared/model/tipo-merchandise.model';
import { type IDetallePedido } from '@/shared/model/detalle-pedido.model';

import { type TipoProducto } from '@/shared/model/enumerations/tipo-producto.model';
export interface IProducto {
  id?: number;
  nombre?: string | null;
  descripcion?: string | null;
  peso?: number | null;
  tipo?: keyof typeof TipoProducto | null;
  producto?: ICostoPrducto | null;
  producto?: ITipoMerchandise | null;
  productos?: IDetallePedido[] | null;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public descripcion?: string | null,
    public peso?: number | null,
    public tipo?: keyof typeof TipoProducto | null,
    public producto?: ICostoPrducto | null,
    public producto?: ITipoMerchandise | null,
    public productos?: IDetallePedido[] | null,
  ) {}
}
