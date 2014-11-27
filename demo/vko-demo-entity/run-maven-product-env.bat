@echo off

echo [INFO] 使用maven根据pom.xml install 生成war
call mvn clean install -Dmaven.test.skip=true -e -Pproduct

pause