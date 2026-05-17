class Jorm < Formula
  desc "Jorm CLI - A modern, reflection-free Java ORM"
  homepage "https://github.com/j-orm/jorm"
  url "https://github.com/j-orm/jorm/releases/download/v0.1.0/jorm-cli-standalone.jar"
  sha256 "UPDATE_WITH_ACTUAL_SHA256_ON_RELEASE"
  license "MIT"

  depends_on "openjdk@21"

  def install
    libexec.install "jorm-cli-standalone.jar"
    bin.write_jar_script libexec/"jorm-cli-standalone.jar", "jorm"
  end

  test do
    system "#{bin}/jorm", "--help"
  end
end
