
import React, { useState, useEffect, useMemo } from 'react';
import { Link } from 'react-router-dom';
import * as api from '../services/apiService';
import type { Conta, Meta, Transacao } from '../types';
import { useAuth } from '../App';

const DashboardPage: React.FC = () => {
    const [contas, setContas] = useState<Conta[]>([]);
    const [metas, setMetas] = useState<Meta[]>([]);
    const [transacoes, setTransacoes] = useState<Transacao[]>([]);
    const [loading, setLoading] = useState(true);
    const { user } = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const [contasData, metasData, transacoesData] = await Promise.all([
                    api.getContas(user.id),
                    api.getMetas(user.id),
                    api.getTransacoes(user.id),
                ]);
                setContas(contasData);
                setMetas(metasData);
                setTransacoes(transacoesData.sort((a, b) => new Date(b.dataCriacao!).getTime() - new Date(a.dataCriacao!).getTime()));
            } catch (error) {
                console.error("Failed to fetch dashboard data", error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const totalBalance = useMemo(() => {
        return contas.reduce((sum, conta) => sum + conta.saldo, 0);
    }, [contas]);

    if (loading) {
        return <div className="text-center p-10">Carregando dashboard...</div>;
    }

    return (
        <div className="mx-auto flex w-full max-w-7xl flex-col gap-8">
            <div className="flex flex-col gap-2">
                <h1 className="text-primary text-4xl font-black leading-tight tracking-[-0.033em]">Dashboard</h1>
                <p className="text-text-secondary text-base font-normal leading-normal">Seu resumo financeiro em um só lugar.</p>
            </div>
            <div className="grid grid-cols-1 gap-8 lg:grid-cols-3">
                <div className="flex flex-col gap-8 lg:col-span-2">
                    <div className="flex flex-col gap-4 rounded-xl bg-primary p-6 text-background-light">
                        <p className="text-base font-medium text-background-light/80">Saldo Total</p>
                        <p className="text-4xl font-bold tracking-tight">{totalBalance.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
                    </div>
                    <div className="flex flex-col gap-4">
                        <div className="flex items-center justify-between">
                            <h2 className="text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Progresso das Metas</h2>
                            <Link className="text-secondary text-sm font-bold hover:underline" to="/metas">Ver todas</Link>
                        </div>
                        <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
                            {metas.slice(0, 2).map(meta => {
                                const progress = Math.min(100, Math.round((meta.valorAtual / meta.valorAlvo) * 100));
                                return (
                                    <div key={meta.id} className="flex flex-col gap-3 rounded-xl bg-white/50 p-4 border border-border-color">
                                        <div className="flex items-center gap-3">
                                            <div className="flex size-10 items-center justify-center rounded-lg bg-secondary/20 text-secondary">
                                                <span className="material-symbols-outlined">flag</span>
                                            </div>
                                            <p className="text-primary font-semibold">{meta.nome}</p>
                                        </div>
                                        <div className="w-full overflow-hidden rounded-full bg-black/10 h-2">
                                            <div className="h-full rounded-full bg-secondary" style={{ width: `${progress}%` }}></div>
                                        </div>
                                        <p className="text-sm text-text-secondary">
                                            <span className="font-bold text-primary">{meta.valorAtual.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</span> / {meta.valorAlvo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })} ({progress}%)
                                        </p>
                                    </div>
                                )
                            })}
                        </div>
                    </div>
                </div>
                <div className="flex flex-col gap-4 lg:col-span-1">
                    <h2 className="text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Últimas Transações</h2>
                    <div className="flex flex-col gap-3 rounded-xl bg-white/50 p-4 border border-border-color">
                        {transacoes.slice(0, 4).map(transacao => (
                            <div key={transacao.id} className="flex items-center gap-4 py-2 border-b border-border-color last:border-b-0">
                                <div className={`flex size-10 items-center justify-center rounded-full ${transacao.tipo === 'Despesa' ? 'bg-red-100 text-expense' : 'bg-green-100 text-income'}`}>
                                    <span className="material-symbols-outlined">
                                        {transacao.tipo === 'Despesa' ? 'arrow_upward' : 'arrow_downward'}
                                    </span>
                                </div>
                                <div className="flex-1">
                                    <p className="font-semibold text-primary">{transacao.descricao}</p>
                                    <p className="text-sm text-text-secondary">{new Date(transacao.dataCriacao!).toLocaleDateString('pt-BR')}</p>
                                </div>
                                <p className={`font-bold ${transacao.tipo === 'Despesa' ? 'text-expense' : 'text-income'}`}>
                                    {transacao.tipo === 'Despesa' ? '-' : '+'} {transacao.valor.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}
                                </p>
                            </div>
                        ))}
                        <Link className="mt-2 text-center text-secondary text-sm font-bold hover:underline" to="/transacoes">Ver todas as transações</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default DashboardPage;
