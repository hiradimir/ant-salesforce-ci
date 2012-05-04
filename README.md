# ant-salesforce-ci

extended deploy:ant-salesforce


## task
1. deployForCI

#### option
1. sfc.testResultFile(default:test-result.xml)



# sample

<https://github.com/yutagithub/ForceComInspection>

## dir-design

* /build.xml
* lib/
 * ant-salesforce.jar
 * ant-salesforce-ci.jar
 * scala-library.jar
* src/
 * sfdc source directory


## build.xml

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


## command
run at command line
```sh
ant -lib lib -Dsfc.testResultFile=<testResultFileName> -Dsf.username=<sf.username> -Dsf.password=<password><securityToken> deployForCI
```