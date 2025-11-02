import Sidebar from "./Sidebar";

interface MainLayoutProps {
  children: React.ReactNode;
}

const MainLayout = ({ children }: MainLayoutProps) => {
  return (
    <div className="flex min-h-screen bg-background-light">
      <Sidebar />
      <main className="flex-1 p-6 md:p-8 lg:p-12">
        {children}
      </main>
    </div>
  );
};

export default MainLayout;
