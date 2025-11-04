
import React, { useState, useEffect, createContext, useContext, useCallback } from 'react';
import { HashRouter, Routes, Route, Link, NavLink, useNavigate, Navigate, Outlet } from 'react-router-dom';
import type { Usuario } from './types';
import * as api from './services/apiService';

// --- AUTH CONTEXT ---
interface AuthContextType {
  user: Usuario | null;
  login: (user: Usuario) => void;
  logout: () => void;
  isLoading: boolean;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<Usuario | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    try {
      const storedUser = localStorage.getItem('user');
      if (storedUser) {
        setUser(JSON.parse(storedUser));
      }
    } catch (error) {
      console.error("Failed to parse user from localStorage", error);
    } finally {
      setIsLoading(false);
    }
  }, []);

  const login = (loggedInUser: Usuario) => {
    localStorage.setItem('user', JSON.stringify(loggedInUser));
    setUser(loggedInUser);
  };

  const logout = () => {
    localStorage.removeItem('user');
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout, isLoading }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

// --- PROTECTED ROUTE ---
const ProtectedRoute: React.FC = () => {
  const { user, isLoading } = useAuth();

  if (isLoading) {
    return <div className="flex items-center justify-center h-screen"><p>Loading...</p></div>;
  }
  
  return user ? <Outlet /> : <Navigate to="/login" replace />;
};

// --- LAYOUT COMPONENT ---
const SideNav: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
     <aside className="flex h-screen min-h-full w-64 flex-col justify-between border-r border-border-color bg-white/50 p-4 sticky top-0">
        <div className="flex flex-col gap-8">
            <div className="flex items-center gap-2 px-2">
                <span className="font-black text-2xl text-primary">meta</span><span className="font-black text-2xl text-secondary">+</span>
            </div>
            <nav className="flex flex-col gap-2">
                <NavLink to="/dashboard" className={({isActive}) => `flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5 ${isActive ? 'bg-secondary/20' : ''}`}>
                    <span className="material-symbols-outlined text-secondary">dashboard</span>
                    <p className="text-sm font-bold leading-normal">Dashboard</p>
                </NavLink>
                <NavLink to="/contas" className={({isActive}) => `flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5 ${isActive ? 'bg-secondary/20' : ''}`}>
                    <span className="material-symbols-outlined text-text-secondary">account_balance_wallet</span>
                    <p className="text-sm font-medium leading-normal">Contas</p>
                </NavLink>
                <NavLink to="/metas" className={({isActive}) => `flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5 ${isActive ? 'bg-secondary/20' : ''}`}>
                    <span className="material-symbols-outlined text-text-secondary">flag</span>
                    <p className="text-sm font-medium leading-normal">Metas</p>
                </NavLink>
                <NavLink to="/transacoes" className={({isActive}) => `flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5 ${isActive ? 'bg-secondary/20' : ''}`}>
                    <span className="material-symbols-outlined text-text-secondary">sync_alt</span>
                    <p className="text-sm font-medium leading-normal">Transações</p>
                </NavLink>
            </nav>
        </div>
        <div className="flex flex-col gap-2">
            <div className="flex items-center gap-3 p-2 border-t border-border-color pt-4">
                <div className="bg-center bg-no-repeat aspect-square bg-cover rounded-full size-10" style={{backgroundImage: `url(https://i.pravatar.cc/150?u=${user?.email})`}}></div>
                <div className="flex flex-col">
                    <h1 className="text-text-primary text-base font-semibold leading-normal">{user?.nome}</h1>
                </div>
            </div>
             <button onClick={handleLogout} className="flex items-center gap-3 rounded-lg px-3 py-2 text-text-primary hover:bg-black/5">
                <span className="material-symbols-outlined text-text-secondary">logout</span>
                <p className="text-sm font-medium leading-normal">Sair</p>
            </button>
        </div>
    </aside>
  );
};


const MainLayout: React.FC = () => (
  <div className="relative flex min-h-screen w-full flex-col">
    <div className="flex h-full flex-1">
      <SideNav />
      <main className="flex-1 p-6 md:p-8 lg:p-12 overflow-y-auto">
        <Outlet />
      </main>
    </div>
  </div>
);

// --- PAGES ---

// We are defining page components here to keep the file count low, as per instructions.
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import GoalsPage from './pages/GoalsPage';
import AccountsPage from './pages/AccountsPage';
import TransactionsPage from './pages/TransactionsPage';

// --- MAIN APP COMPONENT ---
const App: React.FC = () => {
  return (
    <AuthProvider>
      <HashRouter>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route element={<ProtectedRoute />}>
              <Route element={<MainLayout />}>
                  <Route path="/" element={<Navigate to="/dashboard" replace />} />
                  <Route path="/dashboard" element={<DashboardPage />} />
                  <Route path="/metas" element={<GoalsPage />} />
                  <Route path="/contas" element={<AccountsPage />} />
                  <Route path="/transacoes" element={<TransactionsPage />} />
              </Route>
          </Route>
        </Routes>
      </HashRouter>
    </AuthProvider>
  );
};

export default App;
