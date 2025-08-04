@echo off
setlocal enabledelayedexpansion

rem Gộp tất cả file .java trong src vào biến SOURCES
set SOURCES=
for /R src %%f in (*.java) do (
    set SOURCES=!SOURCES! %%f
)

rem Biên dịch toàn bộ
javac --module-path lib --add-modules javafx.controls,javafx.fxml ^
-cp "lib\postgresql-42.7.3.jar;lib\gson-2.10.1.jar" -d out %SOURCES%

xcopy /Y /S src\view\*.fxml out\view\

rem Chạy chương trình
java --module-path lib --add-modules javafx.controls,javafx.fxml ^
-cp "out;lib\postgresql-42.7.3.jar;lib\gson-2.10.1.jar" app.Main

pause