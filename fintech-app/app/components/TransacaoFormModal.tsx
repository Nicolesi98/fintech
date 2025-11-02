"use client";

import { Transacao, Conta, Categoria } from "@/types/usuario";
import { useState, useEffect } from "react";

interface TransacaoFormModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (transacao: Omit<Transacao, 'id'>) => void;
  transacaoToEdit?: Transacao | null;
  contas: Conta[];
  categorias: Categoria[];
}

const TransacaoFormModal = ({ isOpen, onClose, onSubmit, transacaoToEdit, contas, categorias }: TransacaoFormModalProps) => {
  const [descricao, setDescricao] = useState("");
  const [valor, setValor] = useState(0);
  const [tipo, setTipo] = useState<"Pendente" | "Concluída" | "Cancelada">("Concluída");
  const [contaId, setContaId] = useState<number | undefined>();
  const [categoriaId, setCategoriaId] = useState<number | undefined>();

  useEffect(() => {
    if (transacaoToEdit) {
      setDescricao(transacaoToEdit.descricao);
      setValor(transacaoToEdit.valor);
      setTipo(transacaoToEdit.tipo);
      setContaId(transacaoToEdit.conta?.id);
      setCategoriaId(transacaoToEdit.categoria?.id);
    } else {
      setDescricao("");
      setValor(0);
      setTipo("Concluída");
      setContaId(contas[0]?.id);
      setCategoriaId(categorias[0]?.id);
    }
  }, [transacaoToEdit, contas, categorias]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const conta = contas.find(c => c.id === contaId);
    const categoria = categorias.find(c => c.id === categoriaId);
    onSubmit({ descricao, valor, tipo, conta, categoria });
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center">
      <div className="bg-white p-8 rounded-lg w-full max-w-md">
        <h2 className="text-2xl font-bold mb-4">{transacaoToEdit ? "Editar Transação" : "Adicionar Transação"}</h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input type="text" value={descricao} onChange={(e) => setDescricao(e.target.value)} placeholder="Descrição" className="p-2 border rounded" />
          <input type="number" value={valor} onChange={(e) => setValor(Number(e.target.value))} placeholder="Valor" className="p-2 border rounded" />
          <select value={tipo} onChange={(e) => setTipo(e.target.value as any)} className="p-2 border rounded">
            <option value="Concluída">Concluída</option>
            <option value="Pendente">Pendente</option>
            <option value="Cancelada">Cancelada</option>
          </select>
          <select value={contaId} onChange={(e) => setContaId(Number(e.target.value))} className="p-2 border rounded">
            {contas.map(conta => <option key={conta.id} value={conta.id}>{conta.tipoConta}</option>)}
          </select>
          <select value={categoriaId} onChange={(e) => setCategoriaId(Number(e.target.value))} className="p-2 border rounded">
            {categorias.map(cat => <option key={cat.id} value={cat.id}>{cat.nomeCategoria}</option>)}
          </select>
          <div className="flex justify-end gap-4">
            <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-300 rounded">Cancelar</button>
            <button type="submit" className="px-4 py-2 bg-primary text-white rounded">{transacaoToEdit ? "Salvar" : "Adicionar"}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default TransacaoFormModal;
