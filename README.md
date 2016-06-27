## Current target for ant-salesforce version 36
## But not working

# ant-salesforce-ci

[![CircleCI](https://circleci.com/gh/hiradimir/ant-salesforce-ci.svg?style=svg)](https://circleci.com/gh/hiradimir/ant-salesforce-ci)

extended deploy:ant-salesforce

Usually use empty DE edition to target for deploy.

Then you get test/coverage result.


I think to using together Jenkins.


## How to build


### build

> $ sbt assembly


## How to create IDE settings 

> $ sbt eclipse


## task
1. deployForCI

#### option
1. sfc.testResultFile (default:target/sforceci/test-result.xml)
1. sfc.coverageResultFile (default:target/sforceci/coverage.xml)
1. sfc.sobjectPlural (true:add pluralLabel, false:nothing to do, default=false)



# sample

[SampleSFDCProject](https://github.com/hiradimir/ForceComSample "sample project")

## dir-design

TBD I'm re-thinking

* build.xml
* lib/ 
 * ant-salesforce-ci.jar
* src/
 * classes
 * pages
 * triggers


## build.xml

```xml
<project name="Sample usage of Salesforce-CI Ant tasks" default="deployForCI" basedir="." xmlns:sfc="antlib:com.hiradimir.sforce.ci">

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

# Sample Jenkins ScreenShot

## Jenkins-Top
![jenkins-top.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/jenkins-top.png) 
## Test-Result
![test-failure-top.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/test-failure-top.png) 
## Test-Failure-Detail
![test-failure-detail.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/test-failure-detail.png) 
## Coverage-Top
![coverage-top.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/coverage-top.png) 
## Coverage-Classses(package)
![coverage-classes.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/coverage-classes.png) 
## Coverage-Class(sample Util.cls)
![coverage-class-util.png](https://raw.githubusercontent.com/wiki/hiradimir/ant-salesforce-ci/images/coverage-class-util.png) 




   Copyright 2012-2016 hiradimir

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
