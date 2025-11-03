
export interface Categoria {
  id?: number;
  nomeCategoria: string;
  tipoCategoria: 'Receita' | 'Despesa';
}

export interface Conta {
  id?: number;
  agencia: number;
  numero: number;
  tipoConta: 'CORRENTE' | 'POUPANCA' | 'SALARIO';
  saldo: number;
  dataCriacao?: string;
}

export enum StatusMeta {
    ATIVA = "ATIVA",
    CONCLUIDA = "CONCLUIDA",
    CANCELADA = "CANCELADA",
}

export interface Meta {
  id?: number;
  nome: string;
  dataCriacao?: string;
  valorAlvo: number;
  valorAtual: number;
  status: StatusMeta;
  dataAlvo: string;
  categoria?: Categoria;
}

export interface Transacao {
  id?: number;
  descricao: string;
  dataCriacao?: string;
  valor: number;
  tipo: 'Receita' | 'Despesa';
  conta?: Conta;
  categoria?: Categoria;
}

export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string;
  dataCadastro?: string;
  contas?: Conta[];
  metas?: Meta[];
}

export interface LoginRequest {
  email: string;
  senha?: string;
}
