"use client";

import { useState } from "react";
import { useAuth } from "@/app/contexts/AuthContext";
import { useRouter } from "next/navigation";
import { loginUser, registerUser } from "@/app/services/api";

export default function LoginPage() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLogin, setIsLogin] = useState(true);
  const { login } = useAuth();
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      let userData;
      if (isLogin) {
        userData = await loginUser({ email, password });
      } else {
        userData = await registerUser({ name, email, password });
      }
      login(userData);
      router.push("/dashboard");
    } catch (error) {
      console.error("An error occurred:", error);
      if (error instanceof Response) {
        const errorBody = await error.json();
        console.error("Error body:", errorBody);
      }
    }
  };

  return (
    <div className="flex min-h-screen bg-background-light">
      <div className="flex flex-1 flex-col justify-center items-center p-6 lg:w-1/2">
        <div className="flex flex-col max-w-md w-full gap-6">
          <div className="text-left">
            <h1 className="text-text-primary text-3xl md:text-4xl font-bold tracking-tight">
              {isLogin ? "Bem-vindo de volta!" : "Crie sua conta"}
            </h1>
            <p className="text-text-secondary text-base font-normal leading-normal mt-2">
              Acesse sua vida financeira em um só lugar.
            </p>
          </div>

          <div className="flex w-full py-1">
            <div className="flex h-12 flex-1 items-center justify-center rounded-lg bg-white/50 p-1 border border-secondary/30">
              <button onClick={() => setIsLogin(true)} className={`flex-1 h-full rounded-md px-2 text-sm font-medium ${isLogin ? 'bg-white shadow-md text-primary' : 'text-text-secondary'}`}>
                Login
              </button>
              <button onClick={() => setIsLogin(false)} className={`flex-1 h-full rounded-md px-2 text-sm font-medium ${!isLogin ? 'bg-white shadow-md text-primary' : 'text-text-secondary'}`}>
                Criar conta
              </button>
            </div>
          </div>

          <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {!isLogin && (
              <label className="flex flex-col w-full">
                <p className="text-text-primary text-sm font-medium leading-normal pb-2">Nome</p>
                <input
                  type="text"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="form-input flex w-full rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 h-12 p-4"
                  placeholder="Digite seu nome"
                />
              </label>
            )}
            <label className="flex flex-col w-full">
              <p className="text-text-primary text-sm font-medium leading-normal pb-2">E-mail</p>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="form-input flex w-full rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 h-12 p-4"
                placeholder="Digite seu e-mail"
              />
            </label>
            <label className="flex flex-col w-full">
              <p className="text-text-primary text-sm font-medium leading-normal pb-2">Senha</p>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="form-input flex w-full rounded-lg text-text-primary focus:outline-0 focus:ring-2 focus:ring-primary/50 border border-secondary/50 bg-white/50 h-12 p-4"
                placeholder="Digite sua senha"
              />
            </label>
            <button type="submit" className="flex items-center justify-center h-12 px-6 rounded-lg w-full bg-primary text-white text-base font-semibold hover:bg-primary/90">
              {isLogin ? "Entrar" : "Criar conta"}
            </button>
          </form>
        </div>
      </div>
      <div className="hidden lg:flex flex-1 bg-primary/20">
        {/* Imagem de fundo */}
      </div>
    </div>
  );
}
