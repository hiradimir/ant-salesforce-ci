# ant-salesforce-ci

extended deploy:ant-salesforce

Usually use empty DE edition to target for deploy.

Then you get test/coverage result.


I think to using together Jenkins.


## task
1. deployForCI

#### option
1. sfc.testResultFile (default:target/sforceci/test-result.xml)
1. sfc.coverageResultFile (default:target/sforceci/coverage.xml)
1. sfc.sobjectPlural (true:add pluralLabel, false:nothing to do, default=false)




# sample

[SampleSFDCProject](https://github.com/yutagithub/ForceComSample "sample project")

## dir-design

* /build.xml
* [lib/](https://github.com/yutagithub/ForceComSample/tree/master/lib "lib directory") 
 * ant-salesforce.jar
 * [ant-salesforce-ci.jar](https://github.com/yutagithub/ForceComSample/raw/master/lib/ant-salesforce-ci_2.9.1-1.0.jar "ant-salesforce-ci_2.9.1-1.0.jar") 
 * [scala-library.jar](https://github.com/yutagithub/ForceComSample/raw/master/lib/scala-library.jar "scala-library.jar") - this project built by scala
* [src/](https://github.com/yutagithub/ForceComSample/tree/master/src "sfdc source directory")
 * classes
 * pages
 * triggers


## build.xml

```xml
<project name="Sample usage of Salesforce-CI Ant tasks" default="deployForCI" basedir="." xmlns:sfc="antlib:com.yutagithub.sforce.ci">

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
```shell
ant -lib lib -Dsfc.sobjectPlural=true -Dsfc.testResultFile=<testResultFileName> -Dsfc.coverageResultFile=<coverageResultFileName> -Dsf.username=<sf.username> -Dsf.password=<password><securityToken> deployForCI
```

![jenkins-top.png](../../raw/master/src/test/resources/images/jenkins-top.png) 
![test-failure-top.png](../../raw/master/src/test/resources/images/test-failure-top.png) 
![test-failure-detail.png](../../raw/master/src/test/resources/images/test-failure-detail.png) 
![coverage-top.png](../../raw/master/src/test/resources/images/coverage-top.png) 
![coverage-classes.png](../../raw/master/src/test/resources/images/coverage-classes.png) 
![coverage-class-util.png](../../raw/master/src/test/resources/images/coverage-class-util.png) 

