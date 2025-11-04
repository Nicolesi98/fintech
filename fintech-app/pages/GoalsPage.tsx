
import React, { useState, useEffect, useCallback } from 'react';
import * as api from '../services/apiService';
import type { Meta } from '../types';
import { StatusMeta } from '../types';
import { useAuth } from '../App';

// Simplified Modal Component
const Modal: React.FC<{ isOpen: boolean; onClose: () => void; title: string; children: React.ReactNode; }> = ({ isOpen, onClose, title, children }) => {
    if (!isOpen) return null;
    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 z-50 flex justify-center items-center" onClick={onClose}>
            <div className="bg-surface-light rounded-xl p-6 w-full max-w-md" onClick={e => e.stopPropagation()}>
                <div className="flex justify-between items-center mb-4">
                    <h2 className="text-xl font-bold text-primary">{title}</h2>
                    <button onClick={onClose} className="text-text-secondary">&times;</button>
                </div>
                {children}
            </div>
        </div>
    );
};

const GoalsPage: React.FC = () => {
    const [metas, setMetas] = useState<Meta[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentMeta, setCurrentMeta] = useState<Partial<Meta> | null>(null);
    const { user } = useAuth();

    const fetchMetas = useCallback(async () => {
        try {
            setIsLoading(true);
            const data = await api.getMetas(user.id);
            setMetas(data);
        } catch (error) {
            console.error("Failed to fetch goals", error);
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchMetas();
    }, [fetchMetas]);

    const handleOpenModal = (meta: Partial<Meta> | null = null) => {
        setCurrentMeta(meta ? { ...meta } : { nome: '', valorAlvo: 0, valorAtual: 0, dataAlvo: '', status: StatusMeta.ATIVA });
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentMeta(null);
    };

    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!currentMeta) return;

        try {
            if ('id' in currentMeta && currentMeta.id) {
                await api.updateMeta(currentMeta.id, currentMeta);
            } else {
                await api.createMeta(user.id, currentMeta);
            }
            fetchMetas();
            handleCloseModal();
        } catch (error) {
            console.error("Failed to save goal", error);
        }
    };
    
    const handleDelete = async (id: number) => {
        if (window.confirm('Tem certeza que deseja deletar esta meta?')) {
            try {
                await api.deleteMeta(id);
                fetchMetas();
            } catch (error) {
                console.error("Failed to delete goal", error);
            }
        }
    }

    return (
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
            
            <div className="flex flex-col gap-4">
            {isLoading ? <p>Carregando metas...</p> : metas.length > 0 ? metas.map(meta => {
                const progress = Math.min(100, Math.round((meta.valorAtual / meta.valorAlvo) * 100));
                return (
                 <div key={meta.id} className="flex flex-wrap items-center gap-4 rounded-xl bg-white/50 p-4 border border-border-color">
                    <div className="flex flex-1 items-center gap-4 min-w-[250px]">
                        <div className="flex size-14 shrink-0 items-center justify-center rounded-lg bg-secondary/20 text-secondary">
                            <span className="material-symbols-outlined !text-3xl">flag</span>
                        </div>
                        <div className="flex flex-col justify-center">
                            <p className="text-primary text-lg font-semibold leading-normal">{meta.nome}</p>
                            <p className="text-text-secondary text-sm font-normal leading-normal">Meta para {new Date(meta.dataAlvo).toLocaleDateString('pt-BR', {month: 'short', year: 'numeric'})}</p>
                            <p className="text-text-secondary text-sm font-normal leading-normal">{meta.valorAtual.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'})} de {meta.valorAlvo.toLocaleString('pt-BR', {style: 'currency', currency: 'BRL'})}</p>
                        </div>
                    </div>
                    <div className="flex w-full sm:w-auto sm:ml-auto items-center gap-4">
                        <div className="flex-1 sm:flex-none sm:w-48 flex items-center gap-3">
                            <div className="w-full overflow-hidden rounded-full bg-black/10 h-2"><div className="h-full rounded-full bg-secondary" style={{width: `${progress}%`}}></div></div>
                            <p className="text-primary text-sm font-bold leading-normal">{progress}%</p>
                        </div>
                        <div className="relative group">
                            <button className="text-text-secondary hover:text-primary"><span className="material-symbols-outlined">more_vert</span></button>
                            <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10 hidden group-focus-within:block">
                                <a href="#" onClick={(e) => { e.preventDefault(); handleOpenModal(meta); }} className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Editar</a>
                                <a href="#" onClick={(e) => { e.preventDefault(); handleDelete(meta.id!); }} className="block px-4 py-2 text-sm text-red-600 hover:bg-gray-100">Deletar</a>
                            </div>
                        </div>
                    </div>
                </div>
                )
             }) : (
                <div className="flex flex-col items-center justify-center gap-6 rounded-xl border-2 border-dashed border-border-color bg-white/30 py-16 text-center">
                    <div className="flex size-20 items-center justify-center rounded-full bg-secondary/20 text-secondary">
                        <span className="material-symbols-outlined !text-5xl">emoji_flags</span>
                    </div>
                    <div className="flex flex-col gap-2">
                        <h3 className="text-primary text-xl font-bold">Você ainda não tem nenhuma meta.</h3>
                        <p className="text-text-secondary max-w-sm">Crie sua primeira meta e comece a economizar hoje mesmo!</p>
                    </div>
                </div>
             )}
            </div>

            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={currentMeta && 'id' in currentMeta ? 'Editar Meta' : 'Criar Nova Meta'}>
                {currentMeta && (
                    <form onSubmit={handleSave} className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Nome da Meta</label>
                            <input type="text" value={currentMeta.nome} onChange={e => setCurrentMeta({...currentMeta, nome: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50" required/>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Valor Alvo (R$)</label>
                            <input type="number" value={currentMeta.valorAlvo} onChange={e => setCurrentMeta({...currentMeta, valorAlvo: parseFloat(e.target.value) || 0})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Valor Atual (R$)</label>
                            <input type="number" value={currentMeta.valorAtual} onChange={e => setCurrentMeta({...currentMeta, valorAtual: parseFloat(e.target.value) || 0})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Data Alvo</label>
                            <input type="date" value={currentMeta.dataAlvo?.split('T')[0]} onChange={e => setCurrentMeta({...currentMeta, dataAlvo: e.target.value})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                         <div>
                            <label className="block text-sm font-medium text-text-primary">Status</label>
                                                         <select value={currentMeta.status} onChange={e => setCurrentMeta({...currentMeta, status: e.target.value as StatusMeta})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                                                            {Object.values(StatusMeta).map(status => (
                                                                <option key={status} value={status}>{status}</option>
                                                            ))}
                                                        </select>                        </div>
                        <div className="flex justify-end gap-4 mt-6">
                            <button type="button" onClick={handleCloseModal} className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-200 rounded-md hover:bg-gray-300">Cancelar</button>
                            <button type="submit" className="px-4 py-2 text-sm font-medium text-white bg-primary rounded-md hover:bg-primary/90">Salvar</button>
                        </div>
                    </form>
                )}
            </Modal>
        </div>
    );
};

export default GoalsPage;
