param (
    [switch]$Verbose,
    [switch]$Help
)

function Show-Usage {
    Write-Host "Usage: build.ps1 [options]"
    Write-Host ""
    Write-Host "Options:"
    Write-Host "    -Verbose    Enable verbose output"
    Write-Host "    -Help       Show this help message"
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

if ($Help) {
    Show-Usage
    exit 0
}

Log-Info "Starting compilation process"

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Log-Error "Java is not installed. Please install Java before building."
}

$mavenSwitch = if (-not $Verbose) { "-q" } else { "" }

if (-not (Test-Path ".\mvnw")) {
    Log-Error "Maven wrapper './mvnw' not found."
}

Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass
& .\mvnw compile $mavenSwitch
if ($LASTEXITCODE -ne 0) {
    Log-Error "Compilation failed"
}

Log-Info "Build completed successfully! 🚀"
