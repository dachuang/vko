@echo on 
color 2f 
mode con: cols=80 lines=25 
@REM
@echo ��������SVN�ļ������Ժ�...... 
@rem ѭ��ɾ����ǰĿ¼����Ŀ¼�����е�SVN�ļ� 
@rem for /r . %%a in (.) do @if exist "%%a\.project" @echo "%%a\.project" 
@for /r . %%a in (.) do @if exist "%%a\.project" del  /q "%%a\.project" 
@echo ������ϣ����� 
@pause