# Smart Banking Backend - Development Environment Installer
# This script installs all required tools for the project

Write-Host "=======================================" -ForegroundColor Cyan
Write-Host "   Smart Banking Backend Setup" -ForegroundColor Cyan
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host ""

# Check if running as Administrator
$isAdmin = ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)

if (-not $isAdmin) {
    Write-Host "❌ This script needs to run as Administrator!" -ForegroundColor Red
    Write-Host "Right-click PowerShell and select 'Run as Administrator'" -ForegroundColor Yellow
    pause
    exit
}

Write-Host "✓ Running as Administrator" -ForegroundColor Green
Write-Host ""

# Check if Chocolatey is installed
Write-Host "Checking for Chocolatey..." -ForegroundColor Yellow
$chocoInstalled = Get-Command choco -ErrorAction SilentlyContinue

if (-not $chocoInstalled) {
    Write-Host "Installing Chocolatey package manager..." -ForegroundColor Yellow
    Set-ExecutionPolicy Bypass -Scope Process -Force
    [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
    iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
    
    # Refresh environment
    $env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")
    Write-Host "✓ Chocolatey installed successfully!" -ForegroundColor Green
} else {
    Write-Host "✓ Chocolatey is already installed" -ForegroundColor Green
}

Write-Host ""
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host "Installing Development Tools..." -ForegroundColor Cyan
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host ""

# Install Java JDK 17
Write-Host "📦 Installing OpenJDK 17..." -ForegroundColor Yellow
choco install openjdk17 -y
Write-Host "✓ Java JDK 17 installed!" -ForegroundColor Green
Write-Host ""

# Install Maven
Write-Host "📦 Installing Apache Maven..." -ForegroundColor Yellow
choco install maven -y
Write-Host "✓ Maven installed!" -ForegroundColor Green
Write-Host ""

# Install MySQL
Write-Host "📦 Installing MySQL..." -ForegroundColor Yellow
choco install mysql -y
Write-Host "✓ MySQL installed!" -ForegroundColor Green
Write-Host ""

# Install Postman
Write-Host "📦 Installing Postman..." -ForegroundColor Yellow
choco install postman -y
Write-Host "✓ Postman installed!" -ForegroundColor Green
Write-Host ""

# Refresh environment variables
Write-Host "Refreshing environment variables..." -ForegroundColor Yellow
$env:Path = [System.Environment]::GetEnvironmentVariable("Path","Machine") + ";" + [System.Environment]::GetEnvironmentVariable("Path","User")

Write-Host ""
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host "Installation Complete!" -ForegroundColor Green
Write-Host "=======================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "⚠️  IMPORTANT: Close this PowerShell window and open a NEW one to use the tools!" -ForegroundColor Yellow
Write-Host ""
Write-Host "Then run these commands to verify:" -ForegroundColor Cyan
Write-Host "  java -version" -ForegroundColor White
Write-Host "  mvn -version" -ForegroundColor White
Write-Host "  mysql --version" -ForegroundColor White
Write-Host ""

Write-Host "Next steps:" -ForegroundColor Cyan
Write-Host "1. Open a NEW PowerShell window" -ForegroundColor White
Write-Host "2. Configure MySQL database:" -ForegroundColor White
Write-Host "   mysql -u root -p" -ForegroundColor Gray
Write-Host "   (default password is usually empty, just press Enter)" -ForegroundColor Gray
Write-Host "   CREATE DATABASE smart_banking;" -ForegroundColor Gray
Write-Host ""

pause
