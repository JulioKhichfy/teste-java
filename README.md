## Versões Utilizadas

1. java 17.0.9
2. springboot 3.2.5

## Banco de Dados (H2 e Mysql)

As tabelas serão geradas, assim como uma carga inicial de 10 clientes, ao iniciar a aplicação.
Nome do schema: ***teste_julio_db***

### H2

Para facilitar os testes, por padrão o sistema utilizará o ***H2*** em memória, definido na linha 1 do arquivo
***application.properties*** como visto abaixo:

```01 spring.profiles.active=test```

Link para acessar o H2 (não precisa de senha): http://localhost:8080/h2-console

### MYSQL

Para utilizar o ***MYSQL*** é necessário alterar o arquivo ***application.properties*** na linha 1 como visto abaixo:

```01 spring.profiles.active=dev```

Além disso, verifique e altere, se necessário, o usuário e a senha de acesso ao seu Mysql no arquivo
***application-dev.properties***
```
spring.datasource.username=<seu_usuário>
spring.datasource.password=<sua_senha>
```

Crie também o DB de nome: ***teste_julio_db***

## Rodar a aplicação

executar o comando :
```mvn clean spring-boot:run```

## Testar a aplicação com Swagger

Acessar: http://localhost:8080/swagger-ui

Nessa interface encontraremos os endpoints para testes:

#### EndPoints
- Encontrar pedido por número de controle, ex: 130

  GET http://localhost:8080/api/pedidos/130

- Listar todos os pedidos
  
- GET http://localhost:8080/api/pedidos

- Listar todos os pedidos de uma data específica
  
- GET http://localhost:8080/api/pedidos/data/2024-01-02

- Upload do arquivo xml ou json:
  
- POST http://localhost:8080/api/pedidos/upload

Para facilitar os testes de upload de arquivo, 
pode-se utilizar os arquivos dentro da pasta ```src\test\resources\TestFiles```

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
5. ** Caso a quantidade seja maior que 5 aplicar 5% de desconto no valor total, 
6. Caso a quantidade seja maior ou igual 10 aplicar 10% de desconto no valor total.
7. O sistema deve calcular e gravar o valor total do pedido. 
8. Assumir que já existe 10 clientes cadastrados, com códigos de 1 a 10. 

### Política de desconto

A filosofia adotada para fornecer descontos de 5% ou 10% aplica-se para a quantidade de cada item específico.

Porque foi adotada essa filosofia?

Cenário:
"Um cliente efetua compra de 9 lápis e 1 televisão. 
Para evitar descontos desproporcionais em relação ao custo dos ítems dentro de um mesmo pedido,
o desconto não será fornecido no valor total do pedido, somente  terá desconto de 5% na aquisição dos lápis.
"

Contudo, isso pode ser alterado de acordo com novas regras impostas pelo dono da aplicação.







