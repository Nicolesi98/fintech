"use client";

import withAuth from "@/app/components/withAuth";
import MainLayout from "@/app/components/MainLayout";
import TransacaoFormModal from "@/app/components/TransacaoFormModal";
import { useAuth } from "@/app/contexts/AuthContext";
import { useEffect, useState } from "react";
import { Transacao, Conta, Categoria } from "@/app/types/usuario";
import { getAllTransacoes, createTransacao, updateTransacao, deleteTransacao, getAllContas, getAllCategorias } from "@/app/services/api";

const TransacoesPage = () => {
  const { user } = useAuth();
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);
  const [contas, setContas] = useState<Conta[]>([]);
  const [categorias, setCategorias] = useState<Categoria[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [transacaoToEdit, setTransacaoToEdit] = useState<Transacao | null>(null);

  const fetchTransacoes = async () => {
    if (user) {
      const [userTransacoes, userContas, userCategorias] = await Promise.all([
        getAllTransacoes(),
        getAllContas(),
        getAllCategorias(),
      ]);
      setTransacoes(userTransacoes);
      setContas(userContas);
      setCategorias(userCategorias);
    }
  };

  useEffect(() => {
    fetchTransacoes();
  }, [user]);

  const handleOpenModal = (transacao?: Transacao) => {
    setTransacaoToEdit(transacao || null);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setTransacaoToEdit(null);
    setIsModalOpen(false);
  };

  const handleSubmit = async (transacaoData: Omit<Transacao, 'id'>) => {
    if (transacaoToEdit) {
      await updateTransacao(transacaoToEdit.id!, transacaoData);
    } else {
      await createTransacao(transacaoData);
    }
    fetchTransacoes();
    handleCloseModal();
  };

  const handleDelete = async (id: number) => {
    await deleteTransacao(id);
    fetchTransacoes();
  };

  return (
    <MainLayout>
      <div className="mx-auto max-w-4xl">
        <header className="flex flex-wrap justify-between gap-4 items-center mb-6">
          <h1 className="text-primary dark:text-white text-4xl font-black leading-tight tracking-[-0.033em]">Histórico de Transações</h1>
          <button onClick={() => handleOpenModal()} className="flex min-w-[84px] max-w-[480px] cursor-pointer items-center justify-center overflow-hidden rounded-lg h-10 px-4 bg-primary text-white text-sm font-bold leading-normal tracking-[0.015em] hover:bg-primary/90 transition-colors">
            <span className="truncate">Adicionar Transação</span>
          </button>
        </header>

        <div className="flex flex-col sm:flex-row gap-3 p-4 bg-white/60 dark:bg-background-dark/40 rounded-lg mb-6">
          {/* Filters Section placeholder */}
        </div>

        <div className="space-y-2">
          {transacoes.map((transacao) => (
            <div key={transacao.id} className="flex items-center gap-4 bg-white/60 dark:bg-background-dark/40 px-4 py-3 rounded-lg hover:bg-white dark:hover:bg-background-dark/60 transition-colors justify-between">
              <div className="flex items-center gap-4">
                <div className="flex flex-col justify-center">
                  <p className="text-primary dark:text-white text-base font-bold leading-normal line-clamp-1">{transacao.descricao}</p>
                  <p className="text-detail dark:text-gray-400 text-sm font-normal leading-normal line-clamp-2">{transacao.categoria?.nomeCategoria} • {transacao.conta?.tipoConta}</p>
                </div>
              </div>
              <div className="text-right">
                <p className={`${transacao.valor > 0 ? 'text-income' : 'text-expense'} text-base font-bold leading-normal`}>{transacao.valor > 0 ? '+' : '-'} R$ {Math.abs(transacao.valor).toFixed(2)}</p>
                <p className="text-detail dark:text-gray-500 text-xs">{new Date(transacao.dataCriacao!).toLocaleDateString()}</p>
              </div>
              <div className="relative">
                <button onClick={() => handleOpenModal(transacao)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">edit</span></button>
                <button onClick={() => handleDelete(transacao.id!)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">delete</span></button>
              </div>
            </div>
          ))}
        </div>
      </div>
      <TransacaoFormModal isOpen={isModalOpen} onClose={handleCloseModal} onSubmit={handleSubmit} transacaoToEdit={transacaoToEdit} contas={contas} categorias={categorias} />
    </MainLayout>
  );
};

export default withAuth(TransacoesPage);
