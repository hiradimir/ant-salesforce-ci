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

    super.execute();
  }

  /**
   * for allRunTests
   */
  override def getRunTests(): Array[String] = Array()

  override def handleResponse(metadataConnection: MetadataConnection, response: AsyncResult) = {
    val debugHeader = new DebuggingHeader_element();
    debugHeader.setDebugLevel(
      if (this.getLogType != null) {
        LogType.valueOf(this.getLogType)
      } else {
        LogType.None
      });
    metadataConnection.__setDebuggingHeader(debugHeader);
    val deployStatus = metadataConnection.checkDeployStatus(response.getId());
    
    val testResultFile = this.getProject().getProperty("sfc.testResultFile")
    xml.JUnitXmlWriter.saveTestResult(
      if (testResultFile == null) {
        "target/sforceci/test-result.xml"
      } else {
        testResultFile
      }, deployStatus)

    super.handleResponse(metadataConnection, response);

  }
}


