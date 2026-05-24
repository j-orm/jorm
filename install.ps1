param(
    [string]$Version
)

if (-not $Version -or $Version.Trim() -eq "") {
    try {
        $release = Invoke-RestMethod -Uri "https://api.github.com/repos/j-orm/jorm/releases/latest"
        if ($release.tag_name -match "^v(?<v>[0-9]+\\.[0-9]+\\.[0-9]+)$") {
            $Version = $Matches["v"]
        }
    } catch {
    }
}

if (-not $Version -or $Version.Trim() -eq "") {
    $Version = "0.1.1"
}

$JormDir = Join-Path $HOME ".jorm\\bin"
New-Item -Path $JormDir -ItemType Directory -Force | Out-Null

Write-Host "Installing Jorm v$Version..."

$JarUrl = "https://github.com/j-orm/jorm/releases/download/v$Version/jorm-cli-standalone.jar"
$JarPath = Join-Path $JormDir "jorm.jar"
Invoke-WebRequest -Uri $JarUrl -OutFile $JarPath

$CmdPath = Join-Path $JormDir "jorm.cmd"
Set-Content -Path $CmdPath -Value "@echo off`r`njava -jar `"%~dp0jorm.jar`" %*"

Write-Host "`nJorm successfully installed to $CmdPath"
Write-Host "`nTo get started, add the Jorm bin directory to your PATH."
Write-Host "On PowerShell:"
Write-Host "  `$env:PATH += `";$JormDir`""
Write-Host "`nOn Command Prompt (CMD):"
Write-Host "  set PATH=%PATH%;$JormDir"
Write-Host "`nThen run:`n  jorm init"
