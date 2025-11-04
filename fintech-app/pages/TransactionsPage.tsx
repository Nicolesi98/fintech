
import React, { useState, useEffect, useCallback } from 'react';
import * as api from '../services/apiService';
import type { Transacao } from '../types';
import TransactionModal from './TransactionModal'; // Import the modal
import { useAuth } from '../App';

const iconMap: { [key: string]: string } = {
    'Alimentação': 'restaurant',
    'Salário': 'work',
    'Mercado': 'shopping_cart',
    'Assinaturas': 'subscriptions',
    'Transporte': 'commute',
    'Lazer': 'sports_esports',
    'Moradia': 'home',
    'Padrão': 'receipt_long'
};

const TransactionsPage: React.FC = () => {
    const [transacoes, setTransacoes] = useState<Transacao[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [editingTransaction, setEditingTransaction] = useState<Transacao | undefined>(undefined);
    const { user } = useAuth();

    const fetchTransacoes = useCallback(async () => {
        try {
            setIsLoading(true);
            const data = await api.getTransacoes(user.id);
            setTransacoes(data.sort((a, b) => new Date(b.dataCriacao!).getTime() - new Date(a.dataCriacao!).getTime()));
        } catch (error) {
            console.error("Failed to fetch transactions", error);
        } finally {
            setIsLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchTransacoes();
    }, [fetchTransacoes]);

    const handleEdit = (transacao: Transacao) => {
        setEditingTransaction(transacao);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setEditingTransaction(undefined);
    };

    const handleDelete = async (id: number) => {
        if (window.confirm('Tem certeza que deseja excluir esta transação?')) {
            try {
                await api.deleteTransacao(id);
                fetchTransacoes();
            } catch (error) {
                console.error("Failed to delete transaction", error);
            }
        }
    };

    return (
        <div className="mx-auto max-w-4xl">
            <header className="flex flex-wrap justify-between gap-4 items-center mb-6">
                <h1 className="text-primary text-4xl font-black leading-tight tracking-[-0.033em]">Histórico de Transações</h1>
                <button 
                    onClick={() => setIsModalOpen(true)}
                    className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700 flex items-center gap-2"
                >
                    <span className="material-symbols-outlined">add</span>
                    Nova Transação
                </button>
            </header>

            <TransactionModal 
                isOpen={isModalOpen}
                onClose={handleCloseModal}
                onTransactionCreated={fetchTransacoes}
                transactionToEdit={editingTransaction}
            />

            {isLoading ? <p>Carregando transações...</p> : transacoes.length === 0 ? (
                 <div className="text-center py-20 px-6 bg-white/60 rounded-lg">
                    <span className="material-symbols-outlined text-5xl text-detail">search_off</span>
                    <p className="text-primary text-lg font-bold mt-4">Nenhuma transação encontrada</p>
                    <p className="text-detail mt-1">Adicione sua primeira transação para começar.</p>
                </div>
            ) : (
                <div className="space-y-2">
                    {transacoes.map(transacao => (
                        <div key={transacao.id} className="flex items-center gap-4 bg-white/60 px-4 py-3 rounded-lg hover:bg-white transition-colors justify-between">
                            <div className="flex items-center gap-4">
                                <div className="text-primary flex items-center justify-center rounded-lg bg-background-light shrink-0 size-12">
                                    <span className="material-symbols-outlined">{iconMap[transacao.categoria?.nomeCategoria || 'Padrão']}</span>
                                </div>
                                <div className="flex flex-col justify-center">
                                    <p className="text-primary text-base font-bold leading-normal line-clamp-1">{transacao.descricao}</p>
                                    <p className="text-detail text-sm font-normal leading-normal line-clamp-2">{transacao.categoria?.nomeCategoria || 'Sem categoria'} • {transacao.conta?.tipoConta || 'Conta'}</p>
                                </div>
                            </div>
                            <div className="flex items-center gap-4">
                                <div className="text-right">
                                    <p className={`${transacao.tipo === 'Despesa' ? 'text-expense' : 'text-income'} text-base font-bold leading-normal`}>
                                       {transacao.tipo === 'Despesa' ? '-' : '+'} {transacao.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                                    </p>
                                    <p className="text-detail text-xs">{new Date(transacao.dataCriacao!).toLocaleDateString('pt-BR')}</p>
                                </div>
                                <div className="flex flex-col gap-2">
                                    <button onClick={() => handleEdit(transacao)} className="p-1 text-blue-600 hover:text-blue-800">
                                        <span className="material-symbols-outlined">edit</span>
                                    </button>
                                    <button onClick={() => handleDelete(transacao.id!)} className="p-1 text-red-600 hover:text-red-800">
                                        <span className="material-symbols-outlined">delete</span>
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default TransactionsPage;
