@REM Maven Wrapper Script for Windows
@echo off
setlocal
set MAVEN_WRAPPER_JAR=%~dp0.mvn\wrapper\maven-wrapper.jar
set MAVEN_WRAPPER_PROPERTIES=%~dp0.mvn\wrapper\maven-wrapper.properties

for /F "usebackq tokens=1,2 delims==" %%a in ("%MAVEN_WRAPPER_PROPERTIES%") do (
    if "%%a"=="distributionUrl" set DISTRIBUTION_URL=%%b
)

set MAVEN_USER_HOME=%USERPROFILE%\.m2\wrapper
for /F "tokens=*" %%i in ("%DISTRIBUTION_URL%") do set DIST_URL=%%i
set DIST_URL=%DIST_URL: =%

set MVN_CMD=%MAVEN_USER_HOME%\dists\apache-maven-3.9.6\bin\mvn.cmd
if exist "%MVN_CMD%" goto runMaven

echo Downloading Maven 3.9.6...
if not exist "%MAVEN_USER_HOME%\dists" mkdir "%MAVEN_USER_HOME%\dists"
set ZIP_FILE=%MAVEN_USER_HOME%\dists\apache-maven-3.9.6-bin.zip
powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol=[Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%ZIP_FILE%'}"
powershell -Command "Expand-Archive -LiteralPath '%ZIP_FILE%' -DestinationPath '%MAVEN_USER_HOME%\dists' -Force"
del "%ZIP_FILE%"

:runMaven
"%MVN_CMD%" %*
endlocal
