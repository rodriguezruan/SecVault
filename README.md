# 🔐 CryptoVault

> Gerenciador de senhas com criptografia AES-256 de ponta a ponta, desenvolvido em Java.

---

## Sobre o projeto

CryptoVault é uma aplicação CLI (linha de comando) que armazena credenciais de forma segura. Todas as senhas são criptografadas com AES-256-GCM antes de serem salvas em disco — nem mesmo quem tiver acesso ao arquivo consegue ler os dados sem a senha mestra.

O projeto foi desenvolvido do zero com foco em boas práticas de segurança criptográfica, aplicando os mesmos princípios usados em aplicações reais de proteção de dados.

---

## Funcionalidades

-  Adição de credenciais (serviço, usuário e senha)
-  Listagem de todas as credenciais
-  Busca por nome de serviço (case-insensitive)
-  Persistência em arquivo `.vault` criptografado
-  Carregamento automático do cofre ao iniciar

---

## Segurança

Este projeto aplica técnicas reais de criptografia:

| Mecanismo | Detalhe |
|---|---|
| **Algoritmo** | AES-256 no modo GCM (autenticado) |
| **Derivação de chave** | PBKDF2WithHmacSHA256 com 310.000 iterações |
| **Salt** | 16 bytes gerados com `SecureRandom` por operação |
| **IV** | 12 bytes gerados com `SecureRandom` por operação |
| **Formato do cofre** | `salt + IV + dados cifrados` codificados em Base64 |

O modo GCM garante não apenas confidencialidade, mas também **integridade** — qualquer adulteração no arquivo é detectada na descriptografia.

---

## Tecnologias

- **Java 21**
- **Maven**
- **Gson 2.10.1** — serialização JSON
- `javax.crypto` — criptografia AES/GCM
- `java.security` — PBKDF2, SecureRandom

---

## Estrutura do projeto

```
src/main/java/com/secproject/
├── Main.java               # Ponto de entrada
├── menu/
│   └── Menu.java           # Interface CLI e navegação
├── model/
│   └── Credential.java     # Modelo de dados
├── crypto/
│   └── CryptoService.java  # Criptografia AES-256-GCM
└── repository/
    └── VaultRepository.java # Persistência em arquivo .vault
```

---

## Como executar

**Pré-requisitos:** Java 21+ e Maven instalados.

```bash
# Clone o repositorio
git clone https://github.com/rodriguezruan/SecVault.git
cd SecVault

# Compila ele, senao nao funfa
mvn compile

# Depois executa :)
mvn exec:java -Dexec.mainClass="com.secproject.Main"
```

Ao iniciar, o programa solicita uma senha mestra. Na primeira execução, um novo cofre é criado. Nas execuções seguintes, o cofre existente é carregado e descriptografado com a senha fornecida.

---

## Como funciona a criptografia

```
Senha mestra + Salt aleatório
        ↓ PBKDF2 (310.000 iterações)
     Chave AES-256
        ↓ AES/GCM + IV aleatório
   Dados criptografados
        ↓ Base64
  Arquivo cofre.vault
```

A chave nunca é armazenada. Ela é derivada da senha mestra a cada execução — se a senha estiver errada, a descriptografia falha.

---

## Roadmap

- [x] Prineira Sprint — Estrutura base e CLI
- [x] Segunda Sprint — Criptografia AES-256-GCM
- [x] Terceira Sprint — Persistência em arquivo `.vault`
- [ ] Quarta Sprint — Hash da senha mestra com Argon2
- [ ] Quinta Sprint — Gerador de senhas seguras + auditoria

---

## Autor

**Ruan Rodrigues**  
[github.com/rodriguezruan](https://github.com/rodriguezruan)