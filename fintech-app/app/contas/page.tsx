"use client";

import withAuth from "@/app/components/withAuth";
import MainLayout from "@/app/components/MainLayout";
import ContaFormModal from "@/app/components/ContaFormModal";
import { useAuth } from "@/app/contexts/AuthContext";
import { useEffect, useState } from "react";
import { Conta } from "@/app/types/usuario";
import { getAllContas, createConta, updateConta, deleteConta } from "@/app/services/api";

const ContasPage = () => {
  const { user } = useAuth();
  const [contas, setContas] = useState<Conta[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [contaToEdit, setContaToEdit] = useState<Conta | null>(null);

  const fetchContas = async () => {
    if (user) {
      const userContas = await getAllContas();
      setContas(userContas);
    }
  };

  useEffect(() => {
    fetchContas();
  }, [user]);

  const handleOpenModal = (conta?: Conta) => {
    setContaToEdit(conta || null);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setContaToEdit(null);
    setIsModalOpen(false);
  };

  const handleSubmit = async (contaData: Omit<Conta, 'id'>) => {
    if (contaToEdit) {
      await updateConta(contaToEdit.id!, contaData);
    } else {
      await createConta(contaData);
    }
    fetchContas();
    handleCloseModal();
  };

  const handleDelete = async (id: number) => {
    await deleteConta(id);
    fetchContas();
  };

  return (
    <MainLayout>
      <div className="mx-auto flex w-full max-w-5xl flex-col gap-8">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div className="flex flex-col gap-2">
            <h1 className="text-text-primary text-4xl font-black leading-tight tracking-[-0.033em]">Suas Contas</h1>
            <p className="text-text-secondary text-base font-normal leading-normal">Visualize e gerencie suas contas bancárias.</p>
          </div>
          <button onClick={() => handleOpenModal()} className="flex min-w-[84px] cursor-pointer items-center justify-center gap-2 overflow-hidden rounded-lg h-11 px-5 bg-secondary text-white text-sm font-bold leading-normal tracking-[0.015em] hover:bg-secondary/90 transition-colors">
            <span className="material-symbols-outlined">add_circle</span>
            <span className="truncate">Adicionar Conta</span>
          </button>
        </div>

        <div className="flex flex-col gap-4">
          {contas.map((conta) => (
            <div key={conta.id} className="flex flex-wrap items-center gap-4 rounded-xl bg-white/50 p-4 border border-border-color">
              <div className="flex flex-1 items-center gap-4 min-w-[250px]">
                <div className="flex flex-col justify-center">
                  <p className="text-text-primary text-lg font-semibold leading-normal">{conta.tipoConta}</p>
                  <p className="text-text-secondary text-sm font-normal leading-normal">Agência: {conta.agencia} | Número: {conta.numero}</p>
                </div>
              </div>
              <div className="flex w-full sm:w-auto sm:ml-auto items-center gap-4">
                <p className="text-text-primary text-lg font-semibold flex-1 sm:flex-none text-right">R$ {conta.saldo.toFixed(2)}</p>
                <div className="relative">
                  <button onClick={() => handleOpenModal(conta)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">edit</span></button>
                  <button onClick={() => handleDelete(conta.id!)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">delete</span></button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <ContaFormModal isOpen={isModalOpen} onClose={handleCloseModal} onSubmit={handleSubmit} contaToEdit={contaToEdit} />
    </MainLayout>
  );
};

export default withAuth(ContasPage);
