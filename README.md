# Sistema Inova-Empresa

O **Sistema Inova-Empresa** é um projeto destinado a registrar e avaliar ideias de melhorias dentro da empresa, oriundas dos colaboradores. O sistema é dividido em três fases principais: cadastro de colaboradores, criação e avaliação de eventos, e votação popular com a divulgação dos resultados.

## Funcionalidades

### 1. Cadastro de Colaboradores

#### 1.1. Cadastro Voluntário
Colaboradores podem se cadastrar na plataforma fornecendo as seguintes informações:
- Nome
- E-mail
- Senha

#### 1.2. Usuário Administrador
Deve existir um usuário administrador pré-cadastrado com perfil de admin, que terá a capacidade de alterar os perfis dos usuários para:
- Avaliador
- Administrador
- Colaborador

### 2. Criação e Avaliação de Eventos

#### 2.1. Criação de Eventos
Apenas usuários com perfil de admin podem criar novos eventos de ideias. Cada evento deve conter:
- Nome
- Descrição
- Data de início e fim
- Datas de avaliação pelos jurados
- Data para avaliação popular

#### 2.2. Postagem de Ideias
Colaboradores podem postar uma única ideia, individual ou em grupo. Regras:
- Cada colaborador pode estar vinculado a apenas uma ideia.
- A ideia deve incluir:
  - Nome da ideia
  - Impacto na empresa
  - Custo estimado de implantação
  - Descrição da ideia (até 1000 caracteres)

#### 2.3. Seleção de Jurados
O admin seleciona os jurados para cada evento. O registro de jurados deve ser salvo para uso em avaliações futuras, pois os jurados podem variar entre eventos.

#### 2.4. Distribuição de Ideias
As ideias devem ser distribuídas de forma aleatória e igualitária entre os jurados.

#### 2.5. Avaliação das Ideias
Jurados têm acesso apenas aos projetos/ideias atribuídos a eles e atribuem uma nota de 3 a 10.

#### 2.6. Média das Notas
Cada ideia deve ser avaliada por 2 jurados e o resultado é a média das notas atribuídas.

#### 2.7. Seleção das Melhores Ideias
Uma lista com as 10 melhores ideias avaliadas pelos jurados deve ser apresentada para votação popular.

### 3. Votação Popular

#### 3.1. Votação pelos Colaboradores
Todos os colaboradores cadastrados na plataforma podem votar em um projeto, mas apenas uma vez por evento.

#### 3.2. Divulgação dos Resultados
Ao final do prazo de votação, determinado pelo admin, será possível acessar a lista das 10 melhores ideias em ordem decrescente de pontuação, indicando a posição de cada uma.

## Requisitos de Avaliação

1. Todos os requisitos foram atendidos.
2. Implementação de testes unitários e de integração.
3. Cobertura de testes deve ser de no mínimo 80%.
4. Cada função deve conter ao menos 2 testes.

---

**Observação**: Este projeto visa incentivar a inovação dentro da empresa, permitindo que ideias de colaboradores sejam formalmente avaliadas e votadas, promovendo a melhoria contínua.

