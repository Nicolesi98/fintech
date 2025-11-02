export interface Categoria {
  id?: number;
  nomeCategoria: string;
  tipoCategoria: string;
}

export interface Conta {
  id?: number;
  agencia: number;
  numero: number;
  tipoConta: "Conta Corrente" | "Conta Poupança" | "Conta Salário";
  saldo: number;
  dataCriacao?: string;
}

export interface Meta {
  id?: number;
  nome: string;
  dataCriacao?: string;
  valorAlvo: number;
  valorAtual: number;
  status: "Ativa" | "Concluída" | "Cancelada";
  dataAlvo: string;
  categoria?: Categoria;
}

export interface Transacao {
  id?: number;
  descricao: string;
  dataCriacao?: string;
  valor: number;
  tipo: "Pendente" | "Concluída" | "Cancelada";
  conta?: Conta;
  categoria?: Categoria;
}

export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string; // Senha é opcional no retorno
  dataCadastro?: string;
  contas?: Conta[];
  metas?: Meta[];
}

export interface Login {
  email: string;
  senha?: string;
}
