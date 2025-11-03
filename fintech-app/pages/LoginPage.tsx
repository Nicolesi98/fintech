
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../App';
import * as api from '../services/apiService';
import type { Usuario } from '../types';

const LoginPage: React.FC = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const auth = useAuth();
  var user: Usuario;


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);
    setError('');

    try {
      if (isLogin) {
        user = await api.login({ email, senha: password });
      } else {
        user = await api.register({ nome: name, email, senha: password });
      }
      auth.login(user);
      navigate('/dashboard');
    } catch (err: any) {
      setError(err.message || (isLogin ? 'Falha no login.' : 'Falha no cadastro.'));
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="flex flex-col lg:flex-row w-full min-h-screen">
      <div className="flex flex-1 flex-col justify-center items-center p-6 sm:p-8 lg:p-12">
        <div className="flex flex-col max-w-md w-full gap-6">
          <div className="flex items-center gap-2 mb-4 p-2 border border-primary rounded-lg self-start">
             <span className="font-black text-2xl text-primary">meta</span><span className="font-black text-2xl text-secondary">+</span>
          </div>
          <div className="text-left">
            <h1 className="text-text-primary text-3xl md:text-4xl font-bold tracking-tight">
              {isLogin ? 'Bem-vindo de volta!' : 'Crie sua conta'}
            </h1>
            <p className="text-text-secondary text-base font-normal leading-normal mt-2">
              Acesse sua vida financeira em um só lugar.
            </p>
          </div>

          <div className="flex w-full py-1">
            <div className="flex h-12 flex-1 items-center justify-center rounded-lg bg-white/50 p-1 border border-secondary/30">
              <label className="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 has-[:checked]:bg-white has-[:checked]:shadow-md has-[:checked]:text-primary text-text-secondary text-sm font-medium leading-normal transition-all duration-300 ease-in-out">
                <span>Login</span>
                <input checked={isLogin} onChange={() => setIsLogin(true)} className="invisible w-0" name="auth-toggle" type="radio" />
              </label>
              <label className="flex cursor-pointer h-full grow items-center justify-center overflow-hidden rounded-md px-2 has-[:checked]:bg-white has-[:checked]:shadow-md has-[:checked]:text-primary text-text-secondary text-sm font-medium leading-normal transition-all duration-300 ease-in-out">
                <span>Criar conta</span>
                <input checked={!isLogin} onChange={() => setIsLogin(false)} className="invisible w-0" name="auth-toggle" type="radio" />
              </label>
            </div>
          </div>
          
          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {!isLogin && (
               <label className="flex flex-col w-full">
                <p className="text-text-primary text-sm font-medium leading-normal pb-2">Nome</p>
                <input value={name} onChange={e => setName(e.target.value)} required className="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 focus:border-primary h-12 placeholder:text-text-secondary p-[15px] text-base font-normal leading-normal transition-colors duration-200" placeholder="Digite seu nome completo" />
              </label>
            )}
            <label className="flex flex-col w-full">
              <p className="text-text-primary text-sm font-medium leading-normal pb-2">E-mail</p>
              <input type="email" value={email} onChange={e => setEmail(e.target.value)} required className="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 focus:border-primary h-12 placeholder:text-text-secondary p-[15px] text-base font-normal leading-normal transition-colors duration-200" placeholder="Digite seu e-mail" />
            </label>
            <label className="flex flex-col w-full">
              <p className="text-text-primary text-sm font-medium leading-normal pb-2">Senha</p>
              <input type="password" value={password} onChange={e => setPassword(e.target.value)} required className="form-input flex w-full min-w-0 flex-1 resize-none overflow-hidden rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 focus:border-primary h-12 placeholder:text-text-secondary p-[15px] text-base font-normal leading-normal transition-colors duration-200" placeholder="Digite sua senha" />
            </label>
            {error && <p className="text-red-500 text-sm">{error}</p>}
            <button disabled={isLoading} type="submit" className="flex items-center justify-center whitespace-nowrap h-12 px-6 rounded-lg w-full bg-primary text-white text-base font-semibold hover:bg-primary/90 transition-colors duration-200 disabled:bg-gray-400">
              {isLoading ? 'Carregando...' : (isLogin ? 'Entrar' : 'Criar Conta')}
            </button>
          </form>
        </div>
      </div>
      <div className="hidden lg:flex flex-1 justify-center items-center w-full lg:w-1/2 relative overflow-hidden bg-primary/20">
        <img alt="Finance management illustration" className="absolute inset-0 w-full h-full object-cover" src="https://picsum.photos/1200/900?grayscale&blur=2" />
        <div className="absolute inset-0 bg-primary/30"></div>
        <div className="relative w-full max-w-xl aspect-square flex flex-col items-center justify-center text-center p-8">
          <h2 className="text-3xl font-bold text-white">Seu futuro financeiro começa aqui.</h2>
          <p className="text-background-light mt-2">Gerencie suas contas, defina metas e acompanhe seu progresso, tudo em um só lugar.</p>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
