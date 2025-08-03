@echo off
setlocal enabledelayedexpansion

rem Gộp tất cả file .java trong src vào biến SOURCES
set SOURCES=
for /R src %%f in (*.java) do (
    set SOURCES=!SOURCES! %%f
)

rem Biên dịch toàn bộ các file .java vào thư mục 'out'
rem Đảm bảo rằng thư mục 'out' tồn tại trước khi biên dịch
if not exist out mkdir out
javac --module-path lib --add-modules javafx.controls ^
-cp "lib\postgresql-42.7.3.jar;lib\gson-2.10.1.jar" -d out %SOURCES%

rem Chạy chương trình JavaFX
rem Đảm bảo tên class chính (Main Class) là 'view.TestJavaFX'
java --module-path lib --add-modules javafx.controls ^
-cp "out;lib\postgresql-42.7.3.jar;lib\gson-2.10.1.jar" view.TestJavaFX

pause