@echo off

echo [INFO] ʹ��maven����pom.xml install ����war
call mvn clean install -Dmaven.test.skip=true -e -Pproduct

pause