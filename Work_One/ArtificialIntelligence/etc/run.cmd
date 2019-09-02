@echo off
rem
rem ####################################################################################################################
rem *** Run the project app
rem --------------------------------------------------------------------------------------------------------------------
setlocal

rem Application name
   set APP_NAME=ArtificialIntelligence
   set APP_VERSION=1.0-SNAPSHOT
   set MAIN_CLASS=br.feevale.jpe.ai.ninepc/NinePiecesPuzzleV2
rem Container name
   set CT_NAME=openjdk:12.0.2
rem Sources location on host pc
   set HOST_SRC=%CD%

rem Runs
   rem docker run -it --rm --name %APP_NAME% -v %HOST_SRC%:/app -w /app/target %CT_NAME% ls -lah
   docker run -it --rm --name %APP_NAME% -v %HOST_SRC%:/app -w /app/target %CT_NAME% java -cp %APP_NAME%-%APP_VERSION%.jar %MAIN_CLASS%

endlocal