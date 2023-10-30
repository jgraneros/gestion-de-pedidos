import { type IProducto } from '@/shared/model/producto.model';

import { type Merchandise } from '@/shared/model/enumerations/merchandise.model';
export interface ITipoMerchandise {
  id?: number;
  tipo?: keyof typeof Merchandise | null;
  producto?: IProducto | null;
}

export class TipoMerchandise implements ITipoMerchandise {
  constructor(
    public id?: number,
    public tipo?: keyof typeof Merchandise | null,
    public producto?: IProducto | null,
  ) {}
}
