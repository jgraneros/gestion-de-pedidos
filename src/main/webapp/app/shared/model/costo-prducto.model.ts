import { type IProducto } from '@/shared/model/producto.model';

export interface ICostoPrducto {
  id?: number;
  costoMaterial?: number | null;
  costosAgregados?: number | null;
  costoPostImpresion?: number | null;
  producto?: IProducto | null;
}

export class CostoPrducto implements ICostoPrducto {
  constructor(
    public id?: number,
    public costoMaterial?: number | null,
    public costosAgregados?: number | null,
    public costoPostImpresion?: number | null,
    public producto?: IProducto | null,
  ) {}
}
