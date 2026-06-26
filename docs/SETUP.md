# Setup (Desenvolvimento)

Pré-requisitos:

- Java 17+
- Maven
- Banco de dados compatível (ex.: SQL Server) configurado localmente

Passos rápidos:

1. Copie `src/main/resources/application-dev.properties` e ajuste as credenciais do banco.
2. Build:

```bash
mvn clean package
```

3. Rodar em modo dev:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

4. Endpoints protegidos exigem cabeçalho `Authorization: Bearer <token>` (veja `docs/API.md`).

Arquivos de configuração:

- `src/main/resources/application.properties` — configurações gerais
- `application-dev.properties` — configurações para desenvolvimento
- `application-test.properties` — configurações para testes
