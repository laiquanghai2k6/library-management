@echo off
setlocal enabledelayedexpansion

rem Gộp tất cả file .java trong src vào biến SOURCES
set SOURCES=
for /R src %%f in (*.java) do (
    set SOURCES=!SOURCES! %%f
)

rem Biên dịch toàn bộ
javac --module-path lib --add-modules javafx.controls -cp lib\postgresql-42.7.3.jar -d out %SOURCES%

rem Chạy chương trình
java -Djava.net.preferIPv4Stack=true --module-path lib --add-modules javafx.controls -cp "out;lib\postgresql-42.7.3.jar" app.Main
pause
