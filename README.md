## Como executar
```bash
# Para rodar o sistema utilizando o docker
$ docker-compose up -d
```

```bash
# Para rodar apenas o banco de dados, caso queira rodar a aplicação pela IDE
$ docker-compose up -d mysql
```


### L4. Questão aberta
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


