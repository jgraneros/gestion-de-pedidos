export interface IModelo3D {
  id?: number;
  nombre?: string;
  archivo?: string;
  descripcion?: string | null;
}

export class Modelo3D implements IModelo3D {
  constructor(
    public id?: number,
    public nombre?: string,
    public archivo?: string,
    public descripcion?: string | null,
  ) {}
}
