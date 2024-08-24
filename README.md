## Como executar

### Docker
```bash
# Para rodar o sistema utilizando o docker
$ docker-compose up -d
```

### Manual
```bash
# Para instalar as dependências
$ mvn clean install
# Para subir o banco de dados
$ docker-compose up -d mysql
# Para rodar o sistema
$ mvn spring-boot:run
 ```
```bash
# Se quiser verificar a cobertura de testes
$ docker run -d -p 9000:9000 sonarqube
```
> Foi criado um Runner para executar e popular o banco com algumas informações:
> src/main/kotlin/br/com/lucastribioli/auth_transaction/infrastructure/runner/DataInitializer.kt
 

> Na pasta postman tem um arquivo com as requisições para serem testadas no postman, basta importar o arquivo e rodar as requisições.
## Informações adicionais

+ Versão do Java utilizada -> 21
+ Versão do Kotlin -> 1.9
+ Gerenciador de Build -> Maven
+ Framework -> Spring
+ Versão do MySQL -> 8.0
---

### Resposta: l4.Questão aberta
Inicialmente, considerei colocar a transação em uma fila de mensagens para ser processada 
sequencialmente. No entanto, devido à necessidade de processamento rápido, essa pode não ser 
a melhor opção. Se precisasse de uma solução rápida e não houvesse tempo para pensar em 
alternativas mais elaboradas, eu implementaria um semáforo, semelhante ao que usamos para 
gerenciar threads que acessam o mesmo recurso. Embora isso possa resultar em um timeout em 
uma das operações, acho que é uma abordagem mais segura do que correr o risco de perder a 
venda devido a um débito incorreto em saldos não atualizados. Outra alternativa seria 
realizar a subtração do saldo diretamente no banco de dados, utilizando uma stored procedure. Dessa forma, 
mesmo que as transações ocorram simultaneamente, uma sempre será processada antes da outra, garantindo a ordem correta. 
Executando essa lógica no lado do banco, evitamos o risco de latência adicional que poderia ocorrer ao buscar os dados, 
subtrair o saldo e depois atualizar o banco novamente. Além disso, podemos envolver essa operação em uma 
transação, o que nos permite reverter tudo caso algo inesperado aconteça, como uma alteração no saldo durante o processo. 
Nesse caso, uma exceção seria lançada para que a aplicação possa tratá-la adequadamente. Acredito que isso precisaria ser pensado
melhor e possívelmente tem uma solução mais eficiente, mas foi o que consegui pensar em um primeiro momento. Talvez fazer algo com
Gateways de pagamento, ou talvez como é algo que talvez não aconteça com tanta frequência cancelar e aguardar a transação anterior,
porque quem  garante que a transação não é uma fraude? Acho que analisaria melhor esse problema, a frequência e o impacto que isso causa,
para ver se realmente é necessário uma solução tão complexa ou se ela se paga.

------
Uma parte que esqueci de mencionar, não coloquei Event Stream porque fiquei na dúvida se a latência dele não poderia interferir (por causa dos 100ms), sei que ele é em tempo real, mas precisaria testar, nunca usei ele em uma aplicação que o foco era tempo. Acabei que não mencionei, mas poderia ser uma opção, mas como essa frase estou colocando no dia 24/08, pode desconsiderar.
------

### Instalações necessárias:
- [Docker](https://docs.docker.com/get-docker/) 
- [Docker-compose](https://docs.docker.com/compose/install/)
- [Maven](https://maven.apache.org/download.cgi)
- [Java](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)
- [Kotlin](https://kotlinlang.org/docs/getting-started.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [SonarQube (opcional)](https://www.sonarqube.org/downloads/)
- [Postman (opcional)](https://www.postman.com/downloads/)

### Considerações finais
* Eu coloquei a opção de "mcc" como opcional no payload, porque entendi que como ele poderia vir qualquer coisa, poderia vir nulo também ou não vir;
* Como não era o foco ter a possibilidade de mudar o banco de dados ou outras ferramentas externas, não partir para uma arquitetura hexagonal, mirei mas em
  uma arquitetura limpa, sem fazer aquele tratamento mais profundo de separar o framework no Domain/Application;
* Pensei em adicionar uma camada do Spring Security, para garantir a autenticidade do emissor, mas como não foi especificado aqui, acredito que estaria fazendo um overengineering;
* Adicionei as váriaveis de ambiente direto no properties para facilidade na hora de vocês precisarem configurar;
* Segui os atributos descritos e não o payload apresentado, porque estavam um pouco divergentes, por esse motivo que ele está um pouco diferente, porque não tinha o id da transação por exemplo;
* Sobre as questões de L1 a L3, eu fiquei na dúvida seram para ser 3 endpoints diferentes ou um só, então fiz um só, mas se for para ser 3, é só me avisar que eu faço as alterações necessárias;
* Criei uma tabela de eventos para guardar as informações de cada transação, caso aconteça algum problema, teremos os eventos para analisar, no merchant deixei como string, porque pelo que eu entendi ele pode não ter o registro no banco e utilizaremos apenas o mcc.
