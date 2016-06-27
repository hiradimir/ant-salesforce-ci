package com.hiradimir.sforce.ci.xml

import scala.Array.canBuildFrom
import scala.xml.dtd.EMPTY

object JUnitXmlWriter extends XmlWriter{

  case class TestStatus(status: String, time: Double, name: String, methodName: String, message: String, errorType: String, stackTrace: String) {
    lazy val ms = { time / 1000.0f }
  }

  def saveTestResult(filePath: String, deployResult: com.sforce.soap.metadata.DeployResult) = {

    val rtr = deployResult.getDetails.getRunTestResult

    val allTestCase = rtr.getSuccesses.map(scs => {
      TestStatus("run", scs.getTime, scs.getName, scs.getMethodName, "", "", "")
    }) ++ rtr.getFailures.map(flr => {
      TestStatus("fail", flr.getTime, flr.getName, flr.getMethodName, flr.getMessage, flr.getType, flr.getStackTrace)
    })

    val elem = <testsuites name="AllTests" tests={
      allTestCase.size + ""
    } failures={
      allTestCase.filter(_.status == "fail").size + ""
    } time={
      allTestCase.map(_.ms).sum + ""
    }>
                 {
                   allTestCase.groupBy(_.name).map {
                     case (name, testsuites) => {
                       <testsuite name={
                         name
                       } tests={
                         testsuites.size + ""
                       } failures={
                         testsuites.filter(_.status == "fail").size + ""
                       } time={
                         testsuites.map(_.ms).sum + ""
                       }>
                         {
                           testsuites.groupBy(_.methodName).map(g => {
                             g._2.headOption match {
                               case Some(testcase) => {
                                 <testcase name={
                                   testcase.methodName
                                 } status={
                                   testcase.status
                                 } classname={
                                   testcase.name
                                 } time={
                                   testcase.ms + ""
                                 }>
                                   {
                                     g._2.filter(_.status == "fail").map(failure => {
                                       <failure message={ failure.message } type={ failure.errorType }>
                                         { failure.stackTrace }
                                       </failure>
                                     })
                                   }
                                 </testcase>
                               }
                               case None => { EMPTY }
                             }
                           })
                         }
                       </testsuite>
                     }
                   }
                 }
               </testsuites>;

    writeXml(filePath, elem)
  }

}