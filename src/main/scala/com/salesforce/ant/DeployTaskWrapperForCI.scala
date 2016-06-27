package com.salesforce.ant

import com.sforce.ws.ConnectionException
import com.sforce.soap.metadata._
import com.sforce.ws.ConnectionException
import com.sforce.ws.ConnectionException
import com.sforce.soap.metadata.DebuggingHeader_element
import com.hiradimir.sforce.ci.FileUtils
import com.hiradimir.sforce.ci.xml

class DeployTaskWrapperForCI extends DeployTask {
  
  override def execute() = {
    
    // for safety
    this.setCheckonly(true);
    this.setRunAllTests(true);
    this.setRollbackOnError(true);
    // for all deploy
    this.setAutoUpdatePackage(true);
    
    var presentDir = new java.io.File(presentDirectory)
    if(presentDir.exists){
      presentDir.delete;
    }

    FileUtils.copyDirectry(new java.io.File(deployRootCI), presentDir)
    super.setDeployRoot(presentDirectory);

    if (sobjectPlural == true.toString) {
      val dir = new java.io.File(getFileForPath(presentDirectory), "objects")
      println(dir)
      dir.listFiles().filter(_.getName.endsWith(".object")).foreach(file => {
        xml.PluralLabelCombine.combine(file.getCanonicalPath)
      })
    }
    super.execute();
  }
  
  
  var deployRootCI: String = ""
  override def setDeployRoot(deployRoot: String) = {
    this.deployRootCI = deployRoot
    super.setDeployRoot(deployRoot)
  }
  var presentDirectory: String = "target/sforceci/src"
  def setPresentDirectory(presentDirectory: String) = {
    this.presentDirectory = presentDirectory
  }
  var sobjectPlural: String = false.toString;
  def setSobjectPlural(sobjectPlural: String) {
    this.sobjectPlural = sobjectPlural;
  }

  var coverageResultFile: String = "target/sforceci/coverage.xml";
  def setCoverageResultFile(coverageResultFile: String) {
    this.coverageResultFile = coverageResultFile;
  }

  var testResultFile: String = "target/sforceci/test-result.xml";
  def setTestResultFile(testResultFile: String) {
    this.testResultFile = testResultFile;
  }
  
  @throws(classOf[ConnectionException])
  override def handleResponse(metadataConnection: MetadataConnection, result: SFDCMDAPIAntTask.StatusResult): Unit = {
    
    val deployResult = metadataConnection.checkDeployStatus(result.getId(), true);

    xml.JUnitXmlWriter.saveTestResult(
      testResultFile, deployResult)

    xml.CoberturaXmlWriter.saveCoverageResult(
      coverageResultFile, deployResult)

    super.handleResponse(metadataConnection, result);

  }
}

