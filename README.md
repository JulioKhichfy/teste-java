
## Objetivo

API para a recepção de pedidos dos clientes, no formato **xml** e **json** com 6 campos:

1. número controle - número aleatório informado pelo cliente.
2. data cadastro (opcional). 
3. nome - nome do produto. 
4. valor - valor monetário unitário produto.
5. quantidade (opcional) - quantidade de produtos.
6. codigo cliente - identificação numérica do cliente.

Criar um serviço onde possa consultar os pedidos enviados pelos clientes.

Filtros da consulta:

1. número pedido
2. data cadastro 
3. todos os dados do pedido

---

#### Exemplo de arquivo JSON

```json
{
  "pedidos": [
    {
      "numeroControle": 124,
      "codigoCliente": 1,
      "dataCadastro": "2024-01-05",
      "items" : [
        {
          "nome": "Produto A",
          "valor": 100.0,
          "quantidade": 2
        }
      ]
    }
  ]
}

```

#### Exemplo de arquivo XML

```xml
<pedidos>
    <pedido>
        <numeroControle>123</numeroControle>
        <codigoCliente>1</codigoCliente>
        <dataCadastro>2024-01-01</dataCadastro>
        <items>
            <item>
                <nome>PRODUTO j</nome>
                <valor>100.0</valor>
                <quantidade>2</quantidade>
            </item>
            <item>
                <nome>PRODUTO k</nome>
                <valor>200.0</valor>
                <quantidade>3</quantidade>
            </item>
        </items>
    </pedido>
</pedidos>

```
Critérios aceitação e manipulação do arquivo:

1. O arquivo pode conter 1 ou mais pedidos, limitado a 10.
2. Não poderá aceitar um número de controle já cadastrado.
3. Caso a data de cadastro não seja enviada o sistema deve assumir a data atual. 
4. Caso a quantidade não seja enviada considerar 1.
5. Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, 
6. Caso a quantidade seja maior ou igual 10 aplicar 10% de desconto no valor total.
7. O sistema deve calcular e gravar o valor total do pedido. 
8. Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10. 


## Versões Utilizadas

1. java 17.0.9
2. springboot 3.2.5

## Banco de Dados

As tabelas serão geradas, assim como uma carga inicial de 10 clientes, ao iniciar a aplicação.

#### Mysql

Por padrão o sistema utiliza o ***mysql*** definido no
***application.properties***:

```01 spring.profiles.active=dev```

***application-dev.properties***:
```
spring.datasource.url=jdbc:mysql://localhost:3306/teste_julio_db
spring.datasource.username=root
spring.datasource.password=admin
```

#### H2

Para utilizar o H2 em memória, alterar o arquivo ```application.properties```
na linha 1 para ***spring.profiles.active=test*** e depois de iniciada a aplicação acessar:
http://localhost:8080/h2-console

***application-test.properties***:
```
spring.datasource.url=jdbc:h2:mem:teste_julio_db
spring.datasource.username=sa
spring.datasource.password=
```





