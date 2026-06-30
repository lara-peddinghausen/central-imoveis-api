# Central de Imóveis - API REST (Backend)

## Sobre o Projeto
Este repositório contém a infraestrutura de Backend do **Sistema de Administração de Imóveis para Locação**, um aplicativo focado em centralizar informações contratuais, financeiras e operacionais para apoiar a gestão eficiente de locações residenciais e por temporada.

O projeto foi concebido para resolver os gargalos de gerenciamento manual em planilhas eletrônicas, mitigando riscos de perda de prazos de contratos e furos no controle financeiro.

> ⚠️ **Status do Projeto:** 🚧 Em Desenvolvimento

## Tecnologias e Ferramentas Utilizadas
* **Framework Principal:** Spring Boot (Java)
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Banco de Dados:** SQLServer (Ambiente de desenvolvimento local)
* **Segurança e Autenticação:** Spring Security e JWT (JSON Web Tokens)
* **Validação de Dados:** Jakarta Validation (Hibernate Validator)
* **Gerenciador de Dependências:** Maven

## Instalação e execução

### Pré-requisitos

- Node.js
- Android Studio
- SQL Server
- Expo CLI

  ```bash
  npm install -g expo-cli
  ```

- Um dispositivo virtual Android (AVD) ou o aplicativo Expo Go.

### Configuração

Antes de executar o projeto, realize as seguintes configurações:

1. Crie um banco de dados chamado **`central_imoveis`** no SQL Server:
   ```
   CREATE DATABASE central_imoveis; 
   GO
   ```
   - As tabelas serão criadas automaticamente pelo backend na primeira execução.

2. Configure a conexão com o banco de dados.
   - No backend, atualize a URL, a porta e as credenciais do SQL Server no arquivo **`application-dev.properties`**.

3. Configure o diretório para upload das imagens dos imóveis.
   - Crie uma pasta em seu computador para armazenar as imagens.
   - Atualize o caminho dessa pasta nos seguintes arquivos:
     - **`CorsConfig.java`** (linha 29);
     - **`ImovelController.java`**, no método **`cadastrar()`** (linha 57);
     - **`ImovelController.java`**, no método **`atualizar()`** (linha 145).

4. Para cadastrar imóveis com fotos utilizando o emulador Android, é necessário que existam imagens salvas na galeria do emulador.

### Como rodar o projeto

1. Clone os repositórios [central-imoveis-api](https://github.com/lara-peddinghausen/central-imoveis-api) e [central-imoveis-mobile](https://github.com/lara-peddinghausen/central-imoveis-mobile).

2. Abra ambos os projetos na IDE de sua preferência.

3. No diretório do projeto **`central-imoveis-mobile`**, abra um terminal e instale as dependências:

   ```bash
   npm install
   ```

4. No projeto **`central-imoveis-api`**, execute a classe **`ApiApplication.java`**.

   > **Importante:** mantenha o backend (Spring Boot) em execução durante os testes do aplicativo.

5. Abra o **Android Studio** e inicie um dispositivo virtual (Android Virtual Device – AVD).

6. No terminal do diretório **`central-imoveis-mobile`**, execute:

   ```bash
   npx expo start
   ```

7. Com o Expo iniciado, pressione a tecla **`a`** no terminal (ou clique em **Run on Android device/emulator**) para abrir o aplicativo no emulador Android.

## Documentação
Mais detalhes sobre instalação e a API estão em `docs/`.
Veja `docs/API.md` para a referência atualizada dos endpoints, `docs/API-mapeamento.md` para o fluxo do app e o mapeamento com os controllers da API, e `docs/OVERVIEW.md` para um resumo rápido.

