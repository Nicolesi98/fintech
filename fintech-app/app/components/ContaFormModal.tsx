"use client";

import { Conta } from "@/types/usuario";
import { useState, useEffect } from "react";

interface ContaFormModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (conta: Omit<Conta, 'id'>) => void;
  contaToEdit?: Conta | null;
}

const ContaFormModal = ({ isOpen, onClose, onSubmit, contaToEdit }: ContaFormModalProps) => {
  const [agencia, setAgencia] = useState(0);
  const [numero, setNumero] = useState(0);
  const [tipoConta, setTipoConta] = useState<"Conta Corrente" | "Conta Poupança" | "Conta Salário">("Conta Corrente");
  const [saldo, setSaldo] = useState(0);

  useEffect(() => {
    if (contaToEdit) {
      setAgencia(contaToEdit.agencia);
      setNumero(contaToEdit.numero);
      setTipoConta(contaToEdit.tipoConta);
      setSaldo(contaToEdit.saldo);
    } else {
      setAgencia(0);
      setNumero(0);
      setTipoConta("Conta Corrente");
      setSaldo(0);
    }
  }, [contaToEdit]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ agencia, numero, tipoConta, saldo });
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center">
      <div className="bg-white p-8 rounded-lg w-full max-w-md">
        <h2 className="text-2xl font-bold mb-4">{contaToEdit ? "Editar Conta" : "Adicionar Conta"}</h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input type="number" value={agencia} onChange={(e) => setAgencia(Number(e.target.value))} placeholder="Agência" className="p-2 border rounded" />
          <input type="number" value={numero} onChange={(e) => setNumero(Number(e.target.value))} placeholder="Número" className="p-2 border rounded" />
          <select value={tipoConta} onChange={(e) => setTipoConta(e.target.value as any)} className="p-2 border rounded">
            <option value="Conta Corrente">Conta Corrente</option>
            <option value="Conta Poupança">Conta Poupança</option>
            <option value="Conta Salário">Conta Salário</option>
          </select>
          <input type="number" value={saldo} onChange={(e) => setSaldo(Number(e.target.value))} placeholder="Saldo" className="p-2 border rounded" />
          <div className="flex justify-end gap-4">
            <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-300 rounded">Cancelar</button>
            <button type="submit" className="px-4 py-2 bg-primary text-white rounded">{contaToEdit ? "Salvar" : "Adicionar"}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ContaFormModal;
