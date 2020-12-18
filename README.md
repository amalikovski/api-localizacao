### APIs com funções de localização

Intruções para execução do projeto

- Clonar projeto e importar o mesmo na IDE de sua preferência
- Instalar dependências via maven
- Atualizar caminho para banco H2 no arquivo application.properties
(Substituir o 'D:/workspace/bd/banco.db' pelo caminho/nome de sua preferência)
`spring.datasource.url=jdbc:h2:file:/D:/workspace/bd/banco.db`
- Executar aplicação


Para Testar:
- Junto ao projeto, há um arquivo do projeto da ferramenta com exemplos das requisições do insomnia
- Também é possível visualizar mais informações na documentação Swagger da aplicação, sendo acessível, enquanto aplicação rodando local,
pela url http://localhost:8080/swagger-ui.html
- Executar as requisições de inclusão na ordem de País, Estado, Cidade(Ainda preciso incluir script de inclusão de Pais na inicialização do banco)

Em Implementação:
- Testes Unitários
- Melhorias no código
- Melhoria na Implementação do Spring Jpa