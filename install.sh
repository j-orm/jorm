#!/usr/bin/env bash

set -e

# Jorm Universal Installer
# Compatible with macOS and Linux

# Fetch latest version from GitHub API if not hardcoded
JORM_VERSION=$(curl -s "https://api.github.com/repos/seu-user/jorm/releases/latest" | grep -Po '"tag_name": "v\K[0-9.]+')
if [ -z "$JORM_VERSION" ]; then
    JORM_VERSION="0.1.0"
fi

DOWNLOAD_URL="https://github.com/seu-user/jorm/releases/download/v${JORM_VERSION}/jorm-cli-standalone.jar"

JORM_DIR="$HOME/.jorm"
BIN_DIR="$JORM_DIR/bin"

echo "Installing Jorm v${JORM_VERSION}..."

mkdir -p "$BIN_DIR"

# Download the JAR from GitHub Releases
echo "Downloading $DOWNLOAD_URL ..."
curl -sSL -f -o "$BIN_DIR/jorm.jar" "$DOWNLOAD_URL"

# Create the 'jorm' wrapper script
cat << 'EOF' > "$BIN_DIR/jorm"
#!/usr/bin/env bash
java -jar "$HOME/.jorm/bin/jorm.jar" "$@"
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
