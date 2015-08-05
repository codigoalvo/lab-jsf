lab-jsf

===== Release 0.0.3-SNAPSHOT - 05/08/2015 =====
*Realizado
- Refactor rename nos controllers
*Pendências e Implementar:
- Todas as do release 0.0.2
- Corrigir bug onde ao gravar ou excluir não exibe a mensagem no Growl do listar

===== Release 0.0.2-SNAPSHOT - 05/08/2015 =====
*Realizado:
- Login e cadastro de usuários 100% funcionais
- Tela de CRUD realizada com dialog do Primefaces
- GenericDaoJpa com funcionalidades básicas porém funcional
- Criptografia de senha por hash MD5
- Funcionando com Spring reconhecendo as anotações @Named e @Inject do CDI
- Arquivos de configuração do CDI preparados para alterar a injeção do spring para CDI
- Criado tema customizado para o Primefaces
*Pendências:
- Identificar e corrigir espaçamento indevido abaixo do logo no cabeçalho.
- Melhorar exibição de mensagens de erro
- Resolver problema do ViewScope no Spring ou mudar para CDI
- Verificar se é possível melhorar controle de fechamento do dialog do CRUD
- Pesquisar como usar testes unitários com Spring
*Implementar:
- Mudar menu do cabeçalho e transferir a seleção de idiomas para o cabeçalho
- Alteração de senha e/ou perfil de usuário
- Cadastre-se com aprovação de administrador