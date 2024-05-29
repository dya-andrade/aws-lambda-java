# aws-lambda-java
Código de exemplo para criar um Lambda na AWS com Java.

### Tecnologias utilizadas
* Java 21
* AWS Lambda Core
* AWS Lambda Events
* Jackson
* JUnit

### Tarefas

* Gerar o arquivo .jar

```bash
mvn clean package
```

* Criar a função Lambda na AWS via console AWS
  * Habilitar função URL
    * Autenticação: Nenhum
  * Configuração de tempo de execução
    * Editar o Manipulador, passando o caminho a onde está a classe Handler
    * ````tech.buildrun.lambda.Handler::handleRequest````
  * Em Code source fazer o upload do arquivo .jar gerado, que está dentro da pasta target do projeto

* Testar a função Lambda via função URL
  * Vá em configurações da função Lambda
  * Selecione a função URL e copie o link
  * E no Postman ou Insomnia, faça uma requisição ```POST``` passando o JSON de entrada
  * Exemplo de JSON de entrada:
  
```cURL
curl --location --request POST 'https://<codigo-lambda>.lambda-url.sa-east-1.on.aws/' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "admin",
"password": "123"
}'
  ```

