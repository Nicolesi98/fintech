"use client";

import { Meta } from "@/types/usuario";
import { useState, useEffect } from "react";

interface MetaFormModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSubmit: (meta: Omit<Meta, 'id' | 'valorAtual' | 'status'>) => void;
  metaToEdit?: Meta | null;
}

const MetaFormModal = ({ isOpen, onClose, onSubmit, metaToEdit }: MetaFormModalProps) => {
  const [nome, setNome] = useState("");
  const [valorAlvo, setValorAlvo] = useState(0);
  const [dataAlvo, setDataAlvo] = useState("");

  useEffect(() => {
    if (metaToEdit) {
      setNome(metaToEdit.nome);
      setValorAlvo(metaToEdit.valorAlvo);
      setDataAlvo(new Date(metaToEdit.dataAlvo).toISOString().split('T')[0]);
    } else {
      setNome("");
      setValorAlvo(0);
      setDataAlvo("");
    }
  }, [metaToEdit]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({ nome, valorAlvo, dataAlvo });
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-center">
      <div className="bg-white p-8 rounded-lg w-full max-w-md">
        <h2 className="text-2xl font-bold mb-4">{metaToEdit ? "Editar Meta" : "Adicionar Meta"}</h2>
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
          <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome da Meta" className="p-2 border rounded" />
          <input type="number" value={valorAlvo} onChange={(e) => setValorAlvo(Number(e.target.value))} placeholder="Valor Alvo" className="p-2 border rounded" />
          <input type="date" value={dataAlvo} onChange={(e) => setDataAlvo(e.target.value)} className="p-2 border rounded" />
          <div className="flex justify-end gap-4">
            <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-300 rounded">Cancelar</button>
            <button type="submit" className="px-4 py-2 bg-primary text-white rounded">{metaToEdit ? "Salvar" : "Adicionar"}</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default MetaFormModal;
