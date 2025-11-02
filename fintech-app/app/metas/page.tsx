"use client";

import withAuth from "@/app/components/withAuth";
import MainLayout from "@/app/components/MainLayout";
import MetaFormModal from "@/app/components/MetaFormModal";
import { useAuth } from "@/app/contexts/AuthContext";
import { useEffect, useState } from "react";
import { Meta } from "@/app/types/usuario";
import { getAllMetas, createMeta, updateMeta, deleteMeta } from "@/app/services/api";

const MetasPage = () => {
  const { user } = useAuth();
  const [metas, setMetas] = useState<Meta[]>([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [metaToEdit, setMetaToEdit] = useState<Meta | null>(null);

  const fetchMetas = async () => {
    if (user) {
      const userMetas = await getAllMetas();
      setMetas(userMetas);
    }
  };

  useEffect(() => {
    fetchMetas();
  }, [user]);

  const handleOpenModal = (meta?: Meta) => {
    setMetaToEdit(meta || null);
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setMetaToEdit(null);
    setIsModalOpen(false);
  };

  const handleSubmit = async (metaData: Omit<Meta, 'id' | 'valorAtual' | 'status'>) => {
    if (metaToEdit) {
      await updateMeta(metaToEdit.id!, metaData);
    } else {
      await createMeta({ ...metaData, valorAtual: 0, status: 'Ativa' });
    }
    fetchMetas();
    handleCloseModal();
  };

  const handleDelete = async (id: number) => {
    await deleteMeta(id);
    fetchMetas();
  };

  const totalEmMetas = metas.reduce((acc, meta) => acc + meta.valorAtual, 0);
  const metasAtivas = metas.filter(meta => meta.status === 'Ativa').length;
  const progressoMedio = metas.length > 0 ? metas.reduce((acc, meta) => acc + (meta.valorAtual / meta.valorAlvo), 0) / metas.length * 100 : 0;

  return (
    <MainLayout>
      <div className="mx-auto flex w-full max-w-5xl flex-col gap-8">
        <div className="flex flex-wrap items-center justify-between gap-4">
          <div className="flex flex-col gap-2">
            <h1 className="text-primary text-4xl font-black leading-tight tracking-[-0.033em]">Gerencie suas Metas Financeiras</h1>
            <p className="text-text-secondary text-base font-normal leading-normal">Acompanhe o progresso e alcance seus objetivos.</p>
          </div>
          <button onClick={() => handleOpenModal()} className="flex min-w-[84px] cursor-pointer items-center justify-center gap-2 overflow-hidden rounded-lg h-11 px-5 bg-primary text-white text-sm font-bold leading-normal tracking-[0.015em] hover:bg-primary/90 transition-colors">
            <span className="material-symbols-outlined">add_circle</span>
            <span className="truncate">Criar Nova Meta</span>
          </button>
        </div>

        <div className="grid grid-cols-1 gap-4 md:grid-cols-3">
          <div className="flex flex-1 flex-col gap-2 rounded-xl bg-white/50 p-6 border border-border-color">
            <p className="text-text-secondary text-base font-medium leading-normal">Total em Metas</p>
            <p className="text-primary tracking-light text-3xl font-bold leading-tight">R$ {totalEmMetas.toFixed(2)}</p>
          </div>
          <div className="flex flex-1 flex-col gap-2 rounded-xl bg-white/50 p-6 border border-border-color">
            <p className="text-text-secondary text-base font-medium leading-normal">Metas Ativas</p>
            <p className="text-primary tracking-light text-3xl font-bold leading-tight">{metasAtivas}</p>
          </div>
          <div className="flex flex-1 flex-col gap-2 rounded-xl bg-white/50 p-6 border border-border-color">
            <p className="text-text-secondary text-base font-medium leading-normal">Progresso Médio</p>
            <p className="text-primary tracking-light text-3xl font-bold leading-tight">{progressoMedio.toFixed(0)}%</p>
          </div>
        </div>

        <h2 className="text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Minhas Metas</h2>
        <div className="flex flex-col gap-4">
          {metas.map((meta) => (
            <div key={meta.id} className="flex flex-wrap items-center gap-4 rounded-xl bg-white/50 p-4 border border-border-color">
              <div className="flex flex-1 items-center gap-4 min-w-[250px]">
                <div className="flex flex-col justify-center">
                  <p className="text-primary text-lg font-semibold leading-normal">{meta.nome}</p>
                  <p className="text-text-secondary text-sm font-normal leading-normal">Meta para {new Date(meta.dataAlvo).toLocaleDateString()}</p>
                  <p className="text-text-secondary text-sm font-normal leading-normal">R$ {meta.valorAtual.toFixed(2)} de R$ {meta.valorAlvo.toFixed(2)}</p>
                </div>
              </div>
              <div className="flex w-full sm:w-auto sm:ml-auto items-center gap-4">
                <div className="flex-1 sm:flex-none sm:w-48 flex items-center gap-3">
                  <div className="w-full overflow-hidden rounded-full bg-black/10 h-2"><div className="h-full rounded-full bg-secondary" style={{ width: `${(meta.valorAtual / meta.valorAlvo) * 100}%` }}></div></div>
                  <p className="text-primary text-sm font-bold leading-normal">{((meta.valorAtual / meta.valorAlvo) * 100).toFixed(0)}%</p>
                </div>
                <div className="relative">
                  <button onClick={() => handleOpenModal(meta)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">edit</span></button>
                  <button onClick={() => handleDelete(meta.id!)} className="text-text-secondary hover:text-text-primary"><span className="material-symbols-outlined">delete</span></button>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <MetaFormModal isOpen={isModalOpen} onClose={handleCloseModal} onSubmit={handleSubmit} metaToEdit={metaToEdit} />
    </MainLayout>
  );
};

export default withAuth(MetasPage);
