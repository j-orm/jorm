#!/usr/bin/env bash

set -e

# Jorm Universal Installer
# Compatible with macOS and Linux

# Fetch latest version from GitHub API if not hardcoded
JORM_VERSION=$(curl -s "https://api.github.com/repos/j-orm/jorm/releases/latest" | grep -Po '"tag_name": "v\K[0-9.]+')
if [ -z "$JORM_VERSION" ]; then
    JORM_VERSION="0.1.0"
fi

DOWNLOAD_URL="https://github.com/j-orm/jorm/releases/download/v${JORM_VERSION}/jorm-cli-standalone.jar"

JORM_DIR="$HOME/.jorm"
BIN_DIR="$JORM_DIR/bin"

echo "Installing Jorm v${JORM_VERSION}..."

mkdir -p "$BIN_DIR"

# Download the JAR from GitHub Releases
echo "Downloading $DOWNLOAD_URL ..."
curl -sSL -f -o "$BIN_DIR/jorm.jar" "$DOWNLOAD_URL"

# For Windows users running in Git Bash/Cygwin, we need to translate the path
# because Java (a native Windows program) doesn't understand /c/Users/ or /root/ paths
if uname -s | grep -qi "mingw\|msys\|cygwin"; then
    NATIVE_JAR_PATH=$(cygpath -w "$BIN_DIR/jorm.jar")
else
    NATIVE_JAR_PATH="$HOME/.jorm/bin/jorm.jar"
fi

# Create the 'jorm' wrapper script for Bash
cat << EOF > "$BIN_DIR/jorm"
#!/usr/bin/env bash
java -jar "$NATIVE_JAR_PATH" "\$@"
EOF

chmod +x "$BIN_DIR/jorm"

# Create the 'jorm.cmd' wrapper script for Windows (Command Prompt / PowerShell)
cat << EOF > "$BIN_DIR/jorm.cmd"
@echo off
java -jar "$NATIVE_JAR_PATH" %*
EOF

echo ""
echo "Jorm successfully installed to $BIN_DIR/jorm"
echo ""
echo "To get started, add the Jorm bin directory to your PATH."
echo "On macOS/Linux (Bash/Zsh):"
echo "  export PATH=\"\$HOME/.jorm/bin:\$PATH\""
echo ""
echo "On Windows (PowerShell):"
echo "  \$env:PATH += \";\$HOME\.jorm\bin\""
echo ""
echo "On Windows (Command Prompt / CMD):"
echo "  set PATH=%PATH%;%USERPROFILE%\.jorm\bin"
echo ""
echo "Then run:"
echo "  jorm init"
