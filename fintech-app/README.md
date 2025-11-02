# Fintech App - Gerenciador Financeiro

Este é o frontend do aplicativo de gerenciamento financeiro, desenvolvido com Next.js, TypeScript e Tailwind CSS.

## Pré-requisitos

Antes de começar, certifique-se de ter o seguinte software instalado em sua máquina:

- [Node.js](https://nodejs.org/en/) (versão 18.x ou superior)
- [npm](https://www.npmjs.com/) (geralmente vem com o Node.js)

## Como Executar Localmente

Siga os passos abaixo para configurar e executar o projeto em seu ambiente de desenvolvimento.

### 1. Clone o Repositório

Primeiro, clone este repositório para a sua máquina local (se ainda não o fez).

### 2. Instale as Dependências

Navegue até o diretório do projeto `fintech-app` e instale todas as dependências necessárias:

```bash
cd fintech-app
npm install
```

### 3. Inicie o Servidor de Desenvolvimento

Após a instalação das dependências, inicie o servidor de desenvolvimento do Next.js:

```bash
npm run dev
```

O aplicativo estará disponível em [http://localhost:3000](http://localhost:3000).

---

### **⚠️ Importante: Dependência do Backend**

Este é um projeto de frontend que consome uma API para funcionar corretamente. Para que a autenticação (login/cadastro) e todas as funcionalidades de CRUD (contas, metas, transações) operem como esperado, **é essencial que o servidor do backend esteja em execução na porta `8080`**.

Certifique-se de que o backend está rodando em `http://localhost:8080` antes de usar a aplicação.
