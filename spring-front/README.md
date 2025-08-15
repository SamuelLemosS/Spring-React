# Mercadinho do Seu Zé - Frontend React

Sistema de autenticação e dashboard para o Mercadinho do Seu Zé, desenvolvido em React com design minimalista.

## 🚀 Funcionalidades

- **Sistema de Autenticação**
  - Login com email e senha
  - Registro de novos usuários
  - Recuperação de senha por email
  - Rotas protegidas

- **Interface Minimalista**
  - Design limpo com cores branco, cinza e cinza escuro
  - Verde como cor de destaque
  - Responsivo para mobile e desktop

- **Notificações**
  - SnackBar para feedback das ações
  - Mensagens de sucesso e erro
  - Auto-hide após 4 segundos

## 🛠️ Tecnologias Utilizadas

- React 19
- React Router DOM
- Axios para requisições HTTP
- Context API para gerenciamento de estado
- CSS puro para estilização

## 📋 Pré-requisitos

- Node.js (versão 18 ou superior)
- npm ou yarn

## 🔧 Instalação

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd spring-front
```

2. Instale as dependências:
```bash
npm install
```

3. Configure o backend:
   - Certifique-se de que o backend Spring está rodando na porta 8080
   - O proxy está configurado para redirecionar `/api` para `http://localhost:8080`

## 🚀 Executando o Projeto

```bash
npm run dev
```

O projeto estará disponível em `http://localhost:5173`

## 📱 Rotas da Aplicação

- `/` - Redireciona para `/login`
- `/login` - Tela de login
- `/register` - Tela de registro
- `/forgot-password` - Tela de recuperação de senha
- `/dashboard` - Dashboard principal (protegida)

## 🔐 Endpoints do Backend

O frontend espera as seguintes rotas no backend Spring:

### Autenticação
- `POST /api/auth/login` - Login
- `POST /api/auth/register` - Registro
- `POST /api/auth/forgot-password` - Esqueci senha
- `GET /api/auth/verify` - Verificar token
- `POST /api/auth/logout` - Logout

### Usuário
- `GET /api/user/profile` - Obter perfil do usuário
- `PUT /api/user/profile` - Atualizar perfil

## 🎨 Estrutura do Projeto

```
src/
├── components/          # Componentes React
│   ├── Login.jsx       # Tela de login
│   ├── Register.jsx    # Tela de registro
│   ├── ForgotPassword.jsx # Tela de esqueci senha
│   ├── Dashboard.jsx   # Dashboard principal
│   ├── SnackBar.jsx    # Componente de notificação
│   └── ProtectedRoute.jsx # Rota protegida
├── contexts/            # Contextos React
│   ├── AuthContext.jsx # Contexto de autenticação
│   └── SnackBarContext.jsx # Contexto de notificações
├── services/            # Serviços de API
│   └── api.js          # Configuração do Axios e endpoints
├── App.jsx             # Componente principal
└── main.jsx            # Ponto de entrada
```

## 🔧 Configuração do Backend

Para que o frontend funcione corretamente, o backend Spring deve implementar:

1. **Autenticação JWT**
   - Geração de token no login
   - Validação de token nas rotas protegidas
   - Refresh token (opcional)

2. **CORS**
   - Permitir requisições do frontend
   - Headers de autorização

3. **Respostas Padrão**
   ```json
   {
     "success": true,
     "message": "Mensagem de sucesso",
     "data": { ... }
   }
   ```

## 🎯 Próximos Passos

- Implementar refresh token
- Adicionar validação de força da senha
- Implementar testes unitários
- Adicionar internacionalização (i18n)
- Implementar tema escuro

## 📄 Licença

Este projeto está sob a licença MIT.

## 👥 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📞 Suporte

Para dúvidas ou suporte, entre em contato através de:
- Email: [seu-email@exemplo.com]
- Issues do GitHub
