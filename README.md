# WorkoutTracker API

API backend para gerenciamento de treinos e planos de treino, desenvolvida com Spring Boot.  
O projeto tem como objetivo o estudo e a implementação de **modelagem de domínio**, **arquitetura em camadas**, **persistência de dados** e **segurança** em aplicações Java.

Este projeto agora está completo em sua versão inicial, com todas as funcionalidades principais implementadas, incluindo autenticação de usuários, gerenciamento de treinos e registro de exercícios.

## 📌 Objetivo do Projeto

Fornecer uma API REST para:
- Criação e gerenciamento de planos de treino
- Organização de treinos por dia da semana
- Registro da execução de exercícios
- Gerenciamento de usuários (Admin, Aluno, Profissional)
- Autenticação e autorização via JWT

O projeto foi pensado de forma extensível, permitindo evoluções futuras conforme a maturidade da aplicação.

## ✅ Funcionalidades Implementadas

### Estrutura e Infraestrutura
- Projeto base configurado com Spring Boot
- Arquitetura em camadas:
  - Controller
  - Service
  - Repository
  - Domain
  - DTO
- Spring Security com JWT para autenticação e autorização
- Ambiente de banco de dados com Docker Compose:
  - PostgreSQL
  - pgAdmin

### Domínio
- Modelagem das principais entidades do sistema:
  - Usuário
  - Plano de Treino
  - Treino
  - Exercício
  - Registro de Treino
  - Perfil de Treino
- Enumerações para regras de domínio:
  - Dias da semana
  - Grupos musculares
  - Categorias de exercício

### Funcionalidades do Sistema
- CRUD completo para usuários, planos, treinos e exercícios
- Registro de execução de treinos e exercícios
- Perfis de treino para diferentes tipos de usuários
- Endpoints seguros via JWT
- Documentação de API via Swagger (SpringDoc OpenAPI)

### Testes
- Testes unitários e de integração implementados
- Testes de segurança para endpoints autenticados
- Testes de persistência usando Testcontainers com PostgreSQL

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.5.10
- Spring MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Docker / Docker Compose
- Maven
- Lombok
- H2 (banco em memória para testes)
- SpringDoc OpenAPI (Swagger UI)

## 🚀 Como Rodar o Projeto

1. Clonar o repositório:  
```bash
git clone https://github.com/ryann0097/WorkoutTracker.git
cd WorkoutTracker
```

2. Subir o banco de dados PostgreSQL via Docker Compose:
```bash
docker-compose up -d
```

3. Build e run com Maven:
```bash
mvn clean install
mvn spring-boot:run
```

---

**📝 Observações**

Este projeto foi desenvolvido com foco **no aprendizado** e na prática de conceitos de **Java, Spring Boot e arquitetura de software**.  
Embora todas as funcionalidades principais tenham sido implementadas com cuidado, pode haver **erros ou melhorias a serem feitas**.  

Sugestões, feedbacks ou contribuições são muito bem-vindos e podem ser enviadas para: **rsilv97cpp@gmail.com**.

