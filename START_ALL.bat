@echo off
SETLOCAL EnableDelayedExpansion

echo ==========================================
echo 🔥 Banking AI System: One-Click Startup 🔥
echo ==========================================

:: 1. Check for Python
python --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Python is not installed or not in PATH.
    echo Please install Python to run the AI service.
    pause
    exit /b
)

:: 2. Start Python AI Service in a NEW window
echo 🧠 Starting Python AI Microservice...
start "AI-Service (FastAPI)" cmd /k "cd ml-service && echo 📦 Installing dependencies... && pip install -r requirements.txt && echo 🚀 Starting FastAPI... && python main.py"

:: 3. Wait a few seconds for Python to warm up
echo ⏳ Waiting for AI Service to initialize...
timeout /t 5 /nobreak >nul

:: 4. Start Spring Boot Backend
echo ☕ Starting Spring Boot Backend...
echo (In this window)

:: Check if Maven wrapper exists, otherwise use 'mvn'
if exist "mvnw.cmd" (
    call mvnw.cmd spring-boot:run
) else (
    mvn spring-boot:run
)

if %errorlevel% neq 0 (
    echo ❌ Failed to start Spring Boot. 
    echo Make sure you have Maven installed or run it from your IDE.
)

pause
