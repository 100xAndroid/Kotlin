rem ************************************************
rem   Umistit do slozky Android projektu
rem   Po spusteni smaze nepotrebne (build) soubory
rem   ...a taky sam sebe
rem ************************************************

IF EXIST ".gradle" (rmdir ".gradle" /s /q)
IF EXIST ".idea" (rmdir ".idea" /s /q)
IF EXIST "build" (rmdir "build" /s /q)
IF EXIST "app/build" (rmdir "app/build" /s /q)

del smazat.bat



