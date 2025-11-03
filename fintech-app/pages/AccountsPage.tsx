
import React, { useState, useEffect, useCallback, useMemo } from 'react';
import * as api from '../services/apiService';
import type { Conta } from '../types';

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

import { useAuth } from '../App';

const getTipoContaDisplayName = (tipoConta: Conta['tipoConta']) => {
    switch (tipoConta) {
        case 'CORRENTE':
            return 'Conta Corrente';
        case 'POUPANCA':
            return 'Conta Poupança';
        case 'SALARIO':
            return 'Conta Salário';
        default:
            return tipoConta;
    }
};

const AccountsPage: React.FC = () => {
    const { user } = useAuth();
    const [contas, setContas] = useState<Conta[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [currentConta, setCurrentConta] = useState<Partial<Conta> | null>(null);

    const fetchContas = useCallback(async () => {
        try {
            setIsLoading(true);
            const data = await api.getContas(user.id);
            setContas(data);
        } catch (error) {
            console.error("Failed to fetch accounts", error);
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchContas();
    }, [fetchContas]);

    const totalBalance = useMemo(() => {
        return contas.reduce((sum, conta) => sum + conta.saldo, 0);
    }, [contas]);

    const handleOpenModal = (conta: Partial<Conta> | null = null) => {
        setCurrentConta(conta ? { ...conta } : { agencia: 0, numero: 0, tipoConta: 'CORRENTE', saldo: 0 });
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setCurrentConta(null);
    };
    
    const handleSave = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!currentConta) return;
        try {
            if ('id' in currentConta && currentConta.id) {
                await api.updateConta(currentConta.id, currentConta);
            } else {
                await api.createConta(user.id, currentConta);
            }
            fetchContas();
            handleCloseModal();
        } catch (error) {
            console.error("Failed to save account", error);
        }
    };

    const handleDelete = async (id: number) => {
        if (window.confirm('Tem certeza que deseja deletar esta conta?')) {
            try {
                await api.deleteConta(id);
                fetchContas();
            } catch (error) {
                console.error("Failed to delete account", error);
            }
        }
    }

    return (
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
            <div className="flex flex-1 flex-col gap-2 rounded-xl bg-white/50 p-6 border border-border-color">
                <p className="text-text-secondary text-base font-medium leading-normal">Saldo Total</p>
                <p className="text-text-primary tracking-light text-3xl font-bold leading-tight">
                    {totalBalance.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                </p>
            </div>
            <h2 className="text-text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Contas Conectadas</h2>
            <div className="flex flex-col gap-4">
                {isLoading ? <p>Carregando contas...</p> : contas.map(conta => (
                    <div key={conta.id} className="flex flex-wrap items-center gap-4 rounded-xl bg-white/50 p-4 border border-border-color">
                        <div className="flex flex-1 items-center gap-4 min-w-[250px]">
                            <div className="flex size-14 shrink-0 items-center justify-center rounded-lg bg-gray-200">
                                <span className="material-symbols-outlined !text-3xl text-primary">account_balance</span>
                            </div>
                            <div className="flex flex-col justify-center">
                                <p className="text-text-primary text-lg font-semibold leading-normal">Ag. {conta.agencia} / C.c. {conta.numero}</p>
                                <p className="text-text-secondary text-sm font-normal leading-normal">{getTipoContaDisplayName(conta.tipoConta)}</p>
                            </div>
                        </div>
                        <div className="flex w-full sm:w-auto sm:ml-auto items-center gap-4">
                            <p className="text-text-primary text-lg font-semibold flex-1 sm:flex-none text-right">
                                {conta.saldo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                            </p>
                            <div className="relative group">
                                <button className="text-text-secondary hover:text-primary"><span className="material-symbols-outlined">more_vert</span></button>
                                <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-10 hidden group-focus-within:block">
                                    <a href="#" onClick={(e) => { e.preventDefault(); handleOpenModal(conta); }} className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Editar</a>
                                    <a href="#" onClick={(e) => { e.preventDefault(); handleDelete(conta.id!); }} className="block px-4 py-2 text-sm text-red-600 hover:bg-gray-100">Deletar</a>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            
            <Modal isOpen={isModalOpen} onClose={handleCloseModal} title={currentConta && 'id' in currentConta ? 'Editar Conta' : 'Adicionar Conta'}>
                {currentConta && (
                    <form onSubmit={handleSave} className="space-y-4">
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Agência</label>
                            <input type="number" value={currentConta.agencia} onChange={e => setCurrentConta({...currentConta, agencia: parseInt(e.target.value)})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                         <div>
                            <label className="block text-sm font-medium text-text-primary">Número da Conta</label>
                            <input type="number" value={currentConta.numero} onChange={e => setCurrentConta({...currentConta, numero: parseInt(e.target.value)})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                        <div>
                            <label className="block text-sm font-medium text-text-primary">Tipo de Conta</label>
                            <select value={currentConta.tipoConta} onChange={e => setCurrentConta({...currentConta, tipoConta: e.target.value as Conta['tipoConta']})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm">
                                <option value="CORRENTE">Conta Corrente</option>
                                <option value="POUPANCA">Conta Poupança</option>
                                <option value="SALARIO">Conta Salário</option>
                            </select>
                        </div>
                         <div>
                            <label className="block text-sm font-medium text-text-primary">Saldo Inicial (R$)</label>
                            <input type="number" value={currentConta.saldo} onChange={e => setCurrentConta({...currentConta, saldo: parseFloat(e.target.value)})} className="mt-1 block w-full rounded-md border-gray-300 shadow-sm" required/>
                        </div>
                        <div className="flex justify-end gap-4 mt-6">
                            <button type="button" onClick={handleCloseModal} className="px-4 py-2 text-sm font-medium text-gray-700 bg-gray-200 rounded-md hover:bg-gray-300">Cancelar</button>
                            <button type="submit" className="px-4 py-2 text-sm font-medium text-white bg-secondary rounded-md hover:bg-secondary/90">Salvar</button>
                        </div>
                    </form>
                )}
            </Modal>
        </div>
    );
};

export default AccountsPage;
