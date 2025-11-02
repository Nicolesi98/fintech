"use client";

import withAuth from "@/app/components/withAuth";
import MainLayout from "@/app/components/MainLayout";
import { useAuth } from "@/app/contexts/AuthContext";
import { useEffect, useState } from "react";
import { Conta, Meta, Transacao } from "@/app/types/usuario";
import { getAllContas, getAllMetas, getAllTransacoes } from "@/app/services/api";
import Link from "next/link";

const DashboardPage = () => {
  const { user } = useAuth();
  const [contas, setContas] = useState<Conta[]>([]);
  const [metas, setMetas] = useState<Meta[]>([]);
  const [transacoes, setTransacoes] = useState<Transacao[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      if (user) {
        const [userContas, userMetas, userTransacoes] = await Promise.all([
          getAllContas(),
          getAllMetas(),
          getAllTransacoes(),
        ]);
        setContas(userContas);
        setMetas(userMetas);
        setTransacoes(userTransacoes.slice(0, 4)); // Pegar as últimas 4 transações
      }
    };
    fetchData();
  }, [user]);

  const saldoTotal = contas.reduce((acc, conta) => acc + conta.saldo, 0);

  return (
    <MainLayout>
      <div className="mx-auto flex w-full max-w-7xl flex-col gap-8">
        <div className="flex flex-col gap-2">
          <h1 className="text-primary text-4xl font-black leading-tight tracking-[-0.033em]">Dashboard</h1>
          <p className="text-text-secondary text-base font-normal leading-normal">Seu resumo financeiro em um só lugar.</p>
        </div>
        <div className="grid grid-cols-1 gap-8 lg:grid-cols-3">
          <div className="flex flex-col gap-8 lg:col-span-2">
            <div className="flex flex-col gap-4 rounded-xl bg-primary p-6 text-white">
              <p className="text-base font-medium text-white/80">Saldo Total</p>
              <p className="text-4xl font-bold tracking-tight">R$ {saldoTotal.toFixed(2)}</p>
            </div>
            <div className="flex flex-col gap-4">
              <div className="flex items-center justify-between">
                <h2 className="text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Progresso das Metas</h2>
                <Link className="text-secondary text-sm font-bold hover:underline" href="/metas">Ver todas</Link>
              </div>
              <div className="grid grid-cols-1 gap-4 md:grid-cols-2">
                {metas.slice(0, 2).map(meta => (
                  <div key={meta.id} className="flex flex-col gap-3 rounded-xl bg-white p-4 border border-border-color">
                    <p className="text-primary font-semibold">{meta.nome}</p>
                    <div className="w-full overflow-hidden rounded-full bg-black/10 h-2">
                      <div className="h-full rounded-full bg-secondary" style={{ width: `${(meta.valorAtual / meta.valorAlvo) * 100}%` }}></div>
                    </div>
                    <p className="text-sm text-text-secondary"><span className="font-bold text-primary">R$ {meta.valorAtual.toFixed(2)}</span> / R$ {meta.valorAlvo.toFixed(2)}</p>
                  </div>
                ))}
              </div>
            </div>
          </div>
          <div className="flex flex-col gap-4 lg:col-span-1">
            <h2 className="text-primary text-2xl font-bold leading-tight tracking-[-0.015em]">Últimas Transações</h2>
            <div className="flex flex-col gap-3 rounded-xl bg-white p-4 border border-border-color">
              {transacoes.map(transacao => (
                <div key={transacao.id} className="flex items-center gap-4 py-2 border-b border-border-color last:border-b-0">
                  <div className="flex-1">
                    <p className="font-semibold text-primary">{transacao.descricao}</p>
                    <p className="text-sm text-text-secondary">{new Date(transacao.dataCriacao!).toLocaleDateString()}</p>
                  </div>
                  <p className={`font-bold ${transacao.valor > 0 ? 'text-income' : 'text-expense'}`}>{transacao.valor > 0 ? '+' : '-'} R$ {Math.abs(transacao.valor).toFixed(2)}</p>
                </div>
              ))}
              <Link className="mt-2 text-center text-secondary text-sm font-bold hover:underline" href="/transacoes">Ver todas as transações</Link>
            </div>
          </div>
        </div>
      </div>
    </MainLayout>
  );
};

export default withAuth(DashboardPage);
