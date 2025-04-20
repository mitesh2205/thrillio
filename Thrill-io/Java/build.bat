@echo off
cd /d "d:\Git Projects\bookmarking app\Thrill-io\Java"
rmdir /s /q build
mkdir build
set CLASSPATH=WebContent\WEB-INF\lib\*;C:\xampp\tomcat\lib\servlet-api.jar;C:\xampp\tomcat\lib\jsp-api.jar
javac --release 11 -encoding UTF-8 -d build\WEB-INF\classes thrillio\*.java thrillio\bgjobs\*.java thrillio\constants\*.java thrillio\controllers\*.java thrillio\dao\*.java thrillio\entities\*.java thrillio\managers\*.java thrillio\partner\*.java thrillio\util\*.java
xcopy /s /y WebContent\* build\
cd build
jar -cvf thrillio.war *
