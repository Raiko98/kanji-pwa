@ECHO OFF
SET DIR=%~dp0
SET MVNW_REPOURL=https://repo.maven.apache.org/maven2
java -jar "%DIR%\.mvn\wrapper\maven-wrapper.jar" %*
