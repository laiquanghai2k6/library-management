@echo off
javac --module-path lib --add-modules javafx.controls -d out src/app/Main.java src/view/TestJavaFX.java
java --module-path lib --add-modules javafx.controls -cp out app.Main
pause