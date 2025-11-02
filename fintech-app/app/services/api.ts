import { Usuario, Login } from "@/types/usuario";

const API_URL = "http://localhost:8080";

export async function loginUser(credentials: Login): Promise<Usuario> {
  const response = await fetch(`${API_URL}/usuarios/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(credentials),
  });

  if (!response.ok) {
    throw response;
  }

  return response.json();
}

export async function registerUser(userData: Partial<Usuario>): Promise<Usuario> {
  const response = await fetch(`${API_URL}/usuarios`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    throw response;
  }

  return response.json();
}

// Contas
export async function getAllContas(): Promise<Conta[]> {
  const response = await fetch(`${API_URL}/contas`);
  if (!response.ok) throw new Error("Failed to fetch contas");
  return response.json();
}

export async function createConta(contaData: Omit<Conta, 'id'>): Promise<Conta> {
  const response = await fetch(`${API_URL}/contas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(contaData),
  });
  if (!response.ok) throw new Error("Failed to create conta");
  return response.json();
}

export async function updateConta(id: number, contaData: Partial<Conta>): Promise<Conta> {
  const response = await fetch(`${API_URL}/contas/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(contaData),
  });
  if (!response.ok) throw new Error("Failed to update conta");
  return response.json();
}

export async function deleteConta(id: number): Promise<void> {
  const response = await fetch(`${API_URL}/contas/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error("Failed to delete conta");
}

// Metas
export async function getAllMetas(): Promise<Meta[]> {
  const response = await fetch(`${API_URL}/metas`);
  if (!response.ok) throw new Error("Failed to fetch metas");
  return response.json();
}

export async function createMeta(metaData: Omit<Meta, 'id'>): Promise<Meta> {
  const response = await fetch(`${API_URL}/metas`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(metaData),
  });
  if (!response.ok) throw new Error("Failed to create meta");
  return response.json();
}

export async function updateMeta(id: number, metaData: Partial<Meta>): Promise<Meta> {
  const response = await fetch(`${API_URL}/metas/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(metaData),
  });
  if (!response.ok) throw new Error("Failed to update meta");
  return response.json();
}

export async function deleteMeta(id: number): Promise<void> {
  const response = await fetch(`${API_URL}/metas/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error("Failed to delete meta");
}

// Transacoes
export async function getAllTransacoes(): Promise<Transacao[]> {
  const response = await fetch(`${API_URL}/transacoes`);
  if (!response.ok) throw new Error("Failed to fetch transacoes");
  return response.json();
}

export async function createTransacao(transacaoData: Omit<Transacao, 'id'>): Promise<Transacao> {
  const response = await fetch(`${API_URL}/transacoes`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(transacaoData),
  });
  if (!response.ok) throw new Error("Failed to create transacao");
  return response.json();
}

export async function updateTransacao(id: number, transacaoData: Partial<Transacao>): Promise<Transacao> {
  const response = await fetch(`${API_URL}/transacoes/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(transacaoData),
  });
  if (!response.ok) throw new Error("Failed to update transacao");
  return response.json();
}

export async function deleteTransacao(id: number): Promise<void> {
  const response = await fetch(`${API_URL}/transacoes/${id}`, {
    method: 'DELETE',
  });
  if (!response.ok) throw new Error("Failed to delete transacao");
}

// Categorias
export async function getAllCategorias(): Promise<Categoria[]> {
  const response = await fetch(`${API_URL}/categorias`);
  if (!response.ok) throw new Error("Failed to fetch categorias");
  return response.json();
}
