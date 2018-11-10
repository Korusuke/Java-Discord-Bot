@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  OOPM-Discord-Bot startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and OOPM_DISCORD_BOT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\OOPM-Discord-Bot-1.0-SNAPSHOT.jar;%APP_HOME%\lib\JDA-3.8.1_438.jar;%APP_HOME%\lib\newsapi-1.0.jar;%APP_HOME%\lib\firebase-admin-6.5.0.jar;%APP_HOME%\lib\google-cloud-storage-1.43.0.jar;%APP_HOME%\lib\google-cloud-firestore-0.61.0-beta.jar;%APP_HOME%\lib\google-cloud-core-http-1.43.0.jar;%APP_HOME%\lib\google-cloud-core-grpc-1.43.0.jar;%APP_HOME%\lib\google-cloud-core-1.43.0.jar;%APP_HOME%\lib\joda-time-2.10.1.jar;%APP_HOME%\lib\themoviedbapi-1.8.jar;%APP_HOME%\lib\rt-2.3.0.jar;%APP_HOME%\lib\jaxws-rt-2.3.0.jar;%APP_HOME%\lib\jaxws-api-2.3.0.jar;%APP_HOME%\lib\jaxb-api-2.3.0.jar;%APP_HOME%\lib\streambuffer-1.5.4.jar;%APP_HOME%\lib\saaj-impl-1.4.0.jar;%APP_HOME%\lib\stax-ex-1.7.8.jar;%APP_HOME%\lib\activation-1.1.jar;%APP_HOME%\lib\jaxb-impl-2.3.0.jar;%APP_HOME%\lib\jaxb-core-2.3.0.jar;%APP_HOME%\lib\JRAW-1.1.0.jar;%APP_HOME%\lib\gax-grpc-1.30.0.jar;%APP_HOME%\lib\proto-google-cloud-firestore-v1beta1-0.26.0.jar;%APP_HOME%\lib\gax-httpjson-0.47.0.jar;%APP_HOME%\lib\gax-1.30.0.jar;%APP_HOME%\lib\proto-google-iam-v1-0.12.0.jar;%APP_HOME%\lib\api-common-1.7.0.jar;%APP_HOME%\lib\google-auth-library-oauth2-http-0.11.0.jar;%APP_HOME%\lib\google-api-client-gson-1.25.0.jar;%APP_HOME%\lib\google-api-client-1.25.0.jar;%APP_HOME%\lib\opencensus-contrib-grpc-util-0.15.0.jar;%APP_HOME%\lib\opencensus-contrib-http-util-0.15.0.jar;%APP_HOME%\lib\grpc-netty-shaded-1.13.1.jar;%APP_HOME%\lib\grpc-stub-1.13.1.jar;%APP_HOME%\lib\grpc-auth-1.13.1.jar;%APP_HOME%\lib\grpc-protobuf-1.13.1.jar;%APP_HOME%\lib\grpc-protobuf-lite-1.13.1.jar;%APP_HOME%\lib\grpc-core-1.13.1.jar;%APP_HOME%\lib\opencensus-contrib-grpc-metrics-0.12.3.jar;%APP_HOME%\lib\opencensus-api-0.15.0.jar;%APP_HOME%\lib\protobuf-java-util-3.6.0.jar;%APP_HOME%\lib\guava-23.6-jre.jar;%APP_HOME%\lib\google-http-client-jackson2-1.25.0.jar;%APP_HOME%\lib\google-oauth-client-1.25.0.jar;%APP_HOME%\lib\google-http-client-gson-1.25.0.jar;%APP_HOME%\lib\google-http-client-appengine-1.24.1.jar;%APP_HOME%\lib\google-http-client-jackson-1.24.1.jar;%APP_HOME%\lib\google-http-client-1.25.0.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\kotlin-stdlib-1.2.41.jar;%APP_HOME%\lib\annotations-16.0.1.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.7.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\nv-websocket-client-2.5.jar;%APP_HOME%\lib\okhttp-3.11.0.jar;%APP_HOME%\lib\opus-java-1.0.4.pom;%APP_HOME%\lib\commons-collections4-4.1.jar;%APP_HOME%\lib\json-20160810.jar;%APP_HOME%\lib\trove4j-3.0.3.jar;%APP_HOME%\lib\gson-2.8.2.jar;%APP_HOME%\lib\jersey-client-2.26.jar;%APP_HOME%\lib\jersey-hk2-2.26.jar;%APP_HOME%\lib\jersey-common-2.26.jar;%APP_HOME%\lib\jackson-databind-2.4.4.jar;%APP_HOME%\lib\jackson-core-2.9.6.jar;%APP_HOME%\lib\jackson-annotations-2.4.4.jar;%APP_HOME%\lib\commons-lang3-3.3.2.jar;%APP_HOME%\lib\httpclient-4.5.5.jar;%APP_HOME%\lib\commons-codec-1.10.jar;%APP_HOME%\lib\policy-2.7.2.jar;%APP_HOME%\lib\gmbal-api-only-3.1.0-b001.jar;%APP_HOME%\lib\mimepull-1.9.7.jar;%APP_HOME%\lib\woodstox-core-asl-4.4.1.jar;%APP_HOME%\lib\stax2-api-3.1.4.jar;%APP_HOME%\lib\resolver-20050927.jar;%APP_HOME%\lib\javax.xml.soap-api-1.4.0.jar;%APP_HOME%\lib\jsr181-api-1.0-MR1.jar;%APP_HOME%\lib\hk2-locator-2.5.0-b42.jar;%APP_HOME%\lib\hk2-api-2.5.0-b42.jar;%APP_HOME%\lib\hk2-utils-2.5.0-b42.jar;%APP_HOME%\lib\javax.annotation-api-1.3.jar;%APP_HOME%\lib\FastInfoset-1.2.13.jar;%APP_HOME%\lib\ha-api-3.1.9.jar;%APP_HOME%\lib\moshi-1.6.0.jar;%APP_HOME%\lib\netty-codec-http-4.1.22.Final.jar;%APP_HOME%\lib\netty-handler-4.1.22.Final.jar;%APP_HOME%\lib\netty-codec-4.1.22.Final.jar;%APP_HOME%\lib\netty-transport-4.1.22.Final.jar;%APP_HOME%\lib\okio-1.14.0.jar;%APP_HOME%\lib\opus-java-api-1.0.4.jar;%APP_HOME%\lib\opus-java-natives-1.0.4.jar;%APP_HOME%\lib\checker-compat-qual-2.0.0.jar;%APP_HOME%\lib\error_prone_annotations-2.1.3.jar;%APP_HOME%\lib\j2objc-annotations-1.1.jar;%APP_HOME%\lib\animal-sniffer-annotations-1.14.jar;%APP_HOME%\lib\javax.ws.rs-api-2.1.jar;%APP_HOME%\lib\javax.inject-2.5.0-b42.jar;%APP_HOME%\lib\osgi-resource-locator-1.0.1.jar;%APP_HOME%\lib\management-api-3.0.0-b012.jar;%APP_HOME%\lib\google-auth-library-credentials-0.11.0.jar;%APP_HOME%\lib\google-api-services-storage-v1-rev135-1.24.1.jar;%APP_HOME%\lib\auto-value-1.4.jar;%APP_HOME%\lib\netty-buffer-4.1.22.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.22.Final.jar;%APP_HOME%\lib\jna-4.4.0.jar;%APP_HOME%\lib\aopalliance-repackaged-2.5.0-b42.jar;%APP_HOME%\lib\javassist-3.22.0-CR2.jar;%APP_HOME%\lib\stax-api-1.0-2.jar;%APP_HOME%\lib\httpcore-4.4.9.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\proto-google-common-protos-1.12.0.jar;%APP_HOME%\lib\protobuf-java-3.6.0.jar;%APP_HOME%\lib\grpc-context-1.13.1.jar;%APP_HOME%\lib\threetenbp-1.3.3.jar;%APP_HOME%\lib\netty-common-4.1.22.Final.jar;%APP_HOME%\lib\javax.inject-1.jar;%APP_HOME%\lib\jackson-core-asl-1.9.11.jar

@rem Execute OOPM-Discord-Bot
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %OOPM_DISCORD_BOT_OPTS%  -classpath "%CLASSPATH%" Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable OOPM_DISCORD_BOT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%OOPM_DISCORD_BOT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
