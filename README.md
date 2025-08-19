# Spring-React

## Como rodar o projeto

 Clonar o repositório
```
git clone https://github.com/SamuelLemosS/Spring-React.git
cd Spring-React
```

## Backend (Spring Boot)

### Acesse a pasta do backend:

```
cd demo
```

### Configure o arquivo application.properties adicionando suas credenciais de e-mail:

```
spring.mail.username=USERNAME_MAILTRAP 
spring.mail.password=PASSWORD_MAILTRAP
```

Recomendação: utilize o Mailtrap para simular o envio de e-mails em ambiente de testes.

Certifique-se de estar rodando com JDK 21.

Execute o projeto Spring Boot normalmente:

```
./mvnw spring-boot:run
```

ou rode direto pela sua IDE.

## Frontend (React)
### Acesse a pasta do frontend:

```
cd spring-front
```

Instale as dependências:

```
npm install
```

Crie o arquivo .env na raiz do frontend e configure a porta da API:

```
REACT_APP_API_URL="http://localhost:8080/api"
```

A porta 8080 é a padrão do Spring Boot, mas sempre confira se não está diferente no application.properties.  
Certifique-se também que o front está na porta 5173 para não dar error de Cors

### Rode o projeto React:

```
npm run dev
```
Certifique-se de estar rodando com NODE 22.

## Rotas Disponíveis (Backend)

### [POST] /auth/register

Body:

```
{
  "name": "João",
  "email": "joao@email.com",
  "password": "123456"
}
```
Response Sucesso: ``Conta registrado com sucesso!``

### [POST] /auth/login
Body:
```
{
 "email": "joao@email.com",
  "password": "123456"
}
```
Response Sucesso: ``{"token": <TOKEN>,"userId":<ID>}``
### Para as proximas rota é necessario criar uma conta de admin, usando o register com o opcional de role  
### Faça login, pegue o token e coloque no Bearer
```
{
  "name": "Admin",
  "email": "admin@email.com",
  "password": "123456",
  "role":"ADMIN"
}
```
### [GET] /email/history/{userId}
Response Sucesso: ``[Emails detalhados]``

### [GET] /email/history/failed/{userId}
Response Sucesso: ``[Emails detalhados que falharam]``

### [POST] /email/resend/{emailId}/{userId}
Response Sucesso: "Email reenviado com sucesso!"

### [GET] /email/stats/{userID}
Response Sucesso: "Email enviado com sucesso!"

## Simulação de falha no envio de e-mail

Para simular um erro no envio de e-mails, basta remover a variável abaixo do application.properties:

```
spring.mail.host=
```

Assim, o sistema não conseguirá se conectar ao servidor SMTP e lançará erro ao tentar enviar mensagens.

## Implementações realizadas
  Cadastro e autenticação simples, com adim como opcional criando manualmente  
  Ação que dispara e-mail, sobre os dados dos emails  
  Envio de e-mail via SMTP  
  Retries simples, utilizando um Trycatch  
  Persistência de histórico básico, que é mandado por email  
  Possibilidade de reenvio manual por rota prodegida por adiministrador  
  Teste básicos usando dados mokados  
  Idempotência, como enviar emails mostrar dados do email, é importante que o reenvio não gere dados duplicados

## Tempo gasto no projeto de aproximadamente 18h
