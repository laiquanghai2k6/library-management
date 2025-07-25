@echo off
REM Load .env file and set environment variables
for /f "usebackq tokens=1,2 delims==" %%A in (".env") do (
    set "%%A=%%B"
)

call run-javafx.bat
