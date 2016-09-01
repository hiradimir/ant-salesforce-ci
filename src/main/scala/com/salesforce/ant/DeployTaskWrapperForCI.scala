package com.salesforce.ant

import com.sforce.ws.ConnectionException
import com.sforce.soap.metadata._
import com.sforce.ws.ConnectionException
import com.sforce.ws.ConnectionException
import com.sforce.soap.metadata.DebuggingHeader_element
import com.hiradimir.sforce.ci.FileUtils
import com.hiradimir.sforce.ci.xml
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

trait DeployTaskWrapperForCI extends DeployTask {
  
  override def execute() = {
    
    var presentDir = new java.io.File(presentDirectory)
    
    def delete(file: File) {
      if (file.isDirectory) 
        Option(file.listFiles).map(_.toList).getOrElse(Nil).foreach(delete(_))
      file.delete
    }
    
    delete(presentDir);

    FileUtils.copyDirectry(getFileForPath(deployRootCI), presentDir)
    super.setDeployRoot(presentDir.getAbsolutePath);

    if (true.toString.equalsIgnoreCase(sobjectPlural)) {
      val dir = new java.io.File(presentDir, "objects")
      println(dir.getAbsolutePath)
      if (dir.exists()) {
        dir.listFiles().filter(_.getName.endsWith(".object")).foreach(file => {
          xml.PluralLabelCombine.combine(file.getCanonicalPath)
        })
      }
    }
    super.execute();
  }
  
  
  var deployRootCI: String = ""
  override def setDeployRoot(deployRoot: String) = {
    this.deployRootCI = deployRoot
    super.setDeployRoot(deployRoot)
  }
  private var _presentDirectory: String = _
  def presentDirectory = {
    if(null == _presentDirectory){
      getFileForPath("target/sforceci/src").getAbsolutePath
    } else {
      _presentDirectory
    }
  }
  def setPresentDirectory(presentDirectory: String) = {
    this._presentDirectory = presentDirectory
  }
  var sobjectPlural: String = false.toString;
  def setSobjectPlural(sobjectPlural: String) {
    this.sobjectPlural = sobjectPlural;
  }

  private var _coverageResultFile: String = _
  def coverageResultFile = {
    if(null == _coverageResultFile) {
      getFileForPath("target/sforceci/coverage.xml").getAbsolutePath;
    } else {
      _coverageResultFile
    }
  }
  def setCoverageResultFile(coverageResultFile: String) {
    this._coverageResultFile = coverageResultFile;
  }

  private var _testResultFile: String = _
  def testResultFile = {
    if(null == _testResultFile){
      getFileForPath("target/sforceci/test-result.xml").getAbsolutePath;
    } else {
      _testResultFile
    }
  }
  def setTestResultFile(testResultFile: String) {
    this._testResultFile = testResultFile;
  }

  var coverageReportClassNameFilter: String = ".*";
  def setCoverageReportClassNameFilter(coverageReportClassNameFilter: String) {
    this.coverageReportClassNameFilter = coverageReportClassNameFilter;
  }
  
  var noErrorOnTestFail: Boolean = false;
  def setNoErrorOnTestFail(noErrorOnTestFail: Boolean) {
    this.noErrorOnTestFail = noErrorOnTestFail;
  }
  
  var noErrorOnCoveargeWarning: Boolean = false;
  def setNoErrorOnCoveargeWarning(noErrorOnCoveargeWarning: Boolean) {
    this.noErrorOnCoveargeWarning = noErrorOnCoveargeWarning;
  }
  
  @throws(classOf[ConnectionException])
  override def handleResponse(metadataConnection: MetadataConnection, result: SFDCMDAPIAntTask.StatusResult): Unit = {
    
    val deployResult = metadataConnection.checkDeployStatus(result.getId(), true);

    xml.JUnitXmlWriter.saveTestResult(
      testResultFile, deployResult)

    xml.CoberturaXmlWriter.saveCoverageResult(
      coverageResultFile, deployResult, coverageReportClassNameFilter, getFileForPath(deployRootCI).getAbsolutePath)

    try{
      super.handleResponse(metadataConnection, result);
    }catch {
      case e:Exception => 
        if (deployResult.getDetails.getComponentFailures.length > 0 ){
          throw e;
        }
        if (!noErrorOnTestFail && deployResult.getDetails.getRunTestResult.getNumFailures != 0 ){
          throw e;
        }
        if (!noErrorOnCoveargeWarning && deployResult.getDetails.getRunTestResult.getCodeCoverageWarnings.length > 0 ){
          throw e;
        }
        println(e);
    }

  }
}

