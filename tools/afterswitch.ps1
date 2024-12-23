param (
    [switch]$Setup,
    [switch]$Build,
    [switch]$Verbose,
    [switch]$Help
)

function Show-Usage {
    Write-Host "Usage: afterswitch.ps1 [options]"
    Write-Host ""
    Write-Host "Options:"
    Write-Host "    -Setup      Run Setup"
    Write-Host "    -Build      Run Build"
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

if (-not $Setup -and -not $Build) {
    $Setup = $true
    $Build = $true
}

$scriptRoot = Split-Path -Parent $MyInvocation.MyCommand.Path

if ($Setup) {
    Log-Info "Running Setup..."
    & "$scriptRoot\setup.ps1" @($Verbose) 
    if ($LASTEXITCODE -ne 0) {
        Log-Error "Setup failed."
    }
}

if ($Build) {
    Log-Info "Running Build..."
    & "$scriptRoot\build.ps1" @($Verbose) 
    if ($LASTEXITCODE -ne 0) {
        Log-Error "Build failed."
    }
}

Log-Info "Afterswitch completed successfully!"
