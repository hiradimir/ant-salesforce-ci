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

  import java.io.{ File, PrintWriter }

  def xmlWriter(filePath: String, elem: Elem): Unit = xmlWriter(new File(filePath), elem)
  def xmlWriter(filePath: File, elem: Elem): Unit = {
    withPrintWriter(
      filePath,
      pw => {
        pw.println("""<?xml version="1.0" encoding="UTF-8"?>""")
        pw.println((new scala.xml.PrettyPrinter(120, 4)).format(elem))
      })
  }
  def withPrintWriter(file: File, op: PrintWriter => Unit) {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
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
    val rtr = deployStatus.getRunTestResult

    val allTestCase = rtr.getSuccesses.map(scs => {
      ("run", scs.getTime, scs.getName, scs.getMethodName, "", "", "")
    }) ++ rtr.getFailures.map(flr => {
      ("fail", flr.getTime, flr.getName, flr.getMethodName, flr.getMessage, flr.getType, flr.getStackTrace)
    })

    val elem = <testsuites name="AllTests" tests={
      allTestCase.size + ""
    } failures={
      allTestCase.filter(_._1 == "fail").size + ""
    } time={
      allTestCase.map(_._2).sum.asInstanceOf[Float] / 1000 + ""
    }>
                 {
                   allTestCase.groupBy(_._3).map(g => {
                     <testsuite name={
                       g._1
                     } tests={
                       g._2.size + ""
                     } failures={
                       g._2.filter(_._1 == "fail").size + ""
                     } time={
                       g._2.map(_._2).sum.asInstanceOf[Float] / 1000 + ""
                     }>
                       {
                         g._2.groupBy(_._4).map(g => {
                           val (name, testcases) = g
                           val testcase = testcases.first
                           <testcase name={
                             testcase._4
                           } status={
                             testcase._1
                           } classname={
                             testcase._3
                           } time={
                             testcase._2.asInstanceOf[Float] / 1000 + ""
                           }>
                             {
                               g._2.filter(_._1 == "fail").map(failure => {
                                 <failure message={ failure._5 } type={ failure._6 }>
                                   { failure._7 }
                                 </failure>
                               })
                             }
                           </testcase>
                         })
                       }
                     </testsuite>
                   })
                 }
               </testsuites>;

    val testResultFile = this.getProject().getProperty("sfc.testResultFile")
    xmlWriter(
      if (testResultFile != null) {
        "test-result.xml"
      } else {
        testResultFile
      }, elem)

    super.handleResponse(metadataConnection, response);

  }
}


