**#SISTEMA INOVA-EMPRESA**
O objetivo deste projeto é desenvolver um sistema para cadastrar ideias de iniciativas de melhorias na empresa, oriundas dos colaboradores. O sistema será dividido em três fases principais: cadastro de colaboradores, criação e avaliação de eventos, e votação popular com resultados.

##1. CADASTRO DE COLABORADORES
1.1. Cadastro Voluntário: Colaboradores podem se cadastrar na plataforma fornecendo:
• nome
• e-mail
•senha 
1.2. Usuário Administrador: Deve existir um usuário administrador pré-cadastrado com perfil de admin, que terá a capacidade de alterar os perfis dos usuários para avaliadores, administradores ou colaboradores.

##2. CRIAÇÃO E AVALIAÇÃO DE EVENTOS
2.1. Criação de Eventos: Apenas usuários com perfil de admin podem criar novos eventos de ideias. Cada evento deve ter:
• Nome
• Descrição
• Data de início e fim
• Datas de avaliação pelos jurados
• Data para avaliação popular
2.2. Postagem de Ideias: Colaboradores podem postar uma única ideia, que pode ser individual ou em grupo. Cada colaborador pode estar vinculado a apenas uma ideia. As ideias devem incluir:
• Nome da ideia
• Impacto na empresa
• Custo estimado de implantação
• Descrição da ideia (até 1000 caracteres)
2.3. Seleção de Jurados: O admin seleciona os jurados para cada evento. Esse registro deve ser salvo para avaliações futuras, pois os jurados podem variar entre eventos.
2.4. Distribuição de Ideias: As ideias devem ser distribuídas de forma aleatória e igualitária entre os jurados.
2.5. Avaliação das Ideias: Jurados acessam apenas os projetos/ideias atribuídos a eles e atribuem uma nota de 3 a 10.
2.6. Média das Notas: Cada ideia deve ser avaliada por 2 jurados e o resultado é a média das notas.
2.7. Seleção das Melhores Ideias: Uma lista com os 10 melhores projetos avaliados pelos jurados deve ser apresentada para votação popular.

##3. VOTAÇÃO POPULAR
3.1. Votação pelos Colaboradores: Todos os colaboradores da plataforma podem votar em um projeto, mas apenas uma vez por evento.
3.2. Divulgação dos Resultados: Ao final do prazo de votação, determinado pelo admin, deve ser possível acessar a lista dos 10 projetos em ordem de maior nota para menor nota, indicando a posição de cada um.

##PONTOS IMPORTANTES
Ponto de avaliação:
1. Todos os requisitos foram atendidos;
2. Implementação de testes unitários e de integração;
3. Cobertura de testes em 80%;
4. Ao menos 2 testes por função.
