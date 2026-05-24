# Step 1: Installation and Initialization 🚀

Welcome to the official Jorm documentation! This guide will help you prepare your environment to use the fastest and most modern ORM in the Java ecosystem.

## 1. Install the Jorm CLI

The Jorm CLI is the main tool for generating code and managing your database. Choose the method best suited for your operating system.

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

### Java Developers (SDKMAN!)
```bash
sdk install jorm
```

### Universal Installation

**On macOS or Linux (Bash/Zsh):**
If you don't use any package manager, you can install the CLI directly using our official script:

```bash
curl -sSL https://raw.githubusercontent.com/j-orm/jorm/main/install.sh | bash
```

**On Windows (PowerShell):**
For Windows developers who prefer not to use Scoop, we provide a native script:

```powershell
Invoke-WebRequest -Uri "https://raw.githubusercontent.com/j-orm/jorm/main/install.ps1" -OutFile "install.ps1"; .\install.ps1; Remove-Item install.ps1
```

**On Windows (Command Prompt / CMD):**
If you are using the classic CMD, you can delegate the execution to PowerShell with this command:

```cmd
powershell -Command "Invoke-WebRequest -Uri 'https://raw.githubusercontent.com/j-orm/jorm/main/install.ps1' -OutFile 'install.ps1'; .\install.ps1; Remove-Item install.ps1"
```

### ⚙️ Configure PATH

After the **Universal Installation**, you need to ensure that the terminal knows where to find the Jorm executable. If you installed via Homebrew or Scoop, you can skip this step.

Choose the command according to the terminal you are using:

#### On macOS or Linux (Bash/Zsh)
Add this line to your `.bashrc` or `.zshrc` file to make the change permanent:
```bash
export PATH="$HOME/.jorm/bin:$PATH"
```

#### On Windows (PowerShell)
To add to the PATH in the current session:
```powershell
$env:PATH += ";$HOME\.jorm\bin"
```

#### On Windows (Command Prompt / CMD)
To add to the PATH in the current session:
```cmd
set PATH=%PATH%;%USERPROFILE%\.jorm\bin
```

> 💡 **Note for Windows:** To make the change permanent in Windows, you can search for "Edit the system environment variables" in the Start menu and add the path `C:\Users\YourUser\.jorm\bin` to the `Path` variable.

> **Attention:** It is strictly required to have **Java 21** installed on your system.

## 2. Start a New Project

Navigate to the root of your Java project and run the initialization command.

```bash
cd my-java-project
jorm init
```

This command automatically creates a hidden folder named `.jorm` with an example `schema.jorm` file. This file is the "heart" of your database and the Java code that will be generated.

## 3. Next Steps

Now that Jorm is installed, proceed to **Step 2** to learn how to write your first schema.
