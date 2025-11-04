
import React, { useState, useEffect } from 'react';
import type { Transacao, Conta, Categoria } from '../types';
import * as api from '../services/apiService';
import { useAuth } from '../App';

interface TransactionModalProps {
    isOpen: boolean;
    onClose: () => void;
    onTransactionCreated: () => void;
    transactionToEdit?: Transacao;
}

const TransactionModal: React.FC<TransactionModalProps> = ({ isOpen, onClose, onTransactionCreated, transactionToEdit }) => {
    const [descricao, setDescricao] = useState('');
    const [valor, setValor] = useState(0);
    const [tipo, setTipo] = useState<'Receita' | 'Despesa'>('Despesa');
    const [selectedConta, setSelectedConta] = useState<Conta | undefined>(undefined);
    const [categoriaId, setCategoriaId] = useState<number | undefined>(undefined);
    const [contas, setContas] = useState<Conta[]>([]);
    const [categorias, setCategorias] = useState<Categoria[]>([]);
    const { user } = useAuth();

    useEffect(() => {
        if (isOpen) {
            api.getContas(user.id).then(setContas);
            api.getCategorias().then(setCategorias);
            if (transactionToEdit) {
                setDescricao(transactionToEdit.descricao);
                setValor(transactionToEdit.valor);
                setTipo(transactionToEdit.tipo);
                setSelectedConta(transactionToEdit.conta);
                setCategoriaId(transactionToEdit.categoria?.id);
            } else {
                setDescricao('');
                setValor(0);
                setTipo('Despesa');
                setSelectedConta(undefined);
                setCategoriaId(undefined);
            }
        }
    }, [isOpen, transactionToEdit, user.id]);

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const transactionData: Partial<Transacao> = {
            descricao,
            valor,
            tipo,
            conta: selectedConta,
            categoria: { id: categoriaId },
            dataCriacao: transactionToEdit ? transactionToEdit.dataCriacao : new Date().toISOString()
        };
        try {
            if (transactionToEdit) {
                await api.updateTransacao(transactionToEdit.id!, transactionData);
            } else {
                await api.createTransacao(transactionData);
            }
            onTransactionCreated();
            onClose();
        } catch (error) {
            console.error("Failed to save transaction", error);
        }
    };

    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
            <div className="bg-white p-8 rounded-lg w-full max-w-md">
                <h2 className="text-2xl font-bold mb-4">{transactionToEdit ? 'Editar Transação' : 'Nova Transação'}</h2>
                <form onSubmit={handleSubmit}>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700">Descrição</label>
                        <input
                            type="text"
                            value={descricao}
                            onChange={(e) => setDescricao(e.target.value)}
                            className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700">Valor</label>
                        <input
                            type="number"
                            value={valor}
                            onChange={(e) => setValor(parseFloat(e.target.value))}
                            className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            required
                        />
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700">Tipo</label>
                        <select
                            value={tipo}
                            onChange={(e) => setTipo(e.target.value as 'Receita' | 'Despesa')}
                            className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                        >
                            <option value="Despesa">Despesa</option>
                            <option value="Receita">Receita</option>
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700">Conta</label>
                        <select
                            value={selectedConta ? selectedConta.id : ''}
                            onChange={(e) => {
                                const contaId = parseInt(e.target.value);
                                const conta = contas.find(c => c.id === contaId);
                                setSelectedConta(conta);
                            }}
                            className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            required
                        >
                            <option value="">Selecione uma conta</option>
                            {contas.map(conta => (
                                <option key={conta.id} value={conta.id}>
                                    {conta.tipoConta} - Saldo: {conta.saldo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="mb-4">
                        <label className="block text-sm font-medium text-gray-700">Categoria</label>
                        <select
                            value={categoriaId}
                            onChange={(e) => setCategoriaId(parseInt(e.target.value))}
                            className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            required
                        >
                            <option value="">Selecione uma categoria</option>
                            {categorias.map(categoria => (
                                <option key={categoria.id} value={categoria.id}>{categoria.nomeCategoria}</option>
                            ))}
                        </select>
                    </div>
                    <div className="flex justify-end gap-4">
                        <button type="button" onClick={onClose} className="px-4 py-2 bg-gray-200 text-gray-800 rounded-md hover:bg-gray-300">Cancelar</button>
                        <button type="submit" className="px-4 py-2 bg-indigo-600 text-white rounded-md hover:bg-indigo-700">Salvar</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default TransactionModal;
