ant-salesforce-ci

extended deploy:ant-salesforce


ant:deployForCI
  option
   sfc.testResultFile(default:test-result.xml)


sample

build.xml

```xml
<project name="Sample usage of Salesforce-CI Ant tasks" default="deployForCI" basedir="." xmlns:sfc="antlib:com.salesforce.ci">

  <property file="build.properties" />
	<property environment="env" />

	<!-- Shows check only; never actually saves to the server -->
	<target name="deployForCI">
		<sfc:deployForCI username="${sf.username}" password="${sf.password}" serverurl="${sf.serverurl}" deployRoot="src" />
	</target>
	
</project>
```