
@echo off
SETLOCAL EnableDelayedExpansion

echo ==========================================
echo 🔥 Banking AI System: One-Click Startup 🔥
echo ==========================================

:: Use system default Java and Maven (already verified in PATH)
echo 🛠️ Checking environment...
java -version
mvn -version
python --version
node -v

:: 0. Clean Port 8000 (if AI service was left running)
echo 🧹 Cleaning up Port 8000...
for /f "tokens=5" %%a in ('netstat -aon ^| findstr :8000') do taskkill /F /PID %%a >nul 2>&1

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

:: 3. Start Frontend UI in a NEW window
echo 💻 Starting Frontend UI...
start "Banking-UI (Vite)" cmd /k "cd banking-ui && echo 📦 Installing dependencies... && npm install && echo 🚀 Starting UI (Vite)... && npm run dev"

:: 4. Wait a few seconds for services to warm up
echo ⏳ Waiting for services to initialize...
timeout /t 5 /nobreak >nul

:: 5. Start Spring Boot Backend
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
