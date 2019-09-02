@echo off
rem
rem ####################################################################################################################
rem *** Build the containers and the application of the project
rem --------------------------------------------------------------------------------------------------------------------
setlocal

   rem Application name
   set APP_NAME=ArtificialIntelligence
rem Container name
   set CT_NAME=maven:3.6.1-jdk-12
rem Sources location on host pc
   set HOST_SRC=%CD%

rem Build container (just pull it)
   docker pull %CT_NAME%
rem Build the application
   docker run -it --rm -v %HOST_SRC%:/app -w /app %CT_NAME% mvn clean install

endlocal