#!/bin/sh

set -e

# Jorm Universal Installer Script

echo "Downloading Jorm CLI..."

# Determine the version
JORM_VERSION="0.1.0-SNAPSHOT"
DOWNLOAD_URL="https://github.com/seu-user/jorm/releases/download/v${JORM_VERSION}/jorm-cli-standalone.jar"

# Local installation directory
JORM_DIR="$HOME/.jorm"
BIN_DIR="$JORM_DIR/bin"

mkdir -p "$BIN_DIR"

# Download the JAR (mocking it for now if no real URL exists)
# curl -sSL -f -o "$BIN_DIR/jorm.jar" "$DOWNLOAD_URL"
# As we don't have published releases yet, this script serves as a placeholder
echo "Note: This is a demo Universal installer."
echo "To test locally, compile with 'mvn clean package' in the root directory"
echo "and copy 'cli/target/jorm-cli-standalone.jar' to '$BIN_DIR/jorm.jar'."

# Create the 'jorm' wrapper script
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
