# CabalEmulator

Reimplementação em **Java** do servidor de autenticação (Auth Server) do MMORPG **Cabal Online**, feita para fins de estudo do protocolo de rede e engenharia reversa do jogo.

> ⚠️ Projeto educacional, sem fins comerciais. Não possui qualquer vínculo com a ESTsoft ou demais detentores dos direitos de Cabal Online.

## 📖 Sobre o projeto

O CabalEmulator implementa, do zero, a camada de comunicação entre cliente e servidor usada pelo Auth Server de Cabal Online: criptografia proprietária, handshake de chaves, montagem/leitura de pacotes binários e o roteamento de opcodes para os respectivos handlers.

O servidor aceita conexões TCP e trata cada cliente conectado em sua própria thread.

## ✨ Funcionalidades implementadas

- **Servidor TCP multithread** — uma thread dedicada por sessão de cliente (`ClientSession`).
- **Criptografia EP8** — implementação própria de chave e criptografia (`EP8Key`, `EP8KeyFactory`, `EP8KeyGenerator`, `CabalEncryptor`).
- **Handshake RSA** — troca de chave pública entre cliente e servidor.
- **Sistema de pacotes** — construção e serialização de pacotes (`Packet`, `Header`, `Serializer`, `PacketBuilder`).
- **Handlers via Chain of Responsibility** — roteamento de opcodes recebidos do cliente:
  - `CheckVersionHandler` — verificação de versão do cliente
  - `GetCaptchaHandler` / `VerifyCaptcha2SvrHandler` — fluxo de captcha
  - `AuthAccountHandler` / `AuthHandler` — autenticação de conta
  - `PublicKey2SvrHandler` — troca de chave pública
  - `Connect2SvrHandler` — conexão com o servidor de mundo
  - `WarEntryStatus2SvrHandler` — status de entrada em guerra

## 🛠️ Tecnologias

- **Java** (Sockets puros, sem frameworks de rede)
- **Gradle** — build e gerenciamento de dependências
- **JUnit 5** — testes (dependência configurada)

## 📁 Estrutura do projeto

```
CabalEmulator/
├── src/main/java/cabal/
│   ├── Main.java              # Ponto de entrada
│   ├── AuthServer.java        # Servidor TCP (porta 38101)
│   ├── ClientSession.java     # Sessão/thread de cada cliente
│   ├── AuthState.java
│   ├── captcha/               # Leitura de captcha
│   ├── core/                  # Serialização
│   ├── crypt/                 # Criptografia EP8 / RSA
│   ├── packet/
│   │   ├── builder/           # Montagem de pacotes
│   │   ├── handler/           # Handlers de opcode + chain
│   │   └── payload/           # Payloads client → server e server → client
│   └── types/                 # Tipos numéricos com suporte a byte swap (UInt8/16/32/64)
├── build.gradle
└── settings.gradle
```

## 🚀 Como rodar

Pré-requisitos: **JDK 8+** e **Gradle** (ou use o wrapper incluso).

```bash
git clone https://github.com/LeandroRogalaBritez/CabalEmulator.git
cd CabalEmulator
./gradlew build
./gradlew run
```

Por padrão o servidor sobe na porta **38101**, exibindo no console os IPs de clientes conectados.

## 📌 Status

Auth Server funcional para o fluxo básico de handshake, captcha e autenticação. Servidores de mundo/jogo ainda não implementados.
