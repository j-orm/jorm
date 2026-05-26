class Jorm < Formula
  desc "Jorm CLI - A modern, reflection-free Java ORM"
  homepage "https://github.com/j-orm/jorm"
  url "https://github.com/j-orm/jorm/releases/download/v0.1.4/jorm-cli-standalone.jar"
  sha256 "4cbda6d4994ab9d21ca20bc375f8eafd433411b3f814f829bcd3d59aed9082c0"
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
