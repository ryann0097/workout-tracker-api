# WorkoutTracker API

API backend para gerenciamento de treinos e planos de treino, desenvolvida com Spring Boot.  
O projeto tem como objetivo o estudo e a implementação de **modelagem de domínio**, **arquitetura em camadas**, **persistência de dados** e **segurança** em aplicações Java.

Atualmente, o projeto encontra-se em fase inicial, com foco na **estruturação do domínio** e **configuração do ambiente de desenvolvimento**.


## 📌 Objetivo do Projeto

Fornecer uma API REST para:
- Criação e gerenciamento de planos de treino
- Organização de treinos por dia da semana
- Registro da execução de exercícios
- Gerenciamento de usuários

O projeto foi pensado de forma extensível, permitindo evoluções futuras conforme a maturidade da aplicação.


## ✅ Funcionalidades Implementadas (até o momento)

### Estrutura e Infraestrutura
- Projeto base configurado com Spring Boot
- Arquitetura em camadas:
  - Controller
  - Service
  - Repository
  - Domain
  - DTO
- Configuração inicial do Spring Security
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
- Enumerações para regras de domínio (dias da semana, grupos musculares, categorias de exercício)


## 🚧 Em Desenvolvimento

- Implementação da lógica de negócio na camada de Services
- Definição dos fluxos principais da aplicação
- Evolução da camada de segurança
- Estruturação das regras de acesso aos recursos


## 🔜 Próximos Passos

- Finalizar a implementação dos Services
- Consolidar as regras de domínio
- Iniciar a escrita de testes automatizados
- Implementar tratamento global de exceções


## 🛠️ Tecnologias Utilizadas

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Docker / Docker Compose
- Maven
