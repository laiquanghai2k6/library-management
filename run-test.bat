@echo off
setlocal enabledelayedexpansion

REM --- Tạo folder output cho test ---
if not exist out-test mkdir out-test

REM --- Gộp tất cả file .java trong src và src/test ---
set SOURCES=
for /R src %%f in (*.java) do (
    set SOURCES=!SOURCES! %%f
)

REM --- Gộp tất cả .jar trong lib vào CLASSPATH ---
set CP=.
for %%f in (lib\*.jar) do (
    set CP=!CP!;%%f
)

REM --- Biên dịch tất cả ---
javac -cp "!CP!" -d out-test !SOURCES!
if %errorlevel% neq 0 (
    echo Biên dịch thất bại!
    pause
    exit /b
)

REM --- Chạy JUnit 5 test ---
java -cp "out-test;lib\*" org.junit.platform.console.ConsoleLauncher --scan-class-path out-test

pause
