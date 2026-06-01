# 🎨 VivArte API

Backend da plataforma **VivArte**, um marketplace de artesanato desenvolvido com **Java + Spring Boot**.

A aplicação permite o gerenciamento de usuários, produtos, imagens, carrinho de compras e pedidos, simulando o fluxo completo de um e-commerce.

---

## 🚀 Tecnologias

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Bean Validation
- Maven

---

## 📂 Arquitetura

O projeto segue uma arquitetura em camadas:

```text
src/main/java
├── controller
├── service
├── repository
├── entity
├── dto
├── exception
├── enums
└── config
```

### Responsabilidades

| Camada | Função |
|----------|----------|
| Controller | Recebe requisições HTTP |
| Service | Regras de negócio |
| Repository | Acesso ao banco de dados |
| Entity | Modelagem das tabelas |
| DTO | Comunicação da API |
| Exception | Tratamento global de erros |

---

## 🏗️ Funcionalidades

### 👤 Usuários

- Cadastro de usuários
- Consulta de usuários
- Atualização de dados
- Remoção de usuários
- Diferenciação de perfis (CLIENT e ADMIN)

---

### 🛍️ Produtos

- Cadastro de produtos
- Listagem de produtos
- Busca por ID
- Atualização
- Exclusão

---

### 🖼️ Imagens dos Produtos

- Cadastro de múltiplas imagens por produto
- Consulta das imagens
- Exclusão de imagens

---

### 🛒 Carrinho

- Criação automática de carrinho
- Adição de produtos
- Alteração de quantidade
- Remoção de itens
- Limpeza completa do carrinho
- Cálculo automático do total

---

### 📦 Pedidos

- Conversão do carrinho em pedido
- Geração automática dos itens do pedido
- Histórico de pedidos
- Consulta por usuário
- Atualização de status
- Exclusão por administradores

---

## 📊 Modelo de Negócio

```text
User
 ├── Cart
 │    └── CartItem
 │          └── Product
 │
 └── Order
       └── OrderItem
             └── Product
```

Fluxo:

```text
Usuário
   ↓
Adiciona produtos ao carrinho
   ↓
Finaliza compra
   ↓
Pedido criado
   ↓
Itens do carrinho viram itens do pedido
   ↓
Carrinho é esvaziado
```

---

## 📌 Principais Entidades

### User

```java
id
name
email
password
role
```

### Product

```java
id
name
description
price
stock
```

### Cart

```java
id
client
createdAt
```

### CartItem

```java
id
cart
product
quantity
```

### Order

```java
id
client
status
shippingAddress
totalPrice
createdAt
```

### OrderItem

```java
id
order
product
productName
unitPrice
quantity
```

---

## 📡 Principais Endpoints

### Produtos

```http
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
```

### Carrinho

```http
GET    /carts/{userId}
POST   /carts/{userId}/add
DELETE /carts/{userId}/remove
DELETE /carts/{userId}/clear
```

### Itens do Carrinho

```http
PUT    /cart-items/{cartItemId}
DELETE /cart-items/{cartItemId}
```

### Pedidos

```http
POST   /orders
GET    /orders
GET    /orders/{id}
GET    /orders/user/{userId}
PUT    /orders/{id}/status
DELETE /orders/{id}
```

---

## 🔄 Status do Pedido

```java
PENDING
PROCESSING
SHIPPED
DELIVERED
CANCELED
```

---

## 🗄️ Banco de Dados

O projeto utiliza PostgreSQL.

Exemplo de configuração:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/vivarte
spring.datasource.username=postgres
spring.datasource.password=******
```

---

## ▶️ Executando Localmente

Clone o projeto:

```bash
git clone https://github.com/seu-usuario/vivarte-api.git
```

Entre na pasta:

```bash
cd vivarte-api
```

Execute:

```bash
./mvnw spring-boot:run
```

ou

```bash
mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

---

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido com foco em:

- Aprendizado de Spring Boot
- Desenvolvimento de APIs REST
- Modelagem de banco de dados relacional
- Boas práticas de arquitetura em camadas
- Integração futura com aplicações React e React Native

---

## 👨‍💻 Autor

João Victor Freitas da Silva

Desenvolvedor Java | Spring Boot | React

GitHub: https://github.com/seu-usuario
LinkedIn: https://linkedin.com/in/seu-perfil
