ant-salesforce-ci

extended deploy:ant-salesforce


task:deployForCI
  option
   sfc.testResultFile(default:test-result.xml)


dir-design
  lib/
    - ant-salesforce.jar
    - ant-salesforce-ci.jar
    - scala-library.jar
  src/
    - sfdc source directory
   

sample

build.xml

```xml
<project name="Sample usage of Salesforce-CI Ant tasks" default="deployForCI" basedir="." xmlns:sfc="antlib:com.salesforce.ci">

  <property file="build.properties" />
	<property environment="env" />

	<!-- Build / TestResult never actually saves to the server -->
	<target name="deployForCI">
		<sfc:deployForCI username="${sf.username}" password="${sf.password}" serverurl="${sf.serverurl}" deployRoot="src" />
	</target>
	
</project>
```


run at command line
```sh
ant -lib lib -Dsfc.testResultFile=<testResultFileName> -Dsf.username=<sf.username> -Dsf.password=<password><securityToken> deployForCI
```