"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { useAuth } from "@/app/contexts/AuthContext";

const Sidebar = () => {
  const pathname = usePathname();
  const { user, logout } = useAuth();

  const navLinks = [
    { href: "/dashboard", label: "Dashboard", icon: "dashboard" },
    { href: "/contas", label: "Contas", icon: "account_balance_wallet" },
    { href: "/metas", label: "Metas", icon: "flag" },
    { href: "/transacoes", label: "Transações", icon: "sync_alt" },
  ];

  return (
    <aside className="flex h-screen min-h-full w-64 flex-col justify-between border-r border-border-color bg-white/50 p-4 sticky top-0">
      <div className="flex flex-col gap-8">
        <div className="flex items-center gap-2 px-2">
          <span className="font-black text-2xl text-primary">meta</span>
          <span className="font-black text-2xl text-secondary">+</span>
        </div>
        <nav className="flex flex-col gap-2">
          {navLinks.map((link) => (
            <Link key={link.href} href={link.href} className={`flex items-center gap-3 rounded-lg px-3 py-2 ${pathname === link.href ? "bg-secondary/20 text-primary" : "text-text-primary hover:bg-black/5"}`}>
              <span className="material-symbols-outlined text-secondary">{link.icon}</span>
              <p className={`text-sm font-bold leading-normal ${pathname === link.href ? "text-primary" : "text-text-primary"}`}>
                {link.label}
              </p>
            </Link>
          ))}
        </nav>
      </div>
      <div className="flex flex-col gap-2">
        {user && (
          <div className="flex items-center gap-3 p-2 border-t border-border-color pt-4">
            <div className="flex flex-col">
              <h1 className="text-text-primary text-base font-semibold leading-normal">{user.nome}</h1>
            </div>
          </div>
        )}
        <button onClick={logout} className="flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5">
          <span className="material-symbols-outlined text-text-secondary">logout</span>
          <p className="text-sm font-medium leading-normal">Sair</p>
        </button>
      </div>
    </aside>
  );
};

export default Sidebar;
