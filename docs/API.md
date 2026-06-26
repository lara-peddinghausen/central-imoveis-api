# API — Endpoints Principais

Autenticação:

- `POST /auth/login` — recebe `LoginRequest` (email, senha) e retorna `AuthResponse` com `token` JWT, email, role, id, nome, cpf e dataNascimento.

Cabeçalhos:

- Para chamadas protegidas enviar: `Authorization: Bearer <token>`

Endpoints por recurso:

- Administrador (`/administrador`)
  - `POST /administrador` — `DadosCadastroAdministrador` → 201 Created
  - `GET /administrador/perfil` — retorna o perfil do administrador autenticado
  - `GET /administrador` — paginação → `Page<DadosListagemAdministrador>`
  - `PUT /administrador` — `DadosAtualizaAdministrador` → 200 OK
  - `DELETE /administrador/{id}` — 204 No Content
  - `DELETE /administrador/perfil` — inativa o administrador logado (exclusão lógica)

- Pessoa (`/pessoa`)
  - `POST /pessoa` — `DadosCadastroPessoa` → 201 Created
  - `GET /pessoa` — paginação → `Page<DadosListagemPessoa>`
  - `GET /pessoa/{id}` — retorna pessoa ou 404
  - `PUT /pessoa` — `DadosAtualizaPessoa` → 200 OK
  - `DELETE /pessoa/{id}` — 204 No Content

- Proprietário (`/proprietario`)
  - `POST /proprietario` — `DadosCadastroProprietario` → 201 Created ou 400 se CPF duplicado
  - `GET /proprietario` — paginação → `Page<DadosListagemProprietario>`
  - `GET /proprietario/{id}` — retorna proprietário ou 404
  - `PUT /proprietario/{id}` — `DadosAtualizaProprietario` → 200 OK
  - `DELETE /proprietario/{id}` — 204 No Content

- Imóvel (`/imovel`)
  - `POST /imovel` — `DadosCadastroImovel` (multipart/form-data, aceita `foto`) → 201 Created
  - `GET /imovel` — paginação → `Page<DadosListagemImovel>`; opcional `?administradorId=` para filtrar
  - `GET /imovel/{id}` — detalha imóvel ou 404
  - `POST /imovel/vincular-proprietario` — `DadosAtualizaImovel` (vínculo entre imóvel e proprietário)
  - `POST /imovel/atualizar/{id}` — atualiza imóvel via multipart/form-data (aceita `foto`)
  - `DELETE /imovel/{id}` — 204 No Content

- Locação (`/locacao`)
  - `POST /locacao` — `DadosCadastroLocacao` → 201 Created
  - `GET /locacao` — paginação → `Page<DadosListagemLocacao>`
  - `GET /locacao/{id}` — retorna locação por id ou 404
  - `GET /locacao/imovel/{imovelId}` — retorna locação ativa para imóvel
  - `GET /locacao/historico/{imovelId}` — retorna histórico de locações de um imóvel
  - `PUT /locacao` — `DadosAtualizaLocacao` → 200 OK
  - `PUT /locacao/{id}/cancelar` — cancela a locação e libera o imóvel
  - `DELETE /locacao/{id}` — 204 No Content

- Financeiro (`/financeiro`)
  - `POST /financeiro` — `DadosCadastroFinanceiro` → 201 Created
  - `GET /financeiro` — paginação → `Page<DadosListagemFinanceiro>`
  - `PUT /financeiro` — `DadosAtualizaFinanceiro` → 200 OK
  - `DELETE /financeiro/{id}` — 204 No Content
  - `POST /financeiro/movimentacao` — registra movimentação financeira
  - `GET /financeiro/{id}/financeiro/fluxo?visualizacao=mensal` — retorna `DadosFluxoFinanceiro` para gráficos

- Home (`/api`)
  - `GET /api/home` — endpoint protegido de teste (retorna mensagem e email do token)

Exemplo de uso com `curl` (login + chamada protegida):

```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","senha":"senha"}'

# Usar o token retornado nas próximas requests
curl http://localhost:8080/api/home -H "Authorization: Bearer <token>"
```

Observações:

- Alguns endpoints aceitam `multipart/form-data` (upload de fotos) — ver `ImovelController`.
- CORS está habilitado em `FinanceiroController` com `@CrossOrigin(origins = "*")`.
- As rotas retornam DTOs (ex.: `DadosListagem*`, `DadosCadastro*`) definidos em `src/main/java/com/centraldeimoveis/api/dto`.
