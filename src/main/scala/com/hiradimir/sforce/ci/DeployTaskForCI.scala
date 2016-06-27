package com.hiradimir.sforce.ci

import com.salesforce.ant.DeployTask
import com.sforce.soap.metadata._
import scala.xml.Elem
import com.salesforce.ant.SFDCMDAPIAntTask.StatusResult

class DeployTaskForCI extends DeployTask {

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

  /**
   * for allRunTests
   */
  override def getRunTests(): Array[String] = Array()

//  override def handleResponse(metadataConnection: MetadataConnection, result: StatusResult): Unit = {
//    val debugHeader = new DebuggingHeader_element();
//    debugHeader.setDebugLevel(
//      if (this.getLogType != null) {
//        LogType.valueOf(this.getLogType)
//      } else {
//        LogType.None
//      });
//    metadataConnection.__setDebuggingHeader(debugHeader);
//    val deployResult = metadataConnection.checkDeployStatus(result.getId(), true);
//
//    xml.JUnitXmlWriter.saveTestResult(
//      testResultFile, deployResult)
//
//    xml.CoberturaXmlWriter.saveCoverageResult(
//      coverageResultFile, deployResult)
//
////    if (deployResult.getMessages.exists(!_.isSuccess)) {
////      super.handleResponse(metadataConnection, result);
////    }
//
//  }
}


