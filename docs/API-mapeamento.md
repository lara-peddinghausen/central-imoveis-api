# Endpoints e Mapeamento com o App

## Configuração geral

- Base URL do backend: `http://10.0.2.2:8080`
- Autenticação: token JWT enviado em `Authorization: Bearer <token>`
- Endpoints Spring Boot implementados em `src/main/java/com/centraldeimoveis/api/controller`
- A API usa DTOs em `src/main/java/com/centraldeimoveis/api/dto`

---

## 1. Autenticação e cadastro

### Tela de login
- Arquivo mobile: `app/login.js`
- Endpoint backend: 
 - `POST /auth/login`
    - Controller: `src/main/java/com/centraldeimoveis/api/controller/AuthController.java`
    - Método: `login(@Valid @RequestBody LoginRequest req)`
- Payload: `{ email, senha }`
- Retorno: `AuthResponse` com `token`, `email`, `role`, `id`, `nome`, `cpf`, `dataNascimento`

### Tela de cadastro de administrador
- Arquivo mobile: `app/cadastrar-administrador.js`
- Endpoint backend: 
  - `POST /administrador`
    - Controller: `src/main/java/com/centraldeimoveis/api/controller/AdministradorController.java`
    - Método: `cadastrar(@RequestBody DadosCadastroAdministrador dados, UriComponentsBuilder uriBuilder)`
- Payload: `{ nome, email, dataNascimento, cpf, senha }`

---

## 2. Home e perfil

### Tela inicial (Home)
- Arquivo mobile: `app/(tabs)/home.js`
- Endpoints backend:
  - `GET /administrador/perfil`
    - Controller: `AdministradorController`
    - Método: `obterPerfilLogado(Authentication auth)`
  - `GET /imovel` ou `GET /imovel?administradorId={id}`
    - Controller: `ImovelController`
    - Método: `listar(Pageable paginacao, @RequestParam(required = false) Long administradorId)`
- Responsabilidades:
  - Busca perfil do administrador autenticado via JWT
  - Lista imóveis, opcionalmente filtrando por `administradorId`
  - Permite calcular totais de imóveis disponíveis e alugados a partir dos resultados

### Tela de perfil
- Arquivo mobile: `app/(tabs)/perfil.js`
- Endpoints backend:
  - `DELETE /administrador/perfil`
    - Controller: `AdministradorController`
    - Método: `excluirPerfilLogado(Authentication auth)`
  - `PUT /administrador`
    - Controller: `AdministradorController`
    - Método: `atualizar(@RequestBody DadosAtualizaAdministrador dados)`
- Responsabilidades:
  - Excluir ou desativar o administrador autenticado
  - Atualizar dados de perfil do administrador

---

## 3. Cadastro e edição de imóveis

### Tela de cadastro de imóvel
- Arquivo mobile: `app/(tabs)/cadastrar-imovel.js`
- Endpoints externos:
  - `GET https://viacep.com.br/ws/{cep}/json`
- Endpoint backend:
  - `POST /imovel`
    - Controller: `ImovelController`
    - Método: `cadastrar(@ModelAttribute @Valid DadosCadastroImovel dados, @RequestParam(value = "foto", required = false) MultipartFile arquivoFoto)`
- Payload enviado para `/imovel` (via `multipart/form-data`):
  - `nome`, `rua`, `cep`, `numero`, `complemento`, `bairro`, `cidade`, `estado`, `tipoLocacao`, `status`, `administrador`, `foto` (opcional)
- Observação: o backend salva imagem em disco e define `fotoUrl` no imóvel

### Tela de detalhes do imóvel
- Arquivo mobile: `app/(tabs)/imovel/[id].js`
- Endpoints backend:
  - `GET /imovel/{id}`
    - Controller: `ImovelController`
    - Método: `detalhar(@PathVariable Integer id)`
  - `GET /locacao/imovel/{id}`
    - Controller: `LocacaoController`
    - Método: `buscarPorImovel(@PathVariable Integer imovelId)`
- Responsabilidades:
  - Carregar dados completos do imóvel
  - Buscar locação ativa vinculada ao imóvel, se existir

### Tela de edição de imóvel
- Arquivo mobile: `app/(tabs)/imovel/editar-imovel.js`
- Endpoints backend:
  - `GET /imovel/{id}`
    - Controller: `ImovelController`
    - Método: `detalhar(@PathVariable Integer id)`
  - `POST /imovel/atualizar/{id}`
    - Controller: `ImovelController`
    - Método: `atualizar(@PathVariable Integer id, @ModelAttribute @Valid DadosAtualizaImovel dados, @RequestParam(value = "foto", required = false) MultipartFile arquivoFoto)`
- Observações:
  - Usa `multipart/form-data`
  - Pode atualizar dados e foto do imóvel

---

## 4. Locações

### Tela de cadastro de locação
- Arquivo mobile: `app/(tabs)/locacao/cadastrar-locacao.js`
- Endpoint backend: 
  - `POST /locacao`
    - Controller: `LocacaoController`
    - Método: `cadastrar(@RequestBody @Valid DadosCadastroLocacao dados)`
- Payload: `{ status, dataInicio, dataTermino, aluguel, observacao, imovel }`
- Responsabilidade:
  - Criar locação ligada a um imóvel
  - Fluxo pode encaminhar para cadastro de inquilino após criar contrato

### Tela de detalhes da locação
- Arquivo mobile: `app/(tabs)/locacao/detalhes-locacao.js`
- Endpoint backend: 
  - `GET /locacao/{id}`
    - Controller: `LocacaoController`
    - Método: `buscarPorId(@PathVariable Integer id)`

### Tela de edição de locação
- Arquivo mobile: `app/(tabs)/locacao/editar-locacao.js`
- Endpoints backend:
  - `GET /locacao/{id}`
    - Controller: `LocacaoController`
    - Método: `buscarPorId(@PathVariable Integer id)`
  - `PUT /locacao`
    - Controller: `LocacaoController`
    - Método: `atualizar(@RequestBody @Valid DadosAtualizaLocacao dados)`
- Responsabilidade: editar dados do contrato e salvar no backend

### Tela de histórico de locações
- Arquivo mobile: `app/(tabs)/locacao/historico-locacao.js`
- Endpoints backend:
  - `GET /locacao`
    - Controller: `LocacaoController`
    - Método: `listarPorPagina(Pageable paginacao)`
  - `GET /locacao/historico/{imovelId}`
    - Controller: `LocacaoController`
    - Método: `buscarHistoricoPorImovel(@PathVariable Integer imovelId)`
- Responsabilidade: listar histórico de contratos e filtrar por imóvel

### Cancelamento de contrato
- Arquivo mobile: `src/components/DadosLocacaoItem/index.js`
- Endpoint backend: 
  - `PUT /locacao/{id}/cancelar`
    - Controller: `LocacaoController`
    - Método: `cancelarLocacao(@PathVariable Integer id)`
- Responsabilidade: encerrar locação ativa e liberar imóvel

---

## 5. Inquilinos e proprietários

### Tela de cadastro de inquilino
- Arquivo mobile: `app/(tabs)/inquilino/cadastrar-inquilino.js`
- Endpoints backend:
  - `POST /pessoa`
    - Controller: `PessoaController`
    - Método: `cadastrar(@RequestBody @Valid DadosCadastroPessoa dados)`
  - `PUT /locacao`
    - Controller: `LocacaoController`
    - Método: `atualizar(@RequestBody @Valid DadosAtualizaLocacao dados)`
- Fluxo:
  1. Cria pessoa/inquilino no backend
  2. Vincula inquilino à locação ao atualizar o contrato

### Tela de edição de inquilino
- Arquivo mobile: `app/(tabs)/inquilino/editar-inquilino.js`
- Endpoints backend:
  - `GET /pessoa/{id}`
    - Controller: `PessoaController`
    - Método: `buscarPorId(@PathVariable Integer id)`
  - `PUT /pessoa`
    - Controller: `PessoaController`
    - Método: `atualizar(@RequestBody DadosAtualizaPessoa dados)`

### Tela de cadastro de proprietário
- Arquivo mobile: `app/(tabs)/proprietario/cadastrar-proprietario.js`
- Endpoints backend:
  - `POST /proprietario`
    - Controller: `ProprietarioController`
    - Método: `cadastrar(@RequestBody @Valid DadosCadastroProprietario dados)`
  - `GET /imovel/{id}`
    - Controller: `ImovelController.detalhar(...)`
  - `POST /imovel/vincular-proprietario`
    - Controller: `ImovelController`
    - Método: `vincularProprietario(@RequestBody DadosAtualizaImovel dados)`

### Tela de edição de proprietário
- Arquivo mobile: `app/(tabs)/proprietario/editar-proprietario.js`
- Endpoints backend:
  - `GET /proprietario/{id}`
    - Controller: `ProprietarioController`
    - Método: `buscarPorId(@PathVariable Integer id)`
  - `PUT /proprietario/{id}`
    - Controller: `ProprietarioController`
    - Método: `atualizar(@PathVariable Integer id, @RequestBody @Valid DadosAtualizaProprietario dados)`

---

## 6. Observações importantes

- O token JWT é validado pelo Spring Security e extraído no backend via `Authentication auth`.
- O endpoint `GET /administrador/perfil` retorna os dados do administrador logado.
- O app usa `multipart/form-data` para cadastro/atualização de imóvel e envio de foto (`ImovelController`).
- A consulta de CEP via ViaCEP não é um endpoint da API; é uma requisição externa feita pelo app.
- Há também `GET /api/home` em `HomeController` como verificação de token válida.
