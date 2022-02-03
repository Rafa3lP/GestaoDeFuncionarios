# Gestão de Funcionários

COD_ATIVIDADE = P1_PSS_2021-2

Discente: Rafael de Andrade Prenholato

## Gerar .jar
```bash
mvn clean install
```
O jar será gerado em target/dist
## Mecanismo de persistência
O sistema utiliza um banco de dados SQLite como mecanismo de persistencia

## Mecanismo de Log
O sistema armazena logs em txt, json ou xml.

O formato pode ser escolhido no menu de configurações.
O programa reinicializará ao alterar o formato de log quando for executado pelo .jar gerado em target/dist.
## Rodar script sql para preencher o banco com 100 funcionários
use o sqlite para rodar o script:
```bash
sqlite3 banco.db
sqlite> .read criaFuncionarios.sql
```


