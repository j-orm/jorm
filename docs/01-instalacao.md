# Passo 1: Instalação e Inicialização 🚀

Bem-vindo à documentação oficial da Jorm! Este guia vai ajudar a preparar o seu ambiente para utilizar o ORM mais rápido e moderno do ecossistema Java.

## 1. Instalar a CLI da Jorm

A CLI da Jorm é a ferramenta principal para gerar código e gerir a sua base de dados. Escolha o método mais adequado ao seu sistema operativo.

### macOS / Linux (Homebrew)
```bash
brew tap j-orm/jorm
brew install jorm
```

### Windows (Scoop)
```powershell
scoop bucket add jorm https://github.com/j-orm/jorm
scoop install jorm
```

### Programadores Java (SDKMAN!)
```bash
sdk install jorm
```

### Instalação Universal

**No macOS ou Linux (Bash/Zsh):**
Se não usar nenhum gestor de pacotes, pode instalar a CLI diretamente usando o nosso script oficial:

```bash
curl -sSL https://raw.githubusercontent.com/j-orm/jorm/master/install.sh | bash
```

**No Windows (PowerShell):**
Para os programadores Windows que prefiram não usar o Scoop, disponibilizamos um script nativo:

```powershell
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/j-orm/jorm/master/install.ps1" -OutFile "install.ps1"; .\install.ps1; Remove-Item install.ps1
```

**No Windows (Command Prompt / CMD):**
Se estiver a usar o CMD clássico, pode delegar a execução ao PowerShell com este comando:

```cmd
powershell -Command "Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/j-orm/jorm/master/install.ps1' -OutFile 'install.ps1'; .\install.ps1; Remove-Item install.ps1"
```

### ⚙️ Configurar o PATH

Após a **Instalação Universal**, é necessário garantir que o terminal sabe onde encontrar o executável da Jorm. Se instalou via Homebrew ou Scoop, pode saltar este passo.

Escolha o comando de acordo com o terminal que está a utilizar:

#### No macOS ou Linux (Bash/Zsh)
Adicione esta linha ao seu ficheiro `.bashrc` ou `.zshrc` para que a alteração seja permanente:
```bash
export PATH="$HOME/.jorm/bin:$PATH"
```

#### No Windows (PowerShell)
Para adicionar ao PATH na sessão atual:
```powershell
$env:PATH += ";$HOME\.jorm\bin"
```

#### No Windows (Command Prompt / CMD)
Para adicionar ao PATH na sessão atual:
```cmd
set PATH=%PATH%;%USERPROFILE%\.jorm\bin
```

> 💡 **Nota para Windows:** Para tornar a alteração permanente no Windows, pode pesquisar por "Editar as variáveis de ambiente do sistema" no menu Iniciar e adicionar o caminho `C:\Users\OSeuUtilizador\.jorm\bin` à variável `Path`.

> **Atenção:** É estritamente necessário ter o **Java 21** instalado no sistema.

## 2. Iniciar um Novo Projeto

Navegue até à raiz do seu projeto Java e execute o comando de inicialização.

```bash
cd o-meu-projeto-java
jorm init
```

Este comando cria automaticamente uma pasta oculta chamada `.jorm` com um ficheiro de exemplo `schema.jorm`. Este ficheiro é o "coração" da sua base de dados e do código Java que será gerado.

## 3. Próximos Passos

Agora que a Jorm está instalada, avance para o **Passo 2** para aprender a escrever o seu primeiro schema.
