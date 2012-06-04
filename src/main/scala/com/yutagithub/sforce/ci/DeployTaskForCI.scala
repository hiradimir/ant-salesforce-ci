package com.yutagithub.sforce.ci

import com.salesforce.ant.DeployTask
import com.sforce.soap.metadata._
import scala.xml.Elem

case class DeployTaskForCI extends DeployTask {

  override def execute() = {
    // for safety
    this.setCheckonly(true);
    this.setRunAllTests(true);
    this.setRollbackOnError(true);
    // for all deploy
    this.setAutoUpdatePackage(true);
    //if (this.getProject().getProperty("sfc.sobjectPlural") == true.toString) {
    if (sobjectPlural == true.toString) {
      val dir = new java.io.File(getFileForPath(deployRootCI), "objects")
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
  var sobjectPlural: String = false.toString;
  def setSobjectPlural(sobjectPlural: String){
    this.sobjectPlural = sobjectPlural;
  }

  var coverageResultFile: String = null;
  def setCoverageResultFile(coverageResultFile: String){
    this.coverageResultFile = coverageResultFile;
  }

  var testResultFile: String = null;
  def setTestResultFile(testResultFile: String){
    this.testResultFile = testResultFile;
  }

  /**
   * for allRunTests
   */
  override def getRunTests(): Array[String] = Array()

  override def handleResponse(metadataConnection: MetadataConnection, response: AsyncResult): Unit = {
    val debugHeader = new DebuggingHeader_element();
    debugHeader.setDebugLevel(
      if (this.getLogType != null) {
        LogType.valueOf(this.getLogType)
      } else {
        LogType.None
      });
    metadataConnection.__setDebuggingHeader(debugHeader);
    val deployResult = metadataConnection.checkDeployStatus(response.getId());

    xml.JUnitXmlWriter.saveTestResult(
      if (testResultFile == null) {
        "target/sforceci/test-result.xml"
      } else {
        testResultFile
      }, deployResult)

    xml.CoberturaXmlWriter.saveCoverageResult(
      if (coverageResultFile == null) {
        "target/sforceci/coverage.xml"
      } else {
        coverageResultFile
      }, deployResult)

    if (deployResult.getMessages.exists(!_.isSuccess)) {
      super.handleResponse(metadataConnection, response);
    }

  }
}


