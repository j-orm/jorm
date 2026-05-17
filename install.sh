#!/bin/sh

set -e

# Jorm Universal Installer Script

echo "Downloading Jorm CLI..."

# Determinar a versão (pode ser hardcoded para MVP ou usar tags do GitHub Releases futuramente)
JORM_VERSION="0.1.0-SNAPSHOT"
DOWNLOAD_URL="https://github.com/seu-user/jorm/releases/download/v${JORM_VERSION}/jorm-cli-standalone.jar"

# Diretório de instalação local
JORM_DIR="$HOME/.jorm"
BIN_DIR="$JORM_DIR/bin"

mkdir -p "$BIN_DIR"

# Fazer o download do JAR (Por agora criamos um mock se não houver URL real)
# curl -sSL -f -o "$BIN_DIR/jorm.jar" "$DOWNLOAD_URL"
# Como ainda não temos releases publicadas, este script servirá como placeholder
echo "Nota: Isto é um instalador Universal de demonstração."
echo "Para testar localmente, compile com 'mvn clean package' no diretório raiz"
echo "e copie 'cli/target/jorm-cli-standalone.jar' para '$BIN_DIR/jorm.jar'."

# Criar o script 'jorm'
cat << 'EOF' > "$BIN_DIR/jorm"
#!/bin/sh
exec java -jar "$HOME/.jorm/bin/jorm.jar" "$@"
EOF

chmod +x "$BIN_DIR/jorm"

echo ""
echo "Jorm successfully installed to $BIN_DIR/jorm"
echo ""
echo "To get started, add the Jorm bin directory to your PATH:"
echo "  export PATH=\"\$HOME/.jorm/bin:\$PATH\""
echo ""
echo "Then run:"
echo "  jorm init"
