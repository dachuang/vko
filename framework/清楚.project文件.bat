@echo on 
color 2f 
mode con: cols=80 lines=25 
@REM
@echo 正在清理SVN文件，请稍候...... 
@rem 循环删除当前目录及子目录下所有的SVN文件 
@rem for /r . %%a in (.) do @if exist "%%a\.project" @echo "%%a\.project" 
@for /r . %%a in (.) do @if exist "%%a\.project" del  /q "%%a\.project" 
@echo 清理完毕！！！ 
@pause