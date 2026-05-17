class Jorm < Formula
  desc "A Schema-First Modern ORM for Java"
  homepage "https://jorm.dev"
  url "https://github.com/SEU_USUARIO/jorm/releases/download/v0.1.0/jorm-cli-standalone.jar"
  sha256 "COLOQUE_AQUI_O_SHA256_DO_JAR"
  license "MIT"

  depends_on "openjdk@21"

  def install
    libexec.install "jorm-cli-standalone.jar"
    bin.write_jar_script libexec/"jorm-cli-standalone.jar", "jorm"
  end

  test do
    system "#{bin}/jorm", "--version"
  end
end