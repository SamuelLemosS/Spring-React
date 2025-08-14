# Sistema de Autenticação e Email com Spring Boot

## 🚀 Funcionalidades Implementadas

### 1. **Cadastro e Autenticação com JWT**
- ✅ Registro de usuário com nome, email e senha
- ✅ Login com geração de token JWT
- ✅ Validação de credenciais
- ✅ Senhas criptografadas com BCrypt

### 2. **Sistema de Email com Retry**
- ✅ Envio de emails via SMTP configurável
- ✅ Retry automático após 5 segundos em caso de falha
- ✅ Templates personalizados para diferentes tipos de email
- ✅ Emails de boas-vindas e recuperação de senha

### 3. **Persistência e Histórico**
- ✅ Banco de dados H2 em memória
- ✅ Histórico completo de todos os emails enviados
- ✅ Status: PENDING, SENT, FAILED, RETRY
- ✅ Contagem de tentativas de reenvio

### 4. **Administração e Monitoramento**
- ✅ Endpoint para visualizar histórico de emails
- ✅ Lista de emails que falharam
- ✅ Reenvio manual de emails com falha
- ✅ Estatísticas de envio

### 5. **Segurança**
- ✅ Endpoints protegidos com Spring Security
- ✅ Validação de inputs com Bean Validation
- ✅ CORS configurado para desenvolvimento
- ✅ Configurações via variáveis de ambiente

## 🏗️ Estrutura do Projeto

```
src/main/java/com/example/demo/
├── DemoApplication.java          # Classe principal
├── config/                       # Configurações
│   └── SecurityConfig.java      # Configuração de segurança
├── controllers/                  # Controllers REST
│   ├── AuthController.java      # Autenticação
│   └── EmailController.java     # Administração de emails
├── services/                     # Lógica de negócio
│   ├── AuthService.java         # Serviço de autenticação
│   ├── EmailService.java        # Serviço de email com retry
│   └── JwtService.java          # Geração/validação de JWT
├── repositories/                 # Acesso a dados
│   ├── UserRepository.java      # Usuários
│   └── EmailHistoryRepository.java # Histórico de emails
├── models/                       # Entidades
│   ├── User.java                # Usuário
│   └── EmailHistory.java        # Histórico de email
└── dto/                          # Objetos de transferência
    ├── UserRegistrationDto.java  # Registro
    ├── LoginDto.java            # Login
    └── ForgotPasswordDto.java   # Recuperação de senha
```

## 🛠️ Tecnologias

- **Spring Boot 3.5.4**
- **Java 21**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database**
- **Spring Boot Mail**
- **Bean Validation**

## 📋 Endpoints Disponíveis

### **Autenticação (Públicos)**
- `POST /api/auth/register` - Registro de usuário
- `POST /api/auth/login` - Login com JWT
- `POST /api/auth/forgot-password` - Recuperação de senha

### **Administração (Protegidos)**
- `GET /api/email/history` - Histórico completo
- `GET /api/email/history/failed` - Emails com falha
- `POST /api/email/resend/{id}` - Reenviar email
- `GET /api/email/stats` - Estatísticas

### **Desenvolvimento**
- `GET /h2-console` - Console do banco H2

## 🧪 Como Testar

### **1. Registro de Usuário**
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
    "name": "João Silva",
    "email": "joao@exemplo.com",
    "password": "123456"
}
```

### **2. Login**
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "email": "joao@exemplo.com",
    "password": "123456"
}
```

### **3. Recuperação de Senha**
```bash
POST http://localhost:8080/api/auth/forgot-password
Content-Type: application/json

{
    "email": "joao@exemplo.com"
}
```

### **4. Ver Histórico de Emails**
```bash
GET http://localhost:8080/api/email/history
```

### **5. Reenviar Email com Falha**
```bash
POST http://localhost:8080/api/email/resend/1
```

## ⚙️ Configurações

### **Email (application.properties)**
```properties
spring.mail.host=sandbox.smtp.mailtrap.io
spring.mail.port=587
spring.mail.username=seu_username
spring.mail.password=sua_senha
```

### **JWT**
```properties
jwt.secret=sua_chave_secreta_aqui
jwt.expiration=86400000
```

### **Banco de Dados**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
```

## 🚀 Executando o Projeto

1. **Clone e entre na pasta:**
```bash
cd demo
```

2. **Execute com Maven:**
```bash
./mvnw spring-boot:run
```

3. **Acesse:**
- API: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console

## 🧪 Testes

```bash
./mvnw test
```

## 📊 Funcionalidades de Retry

- **Primeira tentativa:** Envio imediato
- **Falha:** Aguarda 5 segundos
- **Retry:** Segunda tentativa
- **Persistência:** Todas as tentativas são registradas
- **Status:** PENDING → SENT/FAILED → RETRY → SENT/FAILED

## 🔒 Segurança

- **BCrypt** para senhas
- **JWT** para autenticação
- **Validação** de inputs
- **CORS** configurado
- **Endpoints** protegidos

## 📈 Monitoramento

- Histórico completo de emails
- Status de cada envio
- Contagem de tentativas
- Mensagens de erro detalhadas
- Estatísticas em tempo real
