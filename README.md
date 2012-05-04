# ant-salesforce-ci

extended deploy:ant-salesforce


## task
1. deployForCI

#### option
1. sfc.testResultFile(default:test-result.xml)



# sample

[SampleSFDCProject](https://github.com/yutagithub/ForceComInspection "sample project")

## dir-design

* /build.xml
* [lib/](https://github.com/yutagithub/ForceComInspection/tree/master/lib "lib directory") 
 * [ant-salesforce.jar](https://github.com/yutagithub/ForceComInspection/raw/master/lib/ant-salesforce.jar "ant-salesforce.jar") 
 * [ant-salesforce-ci.jar](https://github.com/yutagithub/ForceComInspection/raw/master/lib/ant-salesforce-ci_2.9.1-1.0.jar "ant-salesforce-ci_2.9.1-1.0.jar") 
 * [scala-library.jar](https://github.com/yutagithub/ForceComInspection/raw/master/lib/scala-library.jar "scala-library.jar") 
* [src/](https://github.com/yutagithub/ForceComInspection/tree/master/src "sfdc source directory")
 * classes
 * pages
 * triggers


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