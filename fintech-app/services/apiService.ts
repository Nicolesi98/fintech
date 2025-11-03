
import { Usuario, LoginRequest, Conta, Meta, Transacao, Categoria } from '../types';

const API_BASE_URL = 'http://localhost:8080';

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const error = await response.text();
    throw new Error(error || `HTTP error! status: ${response.status}`);
  }
  if (response.status === 204) {
    return null as T;
  }
  // FIX: Await the json() Promise and cast to the generic type T.
  // This ensures the function returns a Promise<T> as declared in its signature,
  // resolving the type mismatch errors where it's used.
  return (await response.json()) as T;
}

// --- Usuario ---
export const login = (credentials: LoginRequest): Promise<Usuario> => {
  return fetch(`${API_BASE_URL}/usuarios/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(credentials),
  }).then(handleResponse);
};

export const register = (user: Usuario): Promise<Usuario> => {
  return fetch(`${API_BASE_URL}/usuarios`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(user),
  }).then(handleResponse);
};

// --- Contas ---
export const getContas = (userId: number): Promise<Conta[]> => fetch(`${API_BASE_URL}/contas/usuario/${userId}`).then(handleResponse);
export const createConta = (userId: number,conta: Partial<Conta>): Promise<Conta> => fetch(`${API_BASE_URL}/contas/${userId}`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(conta) }).then(handleResponse);
export const updateConta = (id: number, conta: Partial<Conta>): Promise<Conta> => fetch(`${API_BASE_URL}/contas/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(conta) }).then(handleResponse);
export const deleteConta = (id: number): Promise<void> => fetch(`${API_BASE_URL}/contas/${id}`, { method: 'DELETE' }).then(handleResponse);

// --- Metas ---
export const getMetas = (userId: number): Promise<Meta[]> => fetch(`${API_BASE_URL}/metas/usuario/${userId}`).then(handleResponse);
export const createMeta = (userId: number, meta: Partial<Meta>): Promise<Meta> => fetch(`${API_BASE_URL}/metas/${userId}`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(meta) }).then(handleResponse);
export const updateMeta = (id: number, meta: Partial<Meta>): Promise<Meta> => fetch(`${API_BASE_URL}/metas/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(meta) }).then(handleResponse);
export const deleteMeta = (id: number): Promise<void> => fetch(`${API_BASE_URL}/metas/${id}`, { method: 'DELETE' }).then(handleResponse);

// --- Transacoes ---
export const getTransacoes = (userId: number): Promise<Transacao[]> => fetch(`${API_BASE_URL}/transacoes/usuario/${userId}`).then(handleResponse);
export const createTransacao = (transacao: Partial<Transacao>): Promise<Transacao> => fetch(`${API_BASE_URL}/transacoes`, { method: 'POST', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(transacao) }).then(handleResponse);
export const updateTransacao = (id: number, transacao: Partial<Transacao>): Promise<Transacao> => fetch(`${API_BASE_URL}/transacoes/${id}`, { method: 'PUT', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(transacao) }).then(handleResponse);
export const deleteTransacao = (id: number): Promise<void> => fetch(`${API_BASE_URL}/transacoes/${id}`, { method: 'DELETE' }).then(handleResponse);

// --- Categorias ---
export const getCategorias = (): Promise<Categoria[]> => fetch(`${API_BASE_URL}/categorias`).then(handleResponse);