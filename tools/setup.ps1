param (
    [switch]$Verbose,
    [switch]$SkipJavaCheck,
    [switch]$Help
)

function Show-Usage {
    Write-Host "Usage: setup.ps1 [options]"
    Write-Host ""
    Write-Host "Options:"
    Write-Host "    -Verbose        Enable verbose output"
    Write-Host "    -Help           Show this help message"
    Write-Host "    -SkipJavaCheck  Skip Java version check"
}

function Log-Info {
    param ([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Green
}

function Log-Error {
    param ([string]$Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
    Exit 1
}

function Check-Java {
    if (-not $env:JAVA_HOME) {
        Log-Error "JAVA_HOME is not set. Please set it to the path of your Java 21 installation."
    }

    $javaPath = Join-Path $env:JAVA_HOME "bin\java.exe"
    if (-not (Test-Path $javaPath)) {
        Log-Error "JAVA_HOME is set to '$env:JAVA_HOME', but no valid 'java' executable was found. Please check your Java installation."
    }

    $javaVersionOutput = & $javaPath -version 2>&1 | Select-String -Pattern '"(.*?)"' -AllMatches | ForEach-Object { $_.Matches.Groups[1].Value }
    $javaMajorVersion = $javaVersionOutput.Split('.')[0]

    if ([int]$javaMajorVersion -lt 21) {
        Log-Error "Java version $javaMajorVersion detected. Java 21 or higher is required. Ensure JAVA_HOME points to a valid Java 21 or higher installation."
    }

    Log-Info "Java $javaVersionOutput detected"
}

if ($Help) {
    Show-Usage
    exit 0
}

Log-Info "Setting up development environment"

if (-not $SkipJavaCheck) {
    Check-Java
}

$mavenSwitch = if (-not $Verbose) { "-q" } else { "" }

if (-not (Test-Path ".\mvnw")) {
    Log-Error "Maven wrapper './mvnw' not found."
}

Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
& .\mvnw clean install $mavenSwitch
if ($LASTEXITCODE -ne 0) {
    Log-Error "Dependency installation failed"
}

& .\mvnw clean $mavenSwitch
if ($LASTEXITCODE -ne 0) {
    Log-Error "Artifact clean failed"
}

Log-Info "Setup completed successfully! 🚀"
